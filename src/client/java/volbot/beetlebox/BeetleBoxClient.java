package volbot.beetlebox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.render.armor.ArmorElytraEntityModel;
import volbot.beetlebox.client.render.armor.BeetleElytraRenderer;
import volbot.beetlebox.client.render.entity.HercEntityModel;
import volbot.beetlebox.client.render.entity.HercEntityRenderer;
import volbot.beetlebox.client.render.entity.JRBEntityModel;
import volbot.beetlebox.client.render.entity.JRBEntityRenderer;
import volbot.beetlebox.client.render.entity.TitanEntityModel;
import volbot.beetlebox.client.render.entity.TitanEntityRenderer;
import volbot.beetlebox.registry.BeetleRegistry;

@Environment(EnvType.CLIENT)
public class BeetleBoxClient implements ClientModInitializer {
	
	public static final EntityModelLayer MODEL_JRB_LAYER = new EntityModelLayer(new Identifier("beetlebox", "jrb"), "main");
	public static final EntityModelLayer MODEL_HERC_LAYER = new EntityModelLayer(new Identifier("beetlebox", "herc"), "main");
	public static final EntityModelLayer MODEL_TITAN_LAYER = new EntityModelLayer(new Identifier("beetlebox", "titan"), "main");

	public static final EntityModelLayer MODEL_ELYTRACHESTPLATE_LAYER = new EntityModelLayer(new Identifier("beetlebox", "elytrachestplate"), "main");
	
	
	@Override
	public void onInitializeClient() {

		ArmorRenderer.register(new BeetleElytraRenderer(), BeetleRegistry.HERC_ELYTRA);
		
		EntityModelLayerRegistry.registerModelLayer(MODEL_ELYTRACHESTPLATE_LAYER, ArmorElytraEntityModel::getTexturedModelData);
		
        EntityRendererRegistry.register(BeetleRegistry.JRB, JRBEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_JRB_LAYER, JRBEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.HERC, HercEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_HERC_LAYER, HercEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(BeetleRegistry.TITAN, TitanEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_TITAN_LAYER, TitanEntityModel::getTexturedModelData);
	}
}