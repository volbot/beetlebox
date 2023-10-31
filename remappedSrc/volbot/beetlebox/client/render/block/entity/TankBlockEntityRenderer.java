package volbot.beetlebox.client.render.block.entity;

import java.awt.Color;

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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LimbAnimator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.item.FruitSyrup;

@Environment(EnvType.CLIENT)
public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {

	private final EntityRenderDispatcher entityRenderDispatcher;
	private final ModelPart substrate_model;
	private final ModelPart ball_model;

	public TankBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		root.addChild("cube", ModelPartBuilder.create().cuboid("cube", 0f, 0f, 0f, 16f, 2f, 16f).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		substrate_model = TexturedModelData.of(modelData, 16, 16).createModel();

		modelData = new ModelData();
		modelPartData = modelData.getRoot();
		root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		root.addChild("cube", ModelPartBuilder.create().cuboid("cube", -2f, -2f, -2f, 4f, 4f, 4f).uv(0, 0),
				ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
		ball_model = TexturedModelData.of(modelData, 16, 16).createModel();

	}

	@Override
	public void render(TankBlockEntity tile_entity, float f, MatrixStack matrices, VertexConsumerProvider vcp, int i,
			int j) {
		BlockRenderManager blockRenderer = MinecraftClient.getInstance().getBlockRenderManager();
		int degrees;
		if (tile_entity.getCachedState().getProperties().contains(BeetleTankBlock.FACING)) {
			switch (tile_entity.getCachedState().get(BeetleTankBlock.FACING)) {
			case NORTH:
				degrees = 180;
				matrices.translate(1.0f, 0f, 1.0f);
				break;
			case WEST:
				degrees = 270;
				matrices.translate(1.0f, 0f, 0f);
				break;
			case SOUTH:
				degrees = 0;
				break;
			case EAST:
				degrees = 90;
				matrices.translate(0.0f, 0f, 1.0f);
				break;
			default:
				degrees = 0;
				break;
			}
		} else {
			degrees = 0;
		}
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(degrees));
		matrices.push();
		if (tile_entity.getStack(0) != ItemStack.EMPTY) {
			VertexConsumer vertexConsumer = vcp.getBuffer(
					RenderLayer.getEntitySolid(new Identifier("beetlebox", "textures/block/entity/substrate.png")));
			matrices.scale(0.98f, 0.96f, 0.98f);
			matrices.translate(0.01, 0.01, 0.01);
			substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		}
		matrices.pop();

		matrices.push();
		int degrees_new;
		if (tile_entity.getCachedState().getProperties().contains(BeetleTankBlock.FACING)) {
			switch (tile_entity.getCachedState().get(BeetleTankBlock.FACING)) {
			case NORTH:
				matrices.translate(-1, 0, 1);
				degrees_new = 90;
				break;
			case WEST:
				degrees_new = 0;
				matrices.translate(-1, 0, 0);
				break;
			case SOUTH:
				degrees_new = 270;
				break;
			case EAST:
				matrices.translate(0, 0, 1);
				degrees_new = 180;
				break;
			default:
				degrees_new = 0;
				break;
			}
		} else {
			degrees_new = 0;
		}
		VertexConsumer vertexConsumer = vcp
				.getBuffer(RenderLayer.getEntityAlpha(new Identifier("beetlebox", "textures/block/tank_top.png")));

		matrices.translate(1.0f, 0.98, 0.0f);
		matrices.scale(1f, 0.01f, 1f);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(degrees_new));
		substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		matrices.translate(0f, -97f, 0f);
		substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
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
				if (stack.isIn(ItemTags.LOGS)) {
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(95));
					matrices.scale(0.5f, 1.0f, 0.45f);
					matrices.translate(-0.55f, -0.85f, 0.65f);
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
				}
				matrices.translate(0.75, 0.122, 0);
				matrices.scale(0.4f, 0.4f, 0.4f);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(315));
				if (stack.isIn(ItemTags.LOGS)) {
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(103));
					matrices.scale(0.5f, 1.0f, 0.45f);
					matrices.translate(-0.55f, -0.85f, 0.65f);
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
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(160));
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
					matrices.scale(0.5f, 1.0f, 0.45f);
					matrices.translate(-1.45f, -0.95f, -0.12f);
				}
				blockRenderer.renderBlockAsEntity(state, matrices, vcp, i, j);
			}
		}
		matrices.pop();
		matrices.push();
		if (tile_entity.decor[2]) {
			vertexConsumer = vcp.getBuffer(RenderLayer
					.getEntityTranslucent(new Identifier("beetlebox", "textures/block/entity/tank_leaves.png")));

			matrices.translate(0.01f, 0.13f, 0.01f);
			matrices.scale(0.49f, 0.001f, 0.49f);
			substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

			matrices.translate(1.0f, 0.0f, 0.0f);
			substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

			matrices.translate(0.0f, 0.0f, 1.0f);
			substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

			matrices.translate(-1.0f, 0.0f, 0.0f);
			substrate_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		}
		matrices.pop();
		matrices.push();
		if (tile_entity.decor[1]) {
			matrices.translate(0.00f, 0.65f, 0.00f);
			matrices.scale(0.33f, 0.33f, 0.33f);
			blockRenderer.renderBlockAsEntity(
					Blocks.VINE.getDefaultState().with(Properties.WEST, true).with(Properties.NORTH, true), matrices,
					vcp, i, j);
			matrices.translate(1.0f, 0.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(Blocks.VINE.getDefaultState().with(Properties.NORTH, true), matrices, vcp,
					i, j);
			matrices.translate(1.0f, 0.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(
					Blocks.VINE.getDefaultState().with(Properties.EAST, true).with(Properties.NORTH, true), matrices,
					vcp, i, j);
			matrices.translate(-2.0f, -1.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(
					Blocks.VINE.getDefaultState().with(Properties.WEST, true).with(Properties.NORTH, true), matrices,
					vcp, i, j);
			matrices.translate(1.0f, 0.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(Blocks.VINE.getDefaultState().with(Properties.NORTH, true), matrices, vcp,
					i, j);
			matrices.translate(1.0f, 0.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(
					Blocks.VINE.getDefaultState().with(Properties.EAST, true).with(Properties.NORTH, true), matrices,
					vcp, i, j);
			matrices.scale(1, 0.95f, 1);
			matrices.translate(-2.0f, -1.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(
					Blocks.VINE.getDefaultState().with(Properties.WEST, true).with(Properties.NORTH, true), matrices,
					vcp, i, j);
			matrices.translate(1.0f, 0.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(Blocks.VINE.getDefaultState().with(Properties.NORTH, true), matrices, vcp,
					i, j);
			matrices.translate(1.0f, 0.0f, 0.0f);
			blockRenderer.renderBlockAsEntity(
					Blocks.VINE.getDefaultState().with(Properties.EAST, true).with(Properties.NORTH, true), matrices,
					vcp, i, j);
		}
		matrices.pop();
		matrices.push();
		if (tile_entity.decor[0]) {
			matrices.translate(0.4f, 0.75f, 0.4f);
			matrices.scale(0.2f, 0.25f, 0.2f);
			blockRenderer.renderBlockAsEntity(Blocks.CHAIN.getDefaultState(), matrices, vcp, i, j);
			matrices.pop();
			matrices.push();

			matrices.translate(0.5f, 0.7f, 0.5f);
			matrices.scale(0.6f, 0.6f, 0.6f);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));
			vertexConsumer = vcp.getBuffer(
					RenderLayer.getEntityTranslucent(new Identifier("beetlebox", "textures/block/entity/disco.png")));
			ball_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

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
				e.entity_data.remove("Flying");
				entity.readCustomDataFromNbt(e.entity_data);
				if (entity instanceof BeetleEntity) {
					((BeetleEntity) entity).setSize(5);
				}
				if (!e.custom_name.isEmpty()) {
					entity.setCustomName(Text.of(e.custom_name));
				}
				g = 0.4f;
				h = Math.max(entity.getHeight(), entity.getWidth());
				if (h >= 1f) {
					g /= h;
				}
				entity.setHeadYaw(0);
				matrices.translate(0.75, 0.12, 0.75);
				matrices.scale(g, g, g);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(225));
				
				if(tile_entity.breedingSetupValid()) {
					matrices.translate(0.0, 0, 0.8);
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));
					matrices.translate(0.0, 0, 0.3);
					matrices.translate(0, 0.25, 0);
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)Math.sin((double)(tile_entity.getWorld().getTime() + f)/2) * 16));
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-120f));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
				}
				
				if(tile_entity.tamingSetupValid()) {
					matrices.translate(0.0, 0, 0.5);
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10f));
					float headpitch = (float)Math.sin((double)(tile_entity.getWorld().getTime() + f)/1.5)*12;
					entity.prevPitch = headpitch;
					entity.setPitch(headpitch);
				}

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
				e.entity_data.remove("Flying");
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
				entity.setHeadYaw(0);

				matrices.translate(0.25, 0.12, 0.25);
				matrices.scale(g, g, g);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));

				if(tile_entity.breedingSetupValid()) {
					matrices.translate(0.0, 0, 0.8);
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));
					matrices.translate(0.0, 0, 0.3);
					matrices.translate(0, 0.25, 0);
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)Math.sin((double)(tile_entity.getWorld().getTime() + f)/2) * 16));
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-120f));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
				}
				
				this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vcp, i);
			}
		}
		matrices.pop();
		matrices.push();
		if (tile_entity.getStack(4) != ItemStack.EMPTY) {
			vertexConsumer = vcp.getBuffer(RenderLayer
					.getEntityTranslucent(new Identifier("beetlebox", "textures/block/entity/jelly_cup.png")));
			matrices.translate(0.5f, 0.1f, 0.5f);
			matrices.scale(0.26f, 0.65f, 0.26f);
			Color color = new Color(FruitSyrup.getColor(tile_entity.getStack(4)));
			ball_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV, color.getRed() / 255f,
					color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
			matrices.scale(1.1f, 0.99f, 1.1f);
			ball_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 30, 30, 30,
					color.getAlpha() / 400f);
			matrices.translate(0f, 0.13f, 0f);
			matrices.scale(1.3f, 0.02f, 1.3f);
			ball_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 30, 30, 30,
					color.getAlpha() / 400f);
		}
		matrices.pop();
		matrices.push();
		if (tile_entity.larva != null) {
			vertexConsumer = vcp.getBuffer(
					RenderLayer.getEntitySolid(new Identifier("beetlebox", "textures/block/entity/jelly_cup.png")));
			matrices.translate(0.5f, 0.125f, 0.5f);
			matrices.scale(0.13f, 0.05f, 0.13f);

			ball_model.render(matrices, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		}
		matrices.pop();
	}

	LimbAnimator limbs;
	
}