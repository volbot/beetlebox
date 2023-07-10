package volbot.beetlebox.client;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.client.render.block.entity.BoilerBlockEntityRenderer;
import volbot.beetlebox.client.render.block.entity.TankBlockEntityRenderer;
import volbot.beetlebox.client.render.entity.ActaeonEntityModel;
import volbot.beetlebox.client.render.entity.ActaeonEntityRenderer;
import volbot.beetlebox.client.render.entity.AtlasEntityModel;
import volbot.beetlebox.client.render.entity.AtlasEntityRenderer;
import volbot.beetlebox.client.render.entity.ElephantEntityModel;
import volbot.beetlebox.client.render.entity.ElephantEntityRenderer;
import volbot.beetlebox.client.render.entity.HercEntityModel;
import volbot.beetlebox.client.render.entity.HercEntityRenderer;
import volbot.beetlebox.client.render.entity.JRBEntityModel;
import volbot.beetlebox.client.render.entity.JRBEntityRenderer;
import volbot.beetlebox.client.render.entity.JunebugEntityModel;
import volbot.beetlebox.client.render.entity.JunebugEntityRenderer;
import volbot.beetlebox.client.render.entity.TitanEntityModel;
import volbot.beetlebox.client.render.entity.TitanEntityRenderer;
import volbot.beetlebox.client.render.entity.TityusEntityModel;
import volbot.beetlebox.client.render.entity.TityusEntityRenderer;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;
import volbot.beetlebox.item.FruitSyrup;
import volbot.beetlebox.item.equipment.BeetleArmorAbilities;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.client.render.armor.BeetleArmorEntityModel;
import volbot.beetlebox.client.render.armor.BeetleArmorRenderer;
import volbot.beetlebox.client.render.armor.BeetleElytraFeatureRenderer;
import volbot.beetlebox.client.render.armor.JRBHelmetModel;
import volbot.beetlebox.client.render.armor.HercHelmetModel;
import volbot.beetlebox.client.render.armor.TitanHelmetModel;
import volbot.beetlebox.client.render.armor.AtlasHelmetModel;
import volbot.beetlebox.client.render.armor.ElephantHelmetModel;
import volbot.beetlebox.client.render.armor.TityusHelmetModel;
import volbot.beetlebox.client.render.armor.StandardHelmetModel;
import volbot.beetlebox.client.render.armor.ActaeonHelmetModel;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class BeetleBoxClient implements ClientModInitializer {
	
	private static KeyBinding elytra_boost_keybind;
	
	public static HashMap<String, BeetleArmorEntityModel<?>> beetle_helmets = new HashMap<>();

	public static final EntityModelLayer MODEL_JRB_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "jrb"), "main");
	public static final EntityModelLayer MODEL_HERC_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "hercules"), "main");
	public static final EntityModelLayer MODEL_TITAN_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "titanus"), "main");
	public static final EntityModelLayer MODEL_ATLAS_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "atlas"), "main");
	public static final EntityModelLayer MODEL_ELEPHANT_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "elephant"), "main");
	public static final EntityModelLayer MODEL_TITYUS_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "tityus"), "main");
	public static final EntityModelLayer MODEL_JUNEBUG_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "junebug"), "main");
	public static final EntityModelLayer MODEL_ACTAEON_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "actaeon"), "main");

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	@Override
	public void onInitializeClient() {
		
		elytra_boost_keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
	            "key.beetlebox.elytra_boost", 
	            InputUtil.Type.KEYSYM, 
	            GLFW.GLFW_KEY_R, 
	            "category.beetlebox.beetlebox"
	        ));
	    
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
		    while (elytra_boost_keybind.wasPressed()) {
		    	BeetleArmorAbilities.elytraBoost(client.getServer().getPlayerManager().getPlayer(client.player.getUuid()));
		    }
		});
		
		BeetleBoxClient.beetle_helmets.put("jrb", new JRBHelmetModel<>());
		BeetleBoxClient.beetle_helmets.put("hercules", new HercHelmetModel<>());
		BeetleBoxClient.beetle_helmets.put("titanus", new TitanHelmetModel<>());
		BeetleBoxClient.beetle_helmets.put("atlas", new AtlasHelmetModel<>());
		BeetleBoxClient.beetle_helmets.put("elephant", new ElephantHelmetModel<>());
		BeetleBoxClient.beetle_helmets.put("tityus", new TityusHelmetModel<>());
		BeetleBoxClient.beetle_helmets.put("junebug", new StandardHelmetModel<>("junebug"));
		BeetleBoxClient.beetle_helmets.put("actaeon", new ActaeonHelmetModel<>());

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("beetlebox", "boiler_fluid"),
				(client, handler, buf, responseSender) -> {
					BlockPos pos = buf.readBlockPos();
					FluidVariant variant = FluidVariant.fromPacket(buf);
					long fluid_amt = buf.readLong();
					client.execute(() -> {
						handler.getWorld().getBlockEntity(pos, BlockRegistry.BOILER_BLOCK_ENTITY)
								.orElse(null).fluidStorage.variant = variant;
						handler.getWorld().getBlockEntity(pos, BlockRegistry.BOILER_BLOCK_ENTITY)
								.orElse(null).fluidStorage.amount = fluid_amt;
					});
				});

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("beetlebox/beetle_packet"),
				(client, handler, buf, responseSender) -> {
					int size = buf.readInt();
					float damage = buf.readFloat();
					float speed = buf.readFloat();
					float maxhealth = buf.readFloat();
					int entity_id = buf.readInt();
					client.execute(() -> {
						BeetleEntity e = ((BeetleEntity) handler.getWorld().getEntityById(entity_id));
						if(e!=null) {
							try {
								e.setSize(size);
								e.setDamageMult(damage);
								e.setSpeedMult(speed);
								e.setMaxHealthMult(maxhealth);
							} catch(NullPointerException ex) {
								e.size_cached = size;
								e.damage_cached = damage;
								e.speed_cached = speed;
								e.maxhealth_cached = maxhealth;
								e.unSynced = true;
							}
						}
					});
				});

		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TANK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LEG_TANK, RenderLayer.getCutout());

		LivingEntityFeatureRendererRegistrationCallback.EVENT
				.register((entityType, entityRenderer, registrationHelper, context) -> {
					if (entityRenderer.getModel() instanceof BipedEntityModel) {
						registrationHelper
								.register(new BeetleElytraFeatureRenderer(entityRenderer, context.getModelLoader()));
					}
				});

		for (Item i : ItemRegistry.beetle_helmets) {
			BeetleArmorItem armorItem = (BeetleArmorItem)i;
			ArmorRenderer.register(new BeetleArmorRenderer(BeetleBoxClient.beetle_helmets.get(armorItem.getMaterial().getName())), armorItem);
		}

		EntityRendererRegistry.register(BeetleRegistry.JRB, JRBEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_JRB_LAYER, JRBEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.HERC, HercEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_HERC_LAYER, HercEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.TITAN, TitanEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_TITAN_LAYER, TitanEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.ATLAS, AtlasEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_ATLAS_LAYER, AtlasEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.ELEPHANT, ElephantEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_ELEPHANT_LAYER, ElephantEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.TITYUS, TityusEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_TITYUS_LAYER, TityusEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.JUNEBUG, JunebugEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_JUNEBUG_LAYER, JunebugEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BeetleRegistry.ACTAEON, ActaeonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_ACTAEON_LAYER, ActaeonEntityModel::getTexturedModelData);

		ModelPredicateProviderRegistry.register(ItemRegistry.BEETLE_JAR, new Identifier("full"),
				(itemStack, clientWorld, livingEntity, whatever) -> {
					if (livingEntity == null || itemStack.getNbt() == null) {
						return 0F;
					}
					return itemStack.getNbt().contains("EntityType") ? 1F : 0F;
				});
		ModelPredicateProviderRegistry.register(ItemRegistry.LEG_BEETLE_JAR, new Identifier("full"),
				(itemStack, clientWorld, livingEntity, whatever) -> {
					if (livingEntity == null || itemStack.getNbt() == null) {
						return 0F;
					}
					return itemStack.getNbt().contains("EntityType") ? 1F : 0F;
				});
		
		for(Item i : BeetleRecipeGenerator.syrups) { 
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
				return FruitSyrup.getColor(stack);
			}, i);
		}
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			return FruitSyrup.getColor(stack);
		}, ItemRegistry.BEETLE_JELLY);

		BlockEntityRendererRegistry.register(BlockRegistry.TANK_BLOCK_ENTITY, TankBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BlockRegistry.BOILER_BLOCK_ENTITY, BoilerBlockEntityRenderer::new);
	}
}