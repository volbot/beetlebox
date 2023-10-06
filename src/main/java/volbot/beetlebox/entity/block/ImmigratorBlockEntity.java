package volbot.beetlebox.entity.block;

import java.util.ArrayList;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import volbot.beetlebox.block.ImmigratorBlock;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.entity.mobstorage.IMobContainerTE;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ImmigratorBlockEntity extends LootableContainerBlockEntity
		implements Hopper, SidedInventory, IMobContainerTE {

	public static final int TRANSFER_COOLDOWN = 8;
	public static final int INVENTORY_SIZE = 5;
	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
	private int transferCooldown = 1;

	public ContainedEntity entity;

	public ImmigratorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.IMMIGRATOR_BLOCK_ENTITY, pos, state);
	}

	public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		ImmigratorBlockEntity te = (ImmigratorBlockEntity) blockEntity;
		--te.transferCooldown;
		world.getTime();
		if (!te.needsCooldown() && !state.get(Properties.POWERED)) {
			te.setTransferCooldown(0);
			tryFromJar(te);
			insert(world, pos, state, te);
		}
	}

	public static boolean insert(World world, BlockPos pos, BlockState state, IMobContainerTE te) {
		BlockEntity b = world.getBlockEntity(ImmigratorBlock.getOutputBlock(state, pos));
		if (b != null) {
			if (b instanceof IMobContainerTE) {
				IMobContainerTE tank = (IMobContainerTE) b;
				if (tank.canPush()) {
					ContainedEntity e = te.popContained();
					if (e != null) {
						if (tank.canPush()) {
							tank.pushContained(e);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static void tryFromJar(ImmigratorBlockEntity te) {
		if (te.getContained() == null) {
			for (ItemStack i : te.inventory) {
				if (i.getItem() instanceof BeetleJarItem && i.hasNbt()) {
					NbtCompound nbt = i.getNbt();
					if (nbt.contains("EntityType")) {
						te.setContained(new ContainedEntity(nbt.getString("EntityType"), nbt.getCompound("EntityTag"),
								nbt.getString("EntityName")));
						nbt.remove("EntityTag");
						nbt.remove("EntityType");
						nbt.remove("EntityName");
						i.setNbt(nbt);
						return;
					}
				}
			}
		}
	}

	private boolean needsCooldown() {
		return this.transferCooldown > 0;
	}

	private void setTransferCooldown(int transferCooldown) {
		this.transferCooldown = transferCooldown;
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public int size() {
		return this.inventory.size();
	}

	@Override
	public double getHopperX() {
		return (double) this.pos.getX() + 0.5;
	}

	@Override
	public double getHopperY() {
		return (double) this.pos.getY() + 0.5;
	}

	@Override
	public double getHopperZ() {
		return (double) this.pos.getZ() + 0.5;
	}

	@Override
	protected DefaultedList<ItemStack> getInvStackList() {
		return this.inventory;
	}

	@Override
	protected void setInvStackList(DefaultedList<ItemStack> list) {
		this.inventory = list;
	}

	@Override
	protected Text getContainerName() {
		return Text.translatable("beetlebox.container.immigrator");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new HopperScreenHandler(syncId, playerInventory, this);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (!this.serializeLootTable(nbt)) {
			Inventories.writeNbt(nbt, this.inventory);
		}
		if (entity != null) {
			nbt.putString("EntityType", entity.contained_id);
			nbt.putString("EntityName", entity.custom_name);
			nbt.putInt("TransferCooldown", transferCooldown);
			if (entity.entity_data != null) {
				nbt.put("EntityTag", entity.entity_data);
			}
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(nbt)) {
			Inventories.readNbt(nbt, this.inventory);
		}
		this.transferCooldown = nbt.getInt("TransferCooldown");
		if (nbt.contains("EntityType")) {
			entity = new ContainedEntity(nbt.getString("EntityType"), nbt.getCompound("EntityTag"),
					nbt.getString("EntityName"));
		} else {
			entity = null;
		}
	}

	public ContainedEntity getContained() {
		return this.entity;
	}

	public void setContained(ContainedEntity e) {
		this.entity = e;
		this.markDirty();
	}

	public void clearContained() {
		this.entity = null;
		this.markDirty();
	}

	@Override
	public ContainedEntity popContained() {
		if (this.getContained() != null) {
			ContainedEntity e = this.getContained();
			this.clearContained();
			this.markDirty();
			return e;
		}
		return null;
	}

	@Override
	public void pushContained(ContainedEntity e) {
		if (this.getContained() == null) {
			this.setContained(e);
			this.markDirty();
		}
	}

	@Override
	public boolean canPush() {
		return this.entity == null;
	}

	private static final int[] SLOTS = new int[] { 0, 1, 2, 3, 4 };

	@Override
	public int[] getAvailableSlots(Direction var1) {
		return SLOTS;

	}

	@Override
	public boolean canInsert(int var1, ItemStack var2, Direction var3) {
		return true;
	}

	@Override
	public boolean canExtract(int var1, ItemStack var2, Direction var3) {
		if(var2.isOf(ItemRegistry.BEETLE_JAR)) {
			if(var2.getOrCreateNbt().contains("EntityType")) {
				return false;
			}
		}
		return true;
	}
}
