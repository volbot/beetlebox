package volbot.beetlebox.data.lang;

import java.util.HashMap;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleEnglishProvider extends FabricLanguageProvider {

	public BeetleEnglishProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_us");
	}
	
	public static HashMap<String,String> gen_lang = new HashMap<>();

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		translationBuilder.add(BeetleRegistry.ITEM_GROUP, "Beetlebox");
		
		translationBuilder.add(BeetleRegistry.TANK, "Beetle Tank");
		translationBuilder.add(BeetleRegistry.LEG_TANK, "Legendary Beetle Tank");
		translationBuilder.add(BeetleRegistry.BOILER, "Boiler");
		
		translationBuilder.add(BeetleRegistry.NET, "Catching Net");
		translationBuilder.add(BeetleRegistry.BEETLE_JAR, "Beetle Jar");
		translationBuilder.add(BeetleRegistry.LEG_BEETLE_JAR, "Legendary Beetle Jar");

		translationBuilder.add(BeetleRegistry.GELATIN, "Gelatin Powder");
		translationBuilder.add(BeetleRegistry.SUGAR_GELATIN, "Sugar Gelatin");
		translationBuilder.add(BeetleRegistry.GELATIN_GLUE, "Gelatin Glue");
		
		translationBuilder.add("item.beetlebox.fruit_syrup.apple", "Apple Syrup");
		translationBuilder.add("item.beetlebox.fruit_syrup.melon", "Melon Syrup");
		translationBuilder.add("item.beetlebox.fruit_syrup.berry", "Berry Syrup");
		translationBuilder.add("item.beetlebox.fruit_syrup.sugar", "Sugar Syrup");
		translationBuilder.add("item.beetlebox.fruit_syrup.glow", "Glow Syrup");
		
		translationBuilder.add("item.beetlebox.jelly_mix", "Jelly Mix");
		
		for(String key : gen_lang.keySet()) {
			translationBuilder.add(key,gen_lang.get(key));
		}
	}

}
