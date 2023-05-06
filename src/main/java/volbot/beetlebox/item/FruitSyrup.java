package volbot.beetlebox.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import volbot.beetlebox.registry.BeetleRegistry;

public class FruitSyrup extends Item {
	
	public FruitSyrup(Settings settings) {
		super(settings);
	}
	
	public static int getColor(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		String fruit = nbt.getString("FruitType");
		if(stack.isOf(BeetleRegistry.APPLE_SYRUP) || fruit.equals("apple")) {
			return 0xDD7777;
		} else if(stack.isOf(BeetleRegistry.MELON_SYRUP) || fruit.equals("melon")) {
			return 0x77DD77;
		} else if(stack.isOf(BeetleRegistry.BERRY_SYRUP) || fruit.equals("berry")) {
			return 0xDD3333;
		} else if(stack.isOf(BeetleRegistry.SUGAR_SYRUP) || fruit.equals("sugar")) {
			return 0xBB8877;
		} else if(stack.isOf(BeetleRegistry.GLOW_SYRUP) || fruit.equals("glow")) {
			return 0xFFFF99;
		}
		return 0x000000;
	}

	
}
