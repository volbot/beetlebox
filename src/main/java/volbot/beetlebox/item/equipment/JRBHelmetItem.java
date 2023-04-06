package volbot.beetlebox.item.equipment;

import net.minecraft.item.ArmorItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class JRBHelmetItem extends ArmorItem {

	public JRBHelmetItem(Settings settings) {
		super(new ChitinMaterial.JRBChitinMaterial(), Type.HELMET, settings);
	}
}
