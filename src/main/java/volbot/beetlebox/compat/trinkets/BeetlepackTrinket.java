package volbot.beetlebox.compat.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.item.equipment.BeetlepackItem;

public class BeetlepackTrinket extends BeetlepackItem implements Trinket {

	public BeetlepackTrinket(Settings settings) {
		super(settings);
	}

	@Override
	public void onEquip(ItemStack newStack, SlotReference slot, LivingEntity entity) {
		//TRINKETS STACK EQUIP METHOD!
		//THIS SHOULD UNEQUIP CHEST BEETLEPACK IF POSSIBLE
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		this.inventoryTick(stack, entity.getWorld(), entity, slot.index(), false);
	}
}
