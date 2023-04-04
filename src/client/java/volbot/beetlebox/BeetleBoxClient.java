package volbot.beetlebox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.render.armor.BeetleElytraFeatureRenderer;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onInitializeClient() {
		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			  	if (entityRenderer.getModel() instanceof PlayerEntityModel) {
			  		registrationHelper.register(new BeetleElytraFeatureRenderer(entityRenderer, context.getModelLoader()));
			  	}
		});
		
        EntityRendererRegistry.register(BeetleRegistry.JRB, JRBEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_JRB_LAYER, JRBEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.HERC, HercEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_HERC_LAYER, HercEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.TITAN, TitanEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_TITAN_LAYER, TitanEntityModel::getTexturedModelData);
	}
}