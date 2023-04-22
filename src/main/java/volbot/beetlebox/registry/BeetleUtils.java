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
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.render.armor.BeetleArmorEntityModel;
import volbot.beetlebox.data.loot.BeetleLootGenerator;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.BeetleElytraItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;
import volbot.beetlebox.client.render.armor.StandardHelmetModel;

public class BeetleUtils {
	public static void registerBeetle(EntityType<? extends BeetleEntity> beetleType, String beetle_id, int color1, int color2, BeetleArmorEntityModel<?> helmet_model) {
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox",beetle_id), beetleType);
		FabricDefaultAttributeRegistry.register(beetleType, BeetleEntity.createBeetleAttributes());
		Item SPAWN_EGG = new SpawnEggItem(beetleType, color1, color2, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_spawn_egg"), SPAWN_EGG);
		BeetleRegistry.spawn_eggs.add(SPAWN_EGG);
		Item ELYTRA = new BeetleElytraItem(new ChitinMaterial(beetle_id), new FabricItemSettings());
		Item HELMET = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.HELMET, new FabricItemSettings());
		Item LEGS = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.LEGGINGS, new FabricItemSettings());
		Item BOOTS = new BeetleArmorItem(new ChitinMaterial(beetle_id), Type.BOOTS, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_helmet"), HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_elytra"), ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_legs"), LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_boots"), BOOTS);
		BeetleRegistry.armor_sets.add(ELYTRA);
		BeetleRegistry.armor_sets.add(HELMET);
		BeetleRegistry.beetle_helmets.put(HELMET, helmet_model == null ? helmet_model : new StandardHelmetModel<>(beetle_id));
		BeetleRegistry.armor_sets.add(LEGS);
		BeetleRegistry.armor_sets.add(BOOTS);
		Item ELYTRON = new Item(new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_elytron"), ELYTRON);
		BeetleRegistry.beetle_drops.add(ELYTRON);
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id+"_elytra", createElytraRecipe(ELYTRA, ELYTRON));
		BeetleRecipeGenerator.shaped_recipes.put(beetle_id+"_helmet", createHelmetRecipe(HELMET, ELYTRON));
		BeetleLootGenerator.beetle_loot.put(beetle_id, createLootTable(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createElytraRecipe(Item ELYTRA, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ELYTRA).pattern("eee").pattern("ele")
				.pattern("eee").input('e', ELYTRON).input('l', Items.ELYTRA)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static ShapedRecipeJsonBuilder createHelmetRecipe(Item HELMET, Item ELYTRON) {
		return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, HELMET).pattern("eee").pattern("ehe")
				.pattern("eee").input('e', ELYTRON).input('h', Items.IRON_HELMET)
				.criterion(RecipeProvider.hasItem(ELYTRON), RecipeProvider.conditionsFromItem(ELYTRON));
	}

	public static LootTable.Builder createLootTable(Item ELYTRON) {
		return LootTable.builder()
	        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
	        .with(ItemEntry.builder(ELYTRON)));
	    }
}
