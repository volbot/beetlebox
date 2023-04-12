package volbot.beetlebox.entity.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.registry.BeetleRegistry;

public class TankBlockEntity extends BlockEntity	{
	
	public String contained_id;
	public NbtCompound entityData;

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(BeetleRegistry.TANK_BLOCK_ENTITY, pos, state);
		System.out.println("BE Created!");
	}
}
