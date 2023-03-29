package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;

public class HercElytraItem extends BeetleElytraItem {
	
	public HercElytraItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity) {
		return false;
	}
}
