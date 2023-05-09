package volbot.beetlebox.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class BeetleJelly extends Item {

	public BeetleJelly(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound nbt = stack.getNbt();
		if (nbt == null) {
			return;
		}
		if(!nbt.contains("FruitType") || !nbt.contains("Magnitude") || !nbt.contains("Increase")) {
			return;
		}
		String fruit = nbt.getString("FruitType");
		String fruitstring = " (Unknown Effect)";
		switch(fruit) {
		case "apple":
			fruitstring = " (Max Health)";
			break;
		case "melon":
			fruitstring = " (Size)";
			break;
		case "berry":
			fruitstring = " (Flight Speed)";
			break;
		case "sugar":
			fruitstring = " (Speed)";
			break;
		case "cactus":
			fruitstring = " (Damage)";
			break;
		case "":
			fruit = "Empty";
			fruitstring = "";
			break;
		}	
		tooltip.add(Text.literal(fruit.substring(0, 1).toUpperCase() + fruit.substring(1) + fruitstring));
		int magnitude = nbt.getInt("Magnitude");
		String magstring = "Unknown";
		boolean increase = nbt.getBoolean("Increase");
		String incstring = increase?" Increase":" Decrease";
		switch(magnitude) {
		case 1:
			magstring = "Small";
			break;
		case 5:
			magstring = "Moderate";
			break;
		case 10:
			magstring = "Large";
			break;
		case 25:
			magstring = "Enormous";
			break;
		}
		tooltip.add(Text.literal(magstring+incstring));
	}

}
