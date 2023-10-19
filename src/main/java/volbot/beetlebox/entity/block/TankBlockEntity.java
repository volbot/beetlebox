package volbot.beetlebox.entity.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.entity.mobstorage.IMobContainerTE;
import volbot.beetlebox.item.Larva;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class TankBlockEntity extends BlockEntity implements SidedInventory, IMobContainerTE {

	public ContainedEntity[] contained = { null, null };
	public Larva larva = null;
	protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
	public int production_time = 0;
	public int production_time_max;
	public PlayerEntity last_user;

	public static int BREEDING_TIME_MAX = 200;
	//public static int TAMING_TIME_MAX = 10800;
	public static int TAMING_TIME_MAX = 2;

	private static final int[] TOP_SLOTS = new int[] { 0, 1, 2, 3, 4 };
	public boolean[] decor = new boolean[] { false, false, false };

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.TANK_BLOCK_ENTITY, pos, state);
	}

	public void setLarva(Larva larva) {
		this.larva = larva;
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	public static void tick(World world, BlockPos pos, BlockState state, TankBlockEntity te) {
		NbtCompound nbt1 = null;
		NbtCompound nbt2 = null;
		if (te.getContained(0) != null) {
			nbt1 = te.getContained(0).getEntityData();
			int age = nbt1.getInt("Age");
			if (nbt1.contains("Age") && age > 0) {
				nbt1.putInt("Age", age - 1);
			} else {
				nbt1.putInt("Age", 0);
			}
			te.getContained(0).setEntityData(nbt1);
		}
		if (te.getContained(1) != null) {
			nbt2 = te.getContained(1).getEntityData();
			int age = nbt2.getInt("Age");
			if (nbt2.contains("Age") && age > 0) {
				nbt2.putInt("Age", age - 1);
			} else {
				nbt2.putInt("Age", 0);
			}
			te.getContained(1).setEntityData(nbt2);
		}
		if (te.breedingSetupValid()) {
			te.production_time_max = BREEDING_TIME_MAX;
			te.production_time++;
			if (te.production_time >= te.production_time_max) {
				te.setProductionTime(0);
				te.setLarva(new Larva(te.getContained(0), te.getContained(1), world));
				nbt1.putInt("Age", 200);
				te.getContained(0).setEntityData(nbt1);
				nbt2.putInt("Age", 200);
				te.getContained(1).setEntityData(nbt2);
			} else {
				if (te.production_time % 10 == 0) {
					double d = world.getRandom().nextGaussian() * 0.02;
					double e = world.getRandom().nextGaussian() * 0.02;
					double f = world.getRandom().nextGaussian() * 0.02;
					world.addParticle(ParticleTypes.HEART,
							0.5 + te.getPos().getX() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5),
							0.5 + te.getPos().getY() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5),
							0.5 + te.getPos().getZ() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5), d, e, f);
				}
			}
			return;
		}
		if (te.tamingSetupValid()) {
			te.production_time_max = TAMING_TIME_MAX;
			te.production_time++;
			if (te.production_time >= te.production_time_max) {
				if (world.isClient) {
					return;
				}
				te.setProductionTime(0);
				int tame_progress = nbt1.getInt("TameProgress");
				tame_progress = tame_progress + 1;
				nbt1.putInt("TameProgress", tame_progress);

				NbtCompound item_nbt = te.getStack(4).getOrCreateNbt();
				switch (item_nbt.getString("FruitType")) {
				case "melon":
					nbt1.putFloat("Size", nbt1.getInt("Size")
							+ (item_nbt.getBoolean("Increase") ? 0.7f : -0.7f) * item_nbt.getFloat("Magnitude"));
					break;
				case "apple":
					nbt1.putFloat("MaxHealth", nbt1.getInt("MaxHealth")
							+ (item_nbt.getBoolean("Increase") ? 0.1f : -0.1f) * item_nbt.getFloat("Magnitude"));
					break;
				case "sugar":
					nbt1.putFloat("Speed", nbt1.getInt("Damage")
							+ (item_nbt.getBoolean("Increase") ? 0.1f : -0.1f) * item_nbt.getFloat("Magnitude"));
					break;
				case "berry":
					nbt1.putFloat("Damage", nbt1.getInt("Damage")
							+ (item_nbt.getBoolean("Increase") ? 0.1f : -0.1f) * item_nbt.getFloat("Magnitude"));
					break;
				}
				te.setStack(4, ItemStack.EMPTY);

				if (te.last_user != null) {
					nbt1.putUuid("Owner", te.last_user.getUuid());
				}

				te.getContained(0).setEntityData(nbt1);
			} else {
				if (te.production_time % 10 == 0) {
					double d = world.getRandom().nextGaussian() * 0.02;
					double e = world.getRandom().nextGaussian() * 0.02;
					double f = world.getRandom().nextGaussian() * 0.02;
					world.addParticle(ParticleTypes.HAPPY_VILLAGER,
							0.5 + te.getPos().getX() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5),
							0.5 + te.getPos().getY() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5),
							0.5 + te.getPos().getZ() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5), d, e, f);
				}
			}
			return;
		}
		te.production_time = 0;
	}

	public void setLastUser(PlayerEntity player) {
		if (getContained(0) != null) {
			if (getContained(0).getEntityData().getInt("TameProgress") != 0) {
				return;
			}
		}
		this.last_user = player;
	}

	public void setProductionTime(int production_time) {
		this.production_time = production_time;
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	public boolean canContainedBreed() {
		NbtCompound nbt1 = this.getContained(0).getEntityData();
		if (nbt1.contains("Age")) {
			if (nbt1.getInt("Age") != 0) {
				return false;
			}
		}
		NbtCompound nbt2 = this.getContained(1).getEntityData();
		if (nbt2.contains("Age")) {
			if (nbt2.getInt("Age") != 0) {
				return false;
			}
		}
		if (0 != this.getContained(0).getContainedId().compareTo(this.getContained(1).getContainedId())) {
			return false;
		}
		return true;
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	public boolean breedingSetupValid() {
		if (!isContainedFull()) {
			return false;
		}
		if (!canContainedBreed()) {
			return false;
		}
		if (getStack(0).getItem() != ItemRegistry.SUBSTRATE) {
			return false;
		}
		if (this.larva != null) {
			return false;
		}
		boolean hasFlower = false;
		boolean hasCandle = false;
		for (int i = 1; i <= 2; i++) {
			ItemStack stack = getStack(i);
			if (Block.getBlockFromItem(stack.getItem()) instanceof FlowerBlock) {
				hasFlower = true;
			}
			if (Block.getBlockFromItem(stack.getItem()) instanceof CandleBlock) {
				hasCandle = true;
			}
		}
		if (!hasFlower || !hasCandle) {
			return false;
		}
		return true;
	}

	public boolean tamingSetupValid() {
		if (!(getContained(0) != null && getContained(1) == null)) {
			return false;
		}
		if (getContained(0).getEntityData().getInt("TameProgress") >= 5) {
			return false;
		}
		if (getStack(0).getItem() != ItemRegistry.SUBSTRATE) {
			return false;
		}
		if (getStack(1).getItem() == getStack(2).getItem() || getStack(2).getItem() == getStack(3).getItem()) {
			return false;
		}
		if (getStack(4) == ItemStack.EMPTY) {
			return false;
		}
		for (int i = 1; i <= 3; i++) {
			if (!this.isEnrichmentMaterial(getStack(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean isEnrichmentMaterial(ItemStack stack) {
		return true;
	}

	public boolean canPush() {
		if (this.isContainedFull()) { // full entity slots
			return false;
		} else if (this.getStack(0) == ItemStack.EMPTY) { // no substrate
			return false;
		} else if (getContained(0) != null && this.inventory.get(3) != ItemStack.EMPTY) { // third item slot used
			return false;
		}
		return true;

	}

	public boolean isContainedFull() {
		return contained[1] != null;
	}

	public void pushContained(ContainedEntity entity) {
		if (contained[0] == null) {
			contained[0] = entity;
		} else if (contained[1] == null) {
			contained[1] = entity;
		}
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	public ContainedEntity getContained(int slot) {
		return contained[slot];
	}

	public ContainedEntity popContained() {
		ContainedEntity r;
		if (contained[1] != null) {
			r = contained[1];
			contained[1] = null;
			markDirty();
			this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
					Block.NOTIFY_LISTENERS);
			return r;
		} else if (contained[0] != null) {
			r = contained[0];
			contained[0] = null;
			markDirty();
			this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
					Block.NOTIFY_LISTENERS);
			return r;
		} else {
			return null;
		}
	}

	public boolean hasDecor() {
		for (int i = 0; i < decor.length; i++) {
			if (decor[i]) {
				return true;
			}
		}
		return false;
	}

	public void clearDecor() {
		for (int i = 0; i < decor.length; i++) {
			decor[i] = false;
		}
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		if (contained[0] != null) {
			nbt.putString("EntityType1", contained[0].contained_id);
			nbt.putString("EntityName1", contained[0].custom_name);
			if (contained[0].entity_data != null) {
				nbt.put("EntityTag1", contained[0].entity_data);
			}
		}
		if (contained[1] != null) {
			nbt.putString("EntityType2", contained[1].contained_id);
			nbt.putString("EntityName2", contained[1].custom_name);
			if (contained[1].entity_data != null) {
				nbt.put("EntityTag2", contained[1].entity_data);
			}
		}
		nbt.putInt("ProdTime", production_time);
		if (larva != null) {
			nbt.putString("LarvaType", larva.type);
			nbt.putFloat("LarvaDamage", larva.damage);
			nbt.putFloat("LarvaSpeed", larva.speed);
			nbt.putInt("LarvaSize", larva.size);
			nbt.putFloat("LarvaMaxHealth", larva.maxhealth);
		}

		nbt.putBoolean("Decor0", decor[0]);
		nbt.putBoolean("Decor1", decor[1]);
		nbt.putBoolean("Decor2", decor[2]);

		Inventories.writeNbt(nbt, this.inventory);
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains("EntityType1")) {
			contained[0] = new ContainedEntity(nbt.getString("EntityType1"), nbt.getCompound("EntityTag1"),
					nbt.getString("EntityName1"));
		} else {
			contained[0] = null;
		}
		if (nbt.contains("EntityType2")) {
			contained[1] = new ContainedEntity(nbt.getString("EntityType2"), nbt.getCompound("EntityTag2"),
					nbt.getString("EntityName2"));
		} else {
			contained[1] = null;
		}
		production_time = nbt.getInt("ProdTime");
		if (nbt.contains("LarvaType")) {
			larva = new Larva(nbt.getString("LarvaType"), nbt.getInt("LarvaSize"), nbt.getFloat("LarvaDamage"),
					nbt.getFloat("LarvaSpeed"), nbt.getFloat("LarvaMaxHealth"));
		} else {
			larva = null;
		}

		decor[0] = nbt.getBoolean("Decor0");
		decor[1] = nbt.getBoolean("Decor1");
		decor[2] = nbt.getBoolean("Decor2");

		inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory);
	}

	public void putTopStack(ItemStack stack) {
		setStack(Math.min(getTopStackId() + 1, 3), stack);
	}

	public ItemStack getTopStack() {
		for (int i = 3; i >= 0; i--) {
			if (getStack(i) != ItemStack.EMPTY) {
				return getStack(i);
			}
		}
		return ItemStack.EMPTY;
	}

	public int getTopStackId() {
		for (int i = 3; i >= 0; i--) {
			if (getStack(i) != ItemStack.EMPTY) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ItemStack getStack(int slot) {
		return this.inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
		return stack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		ItemStack stack = Inventories.removeStack(this.inventory, slot);
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
		return stack;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}
		this.markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	@Override
	public int size() {
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : this.inventory) {
			if (itemStack.isEmpty())
				continue;
			return false;
		}
		return true;
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return Inventory.canPlayerUse(this, player);
	}

	@Override
	public void clear() {
		this.inventory.clear();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		if (side == Direction.UP) {
			return TOP_SLOTS;
		}
		return TOP_SLOTS;
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		if (slot >= this.size()) {
			return false;
		}
		if (stack.isOf(ItemRegistry.SUBSTRATE)) {
			if (slot == 0 && getStack(4) == ItemStack.EMPTY) {
				return true;
			}
		} else if (getStack(0) == ItemStack.EMPTY) {
			return false;
		}
		if (stack.isOf(ItemRegistry.BEETLE_JELLY)) {
			if (slot == 4 && getStack(4) == ItemStack.EMPTY) {
				return true;
			}
		}
		for (int i = 1; i <= 3; i++) {
			if (slot == i && getStack(i) == ItemStack.EMPTY
					&& (stack.isIn(ItemTags.CANDLES) || stack.isIn(ItemTags.LOGS)) || stack.isIn(ItemTags.FLOWERS)
					|| stack.isIn(ItemTags.SAPLINGS) || stack.isOf(Items.BROWN_MUSHROOM)
					|| stack.isOf(Items.RED_MUSHROOM)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return this.isValid(slot, stack);
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}

	public static int getDecorId(ItemStack stack) {
		if (stack.isOf(Items.CHAIN)) {
			return 0;
		}
		if (stack.isOf(Items.VINE)) {
			return 1;
		}
		if (stack.isIn(ItemTags.LEAVES)) {
			return 2;
		}
		return -1;
	}

	public void setDecor(int id, boolean value) {
		decor[id] = value;
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	public void flipDecor(int id) {
		decor[id] = !decor[id];
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}
}
