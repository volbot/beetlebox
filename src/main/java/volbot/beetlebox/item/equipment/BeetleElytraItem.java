package volbot.beetlebox.item.equipment;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public abstract class BeetleElytraItem extends ArmorItem implements FabricElytraItem {

	public BeetleElytraItem(ChitinMaterial mat, Settings settings) {
		super(mat, Type.CHESTPLATE, settings);
	}
	
	@Override
	public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
		doVanillaElytraTick(entity, chestStack);
		beetleBuff(entity, chestStack);
		return true;
	}
	
	public abstract boolean beetleBuff(LivingEntity entity, ItemStack chestStack);
}
