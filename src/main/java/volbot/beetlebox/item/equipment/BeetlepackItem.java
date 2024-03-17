package volbot.beetlebox.item.equipment;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import volbot.beetlebox.client.render.gui.BeetlepackScreenHandler;
import volbot.beetlebox.compat.trinkets.BeetlepackTrinket;
import volbot.beetlebox.compat.trinkets.BeetlepackTrinketRenderer;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.projectile.BeetleProjectileEntity;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.LarvaJarItem;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetlepackItem extends ArmorItem implements ExtendedScreenHandlerFactory {

	public BeetlepackItem(Settings settings) {
		super(ArmorMaterials.LEATHER, Type.CHESTPLATE, settings.maxDamage(-1));
		if (FabricLoader.getInstance().isModLoaded("trinkets")) {
			TrinketsApi.registerTrinket(this, (BeetlepackTrinket) this);
		}
	}

	public void inventoryTick(ItemStack bp, World world, Entity entity, int slot, boolean selected) {
		DefaultedList<ItemStack> bp_inv = readInventory(bp);
		for (ItemStack stack : bp_inv) {
			if (!stack.isEmpty())
				if (stack.getItem() instanceof LarvaJarItem) {
					LarvaJarItem.incrementJarTime(stack, 2);
				}
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (user.isSneaking()) {
			stack.getOrCreateNbt().putBoolean("Open", true);
			user.openHandledScreen(this);
			return TypedActionResult.consume(stack);
		} else {
			if (FabricLoader.getInstance().isModLoaded("trinkets")) {
				if(TrinketItem.equipItem(user, stack)) {
					return TypedActionResult.success(stack);
				};
			}
			return super.use(world, user, hand);
		}
	}

	public static void deployBeetles(PlayerEntity user, BeetleDeployReason reason) {
		World world = user.getWorld();
		if (world.isClient) {
			return;
		}
		ItemStack bp = getBeetlepackOnPlayer(user);
		if (bp.isOf(ItemRegistry.BEETLEPACK)) {
			NbtCompound bp_nbt = bp.getOrCreateNbt();
			if (!bp_nbt.contains(reason.toString() + "Spawn")) {
				DefaultedList<ItemStack> bp_inv = BeetlepackItem.readInventory(bp);
				ArrayList<UUID> spawned_uuids = new ArrayList<UUID>();
				for (ItemStack jar : bp_inv) {
					if (jar.getItem() instanceof BeetleJarItem) {
						switch (reason) {
						case COMBAT:
							if (bp_nbt.getBoolean("ToggleAttack")) {
								return;
							}
							NbtCompound entity_data = jar.getOrCreateNbt().getCompound("EntityTag");
							switch (BeetleEntity.BeetleClass.values()[entity_data.getInt("Class")]) {
							case INFANTRY:
								Optional<LivingEntity> opt = BeetleJarItem.trySpawnFromJar(jar, user.getBlockPos(),
										world, user);
								if (opt.isPresent()) {
									LivingEntity e = opt.get();
									if (e instanceof TameableEntity && ((TameableEntity) e).isOwner(user)) {
										spawned_uuids.add(e.getUuid());
									}
								}
								break;
							case PROJECTILE:
								// fire beetle as projectile
								BeetleProjectileEntity proj = new BeetleProjectileEntity(world, user, jar);
								Entity target = user.getAttacker();
								proj.setVelocity(target.getBoundingBox().getCenter().subtract(user.getEyePos())
										.normalize().multiply(0.5f));
								proj.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
								if (world.spawnEntity(proj)) {
									jar.removeSubNbt("EntityType");
									jar.removeSubNbt("EntityTag");
									jar.removeSubNbt("EntityName");
								}
								break;
							default:
								break;
							}
							break;
						default:
							if (bp_nbt.getBoolean("ToggleFlight")) {
								return;
							}
							Optional<LivingEntity> opt = BeetleJarItem.trySpawnFromJar(jar, user.getBlockPos(), world,
									user);
							if (opt.isPresent()) {
								spawned_uuids.add(opt.get().getUuid());
							}
							break;
						}
					}
				}
				if (!spawned_uuids.isEmpty()) {
					NbtCompound uuid_nbt = new NbtCompound();
					int i = 0;
					for (UUID uuid : spawned_uuids) {
						uuid_nbt.putUuid(reason.toString() + "Spawn" + i, uuid);
						i++;
					}
					bp_nbt.put(reason.toString() + "Spawn", uuid_nbt);
				}
				writeInventory(bp, bp_inv);
			}
		}
	}

	public static void recallBeetles(PlayerEntity user) {
		ItemStack bp = BeetlepackItem.getBeetlepackOnPlayer(user);
		if (bp.isOf(ItemRegistry.BEETLEPACK)) {
			NbtCompound bp_nbt = bp.getOrCreateNbt();
			DefaultedList<ItemStack> bp_inv = readInventory(bp);
			World world = user.getWorld();
			for (BeetleDeployReason reason : BeetleDeployReason.values()) {
				if (bp_nbt.contains(reason.toString() + "Spawn")) {
					NbtCompound flight_spawn = bp_nbt.getCompound(reason.toString() + "Spawn");
					ArrayList<UUID> spawned_uuids = new ArrayList<UUID>();
					int i = 0;
					while (flight_spawn.contains(reason.toString() + "Spawn" + i)) {
						spawned_uuids.add(flight_spawn.getUuid(reason.toString() + "Spawn" + i));
						i++;
					}
					i = -1;
					for (UUID uuid : spawned_uuids) {
						LivingEntity entity = (LivingEntity) user.getWorld().getEntityLookup().get(uuid);
						if (entity == null) {
							continue;
						}
						BeetleProjectileEntity proj = new BeetleProjectileEntity(world, user, entity);
						proj.setVelocity(
								user.getBoundingBox().getCenter().subtract(proj.getPos()).normalize().multiply(0.5));
						proj.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
						proj.landed = true;
						world.spawnEntity(proj);
						entity.discard();
					}
				}
				bp_nbt.remove(reason.toString() + "Spawn");
			}
			writeInventory(bp, bp_inv);
		}
	}

	public static DefaultedList<ItemStack> readInventory(ItemStack stack) {
		DefaultedList<ItemStack> bp_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
		Inventories.readNbt(stack.getOrCreateNbt().getCompound("Inventory"), bp_inv);
		return bp_inv;
	}

	public static void writeInventory(ItemStack stack, DefaultedList<ItemStack> bp_inv) {
		NbtCompound inv_nbt = new NbtCompound();
		Inventories.writeNbt(inv_nbt, bp_inv);
		stack.getOrCreateNbt().put("Inventory", inv_nbt);
	}

	@Override
	public Text getDisplayName() {
		return Text.of("Beetlepack");
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new BeetlepackScreenHandler(syncId, playerInventory);
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

	}

	public static ItemStack getBeetlepackOnPlayer(PlayerEntity player) {
		ItemStack beetlepack = ItemStack.EMPTY;
		PlayerInventory inv = player.getInventory();
		ItemStack chest_stack = inv.getArmorStack(EquipmentSlot.CHEST.getEntitySlotId());
		if (chest_stack.isOf(ItemRegistry.BEETLEPACK)) {
			beetlepack = chest_stack;
		} else if (!(beetlepack.isOf(ItemRegistry.BEETLEPACK))) {
			if (FabricLoader.getInstance().isModLoaded("trinkets")) {
				ItemStack back_stack = BeetlepackTrinketRenderer.getBackStack(player);
				if (back_stack.isOf(ItemRegistry.BEETLEPACK)) {
					beetlepack = back_stack;
				}
			}
		}
		return beetlepack;
	}

	public enum BeetleDeployReason {
		FLIGHT, COMBAT
	}

}
