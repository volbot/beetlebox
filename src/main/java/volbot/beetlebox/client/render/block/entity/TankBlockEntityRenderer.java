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
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;

@Environment(EnvType.CLIENT)
public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {

	private final EntityRenderDispatcher entityRenderDispatcher;
	private final ModelPart m;

	public TankBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		root.addChild("cube", ModelPartBuilder.create().cuboid("cube", 1f, 1f, 1f, 14f, 2f, 14f).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		m = TexturedModelData.of(modelData, 16, 16).createModel();
	}

	@Override
	public void render(TankBlockEntity tile_entity, float f, MatrixStack matrices,
			VertexConsumerProvider vcp, int i, int j) {
		ItemStack substrate = tile_entity.getStack(0);
		matrices.push();
		if(substrate!=ItemStack.EMPTY) {
			VertexConsumer vertexConsumer = vcp
					.getBuffer(RenderLayer.getEntitySolid(
							new Identifier("beetlebox", "textures/block/entity/substrate.png")));
			m.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		}
		matrices.pop();
		matrices.push();
		float g;
		float h = -1f;
		if (tile_entity.getContained(0) != null) {
			ContainedEntity e = tile_entity.getContained(0);
			EntityType<?> entityType2 = EntityType.get(e.contained_id).orElse(null);
			if (entityType2 != null) {
				LivingEntity entity = (LivingEntity) entityType2.create(tile_entity.getWorld());
				entity.readCustomDataFromNbt(e.entity_data);
				if (entity instanceof BeetleEntity) {
					((BeetleEntity) entity).setSize(5);
				}
				if (!e.custom_name.isEmpty()) {
					entity.setCustomName(Text.of(e.custom_name));
				}
				g = 0.4f;
				h = Math.max(entity.getHeight(),entity.getWidth());
				if(h >= 1f) {
					g/=h;
				}
				matrices.translate(0.25, 0.2, 0.25);
				matrices.scale(g, g, g);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));

				this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vcp, i);
			}
		}
		matrices.pop();
		matrices.push();
		if (tile_entity.getContained(1) != null) {
			ContainedEntity e = tile_entity.getContained(1);
			EntityType<?> entityType2 = EntityType.get(e.contained_id).orElse(null);
			if (entityType2 != null) {
				LivingEntity entity = (LivingEntity) entityType2.create(tile_entity.getWorld());
				entity.readCustomDataFromNbt(e.entity_data);
				if (!e.custom_name.isEmpty()) {
					entity.setCustomName(Text.of(e.custom_name));
				}
				if (entity instanceof BeetleEntity) {
					((BeetleEntity) entity).setSize(5);
				}
				g = 0.4f;
				h = Math.max(entity.getHeight(),entity.getWidth());
				if(h >= 1f) {
					g/=h;
				}

				matrices.translate(0.75, 0.2, 0.75);
				matrices.scale(g, g, g);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));

				this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vcp, i);
			}
		}
		matrices.pop();
	}

}
