package volbot.beetlebox.entity.ai;

import java.util.EnumSet;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import volbot.beetlebox.entity.beetle.BeetleEntity;

public class BeetleFlyToTreeGoal extends FlyGoal {
    protected final BeetleEntity beetle;
    protected double x;
    protected double y;
    protected double z;
    private boolean flightTarget = false;

    public BeetleFlyToTreeGoal(BeetleEntity beetle, double d) {
        super(beetle, d);
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        this.beetle = beetle;
    }

    @Override
    public boolean canStart() {
        if ((beetle.getTarget() != null && beetle.getTarget().isAlive())) {
            return false;
        } else {
            if (this.beetle.getRandom().nextInt(30) != 0 && !beetle.isFlying()) {
                return false;
            }
            if (this.beetle.isOnGround()) {
                this.flightTarget = beetle.getRandom().nextBoolean();
            } else {
                this.flightTarget = beetle.getRandom().nextInt(5) > 0 && beetle.timeFlying < 200;
            }
            Vec3d lvt_1_1_ = this.getWanderTarget();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.x = lvt_1_1_.x;
                this.y = lvt_1_1_.y;
                this.z = lvt_1_1_.z;
                return true;
            }
        }
    }

    public void tick() {
        if (flightTarget) {
            beetle.getMoveControl().moveTo(x, y, z, 1F);
        } else {
            this.beetle.getNavigation().startMovingTo(x, y, z, 1F);

            if (beetle.isFlying() && beetle.isOnGround()) {
                beetle.setFlying(false);
            }
        }

        if (beetle.isFlying() && beetle.isOnGround() && beetle.timeFlying > 10) {
            beetle.setFlying(false);
        }
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = null;
        if (this.mob.isTouchingWater()) {
            vec3d = FuzzyTargeting.find(this.mob, 15, 15);
        }
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            vec3d = this.locateTree();
        }
        return vec3d == null ? super.getWanderTarget() : vec3d;
    }

    @Nullable
    private Vec3d locateTree() {
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        Iterable<BlockPos> iterable = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 3.0), MathHelper.floor(this.mob.getY() - 6.0), MathHelper.floor(this.mob.getZ() - 3.0), MathHelper.floor(this.mob.getX() + 3.0), MathHelper.floor(this.mob.getY() + 6.0), MathHelper.floor(this.mob.getZ() + 3.0));
        for (BlockPos blockPos2 : iterable) {
            BlockState blockState;
            if (blockPos.equals(blockPos2) || !((blockState = this.mob.getEntityWorld().getBlockState(mutable2.set((Vec3i)blockPos2, Direction.DOWN))).getBlock() instanceof LeavesBlock || blockState.isIn(BlockTags.LOGS)) || !this.mob.getEntityWorld().isAir(blockPos2) || !this.mob.getEntityWorld().isAir(mutable.set((Vec3i)blockPos2, Direction.UP))) continue;
            return Vec3d.ofBottomCenter(blockPos2);
        }
        return null;
    }
    
    public void start() {
        if (flightTarget) {
            beetle.setFlying(true);
            beetle.getMoveControl().moveTo(x, y, z, x);
        } else {
            this.beetle.getNavigation().startMovingTo(this.x, this.y, this.z, 1F);
        }
    }

    public void stop() {
        this.beetle.getNavigation().stop();
        super.stop();
    }
}
