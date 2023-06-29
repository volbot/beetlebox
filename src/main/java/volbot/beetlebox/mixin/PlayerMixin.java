package volbot.beetlebox.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.BeetleArmorAbilities;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import java.util.Collection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Multimap;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "attack", at = @At(value = "RETURN"))
	private void attack(Entity target, CallbackInfo info) {
		ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);
		if (helmet.getItem() instanceof BeetleArmorItem) {
			Multimap<EntityAttribute, EntityAttributeModifier> attributes = this.getMainHandStack().getItem()
					.getAttributeModifiers(EquipmentSlot.MAINHAND);
			Collection<EntityAttributeModifier> attackModifiers = attributes
					.get(EntityAttributes.GENERIC_ATTACK_DAMAGE);

			if (!attackModifiers.isEmpty() && target instanceof LivingEntity) {
				BeetleArmorAbilities.helmetAttack((LivingEntity) (Object) this, (LivingEntity) target, helmet);
			}
		}
	}
	
	@Override
    public boolean damage(DamageSource source, float amount) {
		ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);
		float new_amount = amount;
		if((boots.getItem() instanceof BeetleArmorItem) && 
				(boots.getOrCreateNbt().contains("beetle_boots_falldamage"))) {
			System.out.println("ubba: "+source.getName());
			System.out.println("gubba: "+DamageSource.FLY_INTO_WALL.getName());
			if(source.isFromFalling() ||
					source.getName() == DamageSource.FLY_INTO_WALL.getName()) {
				new_amount = 0f;
			}
		}
		return super.damage(source, new_amount);
	}
}