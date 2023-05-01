package volbot.beetlebox.item;

import net.minecraft.item.Item;

public class FruitSyrup extends Item {

	private Item fruit;
	
	public FruitSyrup(Item fruit, Settings settings) {
		super(settings);
		this.fruit = fruit;
	}
	
}
