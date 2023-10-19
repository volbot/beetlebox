package volbot.beetlebox.entity.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import volbot.beetlebox.block.EmigratorBlock;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.entity.mobstorage.IMobContainerTE;
import volbot.beetlebox.item.Larva;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EmigratorBlockEntity extends LootableContainerBlockEntity
		implements Hopper, SidedInventory, IMobContainerTE {

	public static final int TRANSFER_COOLDOWN = 8;
	public static final int INVENTORY_SIZE = 5;
	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
	private int transferCooldown = 1;
	private long lastTickTime;

	public Larva larva;

	public ContainedEntity entity;

	public EmigratorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.EMIGRATOR_BLOCK_ENTITY, pos, state);
	}

	public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		EmigratorBlockEntity te = (EmigratorBlockEntity) blockEntity;
		--te.transferCooldown;
		te.lastTickTime = world.getTime();
		if (!te.needsCooldown() && !state.get(Properties.POWERED)) {
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
				if (b.getPos().down(1).getY() == te.getPos().getY()) {
					if (te.getLarva() == null) {
						if (b instanceof TankBlockEntity) {
							TankBlockEntity tank = (TankBlockEntity) b;
							Larva tank_larva = tank.larva;
							if (tank_larva != null) {
								te.larva = tank_larva;
								tank.setLarva(null);
								return true;
							}
						}
					}
				} else {
					if (te.getContained() == null) {
						IMobContainerTE tank = (IMobContainerTE) b;
						ContainedEntity tanked = tank.popContained();
						if (tanked != null) {
							te.setContained(tanked);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Larva getLarva() {
		return this.larva;
	}

	public static void tryIntoJar(EmigratorBlockEntity te) {
		if (!te.isFull()) {
			for (ItemStack i : te.inventory) {
				if (te.getContained() != null && i.getItem() instanceof BeetleJarItem) {
					LivingEntity e = (LivingEntity) ((EntityType.get(te.getContained().getContainedId()).orElse(null)
							.create(te.getWorld())));
					NbtCompound nbt1 = i.getOrCreateNbt();
					if (((BeetleJarItem<?>) i.getItem()).canStore(e) && !nbt1.contains("EntityType")) {
						ItemStack i2 = i.getItem().getDefaultStack();
						NbtCompound nbt = i2.getOrCreateNbt();
						nbt.putString("EntityType", te.getContained().getContainedId());
						String custom_name = te.getContained().CustomName();
						if (!custom_name.isEmpty()) {
							nbt.putString("EntityName", custom_name);
						}
						nbt.put("EntityTag", te.getContained().getEntityData());
						i.decrement(1);
						i2.setNbt(nbt);
						te.addStack(i2);
						te.clearContained();
						return;
					}
				} else if (te.larva != null && i.isOf(ItemRegistry.SUBSTRATE_JAR)) {
					Larva tank_larva = te.larva;
					te.larva=null;
					LivingEntity e = ((LivingEntity) EntityType.get(tank_larva.type).orElse(null)
							.create(te.getWorld()));
					if (e instanceof BeetleEntity) {
						((BeetleEntity) e).setSize(tank_larva.size);
						((BeetleEntity) e).setMaxHealthMult(tank_larva.maxhealth);
						((BeetleEntity) e).setDamageMult(tank_larva.damage);
						((BeetleEntity) e).setSpeedMult(tank_larva.speed);
					}
					ItemStack newstack = ItemRegistry.LARVA_JAR.getDefaultStack();
					NbtCompound tag = new NbtCompound();
					e.writeNbt(tag);
					e.writeCustomDataToNbt(tag);
					NbtCompound nbt = newstack.getOrCreateNbt();
					nbt.put("EntityTag", tag);
					Text custom_name = e.getCustomName();
					if (custom_name != null && !custom_name.getString().isEmpty()) {
						nbt.putString("EntityName", custom_name.getString());
					}
					nbt.putString("EntityType", EntityType.getId(e.getType()).toString());
					newstack.setCount(1);
					newstack.setNbt(nbt);
					te.addStack(newstack);
					i.decrement(1);
					return;
				}
			}
		}
	}

	public boolean isFull() {
		for (ItemStack i : inventory) {
			if (i.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public void addStack(ItemStack stack) {
		for (int i = 0; i < size(); i++) {
			if (getStack(i).isEmpty()) {
				setStack(i, stack);
				return;
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
		if (var2.isOf(ItemRegistry.BEETLE_JAR)) {
			if (var2.getOrCreateNbt().contains("EntityType")) {
				return true;
			}
		}
		if(var2.isOf(ItemRegistry.LARVA_JAR)) {
			return true;
		}
		return false;
	}
}
