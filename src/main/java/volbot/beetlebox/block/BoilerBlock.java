package volbot.beetlebox.block;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.registry.BlockRegistry;

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
		BoilerBlockEntity blockEntity = world.getBlockEntity(pos, BlockRegistry.BOILER_BLOCK_ENTITY).orElse(null);
		if (blockEntity != null) {
			ItemStack current_boiling = blockEntity.getStack(0);
			ItemStack output = blockEntity.getStack(1);
			if (player.getMainHandStack().isEmpty()) {
				if (!output.isEmpty()) {
					// give player output
					player.setStackInHand(player.getActiveHand(), blockEntity.removeStack(1));
				} else if(!current_boiling.isEmpty()) {
					// give player input
					player.setStackInHand(player.getActiveHand(), blockEntity.removeStack(0));
				}
			} else {
				if (player.getStackInHand(player.getActiveHand()).isOf(Items.WATER_BUCKET)) {
					Transaction t = Transaction.openOuter();
					blockEntity.fluidStorage.insert(FluidVariant.of(Fluids.WATER), 1000, t);
					t.commit();
					t.close();
					if(!player.getAbilities().creativeMode) {
						player.getStackInHand(player.getActiveHand()).decrement(1);
						ItemStack stack1 = new ItemStack(Items.BUCKET);
						player.getInventory().insertStack(stack1);
					}
				} else if (player.getStackInHand(player.getActiveHand()).isOf(output.getItem())) {
					ItemStack ret = output.split(current_boiling.getMaxCount()-current_boiling.getCount());
					player.getInventory().insertStack(ret);
					blockEntity.setStack(1,output);
				} else if (current_boiling.isEmpty()) {
					//take player input
					blockEntity.setStack(0, player.getStackInHand(player.getActiveHand()));
					player.setStackInHand(player.getActiveHand(), ItemStack.EMPTY);
				} else if (current_boiling.isOf(player.getStackInHand(player.getActiveHand()).getItem()) && current_boiling.getCount()<current_boiling.getMaxCount()){
					ItemStack ret = player.getStackInHand(player.getActiveHand()).split(current_boiling.getMaxCount()-current_boiling.getCount());
					current_boiling.setCount(current_boiling.getCount()+ret.getCount());
					blockEntity.setStack(0,current_boiling);
				}
			}
		}
	}
	
	@Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockRegistry.BOILER_BLOCK_ENTITY, (world1, pos, state1, be) -> BoilerBlockEntity.tick(world1, pos, state1, be));
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
