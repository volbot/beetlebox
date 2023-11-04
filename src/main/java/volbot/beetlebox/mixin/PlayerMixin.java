package volbot.beetlebox.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.compat.trinkets.BeetlepackTrinketRenderer;
import volbot.beetlebox.item.equipment.BeetleArmorAbilities;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.BeetlepackItem;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "attack", at = @At(value = "RETURN"))
	private void attack(Entity target, CallbackInfo info) {
		ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);
		if (helmet.getItem() instanceof BeetleArmorItem) {
			Multimap<EntityAttribute, EntityAttributeModifier> attributes = this.getMainHandStack().getItem()
					.getAttributeModifiers(EquipmentSlot.MAINHAND);
			Collection<EntityAttributeModifier> attackModifiers = attributes
					.get(EntityAttributes.GENERIC_ATTACK_DAMAGE);

			if (!attackModifiers.isEmpty() && target instanceof LivingEntity) {
				BeetleArmorAbilities.helmetAttack((LivingEntity) (Object) this, (LivingEntity) target, helmet);
			}
		}
	}

	@Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE"), cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);
		if ((boots.getItem() instanceof BeetleArmorItem)
				&& (boots.getOrCreateNbt().contains("beetle_boots_falldamage"))) {
			if (source.isOf(DamageTypes.FALL) || source.isOf(DamageTypes.FLY_INTO_WALL)) {
				info.setReturnValue(true);
			}
		}
	}

	private static void deployBeetles(PlayerEntity user) {
		ItemStack beetlepack = ItemStack.EMPTY;
		if (user.getInventory().containsAny(Set.of(ItemRegistry.BEETLEPACK))) {
			beetlepack = (user.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId()));
		}
		if (!(beetlepack.getItem() instanceof BeetlepackItem)) {
			if (FabricLoader.getInstance().isModLoaded("trinkets")) {
				ItemStack back_stack = BeetlepackTrinketRenderer.getBackStack(user);
				if (back_stack.getItem() instanceof BeetlepackItem) {
					beetlepack = back_stack;
				}
			}
		}
		if (beetlepack.isOf(ItemRegistry.BEETLEPACK)) {
			NbtCompound beetlepack_nbt = beetlepack.getOrCreateNbt();
			if (!beetlepack_nbt.contains("FlightSpawn")) {
				DefaultedList<ItemStack> beetlepack_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
				NbtCompound beetlepack_inv_nbt = beetlepack_nbt.getCompound("Inventory");
				Inventories.readNbt(beetlepack_inv_nbt, beetlepack_inv);
				ArrayList<UUID> spawned_uuids = new ArrayList<UUID>();
				World world = user.getWorld();
				for (ItemStack jar : beetlepack_inv) {
					NbtCompound jar_nbt = jar.getOrCreateNbt();
					if (jar_nbt.contains("EntityTag") && jar_nbt.getCompound("EntityTag").containsUuid("Owner")) {
						EntityType<?> entityType2 = EntityType.get(jar_nbt.getString("EntityType")).orElse(null);
						if (entityType2 == null) {
							continue;
						}
						LivingEntity temp = (LivingEntity) entityType2.create(world);
						temp.readNbt(jar_nbt.getCompound("EntityTag"));
						temp.readCustomDataFromNbt(jar_nbt.getCompound("EntityTag"));
						if (jar_nbt.contains("EntityName")) {
							temp.setCustomName(Text.of(jar_nbt.getString("EntityName")));
						} else {
							temp.setCustomName(null);
						}
						BlockPos blockPos2 = user.getBlockPos();
						temp.refreshPositionAfterTeleport(blockPos2.getX() + 0.5, blockPos2.getY(),
								blockPos2.getZ() + 0.5);
						if (world.spawnEntity(temp)) {
							jar.removeSubNbt("EntityTag");
							jar.removeSubNbt("EntityType");
							jar.removeSubNbt("EntityName");
							spawned_uuids.add(temp.getUuid());
						}
					}
				}
				if (!spawned_uuids.isEmpty()) {
					NbtCompound uuid_nbt = new NbtCompound();
					int i = 0;
					for (UUID uuid : spawned_uuids) {
						uuid_nbt.putUuid("FlightSpawn" + i, uuid);
						i++;
					}
					beetlepack_nbt.put("FlightSpawn", uuid_nbt);
				}
				NbtCompound inv_nbt = new NbtCompound();
				Inventories.writeNbt(inv_nbt, beetlepack_inv);
				beetlepack_nbt.put("Inventory", inv_nbt);
				beetlepack.setNbt(beetlepack_nbt);
			}
		}
	}

	private static void recallBeetles(PlayerEntity user) {
		ItemStack beetlepack = ItemStack.EMPTY;
		if (user.getInventory().containsAny(Set.of(ItemRegistry.BEETLEPACK))) {
			beetlepack = user.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId());
		}
		if (!(beetlepack.getItem() instanceof BeetlepackItem)) {
			if (FabricLoader.getInstance().isModLoaded("trinkets")) {
				ItemStack back_stack = BeetlepackTrinketRenderer.getBackStack(user);
				if (back_stack.getItem() instanceof BeetlepackItem) {
					beetlepack = back_stack;
				}
			}
		}
		if (beetlepack.isOf(ItemRegistry.BEETLEPACK)) {
			NbtCompound beetlepack_nbt = beetlepack.getOrCreateNbt();
			if (beetlepack_nbt.contains("FlightSpawn")) {
				NbtCompound flight_spawn = beetlepack_nbt.getCompound("FlightSpawn");
				ArrayList<UUID> spawned_uuids = new ArrayList<UUID>();
				int i = 0;
				while (flight_spawn.contains("FlightSpawn" + i)) {
					spawned_uuids.add(flight_spawn.getUuid("FlightSpawn" + i));
					i++;
				}
				DefaultedList<ItemStack> beetlepack_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
				Inventories.readNbt(beetlepack_nbt.getCompound("Inventory"), beetlepack_inv);
				i = -1;
				ItemStack jar_stack = null;
				for (UUID uuid : spawned_uuids) {
					Entity entity = user.getWorld().getEntityLookup().get(uuid);
					if (entity == null) {
						continue;
					}
					int j;
					for (j = i + 1; i < beetlepack_inv.size(); j++) {
						ItemStack itemStack = beetlepack_inv.get(j);
						if (itemStack.getItem() instanceof BeetleJarItem<?>) {
							NbtCompound nbt = itemStack.getOrCreateNbt();
							BeetleJarItem<?> item = (BeetleJarItem<?>) itemStack.getItem();
							if (nbt.contains("EntityType") || !item.canStore(entity)) {
								jar_stack = null;
								continue;
							}
							if (!nbt.contains("EntityType") && item.canStore(entity)) {
								jar_stack = itemStack;
								i = j;
								break;
							}
						}
						jar_stack = null;
					}
					if (jar_stack != null) {
						NbtCompound nbt = new NbtCompound();
						NbtCompound tag = new NbtCompound();
						tag = entity.writeNbt(tag);
						if (entity instanceof LivingEntity) {
							((LivingEntity) (entity)).writeCustomDataToNbt(tag);
						}
						nbt.put("EntityTag", tag);
						Text custom_name = entity.getCustomName();
						if (custom_name != null && !custom_name.getString().isEmpty()) {
							nbt.putString("EntityName", custom_name.getString());
						}
						nbt.putString("EntityType", EntityType.getId(entity.getType()).toString());
						ItemStack jar_new = jar_stack.getItem().getDefaultStack();
						jar_new.setNbt(nbt);
						beetlepack_inv.set(i, jar_new);
						entity.remove(RemovalReason.CHANGED_DIMENSION);
					} else {
						break;
					}
					if (j == beetlepack_inv.size()) {
						break;
					}
				}
				beetlepack_nbt.remove("FlightSpawn");
				NbtCompound inv_nbt = new NbtCompound();
				Inventories.writeNbt(inv_nbt, beetlepack_inv);
				beetlepack_nbt.put("Inventory", inv_nbt);
				beetlepack.setNbt(beetlepack_nbt);
			}
		}
	}

	@Override
	protected void setFlag(int index, boolean value) {
		super.setFlag(index, value);
		if (index == Entity.FALL_FLYING_FLAG_INDEX) {
			if (value) {
				deployBeetles(((PlayerEntity) (Object) this));
			} else {
				recallBeetles(((PlayerEntity) (Object) this));
			}
		}
	}

	@Override
	protected void applyMovementEffects(BlockPos pos) {
		if (!this.getEquippedStack(EquipmentSlot.FEET).getOrCreateNbt().contains("beetle_boots_speed")) {
			this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
					.removeModifier(BeetleArmorItem.speed_boost_attribute);
		} else {
			EntityAttributeInstance instance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			if (!instance.hasModifier(BeetleArmorItem.speed_boost_attribute)) {
				instance.addTemporaryModifier(BeetleArmorItem.speed_boost_attribute);
			}
		}
		super.applyMovementEffects(pos);
	}

}