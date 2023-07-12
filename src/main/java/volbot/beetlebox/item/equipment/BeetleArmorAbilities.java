package volbot.beetlebox.item.equipment;

import java.util.HashMap;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BeetleArmorAbilities {

	public static void wallClimb(PlayerEntity player) {
		ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
		if (legs.getOrCreateNbt().getBoolean("beetle_legs_wallclimb") && player.horizontalCollision && !player.isSwimming()) {
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
		ArmorMaterial armorMaterial = armorItem.getMaterial();
		if (helmet.getOrCreateNbt().contains("beetle_helmet_attack")) {
			switch (beetle_abilities.get(armorMaterial.getName())) {
			case "flip":
				if (attacker.isOnGround()) {
					target.setVelocity(target.getVelocity().x, 0.9, target.getVelocity().z);
				} else {
					target.addVelocity(0, 0.625, 0);
				}
				break;
			case "pinch":
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 25, 2),
						attacker);
				break;
			case "headbutt":
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 50, 2),
						attacker);
				break;
			}
		}
	}

	public static void elytra_boost(PlayerEntity player) {
		ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
		if (chest.getOrCreateNbt().contains("beetle_chest_elytra")) {
			if(!player.isFallFlying()) {
				player.startFallFlying();
			} else if (chest.getOrCreateNbt().contains("beetle_chest_boost")) {
				World world = player.getEntityWorld();
				int boost_charge = chest.getOrCreateNbt().getInt("elytraBoost");
				if (player.isFallFlying() && boost_charge > 0) {
					if (!world.isClient) {
						FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, ItemStack.EMPTY,
								player);
						world.spawnEntity(fireworkRocketEntity);
						chest.getOrCreateNbt().putInt("elytraBoost", boost_charge - 1);
						player.sendMessage(Text.literal("Boosts left: "+(boost_charge - 1)), true);
					}
				}
			}
		}
	}

	public static void second_jump(LivingEntity entity) {
		if(entity.isFallFlying()) {
			((PlayerEntity)entity).stopFallFlying();
		}
		Vec3d velocity = entity.getVelocity();
		entity.setVelocity(velocity.x, 0.75, velocity.z);
	}
	
	public static void toggle_wallclimb(PlayerEntity player) {
		ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
		NbtCompound nbt = legs.getOrCreateNbt();
		if(!nbt.contains("beetle_legs_wallclimb")) {
			return;
		}
		boolean current = nbt.getBoolean("beetle_legs_wallclimb");
		legs.getOrCreateNbt().putBoolean("beetle_legs_wallclimb", !current);
		player.sendMessage(Text.literal("Wall Crawler "+(!current?"Enabled":"Disabled")), true);
	}
}
