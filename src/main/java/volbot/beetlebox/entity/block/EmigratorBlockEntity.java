package volbot.beetlebox.entity.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.block.EmigratorBlock;
import volbot.beetlebox.entity.mobstorage.IMobContainerTE;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EmigratorBlockEntity extends LootableContainerBlockEntity implements Hopper, IMobContainerTE {

	public static final int TRANSFER_COOLDOWN = 8;
	public static final int INVENTORY_SIZE = 5;
	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
	private int transferCooldown = -1;
	private long lastTickTime;

	public String contained_id = "";
	public String custom_name = "";
	public NbtCompound entity_data;

	public EmigratorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.EMIGRATOR_BLOCK_ENTITY, pos, state);
	}

	public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		EmigratorBlockEntity te = (EmigratorBlockEntity) blockEntity;
		--te.transferCooldown;
		te.lastTickTime = world.getTime();
		if (!te.needsCooldown()) {
			te.setTransferCooldown(0);
			extract(world, pos, state, te);
			tryIntoJar(te);
			ImmigratorBlockEntity.insert(world, pos, state, te);
		}
	}

	public static boolean extract(World world, BlockPos pos, BlockState state, EmigratorBlockEntity te) {
		BlockEntity b = world.getBlockEntity(EmigratorBlock.getInputBlock(state, pos));
		if (b != null) {
			if (b instanceof IMobContainerTE) {
				IMobContainerTE tank = (IMobContainerTE) b;
				if (te.getContained() == "") {
					te.setContained(tank.getContained());
					te.setEntityCustomName(tank.getEntityCustomName());
					te.setEntityData(tank.getEntityData());
					tank.setContained("");
					tank.setEntityCustomName("");
					tank.setEntityData(null);
					return true;
				}
			}
		}
		return false;
	}

	public static void tryIntoJar(EmigratorBlockEntity te) {
		if (te.contained_id != "") {
			for (ItemStack i : te.inventory) {
				if (i.getItem() instanceof BeetleJarItem) {
					LivingEntity e = (LivingEntity) ((EntityType.get(te.contained_id).orElse(null)
							.create(te.getWorld())));
					NbtCompound nbt = i.getOrCreateNbt();
					if (((BeetleJarItem<?>) i.getItem()).canStore(e) && !nbt.contains("EntityType")) {
						nbt.putString("EntityType", te.getContained());
						String custom_name = te.getEntityCustomName();
						if (!custom_name.isEmpty()) {
							nbt.putString("EntityName", custom_name);
						}
						nbt.put("EntityTag", te.getEntityData());
						i.setNbt(nbt);
						te.setContained("");
						te.setEntityCustomName("");
						te.setEntityData(null);
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
		return Text.translatable("beetlebox.container.emigrator");
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
		nbt.putString("EntityType", contained_id);
		nbt.putString("EntityName", custom_name);
		nbt.putInt("TransferCooldown", transferCooldown);
		if (entity_data != null) {
			nbt.put("EntityTag", entity_data);
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
			contained_id = nbt.getString("EntityType");
			if (nbt.contains("EntityName")) {
				custom_name = nbt.getString("EntityName");
			}
			entity_data = nbt.getCompound("EntityTag");
		}
	}

	public void setContained(String id) {
		this.contained_id = id;
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	public void setEntityData(NbtCompound nbt) {
		this.entity_data = nbt;
		markDirty();
	}

	public void setEntityCustomName(String custom_name) {
		this.custom_name = custom_name;
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	public String getContained() {
		return this.contained_id;
	}

	public String getEntityCustomName() {
		return this.custom_name;
	}

	public NbtCompound getEntityData() {
		return this.entity_data;
	}
}
