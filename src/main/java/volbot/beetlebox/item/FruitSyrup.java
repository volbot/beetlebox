package volbot.beetlebox.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class FruitSyrup extends Item {
	
	public FruitSyrup(Settings settings) {
		super(settings);
	}
	
	public static int getColor(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		String fruit = nbt.getString("FruitType");
		if(fruit.equals("apple")) {
			return 0xDD7777;
		} else if(fruit.equals("melon")) {
			return 0x77DD77;
		} else if(fruit.equals("berry")) {
			return 0xDD3333;
		} else if(fruit.equals("sugar")) {
			return 0xBB8877;
		} else if(fruit.equals("glow")) {
			return 0xFFFF99;
		}
		return 0x000000;
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		String fruit = nbt.getString("FruitType");
        return this.getTranslationKey()+"."+fruit;
    }

	
}
