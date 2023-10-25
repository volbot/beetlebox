package volbot.beetlebox.block;

import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
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
import volbot.beetlebox.entity.block.IncubatorBlockEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.item.tools.LarvaJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class IncubatorBlock extends BlockWithEntity {

	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

	public IncubatorBlock(Settings settings) {
		super(settings.nonOpaque().luminance(state -> state.get(ACTIVE) ? 10 : 0 ));
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new IncubatorBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState) state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@SuppressWarnings("deprecation")
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
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return checkType(type, BlockRegistry.INCUBATOR_BLOCK_ENTITY,
				(world1, pos, state1, te) -> IncubatorBlockEntity.tick(world1, pos, state1, te));	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING).add(ACTIVE);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState) this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(ACTIVE, false);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		BlockEntity blockEntity;
		if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof DispenserBlockEntity) {
			((DispenserBlockEntity) blockEntity).setCustomName(itemStack.getName());
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof IncubatorBlockEntity) {
			ItemStack handstack = player.getStackInHand(hand);
			if (handstack.isOf(ItemRegistry.LARVA_JAR)) {
				NbtCompound nbt = handstack.getOrCreateNbt();
				if (nbt.getInt("GrowingTime") < LarvaJarItem.MAX_GROWING_TIME) {
					if (((IncubatorBlockEntity) blockEntity).pushStack(handstack)) {
						player.setStackInHand(hand, ItemStack.EMPTY);
						return ActionResult.SUCCESS;
					}
				}
			} else if (handstack.isEmpty()) {
				player.setStackInHand(hand, ((IncubatorBlockEntity) blockEntity).popStack());
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.CONSUME;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return BlockUtils.rotateShape(Direction.NORTH, state.get(FACING), Stream
				.of(Block.createCuboidShape(0, 0, 0, 16, 2, 16), Block.createCuboidShape(2, 3, 11, 6, 4, 15),
						Block.createCuboidShape(3, 3, 6, 7, 4, 10), Block.createCuboidShape(10, 12, 5, 12, 14, 6),
						Block.createCuboidShape(4, 12, 10, 6, 14, 11), Block.createCuboidShape(4, 12, 5, 6, 14, 6),
						Block.createCuboidShape(10, 12, 10, 12, 14, 11), Block.createCuboidShape(7, 12, 7, 9, 14, 11),
						Block.createCuboidShape(7, 3, 11, 9, 14, 13), Block.createCuboidShape(2, 3, 1, 6, 4, 5),
						Block.createCuboidShape(1, 2, 1, 15, 3, 15), Block.createCuboidShape(9, 3, 6, 13, 4, 10),
						Block.createCuboidShape(10, 3, 1, 14, 4, 5), Block.createCuboidShape(10, 3, 11, 14, 4, 15),
						Block.createCuboidShape(2, 12, 1, 6, 14, 5), Block.createCuboidShape(3, 12, 6, 7, 14, 10),
						Block.createCuboidShape(2, 12, 11, 6, 14, 15), Block.createCuboidShape(9, 12, 6, 13, 14, 10),
						Block.createCuboidShape(10, 12, 1, 14, 14, 5), Block.createCuboidShape(10, 12, 11, 14, 14, 15))
				.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get());
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return BlockUtils.rotateShape(Direction.NORTH, state.get(FACING), Stream.of(Block.createCuboidShape(0, 0, 0, 16, 2, 16), Block.createCuboidShape(1, 2, 1, 15, 3, 15), Block.createCuboidShape(2, 3, 1, 14, 14, 15))
				.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get());
	}

}
