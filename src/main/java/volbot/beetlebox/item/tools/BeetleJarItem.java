package volbot.beetlebox.item.tools;

import java.util.List;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;

public class BeetleJarItem<T extends LivingEntity> extends Item {

	Class<T> clazz;
	
	public BeetleJarItem(Settings settings, Class<T> clazz) {
		super(settings);
		this.clazz=clazz;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound nbt = stack.getNbt();
		if (nbt == null) {
			tooltip.add(Text.literal("Contained: None").formatted(Formatting.GRAY));
			return;
		}
		EntityType<?> e = EntityType.get(nbt.getString("EntityType")).orElse(null);
		if (e == null) {
			tooltip.add(Text.literal("Contained: None").formatted(Formatting.GRAY));
		} else {
			tooltip.add(Text.literal("Contained: ").append(e.getName()).formatted(Formatting.GRAY));
		}
	}
	
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}
		ItemStack itemStack = context.getStack();
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockState blockState = world.getBlockState(blockPos);
		BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos
				: blockPos.offset(direction);
		NbtCompound nbt = itemStack.getOrCreateNbt();
		if (blockState.getBlock() instanceof BeetleTankBlock) {
			return ActionResult.CONSUME;
		} else {
			if (nbt == null || !nbt.contains("EntityType")) {
				return ActionResult.FAIL;
			}
			EntityType<?> entityType2 = EntityType.get(nbt.getString("EntityType")).orElse(null);
			if (entityType2 == null) {
				return ActionResult.FAIL;
			}
			LivingEntity temp = (LivingEntity) entityType2.create(world);
			temp.readNbt(nbt.getCompound("EntityTag"));
			temp.readCustomDataFromNbt(nbt.getCompound("EntityTag"));
			if (nbt.contains("EntityName")) {
				temp.setCustomName(Text.of(nbt.getString("EntityName")));
				itemStack.removeSubNbt("EntityName");
			} else {
				temp.setCustomName(null);
			}
			temp.teleport(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);
			if (world.spawnEntity(temp) != false) {
				itemStack.removeSubNbt("EntityTag");
				itemStack.removeSubNbt("EntityType");
				world.emitGameEvent((Entity) context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
			} else {
				return ActionResult.FAIL;
			}
			return ActionResult.CONSUME;
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult hitResult = BeetleJarItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		if (((HitResult) hitResult).getType() != HitResult.Type.BLOCK) {
			return TypedActionResult.pass(itemStack);
		}
		if (!(world instanceof ServerWorld)) {
			return TypedActionResult.success(itemStack);
		}
		BlockHitResult blockHitResult = hitResult;
		BlockPos blockPos = blockHitResult.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		if (!(blockState.getBlock() instanceof FluidBlock)) {
			return TypedActionResult.pass(itemStack);
		}
		if (!world.canPlayerModifyAt(user, blockPos)
				|| !user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
			return TypedActionResult.fail(itemStack);
		}
		return TypedActionResult.pass(itemStack);
	}

	public boolean canStore(Entity entity) {
		return clazz.isAssignableFrom(entity.getClass());
	}
	
	public static ContainedEntity getContained(ItemStack stack) {
		NbtCompound nbt = stack.getNbt();
		if(!nbt.contains("EntityType")) {
			return null;
		}
		String contained_id = nbt.getString("EntityType");
		NbtCompound entity_data = nbt.getCompound("EntityData");
		String custom_name = "";
		if(nbt.contains("EntityName")) {
				custom_name = nbt.getString("EntityName");
		}	
		return new ContainedEntity(contained_id,entity_data,custom_name);
	}


}
