package volbot.beetlebox.item;

import net.minecraft.item.Item;

public class BeetleItemUpgrade extends Item {

	public String id;
	
	public BeetleItemUpgrade(String id, Settings settings) {
		super(settings);
		this.id=id;
	}
	
}