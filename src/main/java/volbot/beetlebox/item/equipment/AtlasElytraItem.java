package volbot.beetlebox.item.equipment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class AtlasElytraItem extends BeetleElytraItem {
	
	public AtlasElytraItem(Settings settings) {
		super(new ChitinMaterial.AtlasChitinMaterial(), settings);
	}

	@Override
	public boolean beetleBuff(LivingEntity entity, ItemStack chestStack) {
		return false;
	}
}
