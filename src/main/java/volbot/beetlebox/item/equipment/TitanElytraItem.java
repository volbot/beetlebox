package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class TitanElytraItem extends BeetleElytraItem {
	
	public TitanElytraItem(Settings settings) {
		super(new ChitinMaterial.TitanChitinMaterial(), settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity, ItemStack chestStack) {
		return false;
	}
}
