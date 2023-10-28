package volbot.beetlebox.item.equipment.materials;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import volbot.beetlebox.item.equipment.BeetleArmorItem;

public class DormantUpgrade extends Item {

	public DormantUpgrade(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.addAll(Text.of("Feed to a beetle to activate").getWithStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromFormatting(Formatting.GRAY))));
		BeetleArmorItem.appendUpgradeTooltips(stack,world,tooltip,context);
	}
}
