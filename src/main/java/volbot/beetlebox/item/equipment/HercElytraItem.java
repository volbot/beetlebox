package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class HercElytraItem extends BeetleElytraItem {
	
	public HercElytraItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity, ItemStack chestStack) {
		return false;
	}
}
