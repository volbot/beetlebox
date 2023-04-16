package volbot.beetlebox.render.block.entity;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import volbot.beetlebox.entity.block.BoilerBlockEntity;

public class BoilerBlockEntityRenderer 
implements BlockEntityRenderer<BoilerBlockEntity>{

	private final ModelPart m;
	
	public BoilerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelData modelData = new ModelData();
    	ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
        root.addChild("cube", ModelPartBuilder.create().cuboid("cube", 0.5f, -0.5f, 0.5f, 14f, 14f, 14f), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
        m = TexturedModelData.of(modelData, 32, 32).createModel();
    }
	
	@Override
	public void render(BoilerBlockEntity boiler, float f, MatrixStack matrices, VertexConsumerProvider vcp, int i, int j) {
		matrices.push();
		SingleVariantStorage<FluidVariant> fluidStorage = boiler.fluidStorage;
		long amo = fluidStorage.getAmount();
		long cap = fluidStorage.getCapacity();
		long beep = (10 * amo / cap);
		System.out.println(beep);
		if(beep > 0) {
			matrices.scale(1f, (float)beep / 10f, 1f);
			VertexConsumer vertexConsumer = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/bell/bell_body")).getVertexConsumer(vcp, RenderLayer::getEntitySolid);
			m.render(matrices, vertexConsumer, i, j);
		}
		matrices.pop();
	}

}
