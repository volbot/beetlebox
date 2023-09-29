package volbot.beetlebox.block;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleTankBlock<T extends LivingEntity> extends BlockWithEntity {

	Class<T> clazz;

	public BeetleTankBlock(Settings settings, Class<T> clazz) {
		super(settings);
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

	protected ActionResult tankLogic(World world, BlockPos pos, PlayerEntity player) {
		TankBlockEntity te = world.getBlockEntity(pos, BlockRegistry.TANK_BLOCK_ENTITY).orElse(null);
		ItemStack handstack = player.getMainHandStack();
		if (te != null) {
			if (handstack.isEmpty()) {
				int id = te.getTopStackId();
				if (id == 0) {
					if (te.getContained(0) != null) {
						return ActionResult.CONSUME;
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
					NbtCompound nbt = jar_stack.getOrCreateNbt();
					if (!nbt.contains("EntityType") && te.getContained(0) != null) {
						ContainedEntity contained = te.popContained();
						LivingEntity e = (LivingEntity) ((EntityType.get(contained.contained_id).orElse(null)
								.create(te.getWorld())));
						if (!jar_item.canStore(e)) {
							return ActionResult.FAIL;
						}
						nbt.putString("EntityType", contained.contained_id);
						String custom_name = contained.custom_name;
						if (!custom_name.isEmpty()) {
							nbt.putString("EntityName", custom_name);
						}
						nbt.put("EntityTag", contained.entity_data);
						jar_stack.setNbt(nbt);
						return ActionResult.SUCCESS;
					} else if (nbt.contains("EntityType") && te.canAcceptEntity()) {
						Entity e = EntityType.get(nbt.getString("EntityType")).orElse(null).create(te.getWorld());
						if (!this.canStore(e)) {
							return ActionResult.FAIL;
						}
						te.pushContained(new ContainedEntity(nbt.getString("EntityType"), nbt.getCompound("EntityTag"),
								nbt.getString("EntityName")));
						jar_stack.removeSubNbt("EntityName");
						jar_stack.removeSubNbt("EntityTag");
						jar_stack.removeSubNbt("EntityType");
						return ActionResult.SUCCESS;
					}
				} else {
					if (te.isValid(te.getTopStackId(), handstack)) {
						if (handstack.isOf(te.getTopStack().getItem())
								&& handstack.getCount() < handstack.getMaxCount()) {
							te.setStack(te.getTopStackId(), ItemStack.EMPTY);
							if (!player.isCreative()) {
								ItemStack item = handstack;
								item.increment(1);
								player.setStackInHand(player.getActiveHand(), item);
							}
							return ActionResult.SUCCESS;
						} else {
							ItemStack item_new = handstack.getItem().getDefaultStack();
							item_new.setCount(1);
							if (te.getTopStackId() == 3) {
								player.giveItemStack(te.getTopStack());
							}
							te.putTopStack(item_new);
							if (!player.isCreative()) {
								ItemStack item_old = handstack;
								item_old.decrement(1);
								player.setStackInHand(player.getActiveHand(), item_old);
							}
							return ActionResult.SUCCESS;
						}
					} else if(te.getTopStackId() < te.size()-1 && te.isValid(te.getTopStackId()+1, handstack)) {
						ItemStack item_new = handstack.getItem().getDefaultStack();
						item_new.setCount(1);
						if (te.getTopStackId() == 3) {
							player.giveItemStack(te.getTopStack());
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
