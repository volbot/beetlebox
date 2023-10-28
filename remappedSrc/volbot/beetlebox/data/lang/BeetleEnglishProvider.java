package volbot.beetlebox.data.lang;

import java.util.HashMap;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleEnglishProvider extends FabricLanguageProvider {

	public BeetleEnglishProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_us");
	}
	
	public static HashMap<String,String> gen_lang = new HashMap<>();

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		translationBuilder.add(ItemRegistry.ITEM_GROUP, "Beetlebox");
		
		translationBuilder.add(BlockRegistry.TANK, "Beetle Tank");
		translationBuilder.add(BlockRegistry.LEG_TANK, "Legendary Beetle Tank");
		translationBuilder.add(BlockRegistry.BOILER, "Boiler");
		
		translationBuilder.add(ItemRegistry.NET, "Catching Net");
		translationBuilder.add(ItemRegistry.BEETLE_JAR, "Beetle Jar");
		translationBuilder.add(ItemRegistry.LEG_BEETLE_JAR, "Legendary Beetle Jar");

		translationBuilder.add(ItemRegistry.GELATIN, "Gelatin Powder");
		translationBuilder.add(ItemRegistry.SUGAR_GELATIN, "Sugar Gelatin");
		translationBuilder.add(ItemRegistry.GELATIN_GLUE, "Gelatin Glue");
		
		translationBuilder.add(ItemRegistry.APPLE_SYRUP, "Apple Syrup");
		translationBuilder.add(ItemRegistry.MELON_SYRUP, "Melon Syrup");
		translationBuilder.add(ItemRegistry.BERRY_SYRUP, "Berry Syrup");
		translationBuilder.add(ItemRegistry.SUGAR_SYRUP, "Sugar Syrup");
		translationBuilder.add(ItemRegistry.CACTUS_SYRUP, "Cactus Syrup");
		
		translationBuilder.add(ItemRegistry.BEETLE_JELLY, "Beetle Jelly");
		
		translationBuilder.add(ItemRegistry.UPGRADE_DORMANT, "Dormant Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_H_ATTACK, "Horn Attack Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_H_NV, "Nocturnal Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_C_ELYTRA, "Beetle Elytra Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_C_BOOST, "Elytra Boost Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_L_CLIMB, "Wall Crawler Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_L_2JUMP, "Double Jump Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_B_FALLDAM, "Velocity Protection Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_B_SPEED, "Speed Boost Upgrade Bug");
		translationBuilder.add(ItemRegistry.UPGRADE_B_STEP, "Step Height Upgrade Bug");
		
		translationBuilder.add(BlockRegistry.ASH_LOG, "Ash Log");
		translationBuilder.add(BlockRegistry.ASH_WOOD, "Ash Wood");
		translationBuilder.add(BlockRegistry.ASH_LOG_STRIPPED, "Stripped Ash Log");
		translationBuilder.add(BlockRegistry.ASH_WOOD_STRIPPED, "Stripped Ash Wood");
		translationBuilder.add(BlockRegistry.ASH_PLANKS, "Ash Planks");
		translationBuilder.add(BlockRegistry.ASH_PLATE, "Ash Pressure Plate");
		translationBuilder.add(BlockRegistry.ASH_BUTTON, "Ash Button");
		translationBuilder.add(BlockRegistry.ASH_FENCE, "Ash Fence");
		translationBuilder.add(BlockRegistry.ASH_FENCE_GATE, "Ash Fence Gate");
		translationBuilder.add(BlockRegistry.ASH_SLAB, "Ash Slab");
		translationBuilder.add(BlockRegistry.ASH_SAPLING, "Ash Sapling");
		translationBuilder.add(BlockRegistry.ASH_DOOR, "Ash Door");
		translationBuilder.add(BlockRegistry.ASH_TRAPDOOR, "Ash Trapdoor");
		translationBuilder.add(BlockRegistry.ASH_LEAVES, "Ash Leaves");
		translationBuilder.add(BlockRegistry.ASH_STAIRS, "Ash Stairs");
		
		translationBuilder.add(BlockRegistry.IMMIGRATOR, "Immigrator");
		translationBuilder.add(BlockRegistry.EMIGRATOR, "Emigrator");
		translationBuilder.add("beetlebox.container.immigrator", "Immigrator");
		translationBuilder.add("beetlebox.container.emigrator", "Emigrator");

		translationBuilder.add(BeetleBoxClient.elytra_boost_keybind.getTranslationKey(), "Elytra Boost");
		translationBuilder.add(BeetleBoxClient.wallclimb_keybind.getTranslationKey(), "Toggle Wall Crawler");
		translationBuilder.add(BeetleBoxClient.wallclimb_keybind.getCategory(), "beetlebox");
		
		for(String key : gen_lang.keySet()) {
			translationBuilder.add(key,gen_lang.get(key));
		}
	}

}
