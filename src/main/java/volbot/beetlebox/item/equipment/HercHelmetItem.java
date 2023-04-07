package volbot.beetlebox.item.equipment;

import net.minecraft.item.ArmorItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class HercHelmetItem extends ArmorItem {

	public HercHelmetItem(Settings settings) {
		super(new ChitinMaterial.HercChitinMaterial(), Type.HELMET, settings);
	}
}
