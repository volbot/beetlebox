package volbot.beetlebox.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.entity.tile.TankBlockEntity;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleTankBlock extends Block implements BlockEntityProvider {

	public BeetleTankBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TankBlockEntity(pos, state);
	}

}
