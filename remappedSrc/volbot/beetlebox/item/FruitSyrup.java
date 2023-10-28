package volbot.beetlebox.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import volbot.beetlebox.registry.ItemRegistry;

public class FruitSyrup extends Item {
	
	public FruitSyrup(Settings settings) {
		super(settings);
	}
	
	public static int getColor(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		String fruit = nbt.getString("FruitType");
		if(stack.isOf(ItemRegistry.APPLE_SYRUP) || fruit.equals("apple")) {
			return 0xAA5555;
		} else if(stack.isOf(ItemRegistry.MELON_SYRUP) || fruit.equals("melon")) {
			return 0x77DD77;
		} else if(stack.isOf(ItemRegistry.BERRY_SYRUP) || fruit.equals("berry")) {
			return 0xDD3333;
		} else if(stack.isOf(ItemRegistry.SUGAR_SYRUP) || fruit.equals("sugar")) {
			return 0xBB8877;
		} else if(stack.isOf(ItemRegistry.CACTUS_SYRUP) || fruit.equals("cactus")) {
			return 0xFFFF99;
		}
		return 0xFFFFFF;
	}

	
}
