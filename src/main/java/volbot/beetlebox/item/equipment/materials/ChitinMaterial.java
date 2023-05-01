package volbot.beetlebox.item.equipment.materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ChitinMaterial implements ArmorMaterial {
	
	private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
	private static final int[] PROTECTION_VALUES = new int[] {2, 5, 6, 2}; 
	private final String beetle_id;
	
	public ChitinMaterial(String beetle_id) {
		this.beetle_id = beetle_id;
	}
	
	@Override
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * 333;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot) {
		return PROTECTION_VALUES[slot.getEntitySlotId()];
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
		return beetle_id;
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
