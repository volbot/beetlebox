package volbot.beetlebox.entity.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.entity.mobstorage.IMobContainerTE;
import volbot.beetlebox.registry.BlockRegistry;

public class TankBlockEntity extends BlockEntity implements SidedInventory {

	public ContainedEntity[] contained = { null, null };
	protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

	private static final int[] TOP_SLOTS = new int[] { 1, 2, 3 };

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.TANK_BLOCK_ENTITY, pos, state);
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	public boolean canAcceptEntity() {
		if(this.isContainedFull()) { //full entity slots
			return false;
		} else if(this.getStack(0)==ItemStack.EMPTY) { //no substrate
			return false;
		} else if(this.inventory.get(2)!=ItemStack.EMPTY) { //third item slot used
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
		inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory);
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
		return 3;
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
		return null;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return this.isValid(slot, stack);
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}

	/*
	 * Makes a beetle escape the tank when the tank is broken, but also causes world
	 * saves to fail. Requires further analysis.
	 */
	/*
	 * @Override public void markRemoved() { if(!this.contained_id.isEmpty()) {
	 * EntityType<?> entityType2 = EntityType.get(this.contained_id).orElse(null);
	 * Entity e = entityType2.create(this.getWorld()); e.readNbt(this.entity_data);
	 * if(!this.custom_name.isEmpty()) { e.setCustomName(Text.of(this.custom_name));
	 * } e.teleport(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);
	 * world.spawnEntity(e); } super.markRemoved(); }
	 */
}
