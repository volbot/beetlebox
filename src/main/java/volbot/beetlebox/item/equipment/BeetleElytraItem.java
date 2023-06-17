package volbot.beetlebox.item.equipment;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleElytraItem extends BeetleArmorItem implements FabricElytraItem {

	public BeetleElytraItem(ChitinMaterial mat, Settings settings) {
		super(mat, Type.CHESTPLATE, 2, settings.rarity(Rarity.UNCOMMON));
	}
	
	@Override
	public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
		doVanillaElytraTick(entity, chestStack);
		return true;
	}
}
