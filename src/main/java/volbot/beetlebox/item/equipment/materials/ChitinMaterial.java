package volbot.beetlebox.item.equipment.materials;

import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ChitinMaterial implements ArmorMaterial {
	
	protected static int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
	protected static int[] PROTECTION_VALUES = new int[] {3, 6, 8, 3}; 
	private final String beetle_id;
	private final Ingredient elytron_ingredient;
	
	public ChitinMaterial(String beetle_id, Item elytron) {
		this.beetle_id = beetle_id;
		this.elytron_ingredient = Ingredient.ofItems(elytron);
	}
	
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
		return elytron_ingredient;
	}

	@Override
	public String getName() {
		return beetle_id;
	}

	@Override
	public float getToughness() {
		return 2F;
	}

	@Override
	public float getKnockbackResistance() {
		return 0F;
	}
}
