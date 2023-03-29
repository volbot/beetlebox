package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;

public class JRBElytraItem extends BeetleElytraItem {
	
	public JRBElytraItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity) {
		return false;
	}
}
