package volbot.beetlebox.entity.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.registry.BeetleRegistry;

public class TankBlockEntity extends BlockEntity {

	public String contained_id = "";
	public NbtCompound entity_data;

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(BeetleRegistry.TANK_BLOCK_ENTITY, pos, state);
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
		// this.getWorld().updateListeners(this.getPos(), this.getCachedState(),
		// this.getCachedState(), Block.NO_REDRAW);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		nbt.putString("EntityType", contained_id);
		nbt.put("EntityTag", entity_data);

		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);

		contained_id = nbt.getString("EntityType");
		entity_data = nbt.getCompound("EntityTag");
	}
}
