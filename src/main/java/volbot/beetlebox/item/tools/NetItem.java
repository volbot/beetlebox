package volbot.beetlebox.item.tools;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import volbot.beetlebox.registry.ItemRegistry;

public class NetItem extends Item {

	public NetItem(Settings settings) {
		super(settings.maxCount(1));
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		ItemStack jar_stack = null;
		PlayerInventory inv = user.getInventory();
		if (entity.world.isClient) {
			return ActionResult.PASS;
		}
		for (ItemStack itemStack : inv.main) {
			if (itemStack.getItem() instanceof BeetleJarItem<?>) {
				NbtCompound nbt = itemStack.getOrCreateNbt();
				BeetleJarItem<?> item = (BeetleJarItem<?>) itemStack.getItem();
				if (nbt.contains("EntityType") || !item.canStore(entity)) {
					continue;
				}
				if (!nbt.contains("EntityType") && item.canStore(entity)) {
					if (jar_stack == null || (!PlayerInventory.isValidHotbarIndex(inv.main.indexOf(jar_stack))
							&& PlayerInventory.isValidHotbarIndex(inv.main.indexOf(itemStack)))) {
						jar_stack = itemStack;
					}
					continue;
				}
				jar_stack = itemStack;
			}
		}
		if (jar_stack != null) {
			NbtCompound nbt = new NbtCompound();
			NbtCompound tag = new NbtCompound();
			tag = entity.writeNbt(tag);
			entity.writeCustomDataToNbt(tag);
			nbt.put("EntityTag", tag);
			Text custom_name = entity.getCustomName();
			if (custom_name != null && !custom_name.getString().isEmpty()) {
				nbt.putString("EntityName", custom_name.getString());
			}
			nbt.putString("EntityType", EntityType.getId(entity.getType()).toString());
			ItemStack jar_new = ItemRegistry.BEETLE_JAR.getDefaultStack();
    		entity.remove(RemovalReason.CHANGED_DIMENSION);
			jar_new.setNbt(nbt);
			jar_stack.decrement(1);
			if (user.getInventory().getEmptySlot() == -1) {
				user.dropStack(jar_new);
			} else {
				user.giveItemStack(jar_new);
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

}
