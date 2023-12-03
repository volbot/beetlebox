package volbot.beetlebox.entity.projectile;

import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import volbot.beetlebox.data.damage.BeetleDamageTypes;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.item.equipment.BeetlepackItem;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleProjectileEntity extends PersistentProjectileEntity implements FlyingItemEntity {

	public ContainedEntity entity;
	public boolean landed = false;
	public boolean unSynced = true;

	public BeetleProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public BeetleProjectileEntity(World world) {
		super(BeetleRegistry.BEETLE_PROJECTILE, world);
	}

	public BeetleProjectileEntity(World world, double x, double y, double z) {
		super(BeetleRegistry.BEETLE_PROJECTILE, x, y, z, world);
	}

	public BeetleProjectileEntity(World world, LivingEntity owner, ItemStack jar_stack) {
		super(BeetleRegistry.BEETLE_PROJECTILE, owner, world);
		this.entity = BeetleJarItem.getContained(jar_stack);
		this.entity.getEntityData().putBoolean("Flying", true);
		this.setNoGravity(true);
		this.sendPacket();
	}

	public BeetleProjectileEntity(World world, LivingEntity owner, LivingEntity entity) {
		super(BeetleRegistry.BEETLE_PROJECTILE, owner, world);
		NbtCompound nbt = new NbtCompound();
		entity.writeCustomDataToNbt(nbt);
		entity.writeNbt(nbt);
		String custom_name = "";
		if (entity.hasCustomName()) {
			nbt.putString("EntityName", entity.getCustomName().getString());
		}
		this.setPosition(entity.getPos());
		this.entity = new ContainedEntity(EntityType.getId(entity.getType()).toString(), nbt, custom_name);
		this.entity.getEntityData().putBoolean("Flying", true);
		this.setNoGravity(true);
		this.sendPacket();
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		if (this.world.isClient || this.shake > 0) {
			return;
		}
		if (this.tryPickup(player)) {
			boolean jar_found = false;
			ItemStack bp = BeetlepackItem.getBeetlepackOnPlayer(player);
			if (!bp.isEmpty()) {
				DefaultedList<ItemStack> bp_inv = BeetlepackItem.readInventory(bp);
				for (ItemStack stack : bp_inv) {
					if (stack.getItem() instanceof BeetleJarItem) {
						if (!stack.hasNbt() || !stack.getNbt().contains("EntityType")) {
							stack.decrement(1);
							jar_found = true;
							BeetlepackItem.writeInventory(bp, bp_inv);
							break;
						}
					}
				}
			}
			if (!jar_found) {
				for (ItemStack stack : player.getInventory().main) {
					if (stack.getItem() instanceof BeetleJarItem) {
						if (!stack.hasNbt() || !stack.getNbt().contains("EntityType")) {
							stack.decrement(1);
							jar_found = true;
							break;
						}
					}
				}
			}
			if (!jar_found) {
				NbtCompound nbt = entity.getEntityData();
				EntityType<?> entityType = EntityType.get(entity.getContainedId()).orElse(null);
				LivingEntity temp = (LivingEntity) entityType.create((ServerWorld) world, null, null,
						this.getBlockPos(), SpawnReason.SPAWN_EGG, false, false);
				temp.readNbt(nbt);
				temp.readCustomDataFromNbt(nbt);
				if (!entity.CustomName().isEmpty()) {
					temp.setCustomName(Text.of(entity.CustomName()));
				}
				temp.refreshPositionAndAngles(this.getBlockPos(), this.getBodyYaw(), this.getPitch());
				this.setPosition(this.getPos());
				temp.setVelocity(this.getVelocity());
				if (world.spawnEntity(temp)) {
					this.discard();
					world.emitGameEvent(temp, GameEvent.ENTITY_PLACE, this.getBlockPos());
				}
			} else {
				ItemEntity dropped = new ItemEntity(this.world, this.getX(), this.getY() + (double) 0.1f, this.getZ(),
						this.asItemStack());
				dropped.setPickupDelay(0);
				dropped.setOwner(player.getUuid());
				dropped.setNoGravity(true);
				dropped.setVelocity(player.getPos().subtract(dropped.getPos()).normalize().multiply(0.5));
				if (this.world.spawnEntity(dropped)) {
					this.discard();
				}
			}

		}
	}

	protected boolean tryPickup(PlayerEntity player) {
		if (!this.landed) {
			return false;
		}
		switch (this.pickupType) {
		case ALLOWED: {
			return true;
		}
		case CREATIVE_ONLY: {
			return player.getAbilities().creativeMode;
		}
		default: {
			return false;
		}
		}
	}

	@Override
	protected ItemStack asItemStack() {
		ItemStack jar_stack = ItemRegistry.BEETLE_JAR.getDefaultStack();
		if (entity == null) {
			return jar_stack;
		}
		if (entity != null) {
			LivingEntity e = (LivingEntity) ((EntityType.get(entity.contained_id).orElse(null)
					.create(this.getWorld())));
			BeetleJarItem<?> jar_item = (BeetleJarItem<?>) jar_stack.getItem();
			NbtCompound jar_nbt = jar_stack.getOrCreateNbt();
			if (!jar_item.canStore(e)) {
				return jar_stack;
			}
			NbtCompound new_nbt = jar_nbt.copy();
			new_nbt.putString("EntityType", entity.contained_id);
			String custom_name = entity.custom_name;
			if (!custom_name.isEmpty()) {
				new_nbt.putString("EntityName", custom_name);
			}
			new_nbt.put("EntityTag", entity.entity_data);
			ItemStack jar_new = jar_stack.getItem().getDefaultStack();
			jar_new.setNbt(new_nbt);
			return jar_new;
		}
		return ItemRegistry.BEETLE_JAR.getDefaultStack();
	}

	@Override
	public ItemStack getStack() {
		return asItemStack();
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.world.isClient) {
			this.random.skip(1);
			this.setVelocity(this.getVelocity().addRandom(this.random, 0.025f));
			if (landed) {
				Entity owner = this.getOwner();
				if (owner != null && owner.getUuid().equals(this.entity.getEntityData().getUuid("Owner"))) {
					this.random.skip(1);
					this.setVelocity(
							this.getVelocity().add(owner.getPos().subtract(this.getPos()).add(0, 1, 0))
									.normalize().multiply(0.5).addRandom(this.random, 0.075f));
				}
			}
			if (unSynced) {
				if (this.entity == null) {
					this.unSynced = false;
				} else {
					this.sendPacket();
				}
			}
		}

	}

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		this.onHitSetup();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		// super.onBlockHit(blockHitResult);
		this.onHitSetup();
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (this.landed) {
			return;
		}
		DamageSource damageSource;
		Entity entity2;
		Entity entity = entityHitResult.getEntity();
		float f = (float) this.getVelocity().length();
		int i = MathHelper.ceil(MathHelper.clamp((double) f * this.getDamage(), 0.0, 2.147483647E9));
		if (this.isCritical()) {
			long l = this.random.nextInt(i / 2 + 2);
			i = (int) Math.min(l + (long) i, Integer.MAX_VALUE);
		}
		if ((entity2 = this.getOwner()) == null) {
			damageSource = BeetleDamageTypes.of(world, BeetleDamageTypes.BEETLE_PROJ);
		} else {
			damageSource = BeetleDamageTypes.of(world, BeetleDamageTypes.BEETLE_PROJ, entity2);
		}
		boolean bl = entity.getType() == EntityType.ENDERMAN;
		if (this.isOnFire() && !bl) {
			entity.setOnFireFor(5);
		}
		if (entity.damage(damageSource, i)) {
			if (bl) {
				return;
			}
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				if (!this.world.isClient && entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
				}
				this.onHit(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity
						&& entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity) entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(
							GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, GameStateChangeS2CPacket.DEMO_OPEN_SCREEN));
				}
			}
			this.playSound(this.getSound(), 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
		} else {
			this.onHitSetup();
		}
	}

	private void onHitSetup() {
		Entity entity2 = this.getOwner();
		this.setVelocity(this.getVelocity().multiply(-0.1));
		this.setYaw(this.getYaw() + 180.0f);
		this.prevYaw += 180.0f;
		if (!this.getWorld().isClient) {
			this.landed = true;
			this.sendPacket();
		}
	}

	public void sendPacket() {
		World world = getEntityWorld();
		if (this.world.getPlayers().size() == 0) {
			this.unSynced = true;
			return;
		}
		if (!world.isClient && entity != null && this.getOwner() instanceof PlayerEntity) {
			ServerPlayerEntity p = (ServerPlayerEntity) this.getOwner();
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeBoolean(landed);
			buf.writeString(entity.getContainedId());
			buf.writeNbt(entity.getEntityData());
			buf.writeString(entity.CustomName());
			UUID entity_id = this.getUuid();
			buf.writeUuid(entity_id);
			ServerPlayNetworking.send(p, new Identifier("beetlebox", "beetle_proj_packet"), buf);
		}
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putBoolean("Landed", this.landed);
		nbt.putBoolean("UnSynced", this.unSynced);
		if (entity != null) {
			nbt.putString("EntityType", entity.contained_id);
			nbt.putString("EntityName", entity.custom_name);
			if (entity.entity_data != null) {
				nbt.put("EntityTag", entity.entity_data);
			}
		}
		return nbt;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.landed = nbt.getBoolean("Landed");
		this.unSynced = nbt.getBoolean("UnSynced");
		if (nbt.contains("EntityType")) {
			entity = new ContainedEntity(nbt.getString("EntityType"), nbt.getCompound("EntityTag"),
					nbt.getString("EntityName"));
		} else {
			entity = null;
		}
	}

}
