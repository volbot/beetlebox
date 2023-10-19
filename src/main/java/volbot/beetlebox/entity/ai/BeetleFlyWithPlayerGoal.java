package volbot.beetlebox.entity.ai;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import volbot.beetlebox.entity.beetle.BeetleEntity;

public class BeetleFlyWithPlayerGoal extends Goal {
	private final BeetleEntity beetle;
	private final double speed;
	@Nullable
	private PlayerEntity owner;

	public BeetleFlyWithPlayerGoal(BeetleEntity beetle, double speed) {
		this.beetle = beetle;
		this.speed = speed;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		this.owner = (PlayerEntity) this.beetle.getOwner();
		if (this.owner == null) {
			return false;
		}
		return this.owner.isFallFlying() && this.beetle.getTarget() != this.owner;
	}

	@Override
	public boolean shouldContinue() {
		return this.owner != null && this.owner.isFallFlying();
	}

	@Override
	public void start() {
		if (!beetle.isFlying()) {
			beetle.setFlying(true);
		}
	}

	@Override
	public void stop() {
		this.owner = null;
		this.beetle.getNavigation().stop();
	}

	@Override
	public void tick() {
		this.beetle.getLookControl().lookAt(this.owner, this.beetle.getMaxHeadRotation() + 20,
				this.beetle.getMaxLookPitchChange());
		if (this.beetle.squaredDistanceTo(this.owner) < 6.25) {
			this.beetle.getNavigation().stop();
		} else {
			if (this.beetle.squaredDistanceTo(this.owner) > 20) {
				this.beetle.setVelocity(this.owner.getVelocity().multiply(1.1));
			} else {
				this.beetle.setVelocity(this.owner.getVelocity());
			}
			this.beetle.getNavigation().startMovingTo(this.owner, this.speed);
		}
	}
}
