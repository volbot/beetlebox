package volbot.beetlebox.entity.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.registry.BeetleRegistry;

public class TankBlockEntity extends BlockEntity {

	public String contained_id = "";
	public NbtCompound entity_data;

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(BeetleRegistry.TANK_BLOCK_ENTITY, pos, state);
	}

	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	public boolean shouldRender() {
		return !contained_id.isEmpty();
	}
	
	public void setContained(String id) {
		this.contained_id=id;
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_LISTENERS);
	}
	
	public void setEntityData(NbtCompound nbt) {
		this.entity_data = nbt;
		markDirty();
		//this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NO_REDRAW);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		if (contained_id != "") {
			nbt.putString("EntityType", contained_id);
			nbt.put("EntityTag", entity_data);
		}
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);

		contained_id = nbt.getString("EntityType");
		entity_data = nbt.getCompound("EntityTag");
	}
}
