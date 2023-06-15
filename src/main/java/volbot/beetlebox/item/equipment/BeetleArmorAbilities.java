package volbot.beetlebox.item.equipment;

import net.minecraft.entity.player.PlayerEntity;

public class BeetleArmorAbilities {

	public static void wallClimb(PlayerEntity player) {
		if (player.horizontalCollision && !player.isSwimming()) {
			if(player.isFallFlying()) {
				player.stopFallFlying();
			}
			if (!player.isOnGround() && player.isSneaking()) {
				player.setVelocity(player.getVelocity().x, 0.0D, player.getVelocity().z);
			} else {
				player.setVelocity(player.getVelocity().x, 0.2D, player.getVelocity().z);
			}
		}
	}
}
