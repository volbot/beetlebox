package volbot.beetlebox.compat.trinkets;

import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TrinketUtils {

	public static TypedActionResult<ItemStack> equipAndSwap(Item item, World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = user.getEquippedStack(equipmentSlot); //WRONG!
        if (EnchantmentHelper.hasBindingCurse(itemStack2) || ItemStack.areEqual(itemStack, itemStack2)) {
            return TypedActionResult.fail(itemStack);
        }
        TrinketItem.equipItem(user, itemStack);
        if (!world.isClient()) {
            user.incrementStat(Stats.USED.getOrCreateStat(item));
        }
        if (itemStack2.isEmpty()) {
            itemStack.setCount(0);
        } else {
            user.setStackInHand(hand, itemStack2.copy());
        }
        return TypedActionResult.success(itemStack, world.isClient());
	}
}
