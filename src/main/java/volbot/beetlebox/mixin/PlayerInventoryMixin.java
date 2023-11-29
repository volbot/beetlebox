package volbot.beetlebox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import volbot.beetlebox.item.equipment.BeetlepackItem;
import volbot.beetlebox.item.tools.BeetleJarItem;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable {

	@Inject(method = "insertStack(Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "HEAD"), cancellable = true)
	public void insertStack(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (stack.getItem() instanceof BeetleJarItem) {
			PlayerEntity player = ((PlayerInventory) (Object) this).player;
			ItemStack beetlepack = BeetlepackItem.getBeetlepackOnPlayer(player);
			if (!beetlepack.isEmpty()) {
				DefaultedList<ItemStack> beetlepack_inv = DefaultedList.ofSize(6, ItemStack.EMPTY);
				Inventories.readNbt(beetlepack.getOrCreateNbt().getCompound("Inventory"), beetlepack_inv);
				int op = -1;
				for (int i = 0; i < 6; i++) {
					if (beetlepack_inv.get(i).isEmpty()) {
						op = i;
						break;
					}
				}
				if (op != -1) {
					beetlepack_inv.set(op, stack.copy());
					stack.setCount(0);
					NbtCompound inv_nbt = new NbtCompound();
					Inventories.writeNbt(inv_nbt, beetlepack_inv);
					beetlepack.getOrCreateNbt().put("Inventory", inv_nbt);
					info.setReturnValue(true);
				}
			}
		}
	}
}
