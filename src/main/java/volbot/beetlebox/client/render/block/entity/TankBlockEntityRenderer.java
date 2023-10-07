package volbot.beetlebox.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CandleBlock;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
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
		root.addChild("cube", ModelPartBuilder.create().cuboid("cube", 0f, 0f, 0f, 16f, 2f, 16f).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		m = TexturedModelData.of(modelData, 16, 16).createModel();
	}

	@Override
	public void render(TankBlockEntity tile_entity, float f, MatrixStack matrices, VertexConsumerProvider vcp, int i,
			int j) {
		BlockRenderManager blockRenderer = MinecraftClient.getInstance().getBlockRenderManager();
		matrices.push();
		if (tile_entity.getStack(0) != ItemStack.EMPTY) {
			VertexConsumer vertexConsumer = vcp.getBuffer(
					RenderLayer.getEntitySolid(new Identifier("beetlebox", "textures/block/entity/substrate.png")));
			matrices.scale(0.99f, 1f, 0.99f);
			matrices.translate(0.01, 0, 0.01);
			m.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		}
		matrices.pop();
		matrices.push();
		ItemStack stack;
		Block b;
		if ((stack = tile_entity.getStack(1)) != ItemStack.EMPTY) {
			b = Block.getBlockFromItem(stack.getItem());
			if (b != Blocks.AIR) {
				BlockState state = b.getDefaultState();
				if (b instanceof AbstractCandleBlock) {
					state = state.with(CandleBlock.LIT, true).with(CandleBlock.CANDLES, 3);
				}
				matrices.translate(0.25, 0.122, 1);
				matrices.scale(0.4f, 0.4f, 0.4f);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135));
				if (stack.isIn(ItemTags.LOGS) || stack.isIn(ItemTags.LEAVES)) {
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
					matrices.scale(0.5f, 1.0f, 0.75f);
					matrices.translate(-0.8f, -0.95f, 0.15f);
				}
				blockRenderer.renderBlockAsEntity(state, matrices, vcp, i, j);

			}
		}
		matrices.pop();
		matrices.push();
		if ((stack = tile_entity.getStack(2)) != ItemStack.EMPTY) {
			b = Block.getBlockFromItem(stack.getItem());
			if (b != Blocks.AIR) {
				BlockState state = b.getDefaultState();
				if (b instanceof AbstractCandleBlock) {
					state = state.with(CandleBlock.LIT, true).with(CandleBlock.CANDLES, 3);
				} else {
					matrices.translate(0, -0.25, 0);
				}
				matrices.translate(0.75, 0.122, 0);
				matrices.scale(0.4f, 0.4f, 0.4f);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(315));
				if (stack.isIn(ItemTags.LOGS) || stack.isIn(ItemTags.LEAVES)) {
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
					matrices.scale(0.5f, 1.0f, 0.75f);
					matrices.translate(0.4f, -0.95f, 0.15f);
				}
				blockRenderer.renderBlockAsEntity(state, matrices, vcp, i, j);
			}
		}
		matrices.pop();
		matrices.push();
		if ((stack = tile_entity.getStack(3)) != ItemStack.EMPTY) {
			b = Block.getBlockFromItem(stack.getItem());
			if (b != Blocks.AIR) {
				BlockState state = b.getDefaultState();
				if (b instanceof AbstractCandleBlock) {
					state = state.with(CandleBlock.LIT, true).with(CandleBlock.CANDLES, 3);
				}
				matrices.translate(0, 0.122, 0.25);
				matrices.scale(0.4f, 0.4f, 0.4f);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
				if (stack.isIn(ItemTags.LOGS) || stack.isIn(ItemTags.LEAVES)) {
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
					matrices.scale(0.5f, 1.0f, 0.75f);
					matrices.translate(-0.8f, -0.95f, 0.15f);
				}
				blockRenderer.renderBlockAsEntity(state, matrices, vcp, i, j);
			}
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
					((BeetleEntity) entity).setFlying(false);
				}
				if (!e.custom_name.isEmpty()) {
					entity.setCustomName(Text.of(e.custom_name));
				}
				g = 0.4f;
				h = Math.max(entity.getHeight(), entity.getWidth());
				if (h >= 1f) {
					g /= h;
				}
				matrices.translate(0.75, 0.12, 0.75);
				matrices.scale(g, g, g);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(225));

				this.entityRenderDispatcher.render(entity, 0, 0, 0, 0.0f, f, matrices, vcp, i);
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
				entity.getPos();
				if (entity instanceof BeetleEntity) {
					((BeetleEntity) entity).setSize(5);
					((BeetleEntity) entity).setFlying(false);
				}
				g = 0.4f;
				h = Math.max(entity.getHeight(), entity.getWidth());
				if (h >= 1f) {
					g /= h;
				}

				matrices.translate(0.25, 0.12, 0.25);
				matrices.scale(g, g, g);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));

				this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vcp, i);
			}
		}
		matrices.pop();
	}

}
