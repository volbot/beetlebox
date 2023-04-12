package volbot.beetlebox.item.tools;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import volbot.beetlebox.entity.beetle.BeetleEntity;

public class BeetleJarItem extends Item {
	
		public boolean beetlesOnly;
	
	    public BeetleJarItem(Settings settings, boolean beetlesOnly) {
	        super(settings.maxCount(1));
	        this.beetlesOnly = beetlesOnly;
	    }
	    
	    @Override
	    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
	    	NbtCompound nbt = stack.getNbt();
	    	if(nbt == null) {
		    	tooltip.add(Text.literal("Contained: None"));
		    	return;
	    	}
	    	EntityType<?> e = EntityType.get(nbt.getString("EntityType")).orElse(null);
	    	if(e == null) {
		    	tooltip.add(Text.literal("Contained: None"));
	    	} else {
	    		tooltip.add(Text.literal("Contained: ").append(e.getName()));
	    	}
	    }
	    
	    @Override
	    public ActionResult useOnBlock(ItemUsageContext context) {
	        World world = context.getWorld();
	        if (!(world instanceof ServerWorld)) {
	            return ActionResult.SUCCESS;
	        }
	        ItemStack itemStack = context.getStack();
	        BlockPos blockPos = context.getBlockPos();
	        Direction direction = context.getSide();
	        BlockState blockState = world.getBlockState(blockPos);
	        BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
	        NbtCompound nbt = itemStack.getNbt();
	        if(nbt == null) {
	        	return ActionResult.FAIL;
	        }
	        EntityType<?> entityType2 = EntityType.get(nbt.getString("EntityType")).orElse(null);
	        if(entityType2 == null) {
	        	return ActionResult.FAIL;
	        }
	        Entity temp = entityType2.create(world);
	        temp.readNbt(nbt.getCompound("EntityTag"));
            temp.teleport(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
	        if (world.spawnEntity(temp) != false) {
	            itemStack.removeSubNbt("EntityTag");
	            itemStack.removeSubNbt("EntityType");
	        	world.emitGameEvent((Entity)context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
	        }
	        return ActionResult.CONSUME;
	    }
	    
	    @Override
	    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
	        ItemStack itemStack = user.getStackInHand(hand);
	        BlockHitResult hitResult = SpawnEggItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
	        if (((HitResult)hitResult).getType() != HitResult.Type.BLOCK) {
	            return TypedActionResult.pass(itemStack);
	        }
	        if (!(world instanceof ServerWorld)) {
	            return TypedActionResult.success(itemStack);
	        }
	        BlockHitResult blockHitResult = hitResult;
	        BlockPos blockPos = blockHitResult.getBlockPos();
	        if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
	            return TypedActionResult.pass(itemStack);
	        }
	        if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
	            return TypedActionResult.fail(itemStack);
	        }
	        EntityType<?> entityType = EntityType.get(itemStack.getNbt().getString("EntityType")).orElse(null);
	        Entity temp = entityType.create(world);
	        temp.readNbt(itemStack.getNbt().getCompound("EntityTag"));
            temp.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ());
	        world.spawnEntity(temp);
            itemStack.removeSubNbt("EntityTag");
	        itemStack.removeSubNbt("EntityType");
	        return TypedActionResult.success(itemStack);
	    }
	    
	    
}
