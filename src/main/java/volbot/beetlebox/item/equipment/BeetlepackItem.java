package volbot.beetlebox.item.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BeetlepackItem extends ArmorItem {

	public BeetlepackItem(Settings settings) {
		super(ArmorMaterials.LEATHER, Type.CHESTPLATE, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			user.openHandledScreen(
					new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new AnvilScreenHandler(syncId,
							inventory, ScreenHandlerContext.create(world, user.getBlockPos())), Text.of("Beetlepack")));
			return TypedActionResult.consume(user.getStackInHand(hand));
		} else {
			return this.equipAndSwap(this, world, user, hand);
		}
	}

}
