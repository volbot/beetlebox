package volbot.beetlebox.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class BeetleItemUpgrade extends Item {

	public String id;
	
	public BeetleItemUpgrade(String id, Settings settings) {
		super(settings.rarity(Rarity.UNCOMMON));
		this.id=id;
	}
	
}