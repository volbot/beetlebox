package volbot.beetlebox.entity.block;

import java.util.ArrayList;

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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import volbot.beetlebox.block.IncubatorBlock;
import volbot.beetlebox.item.tools.LarvaJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class IncubatorBlockEntity extends BlockEntity implements SidedInventory {

	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);

	public IncubatorBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockRegistry.INCUBATOR_BLOCK_ENTITY, blockPos, blockState);
	}


	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}


	public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

		IncubatorBlockEntity te = (IncubatorBlockEntity) blockEntity;
		boolean active = false;
		for (int slot = 0; slot < te.size(); slot++) {
			ItemStack stack = te.getStack(slot);
			if (stack.isOf(ItemRegistry.LARVA_JAR)) {
				active = true;
				NbtCompound nbt = stack.getOrCreateNbt();
				int growing_time = nbt.getInt("GrowingTime");
				if (growing_time >= LarvaJarItem.MAX_GROWING_TIME) {
					return;
				}
				growing_time += 5;
				nbt.putInt("GrowingTime", growing_time);
				stack.setNbt(nbt);

				if (world.getTime() % (20) == slot*2) {
					double d = world.getRandom().nextGaussian() * 0.02;
					double e = world.getRandom().nextGaussian() * 0.02;
					double f = world.getRandom().nextGaussian() * 0.02;
					world.addParticle(ParticleTypes.HAPPY_VILLAGER,
							0.5 + pos.getX() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5),
							0.5 + pos.getY() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5),
							0.5 + pos.getZ() + ((2.0 * world.getRandom().nextDouble() - 1.0) / 1.5), d, e, f);

				}
			}
		}
		if (active) {
			if (!state.get(IncubatorBlock.ACTIVE)) {
				world.setBlockState(pos, te.getCachedState().with(IncubatorBlock.ACTIVE, true));
			}
		} else {
			if (state.get(IncubatorBlock.ACTIVE)) {
				world.setBlockState(pos, te.getCachedState().with(IncubatorBlock.ACTIVE, false));
			}
		}
	}

	@Override
	public int size() {
		return 6;
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, this.inventory);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory);

	}


	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public int[] getAvailableSlots(Direction dir) {
		ArrayList<Integer> done = new ArrayList<Integer>();
		ArrayList<Integer> not = new ArrayList<Integer>();
		for (int i = 0; i <= this.size(); i++) {
			ItemStack stack = getStack(i);
			if (stack.isOf(ItemRegistry.LARVA_JAR)) {
				NbtCompound nbt = stack.getOrCreateNbt();
				if (nbt.getInt("GrowingTime") >= LarvaJarItem.MAX_GROWING_TIME) {
					done.add(i);
					continue;
				}
			}
			not.add(i);
		}
		if (dir == Direction.DOWN) {
			return done.stream().mapToInt(i -> i).toArray();
		} else {
			return not.stream().mapToInt(i -> i).toArray();
		}
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir) {
		return getStack(slot).equals(ItemStack.EMPTY) && stack.isOf(ItemRegistry.LARVA_JAR);
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		if (stack.isOf(ItemRegistry.LARVA_JAR)) {
			NbtCompound nbt = stack.getOrCreateNbt();
			if (nbt.getInt("GrowingTime") >= LarvaJarItem.MAX_GROWING_TIME) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	public boolean pushStack(ItemStack stack) {
		for (int i = 0; i < this.size(); i++) {
			if (getStack(i).isEmpty()) {
				setStack(i, stack);
				return true;
			}
		}
		return false;
	}

	public ItemStack popStack() {
		int fallback = -1;
		for (int i = 0; i < this.size(); i++) {
			ItemStack stack = getStack(i);
			if (!stack.isEmpty()) {
				NbtCompound nbt = stack.getOrCreateNbt();
				if (nbt.getInt("GrowingTime") >= LarvaJarItem.MAX_GROWING_TIME) {
					this.removeStack(i);
					return stack;
				} else {
					fallback = i;
				}
			}
		}
		if (fallback != -1) {
			ItemStack fbstack = getStack(fallback);
			removeStack(fallback);
			return fbstack;
		} else {
			return ItemStack.EMPTY;
		}
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
		inventory.clear();
	}

}
