package volbot.beetlebox.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import volbot.beetlebox.data.lang.BeetleEnglishProvider;
import volbot.beetlebox.data.loot.BeetleLootGenerator;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.item.equipment.BeetleArmorAbilities;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class BeetleUtils {
	public static void registerBeetle(EntityType<? extends BeetleEntity> beetle_type, String beetle_id,
			String beetle_name, String helmet_ability, int color1, int color2) {

		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox", beetle_id), beetle_type);

		FabricDefaultAttributeRegistry.register(beetle_type, BeetleEntity.createBeetleAttributes());
		Item SPAWN_EGG = new SpawnEggItem(beetle_type, color1, color2, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id + "_spawn_egg"), SPAWN_EGG);
		ItemRegistry.spawn_eggs.add(SPAWN_EGG);
		Item HELMET = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.HELMET, new FabricItemSettings());
		Item LEGS = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.LEGGINGS, new FabricItemSettings());
		Item BOOTS = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.BOOTS, new FabricItemSettings());
		Item CHESTPLATE = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.CHESTPLATE, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id + "_helmet"), HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id + "_chestplate"), CHESTPLATE);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id + "_legs"), LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id + "_boots"), BOOTS);
		ItemRegistry.armor_sets.add(HELMET);
		ItemRegistry.armor_sets.add(CHESTPLATE);
		ItemRegistry.beetle_helmets.add(HELMET);
		ItemRegistry.armor_sets.add(LEGS);
		ItemRegistry.armor_sets.add(BOOTS);
		Item ELYTRON = new Item(new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id + "_elytron"), ELYTRON);
		ItemRegistry.beetle_drops.add(ELYTRON);
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id + "_helmet", createHelmetRecipe(HELMET, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id + "_legs", createLegsRecipe(LEGS, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id + "_boots", createBootsRecipe(BOOTS, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id + "_chestplate",
				createChestplateRecipe(CHESTPLATE, ELYTRON));
		
		BeetleLootGenerator.beetle_loot.put(beetle_id, createLootTable(ELYTRON));

		BeetleArmorAbilities.beetle_abilities.put(beetle_id, helmet_ability);

		BeetleEnglishProvider.gen_lang.put(beetle_type.getTranslationKey(), beetle_name);

		BeetleEnglishProvider.gen_lang.put(CHESTPLATE.getTranslationKey(), beetle_name + " Chestplate");
		BeetleEnglishProvider.gen_lang.put(HELMET.getTranslationKey(), beetle_name + " Helmet");
		BeetleEnglishProvider.gen_lang.put(LEGS.getTranslationKey(), beetle_name + " Leggings");
		BeetleEnglishProvider.gen_lang.put(BOOTS.getTranslationKey(), beetle_name + " Boots");
		BeetleEnglishProvider.gen_lang.put(ELYTRON.getTranslationKey(), beetle_name + " Elytron");
		BeetleEnglishProvider.gen_lang.put(SPAWN_EGG.getTranslationKey(), beetle_name + " Spawn Egg");
	}

	public static ShapedRecipeJsonBuilder createChestplateRecipe(Item CHESTPLATE, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, CHESTPLATE).pattern("eoe").pattern("gcg")
				.pattern("eoe").input('e', ELYTRON).input('c', Items.DIAMOND_CHESTPLATE)
				.input('g', ItemRegistry.GELATIN_GLUE).input('o', Items.OBSIDIAN)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createHelmetRecipe(Item HELMET, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, HELMET).pattern("eoe").pattern("ghg")
				.pattern("eoe").input('e', ELYTRON).input('h', Items.DIAMOND_HELMET)
				.input('g', ItemRegistry.GELATIN_GLUE).input('o', Items.OBSIDIAN)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createLegsRecipe(Item LEGS, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, LEGS).pattern("eoe").pattern("glg").pattern("eoe")
				.input('e', ELYTRON).input('l', Items.DIAMOND_LEGGINGS).input('g', ItemRegistry.GELATIN_GLUE)
				.input('o', Items.OBSIDIAN)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createBootsRecipe(Item BOOTS, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BOOTS).pattern("eoe").pattern("gbg").pattern("eoe")
				.input('e', ELYTRON).input('b', Items.DIAMOND_BOOTS).input('g', ItemRegistry.GELATIN_GLUE)
				.input('o', Items.OBSIDIAN)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static LootTable.Builder createLootTable(Item ELYTRON) {
		return LootTable.builder().pool(
				LootPool.builder().rolls(UniformLootNumberProvider.create(1.0F,2.0F)).with(ItemEntry.builder(ELYTRON))
				.apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0F,3.0F))));
	}
}
