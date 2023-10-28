package volbot.beetlebox.block;

import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import volbot.beetlebox.entity.block.ImmigratorBlockEntity;

public class ImmigratorBlock extends BlockWithEntity {

	public static final DirectionProperty FACING = FacingBlock.FACING;
	public static final BooleanProperty POWERED = Properties.POWERED;

	private static final VoxelShape SHAPE = Stream
			.of(Block.createCuboidShape(2, 2, 12, 14, 14, 16), Block.createCuboidShape(4, 4, 4, 12, 12, 12),
					Block.createCuboidShape(3, 3, 2, 13, 13, 4), Block.createCuboidShape(5, 5, 0, 11, 11, 2))
			.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

	public ImmigratorBlock(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(FACING,
				Direction.NORTH)));
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState) state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof Inventory) {
				ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
				// update comparators
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
		case UP:
			return Stream.of(Block.createCuboidShape(2, 0, 2, 14, 4, 14), Block.createCuboidShape(4, 4, 4, 12, 12, 12),
					Block.createCuboidShape(3, 12, 3, 13, 14, 13), Block.createCuboidShape(5, 14, 5, 11, 16, 11))
					.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
		case DOWN:
			return Stream
					.of(Block.createCuboidShape(2, 12, 2, 14, 16, 14), Block.createCuboidShape(4, 4, 4, 12, 12, 12),
							Block.createCuboidShape(3, 2, 3, 13, 4, 13), Block.createCuboidShape(5, 0, 5, 11, 2, 11))
					.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
		default:
			return BlockUtils.rotateShape(Direction.NORTH, state.get(FACING), SHAPE);
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
		case UP:
			return Stream.of(Block.createCuboidShape(2, 0, 2, 14, 4, 14), Block.createCuboidShape(4, 4, 4, 12, 12, 12),
					Block.createCuboidShape(3, 12, 3, 13, 14, 13), Block.createCuboidShape(5, 14, 5, 11, 16, 11))
					.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
		case DOWN:
			return Stream
					.of(Block.createCuboidShape(2, 12, 2, 14, 16, 14), Block.createCuboidShape(4, 4, 4, 12, 12, 12),
							Block.createCuboidShape(3, 2, 3, 13, 4, 13), Block.createCuboidShape(5, 0, 5, 11, 2, 11))
					.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
		default:
			return BlockUtils.rotateShape(Direction.NORTH, state.get(FACING), SHAPE);
		}
	}

	public static BlockPos getOutputBlock(BlockState state, BlockPos pos) {
		Direction direction = state.get(FACING);
		int d = pos.getX() + 1 * direction.getOffsetX();
		int e = pos.getY() + 1 * direction.getOffsetY();
		int f = pos.getZ() + 1 * direction.getOffsetZ();
		return new BlockPos(d, e, f);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return world.isClient ? null : ImmigratorBlockEntity::serverTick;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ImmigratorBlockEntity(pos, state);
	}
	


	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos,
			boolean notify) {
		this.updateEnabled(world, pos, state, 4);
	}

	private void updateEnabled(World world, BlockPos pos, BlockState state, int flags) {

		world.setBlockState(pos, (BlockState) state.with(POWERED, world.isReceivingRedstonePower(pos)), flags);

	}


	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(POWERED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite())
				.with(POWERED, false);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		BlockEntity blockEntity;
		if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof DispenserBlockEntity) {
			((DispenserBlockEntity) blockEntity).setCustomName(itemStack.getName());
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ImmigratorBlockEntity) {
			player.openHandledScreen((ImmigratorBlockEntity) blockEntity);
		}
		return ActionResult.CONSUME;
	}

}
