package volbot.beetlebox.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.client.render.item.JarRenderer;
import volbot.beetlebox.entity.block.IncubatorBlockEntity;
import volbot.beetlebox.registry.ItemRegistry;

@Environment(EnvType.CLIENT)
public class IncubatorBlockEntityRenderer implements BlockEntityRenderer<IncubatorBlockEntity> {


	public IncubatorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

	}

	@Override
	public void render(IncubatorBlockEntity te, float f, MatrixStack matrices, VertexConsumerProvider vcp, int i,
			int j) {
		ItemStack stack;
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
		for (int slot = 0; slot < te.size(); slot++) {
			stack = te.getStack(slot);
			if (stack.isOf(ItemRegistry.LARVA_JAR)) {
				matrices = translateForNextJar(matrices, slot);
				JarRenderer.renderJar(matrices, vcp, i);
			} else {
				matrices = translateForNextJar(matrices, slot);
			}
		}
	}

	public MatrixStack translateForNextJar(MatrixStack matrices, int slot) {
		switch (slot) {
		case 0:
			matrices.translate(0.44f, 0f, -0.4375f);
			break;
		case 1:
			matrices.translate(0.31f, 0f, -0.0625f);
			break;
		case 2:
			matrices.translate(0.31f, 0f, 0.0625f);
			break;
		case 3:
			matrices.translate(-0.62f, 0f, -0.5f);
			break;
		case 4:
			matrices.translate(0.31f, 0f, 0.0625f);
			break;
		case 5:
			matrices.translate(0.31f, 0f, -0.0625f);
			break;
		}
		return matrices;
	}
}
