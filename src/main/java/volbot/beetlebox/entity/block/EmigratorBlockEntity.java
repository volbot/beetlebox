package volbot.beetlebox.entity.block;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.world.World;
import volbot.beetlebox.block.EmigratorBlock;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.block.DispenserBlock;

public class EmigratorBlockEntity extends LootableContainerBlockEntity implements Hopper {

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
		}
	}

	public static boolean extract(World world, BlockPos pos, BlockState state, EmigratorBlockEntity te) {
		TankBlockEntity tank;
		Optional<TankBlockEntity> op = world.getBlockEntity(EmigratorBlock.getInputBlock(state, pos),
				BlockRegistry.TANK_BLOCK_ENTITY);
		if (op.isPresent()) {
			tank = op.get();
			if (tank.contained_id != "") {
				te.contained_id = tank.contained_id;
				te.custom_name = tank.custom_name;
				te.entity_data = tank.entity_data;
				tank.setContained("");
				tank.setCustomName("");
				tank.setEntityData(null);
				tank.markDirty();
				return true;
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
						nbt.putString("EntityType", te.contained_id);
						String custom_name = te.custom_name;
						if (!custom_name.isEmpty()) {
							nbt.putString("EntityName", custom_name);
						}
						nbt.put("EntityTag", te.entity_data);
						i.setNbt(nbt);
						te.contained_id = "";
						te.custom_name = "";
						te.entity_data = null;
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
}
