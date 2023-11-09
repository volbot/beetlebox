package volbot.beetlebox.client.render.gui;

import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import volbot.beetlebox.client.render.gui.BeetlepackScreenHandler.BeetlepackSlot;
import volbot.beetlebox.data.tags.BeetleEntityTagGenerator;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.LarvaJarItem;

public class BeetlepackScreen extends HandledScreen<ScreenHandler> {
	private static final Identifier TEXTURE = new Identifier("beetlebox", "textures/gui/container/beetlepack.png");

	private UUID player_uuid;
	private World world;

	public BeetlepackScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, Text.of("Beetlepack"));
		player_uuid = inventory.player.getUuid();
		world = inventory.player.getWorld();
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
			if (slot.getStack().getItem() instanceof BeetleJarItem
					|| slot.getStack().getItem() instanceof LarvaJarItem) {
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
						RenderSystem.setShaderTexture(0,
								new Identifier("beetlebox", "textures/gui/beetlepack_icons.png"));
						if (slot.getStack().getItem() instanceof LarvaJarItem) {
							BeetlepackScreen.drawTexture(matrices, slot.x + i + 18, slot.y + j + 7, 0, 36, 9, 8);
							BeetlepackScreen.drawTexture(matrices, slot.x + i + 60, slot.y + j + 5, 10, 36, 11, 11);
							BeetlepackScreen.drawTexture(matrices, slot.x + i + 28, slot.y + j + 8, 0, 53, 31, 6);
							BeetlepackScreen.drawTexture(matrices, slot.x + i + 28, slot.y + j + 8, 0, 47, (int)Math.round(31*nbt.getInt("GrowingTime")/LarvaJarItem.MAX_GROWING_TIME), 6);
						} else if (e.isIn(BeetleEntityTagGenerator.BEETLES)) {
							NbtCompound entity_nbt = nbt.getCompound("EntityTag");
							boolean owned = (entity_nbt.containsUuid("Owner")
									&& entity_nbt.getUuid("Owner").compareTo(player_uuid) == 0);
							BeetlepackScreen.drawTexture(matrices, slot.x + i + 18, slot.y + j + 7, owned ? 0 : 9, 0, 9,
									9);
							if (owned) {
								BeetleEntity beetle = (BeetleEntity) e.create(world);
								beetle.readNbt(entity_nbt);
								beetle.readCustomDataFromNbt(entity_nbt);
								float health = beetle.getHealth() / beetle.getMaxHealth();
								BeetlepackScreen.drawTexture(matrices, slot.x + i + 28, slot.y + j + 7,
										health >= 0.5 ? 0 : health >= 0.25 ? 9 : 18, 9, 9, 9);
								BeetlepackScreen.drawTexture(matrices, slot.x + i + 38, slot.y + j + 7,
										beetle.getHighestStat().ordinal() * 9, 18, 9, 9);

							}

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
