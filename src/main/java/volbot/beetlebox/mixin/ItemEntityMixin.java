package volbot.beetlebox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import volbot.beetlebox.item.BeetleItemUpgrade;
import volbot.beetlebox.item.equipment.BeetleArmorItem;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

	@Inject(method = "tryMerge(Lnet/minecraft/entity/ItemEntity;)V", at = @At(value = "HEAD"))
	private void tryMerge(ItemEntity other, CallbackInfo info) {
		ItemStack stack1 = ((ItemEntity) (Object) this).getStack();
		ItemStack stack2 = other.getStack();
		if (stack1.getItem() instanceof BeetleArmorItem) {
			if (stack2.getItem() instanceof BeetleItemUpgrade) {
				String potential_upgrade = ((BeetleItemUpgrade) stack2.getItem()).id;
				if (!stack1.getOrCreateNbt().contains(potential_upgrade)) {
					EquipmentSlot armor_type = ((ArmorItem) stack1.getItem()).getSlotType();
					EquipmentSlot upgrade_type = ((BeetleItemUpgrade) stack2.getItem()).slot;
					if (armor_type.equals(upgrade_type)) {
						stack2.decrement(1);
						stack1.getOrCreateNbt().putBoolean(potential_upgrade, true);
					}
				}
			}
			((ItemEntity) (Object) this).setStack(stack1);
		}
		if (stack2.getItem() instanceof BeetleArmorItem) {
			if (stack1.getItem() instanceof BeetleItemUpgrade) {
				String potential_upgrade = ((BeetleItemUpgrade) stack1.getItem()).id;
				if (!stack2.getOrCreateNbt().contains(potential_upgrade)) {
					EquipmentSlot armor_type = ((ArmorItem) stack2.getItem()).getSlotType();
					EquipmentSlot upgrade_type = ((BeetleItemUpgrade) stack1.getItem()).slot;
					if (armor_type.equals(upgrade_type)) {
						stack1.decrement(1);
						stack2.getOrCreateNbt().putBoolean(potential_upgrade, true);
					}
				}
			}
			((ItemEntity) (Object) this).setStack(stack1);
		}
	}
	
	@Inject(method = "canMerge()Z", at = @At(value = "HEAD"), cancellable = true)
	private void canMerge(CallbackInfoReturnable<Boolean> info) {
		Item item = ((ItemEntity)(Object)this).getStack().getItem();
		if(item instanceof BeetleItemUpgrade || item instanceof BeetleArmorItem) {
			info.setReturnValue(true);
		}
	}
}
