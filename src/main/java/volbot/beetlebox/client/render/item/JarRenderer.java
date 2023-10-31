package volbot.beetlebox.client.render.item;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class JarRenderer {

	public static void renderJar(MatrixStack matrices, VertexConsumerProvider vcp, int i) {
		matrices.push();
		VertexConsumer cork_vc = vcp.getBuffer(
				RenderLayer.getEntityTranslucent(new Identifier("beetlebox", "textures/block/entity/cork.png")));
		JarModels.cork.render(matrices, cork_vc, i, OverlayTexture.DEFAULT_UV);
		matrices.scale(0.9f, 0.7f, 0.9f);
		float x = -0.025f;
		float y = -0.125f;
		float z = 0.02f;
		matrices.translate(x, y, z);
		VertexConsumer substrate_vc = vcp.getBuffer(
				RenderLayer.getEntityTranslucent(new Identifier("beetlebox", "textures/block/entity/substrate.png")));
		JarModels.substrate.render(matrices, substrate_vc, i, OverlayTexture.DEFAULT_UV);
		matrices.translate(-x, -y, -z);
		matrices.scale(1 / 0.9f, 1 / 0.7f, 1 / 0.9f);
		VertexConsumer jar_vc = vcp.getBuffer(
				RenderLayer.getEntityTranslucent(new Identifier("beetlebox", "textures/block/entity/jelly_cup.png")));
		JarModels.jar_base.render(matrices, jar_vc, i, OverlayTexture.DEFAULT_UV, 30f, 30f, 30f, 100f);
		matrices.pop();
	}

}
