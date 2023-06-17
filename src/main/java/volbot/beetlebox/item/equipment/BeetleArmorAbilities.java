package volbot.beetlebox.item.equipment;

import java.util.HashMap;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;

public class BeetleArmorAbilities {

	public static void wallClimb(PlayerEntity player) {
		if (player.horizontalCollision && !player.isSwimming()) {
			if (player.isFallFlying()) {
				player.stopFallFlying();
			}
			if (!player.isOnGround() && player.isSneaking()) {
				player.setVelocity(player.getVelocity().x, 0.0D, player.getVelocity().z);
			} else {
				player.setVelocity(player.getVelocity().x, 0.2D, player.getVelocity().z);
			}
		}
	}

	public static HashMap<String, String> beetle_abilities = new HashMap<>();

	public static void helmetAttack(LivingEntity attacker, LivingEntity target, ItemStack helmet) {
		ArmorItem armorItem = (ArmorItem) helmet.getItem();
		if(((BeetleArmorItem)armorItem).tier==1) {
			return;
		}
		ArmorMaterial armorMaterial = armorItem.getMaterial();
		switch (beetle_abilities.get(armorMaterial.getName())) {
		case "flip":
			if (attacker.isOnGround()) {
				target.setVelocity(target.getVelocity().x, 0.9, target.getVelocity().z);
			} else {
				target.addVelocity(0, 0.625, 0);
			}
			break;
		case "pinch":
			((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 25, 2), attacker);
			break;
		case "headbutt":
			((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 50, 2), attacker);
			break;
		}
	}
}
