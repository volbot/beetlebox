package volbot.beetlebox.client.render.armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
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
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

@Environment(EnvType.CLIENT)
public class BeetleElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraFeatureRenderer<T, M> {

    private final ElytraEntityModel<T> elytra;

    public BeetleElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context,loader);
        this.elytra = new ElytraEntityModel<T>(ElytraEntityModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (!(itemStack.getOrCreateNbt().contains("beetle_chest_elytra"))) {
        	return;
        }
        Identifier skin = new Identifier("beetlebox","textures/entity/elytra/"
        		+((ChitinMaterial)(((BeetleArmorItem)itemStack.getItem()).getMaterial())).getName()
        		+"_elytra.png");
        matrixStack.push();
        matrixStack.translate(0.0f, 0.0f, 0.125f);
        M context = this.getContextModel();
        context.copyStateTo(this.elytra);
        this.elytra.setAngles(livingEntity, f, g, j, k, l);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(skin), false, itemStack.hasGlint());
        this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

}
