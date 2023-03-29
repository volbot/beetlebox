package volbot.beetlebox.item.equipment;

import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ChitinMaterial implements ArmorMaterial {
	
	private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
	private static final int[] PROTECTION_VALUES = new int[] {2, 5, 6, 2}; 

	@Override
	public int getDurability(Type type) {
		return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * 333;
	}

	@Override
	public int getProtection(Type type) {
		return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
	}

	@Override
	public int getEnchantability() {
		return 21;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_TURTLE;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return null;
	}

	@Override
	public String getName() {
		return "chitin";
	}

	@Override
	public float getToughness() {
		return 0F;
	}

	@Override
	public float getKnockbackResistance() {
		return 0F;
	}

}
