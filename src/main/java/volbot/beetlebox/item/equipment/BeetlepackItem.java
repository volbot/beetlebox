package volbot.beetlebox.item.equipment;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
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
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.entity.projectile.BeetleProjectileEntity;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.LarvaJarItem;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetlepackItem extends ArmorItem implements ExtendedScreenHandlerFactory {

	public BeetlepackItem(Settings settings) {
		super(ArmorMaterials.LEATHER, Type.CHESTPLATE, settings.maxDamage(0));
		if (FabricLoader.getInstance().isModLoaded("trinkets")) {
			TrinketsApi.registerTrinket(this, (BeetlepackTrinket) this);
		}
	}

	public void inventoryTick(ItemStack beetlepack, World world, Entity entity, int slot, boolean selected) {
		NbtCompound beetlepack_nbt = beetlepack.getOrCreateNbt();
		DefaultedList<ItemStack> beetlepack_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
		Inventories.readNbt(beetlepack_nbt.getCompound("Inventory"), beetlepack_inv);
		for (ItemStack stack : beetlepack_inv) {
			if (!stack.isEmpty())
				if (stack.getItem() instanceof LarvaJarItem) {
					LarvaJarItem.incrementJarTime(stack, 2);
				}
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			ItemStack stack = user.getStackInHand(hand);
			stack.getOrCreateNbt().putBoolean("Open", true);
			user.openHandledScreen(this);
			return TypedActionResult.consume(stack);
		} else {
			return this.equipAndSwap(this, world, user, hand);
		}
	}

	public static void deployBeetles(PlayerEntity user, BeetleDeployReason reason) {
		ItemStack beetlepack = BeetlepackItem.getBeetlepackOnPlayer(user);
		if (beetlepack.isOf(ItemRegistry.BEETLEPACK)) {
			NbtCompound beetlepack_nbt = beetlepack.getOrCreateNbt();
			if (!beetlepack_nbt.contains(reason.toString() + "Spawn")) {
				DefaultedList<ItemStack> beetlepack_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
				NbtCompound beetlepack_inv_nbt = beetlepack_nbt.getCompound("Inventory");
				Inventories.readNbt(beetlepack_inv_nbt, beetlepack_inv);
				ArrayList<UUID> spawned_uuids = new ArrayList<UUID>();
				World world = user.getWorld();
				for (ItemStack jar : beetlepack_inv) {
					if (jar.getItem() instanceof BeetleJarItem) {
						switch (reason) {
						case COMBAT:
							NbtCompound entity_data = jar.getOrCreateNbt().getCompound("EntityTag");
							switch (BeetleEntity.BeetleClass.values()[entity_data.getInt("Class")]) {
							case INFANTRY:
								Optional<LivingEntity> opt = BeetleJarItem.trySpawnFromJar(jar, user.getBlockPos(),
										world, user);
								if (opt.isPresent()) {
									spawned_uuids.add(opt.get().getUuid());
								}
								break;
							case PROJECTILE:
								// fire beetle as projectile
								System.out.println("yeep");
					            BeetleProjectileEntity persistentProjectileEntity = new BeetleProjectileEntity(world, user);
					            persistentProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 10.0f, 1.0f);
					            world.spawnEntity(persistentProjectileEntity);
					            break;
							default:
								break;
							}
							break;
						default:
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
					beetlepack_nbt.put(reason.toString() + "Spawn", uuid_nbt);
				}
				NbtCompound inv_nbt = new NbtCompound();
				Inventories.writeNbt(inv_nbt, beetlepack_inv);
				beetlepack_nbt.put("Inventory", inv_nbt);
				beetlepack.setNbt(beetlepack_nbt);
			}
		}
	}

	public static void recallBeetles(PlayerEntity user) {
		ItemStack beetlepack = BeetlepackItem.getBeetlepackOnPlayer(user);
		if (beetlepack.isOf(ItemRegistry.BEETLEPACK)) {
			NbtCompound beetlepack_nbt = beetlepack.getOrCreateNbt();
			DefaultedList<ItemStack> beetlepack_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
			Inventories.readNbt(beetlepack_nbt.getCompound("Inventory"), beetlepack_inv);
			for (BeetleDeployReason reason : BeetleDeployReason.values()) {
				if (beetlepack_nbt.contains(reason.toString() + "Spawn")) {
					NbtCompound flight_spawn = beetlepack_nbt.getCompound(reason.toString() + "Spawn");
					ArrayList<UUID> spawned_uuids = new ArrayList<UUID>();
					int i = 0;
					while (flight_spawn.contains(reason.toString() + "Spawn" + i)) {
						spawned_uuids.add(flight_spawn.getUuid(reason.toString() + "Spawn" + i));
						i++;
					}
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
				}
			}
			for (BeetleDeployReason reason : BeetleDeployReason.values()) {
				beetlepack_nbt.remove(reason.toString() + "Spawn");
				NbtCompound inv_nbt = new NbtCompound();
				Inventories.writeNbt(inv_nbt, beetlepack_inv);
				beetlepack_nbt.put("Inventory", inv_nbt);
				beetlepack.setNbt(beetlepack_nbt);
			}

		}
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
