package volbot.beetlebox.item.equipment;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleArmorItem extends ArmorItem implements FabricElytraItem {

	public BeetleArmorItem(ChitinMaterial mat, Type type, Settings settings) {
		super(mat, type, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (entity instanceof LivingEntity) {
			if (((LivingEntity) entity).getEquippedStack(getSlotType()).getItem() instanceof BeetleArmorItem) {
				if (slot == 1) {
					if (stack.getOrCreateNbt().contains("beetle_legs_wallclimb")) {
						BeetleArmorAbilities.wallClimb((PlayerEntity) entity);
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
					tooltip.add(Text.literal("Launches enemies into air on hit").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
					break;
				case "pinch":
					tooltip.add(Text.literal("Ability: Pinch").formatted(Formatting.GRAY));
					tooltip.add(Text.literal("Deals extra, delayed damage").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
					break;
				case "headbutt":
					tooltip.add(Text.literal("Ability: Headbutt").formatted(Formatting.GRAY));
					tooltip.add(Text.literal("Stuns enemies, slowing them").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
					break;
				}
			}
			break;
		case CHEST:
			if (stack.getOrCreateNbt().contains("beetle_chest_elytra")) {
				tooltip.add(Text.literal("Ability: Fall-Flight").formatted(Formatting.GRAY));
			}
			break;
		case LEGS:
			if (stack.getOrCreateNbt().contains("beetle_legs_wallclimb")) {
				tooltip.add(Text.literal("Ability: Wall Climbing").formatted(Formatting.GRAY));
			}
			break;
		case FEET:
			if (stack.getOrCreateNbt().contains("beetle_boots_falldamage")) {
				tooltip.add(Text.literal("Ability: Fall Protection").formatted(Formatting.GRAY));
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
		if(chestStack.getOrCreateNbt().contains("beetle_chest_elytra")) {
			doVanillaElytraTick(entity, chestStack);
			return true;
		} else {
			return false;
		}
	}

}
