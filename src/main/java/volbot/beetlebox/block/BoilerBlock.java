package volbot.beetlebox.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.registry.BeetleRegistry;

public class BoilerBlock extends BlockWithEntity {

	public BoilerBlock(Settings settings) {
		super(settings);
	}

	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        this.boilerLogic(world, pos, player);
        return ActionResult.CONSUME;
    }

	protected void boilerLogic(World world, BlockPos pos, PlayerEntity player) {
		BoilerBlockEntity blockEntity = world.getBlockEntity(pos, BeetleRegistry.BOILER_BLOCK_ENTITY).orElse(null);
		if (blockEntity != null) {
			ItemStack current_boiling = blockEntity.getStack(0);
			ItemStack output = blockEntity.getStack(1);
			if (player.getMainHandStack().isEmpty()) {
				if (!output.isEmpty()) {
					// give player output
					System.out.println("give output");
					player.setStackInHand(player.getActiveHand(), blockEntity.removeStack(1));
				} else if(!current_boiling.isEmpty()) {
					// give player input
					System.out.println("give input");
					player.setStackInHand(player.getActiveHand(), blockEntity.removeStack(0));
				}
			} else {

				System.out.println("eeby3");
				if (current_boiling.isEmpty()) {
					//take player input
					System.out.println(player.getStackInHand(player.getActiveHand()).getName().getString());
					blockEntity.setStack(0, player.getStackInHand(player.getActiveHand()));
					player.setStackInHand(player.getActiveHand(), ItemStack.EMPTY);
				} else if (current_boiling.isOf(player.getStackInHand(player.getActiveHand()).getItem()) && current_boiling.getCount()<current_boiling.getMaxCount()){
					//add player hand contents to current_boiling
				}
			}
		}
	}
	
	@Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BeetleRegistry.BOILER_BLOCK_ENTITY, (world1, pos, state1, be) -> BoilerBlockEntity.tick(world1, pos, state1, be));
    }

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BoilerBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

}
