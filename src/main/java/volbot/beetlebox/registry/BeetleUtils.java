package volbot.beetlebox.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import volbot.beetlebox.data.lang.BeetleEnglishProvider;
import volbot.beetlebox.data.loot.BeetleLootGenerator;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.data.recipe.JellyMixRecipeJsonBuilder;
import volbot.beetlebox.data.recipe.UpgradeUsageRecipeJsonBuilder;
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
		int j = 0;
		for (Item i : ItemRegistry.helmet_upgrades) {
			BeetleRecipeGenerator.upgrade_recipes.put("upgrade_" + beetle_id + "_helmet_" + (j++),
					UpgradeUsageRecipeJsonBuilder.create(RecipeCategory.COMBAT, HELMET).input(Ingredient.ofItems(i)).input(HELMET)
							.criterion(RecipeProvider.hasItem(HELMET), RecipeProvider.conditionsFromItem(HELMET)));
		}
		j = 0;
		for (Item i : ItemRegistry.chest_upgrades) {
			BeetleRecipeGenerator.upgrade_recipes.put("upgrade_" + beetle_id + "_chest_" + (j++),
					UpgradeUsageRecipeJsonBuilder.create(RecipeCategory.COMBAT, CHESTPLATE).input(Ingredient.ofItems(i)).input(CHESTPLATE)
							.criterion(RecipeProvider.hasItem(CHESTPLATE),
									RecipeProvider.conditionsFromItem(CHESTPLATE)));
		}
		j = 0;
		for (Item i : ItemRegistry.legs_upgrades) {
			BeetleRecipeGenerator.upgrade_recipes.put("upgrade_" + beetle_id + "_legs_" + (j++),
					UpgradeUsageRecipeJsonBuilder.create(RecipeCategory.COMBAT, LEGS).input(Ingredient.ofItems(i)).input(LEGS)
							.criterion(RecipeProvider.hasItem(LEGS), RecipeProvider.conditionsFromItem(LEGS)));
		}
		j = 0;
		for (Item i : ItemRegistry.boots_upgrades) {
			BeetleRecipeGenerator.upgrade_recipes.put("upgrade_" + beetle_id + "_boots_" + (j++),
					UpgradeUsageRecipeJsonBuilder.create(RecipeCategory.COMBAT, BOOTS).input(Ingredient.ofItems(i)).input(BOOTS)
							.criterion(RecipeProvider.hasItem(BOOTS), RecipeProvider.conditionsFromItem(BOOTS)));
		}
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
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, CHESTPLATE).pattern("eee").pattern("ece")
				.pattern("eee").input('e', ELYTRON).input('c', Items.DIAMOND_CHESTPLATE)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createHelmetRecipe(Item HELMET, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, HELMET).pattern("eee").pattern("ehe")
				.pattern("eee").input('e', ELYTRON).input('h', Items.DIAMOND_HELMET)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createLegsRecipe(Item LEGS, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, LEGS).pattern("eee").pattern("ele").pattern("eee")
				.input('e', ELYTRON).input('l', Items.DIAMOND_LEGGINGS)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createBootsRecipe(Item BOOTS, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BOOTS).pattern("eee").pattern("ebe").pattern("eee")
				.input('e', ELYTRON).input('b', Items.DIAMOND_BOOTS)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static LootTable.Builder createLootTable(Item ELYTRON) {
		return LootTable.builder().pool(
				LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(ELYTRON)));
	}
}
