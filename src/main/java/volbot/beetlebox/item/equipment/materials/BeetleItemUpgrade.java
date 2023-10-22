package volbot.beetlebox.item.equipment.materials;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import volbot.beetlebox.item.equipment.BeetleArmorItem;

public class BeetleItemUpgrade extends Item {

	public String id;
	public EquipmentSlot slot;
	
	public BeetleItemUpgrade(String id, EquipmentSlot slot, Settings settings) {
		super(settings.rarity(Rarity.UNCOMMON));
		this.id=id;
		this.slot=slot;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		BeetleArmorItem.appendUpgradeTooltips(stack,world,tooltip,context);
	}
	
}