package volbot.beetlebox.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.item.Larva;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleTankBlock<T extends LivingEntity> extends BlockWithEntity {

	Class<T> clazz;

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public BeetleTankBlock(Settings settings, Class<T> clazz) {
		super(settings);
		setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
		this.clazz = clazz;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TankBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	public boolean canStore(Entity entity) {
		return clazz.isAssignableFrom(entity.getClass());
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}
		return this.tankLogic(world, pos, player);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return checkType(type, BlockRegistry.TANK_BLOCK_ENTITY,
				(world1, pos, state1, te) -> TankBlockEntity.tick(world1, pos, state1, te));
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
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING,
				ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING);
	}

	protected ActionResult tankLogic(World world, BlockPos pos, PlayerEntity player) {
		TankBlockEntity te = world.getBlockEntity(pos, BlockRegistry.TANK_BLOCK_ENTITY).orElse(null);
		ItemStack handstack = player.getMainHandStack();
		if (te != null) {
			if (handstack.isEmpty()) {
				int id = te.getTopStackId();
				if (te.getStack(4) != ItemStack.EMPTY) {
					id = 4;
				} else {
					if (id == 0) {
						if (te.hasDecor()) {
							te.clearDecor();
							return ActionResult.SUCCESS;
						}
						if (te.getContained(0) != null) {
							return ActionResult.CONSUME;
						}
					}
				}
				player.setStackInHand(player.getActiveHand(), te.getStack(id));
				te.setStack(id, ItemStack.EMPTY);
				return ActionResult.SUCCESS;
			} else {
				if (te.getStack(0) == ItemStack.EMPTY) {
					if (te.isValid(0, handstack)) {
						if (te.getContained(0) == null) {
							if (te.getStack(0) == ItemStack.EMPTY) {
								ItemStack substrate_new = handstack.getItem().getDefaultStack();
								substrate_new.setCount(1);
								te.setStack(0, substrate_new);
								if (!player.isCreative()) {
									ItemStack substrate_old = handstack;
									substrate_old.decrement(1);
									player.setStackInHand(player.getActiveHand(), substrate_old);
								}
								return ActionResult.SUCCESS;
							} else if (handstack.getCount() < handstack.getItem().getMaxCount()
									&& handstack.isOf(te.getStack(0).getItem())) {
								te.setStack(0, ItemStack.EMPTY);
								if (!player.isCreative()) {
									ItemStack substrate = player.getStackInHand(player.getActiveHand());
									substrate.increment(1);
									player.setStackInHand(player.getActiveHand(), substrate);
								}
								return ActionResult.SUCCESS;
							}
						}
					}
				} else if (handstack.getItem() instanceof BeetleJarItem) {
					ItemStack jar_stack = handstack;
					BeetleJarItem<?> jar_item = (BeetleJarItem<?>) jar_stack.getItem();
					NbtCompound jar_nbt = jar_stack.getOrCreateNbt();
					if (!jar_nbt.contains("EntityType") && te.getContained(0) != null) {
						ContainedEntity contained = te.popContained();
						LivingEntity e = (LivingEntity) ((EntityType.get(contained.contained_id).orElse(null)
								.create(te.getWorld())));
						if (!jar_item.canStore(e)) {
							return ActionResult.FAIL;
						}
						NbtCompound new_nbt = new NbtCompound();
						new_nbt.putString("EntityType", contained.contained_id);
						String custom_name = contained.custom_name;
						if (!custom_name.isEmpty()) {
							new_nbt.putString("EntityName", custom_name);
						}
						new_nbt.put("EntityTag", contained.entity_data);
						ItemStack jar_new = jar_item.getDefaultStack();
						jar_new.setNbt(new_nbt);
						jar_stack.decrement(1);
						if (player.getInventory().getEmptySlot() == -1) {
							player.dropStack(jar_new);
						} else {
							player.giveItemStack(jar_new);
						}
						return ActionResult.SUCCESS;
					} else if (jar_nbt.contains("EntityType") && te.canPush()) {
						Entity e = EntityType.get(jar_nbt.getString("EntityType")).orElse(null).create(te.getWorld());
						if (!this.canStore(e)) {
							return ActionResult.FAIL;
						}
						te.pushContained(new ContainedEntity(jar_nbt.getString("EntityType"),
								jar_nbt.getCompound("EntityTag"), jar_nbt.getString("EntityName")));
						jar_stack.removeSubNbt("EntityName");
						jar_stack.removeSubNbt("EntityTag");
						jar_stack.removeSubNbt("EntityType");
						return ActionResult.SUCCESS;
					}
				} else if (handstack.isOf(ItemRegistry.SUBSTRATE_JAR)
						&& !handstack.getOrCreateNbt().contains("EntityType")) {
					Larva larva = te.larva;
					if (larva == null) {
						return ActionResult.FAIL;
					}
					LivingEntity e = ((LivingEntity) EntityType.get(larva.type).orElse(null).create(te.getWorld()));
					if (e instanceof BeetleEntity) {
						((BeetleEntity) e).setSize(larva.size);
						((BeetleEntity) e).setMaxHealthMult(larva.maxhealth);
						((BeetleEntity) e).setDamageMult(larva.damage);
						((BeetleEntity) e).setSpeedMult(larva.speed);
					}
					ItemStack newstack = ItemRegistry.LARVA_JAR.getDefaultStack();
					NbtCompound tag = new NbtCompound();
					e.writeNbt(tag);
					e.writeCustomDataToNbt(tag);
					NbtCompound nbt = newstack.getOrCreateNbt();
					nbt.put("EntityTag", tag);
					Text custom_name = e.getCustomName();
					if (custom_name != null && !custom_name.getString().isEmpty()) {
						nbt.putString("EntityName", custom_name.getString());
					}
					nbt.putString("EntityType", EntityType.getId(e.getType()).toString());
					newstack.setCount(1);
					newstack.setNbt(nbt);
					player.giveItemStack(newstack);
					handstack.decrement(1);
					te.setLarva(null);
					return ActionResult.SUCCESS;
				} else if (handstack.isOf(ItemRegistry.BEETLE_JELLY) && te.getStack(4) == ItemStack.EMPTY) {
					ItemStack item_new = handstack.copy();
					item_new.setCount(1);
					te.setStack(4, item_new);
					if (!player.isCreative()) {
						ItemStack item_old = handstack;
						item_old.decrement(1);
						player.setStackInHand(player.getActiveHand(), item_old);
					}
					return ActionResult.SUCCESS;
				} else {
					int decor_id = TankBlockEntity.getDecorId(handstack);
					if (decor_id != -1) {
						if (te.decor[decor_id]) {
							player.getMainHandStack().increment(1);
						} else {
							player.getMainHandStack().decrement(1);
						}
						te.flipDecor(decor_id);
						return ActionResult.SUCCESS;
					}
					if (te.isValid(te.getTopStackId() + 1, handstack)) {
						ItemStack item_new = handstack.getItem().getDefaultStack();
						item_new.setCount(1);
						if (te.getTopStackId() + 1 == 3) {
							if (te.isContainedFull()) {
								return ActionResult.FAIL;
							} else {
								player.giveItemStack(te.getStack(3));
							}
						}
						te.putTopStack(item_new);
						if (!player.isCreative()) {
							ItemStack item_old = handstack;
							item_old.decrement(1);
							player.setStackInHand(player.getActiveHand(), item_old);
						}
						return ActionResult.SUCCESS;
					}
				}
			}
		}
		return ActionResult.CONSUME;
	}

}
