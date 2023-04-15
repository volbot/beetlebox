package volbot.beetlebox.item.equipment;

import net.minecraft.item.ArmorItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleArmorItem extends ArmorItem {
	public BeetleArmorItem(ChitinMaterial mat, Type type, Settings settings) {
		super(mat, type, settings);
	}
}
