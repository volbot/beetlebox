package volbot.beetlebox.client.render.gui;

import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
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

	private UUID player_uuid;
	
	public BeetlepackScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, Text.of("Beetlepack"));
		player_uuid = inventory.player.getUuid();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		for (int k = 0; k < this.getScreenHandler().slots.size(); k++) {
			Slot slot = this.getScreenHandler().getSlot(k);
			if (!(slot instanceof BeetlepackSlot)) {
				continue;
			}
			if (slot.getStack().getItem() instanceof BeetleJarItem) {
				NbtCompound nbt = slot.getStack().hasNbt() ? slot.getStack().getNbt() : new NbtCompound();
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
						if (name.getString().length() > 11) {
							name = Text.of(name.asTruncatedString(10) + "...");
						}
						textRenderer.draw(matrices, name, slot.x + i + 19, slot.y + j - 1, 0x404040);
						NbtCompound entity_nbt = nbt.getCompound("EntityTag");
						if (entity_nbt.containsUuid("Owner") && entity_nbt.getUuid("Owner").compareTo(player_uuid)==0) {
					        this.client.getProfiler().push("health");
				            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
							BeetlepackScreen.drawTexture(matrices, slot.x + i + 18, slot.y + j + 6, 52, 0, 9, 9);
						}
					}
				}

			}
		}
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
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
