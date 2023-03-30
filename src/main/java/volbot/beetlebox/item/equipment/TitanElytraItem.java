package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TitanElytraItem extends BeetleElytraItem {
	
	public TitanElytraItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity, ItemStack chestStack) {
		return false;
	}
}
