package volbot.beetlebox.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.entity.block.TankBlockEntity;

public class BeetleTankBlock extends BlockWithEntity {
	
	public boolean beetlesOnly;

	public BeetleTankBlock(boolean beetlesOnly, Settings settings) {
		super(settings);
		this.beetlesOnly = beetlesOnly;
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