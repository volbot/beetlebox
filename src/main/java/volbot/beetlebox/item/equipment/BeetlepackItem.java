package volbot.beetlebox.item.equipment;

import java.util.Set;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import volbot.beetlebox.client.render.gui.BeetlepackScreenHandler;
import volbot.beetlebox.compat.trinkets.BeetlepackTrinketRenderer;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetlepackItem extends ArmorItem implements ExtendedScreenHandlerFactory {

	public BeetlepackItem(Settings settings) {
		super(ArmorMaterials.LEATHER, Type.CHESTPLATE, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			ItemStack stack = user.getStackInHand(hand);
			stack.getOrCreateNbt().putBoolean("Open", true);
			user.openHandledScreen(this);
			return TypedActionResult.consume(stack);
		} else {
			return this.equipAndSwap(this, world, user, hand);
		}
	}

	@Override
	public Text getDisplayName() {
		return Text.of("Beetlepack");
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new BeetlepackScreenHandler(syncId, playerInventory);
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

	}

	public static ItemStack getBeetlepackOnPlayer(PlayerEntity player) {
		ItemStack beetlepack = ItemStack.EMPTY;
		PlayerInventory inv = player.getInventory();
		ItemStack chest_stack = inv.getArmorStack(EquipmentSlot.CHEST.getEntitySlotId());
		if (chest_stack.isOf(ItemRegistry.BEETLEPACK)) {
			beetlepack = chest_stack;
		} else if (!(beetlepack.getItem() instanceof BeetlepackItem)) {
			if (FabricLoader.getInstance().isModLoaded("trinkets")) {
				ItemStack back_stack = BeetlepackTrinketRenderer.getBackStack(player);
				if (back_stack.getItem() instanceof BeetlepackItem) {
					beetlepack = back_stack;
				}
			}
		}
		return beetlepack;
	}

}
