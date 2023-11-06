package volbot.beetlebox.client.render.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import volbot.beetlebox.item.equipment.BeetlepackItem;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.LarvaJarItem;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetlepackScreenHandler extends ScreenHandler {
	public static final ScreenHandlerType<BeetlepackScreenHandler> BEETLEPACK_SCREEN_HANDLER_TYPE = new ExtendedScreenHandlerType<>(
			BeetlepackScreenHandler::new);

	private static final int INVENTORY_SIZE = 6;
	private final Inventory inventory;
	private ItemStack stack = null;

	public BeetlepackScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory);
	}

	public BeetlepackScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(INVENTORY_SIZE));
	}

	public BeetlepackScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(BEETLEPACK_SCREEN_HANDLER_TYPE, syncId);

		this.stack = locateStack(playerInventory);

		int l;
		int k;
		BeetlepackScreenHandler.checkSize(inventory, INVENTORY_SIZE);

		NbtCompound stack_nbt = stack.getOrCreateNbt();

		this.inventory = inventory;

		inventory.onOpen(playerInventory.player);

		for (k = 0; k < 3; k++) {
			for (l = 0; l < 2; l++) {
				this.addSlot(new BeetlepackSlot(inventory, l + k * 2, 8 + l * 81, 18 + k * 18));
			}
		}
		for (k = 0; k < 3; ++k) {
			for (l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
			}
		}
		for (k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142) {
				
				@Override
				public boolean canInsert(ItemStack stack) {
					if (this.getStack().equals(stack)) {
						return false;
					} else {
						return true;
					}
				}

				@Override
				public boolean canTakeItems(PlayerEntity playerEntity) {
					if (this.getStack().equals(stack)) {
						return false;
					} else {
						return true;
					}
				}
				
			});
		}

		if (stack_nbt.contains("Inventory")) {
			DefaultedList<ItemStack> inv_read = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
			Inventories.readNbt(stack_nbt.getCompound("Inventory"), inv_read);
			for (int i = 0; i < INVENTORY_SIZE; i++) {
				getSlot(i).setStack(inv_read.get(i));
			}
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = (Slot) this.slots.get(slot);
		if (slot2 != null && slot2.hasStack()) {
			ItemStack itemStack2 = slot2.getStack();
			itemStack = itemStack2.copy();
			if (slot < this.inventory.size()
					? !this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)
					: !this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) {
				slot2.setStack(ItemStack.EMPTY);
			} else {
				slot2.markDirty();
			}
		}
		return itemStack;
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.inventory.onClose(player);

		NbtCompound inv_nbt = new NbtCompound();
		DefaultedList<ItemStack> stored = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			stored.set(i, getSlot(i).getStack());
		}
		Inventories.writeNbt(inv_nbt, stored);
		this.stack.getOrCreateNbt().put("Inventory", inv_nbt);
		this.stack.getOrCreateNbt().putBoolean("Open", false);
	}

	public static ItemStack locateStack(Inventory inventory) {
		ItemStack stack;
		for (int i = 0; i < inventory.size(); i++) {
			stack = inventory.getStack(i);
			if (stack.getItem() instanceof BeetlepackItem) {
				if (stack.getOrCreateNbt().getBoolean("Open")) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public class BeetlepackSlot extends Slot {

		public BeetlepackSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean canInsert(ItemStack stack) {
			return stack.isOf(ItemRegistry.SUBSTRATE_JAR) || stack.getItem() instanceof BeetleJarItem
					|| stack.getItem() instanceof LarvaJarItem;
		}

	}
}
