package volbot.beetlebox.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
	@ModifyArg(
			method = "attack",
			at = @At(value = "INVOKE", 
			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z")
		)
		private DamageSource onDamageTarget(DamageSource source, float amount) {
			//PUT METHOD HERE TO GIVE EFFECTS DamageSource newSource = TerrasteelHelmItem.onEntityAttacked(source, amount, (PlayerEntity) (Object) this, terraWillCritTarget);
			System.out.println("garp");
			return source;
		}
}