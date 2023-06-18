package volbot.beetlebox.data.lang;

import java.util.HashMap;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
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
		
		for(String key : gen_lang.keySet()) {
			translationBuilder.add(key,gen_lang.get(key));
		}
	}

}
