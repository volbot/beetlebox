package volbot.beetlebox.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import volbot.beetlebox.entity.block.EmigratorBlockEntity;
import volbot.beetlebox.registry.BlockRegistry;

public class EmigratorBlock extends BlockWithEntity {
	
    public static final DirectionProperty FACING = FacingBlock.FACING;

	public EmigratorBlock(Settings settings) {
		super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
	}
	
	public static Position getOutputLocation(BlockPointer pointer) {
        Direction direction = pointer.getBlockState().get(FACING);
        double d = pointer.getX() + 0.7 * (double)direction.getOffsetX();
        double e = pointer.getY() + 0.7 * (double)direction.getOffsetY();
        double f = pointer.getZ() + 0.7 * (double)direction.getOffsetZ();
        return new PositionImpl(d, e, f);
    }
	
	public static BlockPos getInputBlock(BlockState state, BlockPos pos) {
        Direction direction = state.get(FACING);
        int d = pos.getX() - 1 * direction.getOffsetX();
        int e = pos.getY() - 1 * direction.getOffsetY();
        int f = pos.getZ() - 1 * direction.getOffsetZ();
        return new BlockPos(d, e, f);
    
	
	}@Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : EmigratorBlockEntity::serverTick;
    }

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EmigratorBlockEntity(pos, state);
	}
	
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)blockEntity).setCustomName(itemStack.getName());
        }
    }
	
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof EmigratorBlockEntity) {
            player.openHandledScreen((EmigratorBlockEntity)blockEntity);
        }
        return ActionResult.CONSUME;
    }

}
