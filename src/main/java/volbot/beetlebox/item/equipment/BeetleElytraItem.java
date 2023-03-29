package volbot.beetlebox.item.equipment;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class BeetleElytraItem extends ArmorItem implements FabricElytraItem {

	public BeetleElytraItem(Settings settings) {
		super(new ChitinMaterial(), Type.CHESTPLATE, settings);
	}
	
	@Override
	public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
		doVanillaElytraTick(entity, chestStack);
		return true;
	}

}