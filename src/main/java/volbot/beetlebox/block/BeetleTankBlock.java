package volbot.beetlebox.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleTankBlock extends BlockWithEntity {

	public BeetleTankBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TankBlockEntity(pos, state);
	}
	
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
