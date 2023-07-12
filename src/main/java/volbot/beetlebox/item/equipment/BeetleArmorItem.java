package volbot.beetlebox.item.equipment;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleArmorItem extends ArmorItem implements FabricElytraItem {

	public static EntityAttributeModifier speed_boost_attribute = new EntityAttributeModifier(
			EntityAttributes.GENERIC_MOVEMENT_SPEED.getTranslationKey(), 1.2,
			EntityAttributeModifier.Operation.MULTIPLY_BASE);

	public BeetleArmorItem(ChitinMaterial mat, Type type, Settings settings) {
		super(mat, type, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (entity instanceof LivingEntity) {
			if (((LivingEntity) entity).getEquippedStack(getSlotType()).getItem() instanceof BeetleArmorItem) {
				if (slot == EquipmentSlot.LEGS.getEntitySlotId()) {
					if (stack.getOrCreateNbt().contains("beetle_legs_wallclimb")) {
						BeetleArmorAbilities.wallClimb((PlayerEntity) entity);
					}
					if (stack.getOrCreateNbt().contains("beetle_legs_2jump")) {
						NbtCompound nbt = stack.getOrCreateNbt();
						if (entity.isOnGround()) {
							nbt.putInt("doubleJump", 0);
						} else if (nbt.getInt("doubleJump") == 0) {
							if (!isJumping()) {
								nbt.putInt("doubleJump", 1);
							}
						} else if (nbt.getInt("doubleJump") == 1) {
							if (isJumping()) {
								BeetleArmorAbilities.second_jump((LivingEntity) entity);
								nbt.putInt("doubleJump", 2);
							}
						}
					}
				} else if (slot == EquipmentSlot.HEAD.getEntitySlotId()) {
					if (stack.getOrCreateNbt().contains("beetle_helmet_nv")) {
						StatusEffectInstance curr = ((PlayerEntity) entity).getActiveStatusEffects()
								.get(StatusEffects.NIGHT_VISION);
						if (curr == null || curr.isDurationBelow(300)) {
							((PlayerEntity) entity).addStatusEffect(
									new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, true, false));
						}
					}
				} else if (slot == EquipmentSlot.FEET.getEntitySlotId()) {
					if (stack.getOrCreateNbt().contains("beetle_boots_step")) {
						((LivingEntity) entity).setStepHeight(1.0f);
					}
				} else if (slot == EquipmentSlot.CHEST.getEntitySlotId()) {
					if (stack.getOrCreateNbt().contains("beetle_chest_boost")) {
						NbtCompound nbt = stack.getOrCreateNbt();
						if (entity.isOnGround()) {
							nbt.putInt("elytraBoost", 3);
						}
					}
				}
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		switch (this.getSlotType()) {
		case HEAD:
			if (stack.getOrCreateNbt().contains("beetle_helmet_attack")) {
				switch (BeetleArmorAbilities.beetle_abilities.get(this.material.getName())) {
				case "flip":
					tooltip.add(Text.literal("Ability: Flip").formatted(Formatting.GRAY));
					tooltip.add(Text.literal("Launches enemies into air on hit").formatted(Formatting.DARK_GRAY));
					break;
				case "pinch":
					tooltip.add(Text.literal("Ability: Pinch").formatted(Formatting.GRAY));
					tooltip.add(Text.literal("Deals extra, delayed damage").formatted(Formatting.DARK_GRAY));
					break;
				case "headbutt":
					tooltip.add(Text.literal("Ability: Headbutt").formatted(Formatting.GRAY));
					tooltip.add(Text.literal("Stuns enemies, slowing them").formatted(Formatting.DARK_GRAY));
					break;
				}
			}
			if (stack.getOrCreateNbt().contains("beetle_helmet_nv")) {
				tooltip.add(Text.literal("Ability: Nocturnal").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Enables night vision").formatted(Formatting.DARK_GRAY));
			}

			break;
		case CHEST:
			if (stack.getOrCreateNbt().contains("beetle_chest_elytra")) {
				tooltip.add(Text.literal("Ability: Beetle Elytra").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Enables elytra flight").formatted(Formatting.DARK_GRAY));
				tooltip.add(Text.literal(
						"   Press " + BeetleBoxClient.elytra_boost_keybind.getBoundKeyLocalizedText().getString()
								+ " to start flying")
						.formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));

			}
			if (stack.getOrCreateNbt().contains("beetle_chest_boost")) {
				tooltip.add(Text.literal("Ability: Elytra Boost").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Provides thrust while flying").formatted(Formatting.DARK_GRAY));
				tooltip.add(Text.literal("   Press "
						+ BeetleBoxClient.elytra_boost_keybind.getBoundKeyLocalizedText().getString() + " to boost")
						.formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));

			}
			break;
		case LEGS:
			if (stack.getOrCreateNbt().contains("beetle_legs_wallclimb")) {
				tooltip.add(Text.literal("Ability: Wall Crawler").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Enables wall climbing").formatted(Formatting.DARK_GRAY));
				tooltip.add(Text.literal("   Sneak to stop in place").formatted(Formatting.DARK_GRAY)
						.formatted(Formatting.ITALIC));
				tooltip.add(Text.literal("   Press "
						+ BeetleBoxClient.wallclimb_keybind.getBoundKeyLocalizedText().getString() + " to toggle")
						.formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));
			}
			if (stack.getOrCreateNbt().contains("beetle_legs_2jump")) {
				tooltip.add(Text.literal("Ability: Double Jump").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Adds a second, higher jump").formatted(Formatting.DARK_GRAY));
				tooltip.add(Text.literal("   Cancels elytra flight for a precise landing")
						.formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));
			}
			break;
		case FEET:
			if (stack.getOrCreateNbt().contains("beetle_boots_falldamage")) {
				tooltip.add(Text.literal("Ability: Velocity Protection").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Negates damage from falling and elytra collision")
						.formatted(Formatting.DARK_GRAY));
			}
			if (stack.getOrCreateNbt().contains("beetle_boots_speed")) {
				tooltip.add(Text.literal("Ability: Speed Boost").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Enables faster movement").formatted(Formatting.DARK_GRAY));
			}
			if (stack.getOrCreateNbt().contains("beetle_boots_step")) {
				tooltip.add(Text.literal("Ability: Step Height Boost").formatted(Formatting.GRAY));
				tooltip.add(Text.literal("Enables full-block step height").formatted(Formatting.DARK_GRAY));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
		if (chestStack.getOrCreateNbt().contains("beetle_chest_elytra")) {
			doVanillaElytraTick(entity, chestStack);
			return true;
		} else {
			return false;
		}
	}

	private boolean isJumping() {
		MinecraftClient client = MinecraftClient.getInstance();
		return client != null && client.player != null && client.player.input.jumping;
	}

}
