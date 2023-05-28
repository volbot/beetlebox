package volbot.beetlebox.registry;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import volbot.beetlebox.data.lang.BeetleEnglishProvider;
import volbot.beetlebox.data.loot.BeetleLootGenerator;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.BeetleElytraItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleUtils {
	public static void registerBeetle(EntityType<? extends BeetleEntity> beetle_type, String beetle_id, String beetle_name, int color1, int color2) {
		
		Registry.register(Registry.ENTITY_TYPE, new Identifier("beetlebox",beetle_id), beetle_type);
		
		FabricDefaultAttributeRegistry.register(beetle_type, BeetleEntity.createBeetleAttributes());
		Item SPAWN_EGG = new SpawnEggItem(beetle_type, color1, color2, new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_spawn_egg"), SPAWN_EGG);
		BeetleRegistry.spawn_eggs.add(SPAWN_EGG);
		Item ELYTRA = new BeetleElytraItem(new ChitinMaterial(beetle_id), new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Item HELMET = new BeetleArmorItem(new ChitinMaterial(beetle_id), EquipmentSlot.HEAD, new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Item LEGS = new BeetleArmorItem(new ChitinMaterial(beetle_id), EquipmentSlot.LEGS, new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Item BOOTS = new BeetleArmorItem(new ChitinMaterial(beetle_id), EquipmentSlot.FEET, new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Item CHESTPLATE = new BeetleArmorItem(new ChitinMaterial(beetle_id), EquipmentSlot.CHEST, new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_helmet"), HELMET);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_elytra"), ELYTRA);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_chestplate"), CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_legs"), LEGS);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_boots"), BOOTS);
		BeetleRegistry.armor_sets.add(HELMET);
		BeetleRegistry.armor_sets.add(ELYTRA);
		BeetleRegistry.armor_sets.add(CHESTPLATE);
		BeetleRegistry.beetle_helmets.add(HELMET);
		BeetleRegistry.armor_sets.add(LEGS);
		BeetleRegistry.armor_sets.add(BOOTS);
		Item ELYTRON = new Item(new FabricItemSettings().group(BeetleRegistry.ITEM_GROUP));
		Registry.register(Registry.ITEM, new Identifier("beetlebox", beetle_id+"_elytron"), ELYTRON);
		BeetleRegistry.beetle_drops.add(ELYTRON);
		BeetleRecipeGenerator.shapeless_recipes.put(beetle_id+"_elytra", createElytraRecipe(CHESTPLATE, ELYTRA, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id+"_helmet", createHelmetRecipe(HELMET, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id+"_legs", createLegsRecipe(LEGS, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id+"_boots", createBootsRecipe(BOOTS, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id+"_chestplate", createChestplateRecipe(CHESTPLATE, ELYTRON));
		BeetleLootGenerator.beetle_loot.put(beetle_id, createLootTable(ELYTRON));
		
		BeetleEnglishProvider.gen_lang.put(beetle_type.getTranslationKey(), beetle_name);

		BeetleEnglishProvider.gen_lang.put(ELYTRA.getTranslationKey(), beetle_name + " Elytra");
		BeetleEnglishProvider.gen_lang.put(CHESTPLATE.getTranslationKey(), beetle_name + " Chestplate");
		BeetleEnglishProvider.gen_lang.put(HELMET.getTranslationKey(), beetle_name + " Helmet");
		BeetleEnglishProvider.gen_lang.put(LEGS.getTranslationKey(), beetle_name + " Leggings");
		BeetleEnglishProvider.gen_lang.put(BOOTS.getTranslationKey(), beetle_name + " Boots");
		BeetleEnglishProvider.gen_lang.put(ELYTRON.getTranslationKey(), beetle_name + " Elytron");
		BeetleEnglishProvider.gen_lang.put(SPAWN_EGG.getTranslationKey(), beetle_name + " Spawn Egg");
	}

	public static ShapelessRecipeJsonBuilder createElytraRecipe(Item CHESTPLATE, Item ELYTRA, Item ELYTRON) {
		return ShapelessRecipeJsonBuilder.create(ELYTRA)
				.input(Ingredient.ofItems(CHESTPLATE))
				.input(Ingredient.ofItems(Items.ELYTRA))
				.criterion(FabricRecipeProvider.hasItem(CHESTPLATE), FabricRecipeProvider.conditionsFromItem(CHESTPLATE));
	}
	
	public static ShapedRecipeJsonBuilder createChestplateRecipe(Item CHESTPLATE, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(CHESTPLATE).pattern("eee").pattern("ece")
				.pattern("eee").input('e', ELYTRON).input('c', Items.DIAMOND_CHESTPLATE)
				.criterion(FabricRecipeProvider.hasItem(ELYTRON), FabricRecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createHelmetRecipe(Item HELMET, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(HELMET).pattern("eee").pattern("ehe")
				.pattern("eee").input('e', ELYTRON).input('h', Items.DIAMOND_HELMET)
				.criterion(FabricRecipeProvider.hasItem(ELYTRON), FabricRecipeProvider.conditionsFromItem(ELYTRON));
	}
	
	public static ShapedRecipeJsonBuilder createLegsRecipe(Item LEGS, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(LEGS).pattern("eee").pattern("ele")
				.pattern("eee").input('e', ELYTRON).input('l', Items.DIAMOND_LEGGINGS)
				.criterion(FabricRecipeProvider.hasItem(ELYTRON), FabricRecipeProvider.conditionsFromItem(ELYTRON));
	}
	
	public static ShapedRecipeJsonBuilder createBootsRecipe(Item BOOTS, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(BOOTS).pattern("eee").pattern("ebe")
				.pattern("eee").input('e', ELYTRON).input('b', Items.DIAMOND_BOOTS)
				.criterion(FabricRecipeProvider.hasItem(ELYTRON), FabricRecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static LootTable.Builder createLootTable(Item ELYTRON) {
		return LootTable.builder()
	        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
	        .with(ItemEntry.builder(ELYTRON)));
	    }
}
