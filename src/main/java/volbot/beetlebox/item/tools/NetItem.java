package volbot.beetlebox.item.tools;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import volbot.beetlebox.item.equipment.BeetlepackItem;

public class NetItem extends Item {

	public NetItem(Settings settings) {
		super(settings.maxCount(1));
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		ItemStack jar_stack = null;
		PlayerInventory inv = user.getInventory();
		ItemStack bp = BeetlepackItem.getBeetlepackOnPlayer(user);
		DefaultedList<ItemStack> bp_inv = null;
		boolean bp_acc = false;

		if (entity.world.isClient) {
			return ActionResult.PASS;
		}
		
		if (jar_stack == null) {
			if (!bp.isEmpty()) {
				bp_inv = BeetlepackItem.readInventory(bp);
				for (int i = 0; i < 6; i++) {
					ItemStack itemStack = bp_inv.get(i);
					if (itemStack.getItem() instanceof BeetleJarItem<?>) {
						BeetleJarItem<?> item = (BeetleJarItem<?>) itemStack.getItem();
						if (itemStack.hasNbt()) {
							NbtCompound nbt = itemStack.getNbt();
							if (nbt.contains("EntityType") || !item.canStore(entity)) {
								continue;
							}
						}
						jar_stack = itemStack;
						bp_acc = true;
						break;
					}
				}
			}
		}

		if (jar_stack == null) {
			for (ItemStack itemStack : inv.main) {
				if (itemStack.getItem() instanceof BeetleJarItem<?>) {
					NbtCompound nbt = itemStack.getOrCreateNbt();
					BeetleJarItem<?> item = (BeetleJarItem<?>) itemStack.getItem();
					if (nbt.contains("EntityType") || !item.canStore(entity)) {
						continue;
					}
					if (!nbt.contains("EntityType") && item.canStore(entity)) {
						if ((!PlayerInventory.isValidHotbarIndex(inv.main.indexOf(jar_stack))
								&& PlayerInventory.isValidHotbarIndex(inv.main.indexOf(itemStack)))) {
							jar_stack = itemStack;
							break;
						} else if (jar_stack == null) {
							jar_stack = itemStack;
						}
					}
				}
			}
		}

		if (jar_stack != null) {
			NbtCompound nbt = jar_stack.getOrCreateNbt().copy();
			NbtCompound tag = new NbtCompound();
			entity.writeNbt(tag);
			entity.writeCustomDataToNbt(tag);
			nbt.put("EntityTag", tag);
			Text custom_name = entity.getCustomName();
			if (custom_name != null && !custom_name.getString().isEmpty()) {
				nbt.putString("EntityName", custom_name.getString());
			}
			nbt.putString("EntityType", EntityType.getId(entity.getType()).toString());
			entity.discard();
			
			ItemStack jar_new = jar_stack.getItem().getDefaultStack();
			jar_new.setNbt(nbt);
			jar_stack.decrement(1);
			
			if (user.getInventory().getEmptySlot() == -1) {
				user.dropStack(jar_new);
			} else {
				user.giveItemStack(jar_new);
			}
			
			if(bp_acc) {
				BeetlepackItem.writeInventory(bp, bp_inv);
			}
			
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

}
