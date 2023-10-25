package volbot.beetlebox.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.entity.block.IncubatorBlockEntity;
import volbot.beetlebox.registry.ItemRegistry;

@Environment(EnvType.CLIENT)
public class IncubatorBlockEntityRenderer implements BlockEntityRenderer<IncubatorBlockEntity> {

	private final ModelPart jar_base;
	private final ModelPart cork;

	public IncubatorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		root.addChild("cube",
				ModelPartBuilder.create().cuboid("cube", -6.25F, -10.0F, 0.75F, 4.5F, 6.0F, 4.5F).uv(0, 0)
						.cuboid("cube", -5.75F, -10.5F, 1.25F, 3.5F, 0.5F, 3.5F).uv(0, 0)
						.cuboid("cube", -6.0F, -11.0F, 1.0F, 4.0F, 0.5F, 4.0F).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		jar_base = TexturedModelData.of(modelData, 16, 16).createModel();

		modelData = new ModelData();
		modelPartData = modelData.getRoot();
		root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		root.addChild("cube",
				ModelPartBuilder.create().cuboid("cube", -5.5F, -11.75F, 1.5F, 3.0F, 0.75F, 3.0F).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		cork = TexturedModelData.of(modelData, 16, 16).createModel();
	}

	@Override
	public void render(IncubatorBlockEntity te, float f, MatrixStack matrices, VertexConsumerProvider vcp, int i,
			int j) {
		ItemStack stack;
		NbtCompound nbt;
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
		for (int slot = 0; slot < te.size(); slot++) {
			stack = te.getStack(slot);
			if (stack.isOf(ItemRegistry.LARVA_JAR)) {
				matrices = translateForNextJar(matrices, slot);
				matrices.push();
				VertexConsumer vertexConsumer = vcp.getBuffer(
						RenderLayer.getEntitySolid(new Identifier("beetlebox", "textures/block/entity/disco.png")));
				jar_base.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
				cork.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
				matrices.pop();
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
