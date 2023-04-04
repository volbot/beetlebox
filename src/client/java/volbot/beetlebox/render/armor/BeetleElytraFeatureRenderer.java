package volbot.beetlebox.render.armor;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import volbot.beetlebox.item.equipment.BeetleElytraItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraFeatureRenderer<T, M> {

    private final ElytraEntityModel<T> elytra;

    public BeetleElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context,loader);
        this.elytra = new ElytraEntityModel<T>(ElytraEntityModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        AbstractClientPlayerEntity abstractClientPlayerEntity;
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (!(itemStack.getItem() instanceof BeetleElytraItem)) {
        	return;
        }
        Identifier skin = new Identifier("beetlebox","textures/entity/elytra/"
        		+((ChitinMaterial)(((BeetleElytraItem)itemStack.getItem()).getMaterial())).getName()
        		+"_elytra.png");
        Identifier identifier = livingEntity instanceof AbstractClientPlayerEntity ? ((abstractClientPlayerEntity = (AbstractClientPlayerEntity)((Object)livingEntity)).canRenderElytraTexture() && abstractClientPlayerEntity.getElytraTexture() != null ? abstractClientPlayerEntity.getElytraTexture() : (abstractClientPlayerEntity.canRenderCapeTexture() && abstractClientPlayerEntity.getCapeTexture() != null && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE) ? abstractClientPlayerEntity.getCapeTexture() : skin)) : skin;
        matrixStack.push();
        matrixStack.translate(0.0f, 0.0f, 0.125f);
        this.getContextModel().copyStateTo(this.elytra);
        this.elytra.setAngles(livingEntity, f, g, j, k, l);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier), false, itemStack.hasGlint());
        this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

}
