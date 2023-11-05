package volbot.beetlebox.client.render.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.render.gui.BeetlepackScreenHandler.BeetlepackSlot;
import volbot.beetlebox.item.tools.BeetleJarItem;

public class BeetlepackScreen extends HandledScreen<ScreenHandler> {
	private static final Identifier TEXTURE = new Identifier("beetlebox", "textures/gui/container/beetlepack.png");

	public BeetlepackScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, Text.of("Beetlepack"));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		for (int k = 0; k < this.getScreenHandler().slots.size(); k++) {
			Slot slot = this.getScreenHandler().getSlot(k);
			if (!(slot instanceof BeetlepackSlot)) {
				continue;
			}
			if (slot.getStack().getItem() instanceof BeetleJarItem) {
				NbtCompound nbt = slot.getStack().getOrCreateNbt();
				Text name = Text.of("ERROR");
				boolean bl = nbt.contains("EntityType");
				if (!bl)
					continue;
				if (nbt.contains("EntityName")) {
					name = Text.of(nbt.getString("EntityName"));
				} else {
					EntityType<?> e = EntityType.get(nbt.getString("EntityType")).orElse(null);
					if (e != null) {
						name = e.getName();
					}
				}
				if (name.getString().length() > 11) {
					name = Text.of(name.asTruncatedString(10) + "...");
				}
				textRenderer.draw(matrices, name, slot.x + i + 19, slot.y + j - 1,
						0x404040);

			}
		}
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		BeetlepackScreen.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
	}

	@Override
	protected void init() {
		super.init();
	}

}
