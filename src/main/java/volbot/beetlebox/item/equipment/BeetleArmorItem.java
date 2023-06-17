package volbot.beetlebox.item.equipment;

import java.util.List;

import org.jetbrains.annotations.Nullable;

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

public class BeetleArmorItem extends ArmorItem {
	
	public BeetleArmorItem(ChitinMaterial mat, Type type, int tier, Settings settings) {
		super(mat, type, settings);		
		this.tier=tier;
	}

	public int tier;

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if(this.tier==1) {
			return;
		}
		if (entity instanceof LivingEntity) {
			if (((LivingEntity) entity).getEquippedStack(getSlotType()).getItem() instanceof BeetleArmorItem) {
				if (slot == 1) {
					BeetleArmorAbilities.wallClimb((PlayerEntity) entity);
				}
				if (slot == 0) {
					entity.fallDistance = Math.min(1, entity.fallDistance);
				}
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		switch (this.getSlotType()) {
		case HEAD:
			switch (BeetleArmorAbilities.beetle_abilities.get(this.material.getName())) {
			case "flip":
				tooltip.add(Text.literal("Ability: Flip"));
				tooltip.add(Text.literal("Launches enemies into air on hit").formatted(Formatting.ITALIC));
				break;
			case "pinch":
				tooltip.add(Text.literal("Ability: Pinch"));
				tooltip.add(Text.literal("Deals extra, delayed damage").formatted(Formatting.ITALIC));
				break;
			case "headbutt":
				tooltip.add(Text.literal("Ability: Headbutt"));
				tooltip.add(Text.literal("Stuns enemies, slowing them").formatted(Formatting.ITALIC));
				break;
			}
			break;
		case CHEST:
			tooltip.add(Text.literal("Ability: Fall-Flight"));
			break;
		case LEGS:
			tooltip.add(Text.literal("Ability: Wall Climbing"));
			break;
		case FEET:
			tooltip.add(Text.literal("Ability: Fall Protection"));
			break;
		default:
			break;
		}
	}

}
