package volbot.beetlebox.client.render.block.entity;

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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import volbot.beetlebox.entity.block.BoilerBlockEntity;

public class BoilerBlockEntityRenderer implements BlockEntityRenderer<BoilerBlockEntity> {

	private final ModelPart m;
	private float d = 0f;

	public BoilerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		root.addChild("cube", ModelPartBuilder.create().cuboid("cube", 0.5f, 0.5f, 0.5f, 14f, 14f, 14f).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		m = TexturedModelData.of(modelData, 16, 16).createModel();
	}

	@Override
	public void render(BoilerBlockEntity boiler, float f, MatrixStack matrices, VertexConsumerProvider vcp, int i,
			int j) {
		ItemStack input = boiler.getStack(0);
		ItemStack output = boiler.getStack(1);
		SingleVariantStorage<FluidVariant> fluidStorage = boiler.fluidStorage;
		long amo = fluidStorage.getAmount();
		long cap = fluidStorage.getCapacity();
		double beep = Math.ceil(99 * amo / cap) + 16;
		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
		int light = getLightLevel(boiler.getWorld(), boiler.getPos().add(0, 1, 0));
		matrices.push();
		matrices.pop();
		matrices.push();

		matrices.pop();
		matrices.push();
		matrices.scale(1f, (float) beep / 120f, 1f);
		d += MinecraftClient.getInstance().getLastFrameDuration() / (boiler.fireLit() ? 0.5 : 2);
		if (d >= 32) {
			d = 0;
		}
		VertexConsumer vertexConsumer = vcp
				.getBuffer(RenderLayer.getEntityTranslucent(this.getFluidTexture((int) d)));
		m.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		if (!input.isEmpty()) {
			matrices.translate(0.5, 0.75, 0.7);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20));
			itemRenderer.renderItem(input, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices,
					vcp, boiler.getWorld(), 1);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-20));
			matrices.translate(-0.5, -0.75, -0.7);
			if (input.getCount() > 1) {
				matrices.translate(0.7, 0.75, 0.4);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(120));
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20));
				itemRenderer.renderItem(input, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV,
						matrices, vcp, boiler.getWorld(), 1);
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-20));
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-120));
				matrices.translate(-0.7, -0.75, -0.4);
				if (input.getCount() > 2) {
					matrices.translate(0.3, 0.75, 0.4);
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-120));
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20));
					itemRenderer.renderItem(input, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV,
							matrices, vcp, boiler.getWorld(), 1);
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-20));
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(120));
					matrices.translate(-0.3, -0.75, -0.4);
				}
			}
		}
		if (!output.isEmpty()) {
			matrices.translate(0.5, 0.925, 0.4);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
			itemRenderer.renderItem(output, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices,
					vcp, boiler.getWorld(), 5);
		}

		matrices.pop();

	}

	private int getLightLevel(World world, BlockPos pos) {
		int bLight = world.getLightLevel(LightType.BLOCK, pos);
		int sLight = world.getLightLevel(LightType.SKY, pos);
		return LightmapTextureManager.pack(bLight, sLight);
	}

	private Identifier getFluidTexture(int state) {
		return new Identifier("beetlebox", "textures/block/fluid_still_" + state + ".png");

	}

}
