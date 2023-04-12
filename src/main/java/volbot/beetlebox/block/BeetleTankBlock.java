package volbot.beetlebox.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.entity.tile.TankBlockEntity;

public class BeetleTankBlock extends Block implements BlockEntityProvider {

	public BeetleTankBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		System.out.println("Create BE");
		return new TankBlockEntity(pos, state);
	}

}
