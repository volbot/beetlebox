package volbot.beetlebox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin 
extends Entity
implements Attackable {

	@Shadow private int jumpingCooldown;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	public void setJumpingCooldown(int cooldown) {
		this.jumpingCooldown = cooldown;
	}
}
