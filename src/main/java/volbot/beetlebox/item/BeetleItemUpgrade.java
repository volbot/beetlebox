package volbot.beetlebox.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class BeetleItemUpgrade extends Item {

	public String id;
	public EquipmentSlot slot;
	
	public BeetleItemUpgrade(String id, EquipmentSlot slot, Settings settings) {
		super(settings.rarity(Rarity.UNCOMMON));
		this.id=id;
		this.slot=slot;
	}
	
}