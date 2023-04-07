package volbot.beetlebox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.render.armor.BeetleArmorRenderer;
import volbot.beetlebox.render.armor.BeetleElytraFeatureRenderer;
import volbot.beetlebox.render.armor.JRBHelmetModel;
import volbot.beetlebox.render.armor.HercHelmetModel;
import volbot.beetlebox.render.entity.AtlasEntityModel;
import volbot.beetlebox.render.entity.AtlasEntityRenderer;
import volbot.beetlebox.render.entity.HercEntityModel;
import volbot.beetlebox.render.entity.HercEntityRenderer;
import volbot.beetlebox.render.entity.JRBEntityModel;
import volbot.beetlebox.render.entity.JRBEntityRenderer;
import volbot.beetlebox.render.entity.TitanEntityModel;
import volbot.beetlebox.render.entity.TitanEntityRenderer;

@Environment(EnvType.CLIENT)
public class BeetleBoxClient implements ClientModInitializer {
	
	public static final EntityModelLayer MODEL_JRB_LAYER = new EntityModelLayer(new Identifier("beetlebox", "jrb"), "main");
	public static final EntityModelLayer MODEL_HERC_LAYER = new EntityModelLayer(new Identifier("beetlebox", "herc"), "main");
	public static final EntityModelLayer MODEL_TITAN_LAYER = new EntityModelLayer(new Identifier("beetlebox", "titan"), "main");
	public static final EntityModelLayer MODEL_ATLAS_LAYER = new EntityModelLayer(new Identifier("beetlebox", "atlas"), "main");
	public static final EntityModelLayer MODEL_JRB_HELMET_LAYER = new EntityModelLayer(new Identifier("beetlebox", "jrb_helmet"), "main");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onInitializeClient() {
		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			  	if (entityRenderer.getModel() instanceof PlayerEntityModel) {
			  		registrationHelper.register(new BeetleElytraFeatureRenderer(entityRenderer, context.getModelLoader()));
			  	}
		});
		
		ArmorRenderer.register(new BeetleArmorRenderer(new JRBHelmetModel()), BeetleRegistry.JRB_HELMET);
		ArmorRenderer.register(new BeetleArmorRenderer(new HercHelmetModel()), BeetleRegistry.HERC_HELMET);
		
        EntityRendererRegistry.register(BeetleRegistry.JRB, JRBEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_JRB_LAYER, JRBEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.HERC, HercEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_HERC_LAYER, HercEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.TITAN, TitanEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_TITAN_LAYER, TitanEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.ATLAS, AtlasEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_ATLAS_LAYER, AtlasEntityModel::getTexturedModelData);
	}
}