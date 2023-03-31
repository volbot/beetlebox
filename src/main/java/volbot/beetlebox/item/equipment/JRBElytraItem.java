package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class JRBElytraItem extends BeetleElytraItem {
	
	public JRBElytraItem(Settings settings) {
		super(new ChitinMaterial.JRBChitinMaterial(), settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity, ItemStack chestStack) {
		return false;
	}
}
