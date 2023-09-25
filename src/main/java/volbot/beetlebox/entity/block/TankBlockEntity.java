package volbot.beetlebox.entity.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.entity.mobstorage.IMobContainerTE;
import volbot.beetlebox.registry.BlockRegistry;

public class TankBlockEntity extends BlockEntity implements IMobContainerTE {

	public String contained_id = "";
	public String custom_name = "";
	public NbtCompound entity_data;

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.TANK_BLOCK_ENTITY, pos, state);
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
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

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		nbt.putString("EntityType", contained_id);
		nbt.putString("EntityName", custom_name);
		if(entity_data != null) {
			nbt.put("EntityTag", entity_data);
		}

		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains("EntityType")) {
			contained_id = nbt.getString("EntityType");
			if (nbt.contains("EntityName")) {
				custom_name = nbt.getString("EntityName");
			}
			entity_data = nbt.getCompound("EntityTag");
		}
	}

	/*
	 * Makes a beetle escape the tank when the tank is broken, but also causes world saves to fail.
	 *  Requires further analysis.
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
