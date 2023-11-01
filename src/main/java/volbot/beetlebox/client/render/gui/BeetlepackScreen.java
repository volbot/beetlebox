package volbot.beetlebox.client.render.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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
