package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;

public class TitanElytraItem extends BeetleElytraItem {
	
	public TitanElytraItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity) {
		return false;
	}
}
