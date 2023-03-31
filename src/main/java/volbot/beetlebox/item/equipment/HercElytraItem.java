package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class HercElytraItem extends BeetleElytraItem {
	
	public HercElytraItem(Settings settings) {
		super(new ChitinMaterial.HercChitinMaterial(), settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity, ItemStack chestStack) {
		return false;
	}
}
