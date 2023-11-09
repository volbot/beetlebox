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
		if (newStack.getItem() instanceof BeetlepackItem && entity instanceof PlayerEntity) {
			PlayerEntity user = (PlayerEntity) entity;
			if (entity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof BeetlepackItem) {
				if (user.getWorld().isClient()) {
					return;
				}
				if (user.getInventory().getEmptySlot() == -1) {
					ItemStack newNewStack = newStack.copy();
					newStack.setCount(0);
					user.dropStack(newNewStack);
				} else {
					user.giveItemStack(newStack);
				}
			}
		}
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		this.inventoryTick(stack, entity.getWorld(), entity, slot.index(), false);
	}
}
