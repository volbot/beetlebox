package volbot.beetlebox.client;

import java.util.HashMap;
import java.util.UUID;

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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.client.render.block.entity.BoilerBlockEntityRenderer;
import volbot.beetlebox.client.render.block.entity.IncubatorBlockEntityRenderer;
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
import volbot.beetlebox.client.render.entity.projectile.BeetleProjectileEntityRenderer;
import volbot.beetlebox.compat.trinkets.BeetlepackTrinketRenderer;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.entity.projectile.BeetleProjectileEntity;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;
import volbot.beetlebox.item.FruitSyrup;
import volbot.beetlebox.item.equipment.BeetleArmorAbilities;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.client.render.armor.BeetlepackRenderer;
import volbot.beetlebox.client.render.armor.beetle.ActaeonHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.AtlasHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.BeetleArmorEntityModel;
import volbot.beetlebox.client.render.armor.beetle.BeetleArmorRenderer;
import volbot.beetlebox.client.render.armor.beetle.BeetleElytraFeatureRenderer;
import volbot.beetlebox.client.render.armor.beetle.ElephantHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.HercHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.JRBHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.StandardHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.TitanHelmetModel;
import volbot.beetlebox.client.render.armor.beetle.TityusHelmetModel;
import volbot.beetlebox.client.render.armor.BeetlepackModel;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class BeetleBoxClient implements ClientModInitializer {

	public static KeyBinding elytra_boost_keybind;
	public static KeyBinding wallclimb_keybind;

	public static HashMap<String, BeetleArmorEntityModel<?>> beetle_helmets = new HashMap<>();

	public static final EntityModelLayer MODEL_JRB_LAYER = new EntityModelLayer(new Identifier("beetlebox", "jrb"),
			"main");
	public static final EntityModelLayer MODEL_HERC_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "hercules"), "main");
	public static final EntityModelLayer MODEL_TITAN_LAYER = new EntityModelLayer(
			new Identifier("beetlebox", "titanus"), "main");
	public static final EntityModelLayer MODEL_ATLAS_LAYER = new EntityModelLayer(new Identifier("beetlebox", "atlas"),
			"main");
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

		elytra_boost_keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.beetlebox.elytra_boost",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.beetlebox.beetlebox"));
		wallclimb_keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.beetlebox.wall_climb",
				InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "category.beetlebox.beetlebox"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (elytra_boost_keybind.wasPressed()) {
				BeetleArmorAbilities
						.elytra_boost(client.getServer().getPlayerManager().getPlayer(client.player.getUuid()));
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (wallclimb_keybind.wasPressed()) {
				BeetleArmorAbilities.toggle_wallclimb(
						(PlayerEntity) client.getServer().getPlayerManager().getPlayer(client.player.getUuid()));
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

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("beetlebox", "beetle_proj_packet"),
				(client, handler, buf, responseSender) -> {
					boolean landed = buf.readBoolean();
					String entity_type = buf.readString();
					NbtCompound entity_data = buf.readNbt();
					String entity_name = buf.readString();
					UUID entity_id = buf.readUuid();
					client.execute(() -> {
						Entity entity = handler.getWorld().getEntityLookup().get(entity_id);
						BeetleProjectileEntity e = ((BeetleProjectileEntity) entity);
						if (e != null) {
							try {
								e.landed = landed;
								e.entity = new ContainedEntity(entity_type, entity_data, entity_name);
							} catch (NullPointerException ex) {
								System.out.println("BEETLE PROJECTILE ERROR #1218");
							}
							;
						}
					});
				});

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("beetlebox/beetle_packet"),
				(client, handler, buf, responseSender) -> {
					int size = buf.readInt();
					float damage = buf.readFloat();
					float speed = buf.readFloat();
					float maxhealth = buf.readFloat();
					UUID entity_id = buf.readUuid();
					client.execute(() -> {
						BeetleEntity e = ((BeetleEntity) handler.getWorld().getEntityLookup().get(entity_id));
						if (e != null) {
							try {
								e.setSize(size);
								e.setDamageMult(damage);
								e.setSpeedMult(speed);
								e.setMaxHealthMult(maxhealth);
							} catch (NullPointerException ex) {
								e.size_cached = size;
								e.damage_cached = damage;
								e.speed_cached = speed;
								e.maxhealth_cached = maxhealth;
								e.unSynced = true;
							}
						}
					});
				});

		LivingEntityFeatureRendererRegistrationCallback.EVENT
				.register((entityType, entityRenderer, registrationHelper, context) -> {
					if (entityRenderer.getModel() instanceof BipedEntityModel) {
						registrationHelper
								.register(new BeetleElytraFeatureRenderer(entityRenderer, context.getModelLoader()));
					}
				});

		for (Item i : ItemRegistry.beetle_helmets) {
			BeetleArmorItem armorItem = (BeetleArmorItem) i;
			ArmorRenderer.register(
					new BeetleArmorRenderer(BeetleBoxClient.beetle_helmets.get(armorItem.getMaterial().getName())),
					armorItem);
		}
		ArmorRenderer.register(new BeetlepackRenderer(new BeetlepackModel<>()), ItemRegistry.BEETLEPACK);
		if (FabricLoader.getInstance().isModLoaded("trinkets")) {
			BeetlepackTrinketRenderer.registerRenderer();
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

		for (Item i : BeetleRecipeGenerator.syrups) {
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
				return FruitSyrup.getColor(stack);
			}, i);
		}
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			return FruitSyrup.getColor(stack);
		}, ItemRegistry.BEETLE_JELLY);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
			return FoliageColors.getBirchColor();
		}, BlockRegistry.ASH_LEAVES);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			return FoliageColors.getBirchColor();
		}, BlockRegistry.ASH_LEAVES);

		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TANK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LEG_TANK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ASH_LEAVES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ASH_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ASH_TRAPDOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ASH_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.IMMIGRATOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.EMIGRATOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INCUBATOR, RenderLayer.getCutout());

		EntityRendererRegistry.register(BeetleRegistry.BEETLE_PROJECTILE,
				(context) -> new BeetleProjectileEntityRenderer(context));

		BlockEntityRendererRegistry.register(BlockRegistry.TANK_BLOCK_ENTITY, TankBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BlockRegistry.BOILER_BLOCK_ENTITY, BoilerBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BlockRegistry.INCUBATOR_BLOCK_ENTITY, IncubatorBlockEntityRenderer::new);
	}
}