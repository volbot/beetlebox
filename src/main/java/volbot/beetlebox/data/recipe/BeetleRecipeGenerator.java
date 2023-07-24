package volbot.beetlebox.data.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.OneTwentyRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import volbot.beetlebox.data.tags.BeetleItemTagGenerator;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleRecipeGenerator extends FabricRecipeProvider {

	public BeetleRecipeGenerator(FabricDataOutput output) {
		super(output);
	}

	public static HashMap<String, ShapedRecipeJsonBuilder> shaped_recipes = new HashMap<>();
	public static HashMap<String, ShapelessRecipeJsonBuilder> shapeless_recipes = new HashMap<>();
	public static Item[] syrups = new Item[] { ItemRegistry.APPLE_SYRUP, ItemRegistry.MELON_SYRUP,
			ItemRegistry.BERRY_SYRUP, ItemRegistry.SUGAR_SYRUP };
	public static Item[] mags = new Item[] { Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.NETHERITE_SCRAP };

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		for (int syrup_dex = 0; syrup_dex < syrups.length; syrup_dex++) {
			for (int mag_dex = 0; mag_dex < mags.length; mag_dex++) {
				for (int dir_dex = 0; dir_dex < 2; dir_dex++) {
					JellyMixRecipeJsonBuilder recipe = JellyMixRecipeJsonBuilder
							.create(RecipeCategory.FOOD, ItemRegistry.BEETLE_JELLY)
							.input(Ingredient.ofItems(ItemRegistry.GELATIN))
							.criterion(RecipeProvider.hasItem(ItemRegistry.GELATIN),
									RecipeProvider.conditionsFromItem(ItemRegistry.GELATIN));
					recipe.input(Ingredient.ofItems(syrups[syrup_dex]));
					recipe.input(Ingredient.ofItems(mags[mag_dex]));
					if (dir_dex == 0) {
						recipe.input(Ingredient.ofItems(Items.GRASS));
					} else {
						recipe.input(Ingredient.ofItems(Items.TALL_GRASS));
					}

					recipe.offerTo(exporter,
							new Identifier("beetlebox", "beetle_jelly_" + syrup_dex + mag_dex + dir_dex));
				}
			}
		}

		offerSmelting(exporter, List.of(ItemRegistry.SUGAR_GELATIN), RecipeCategory.MISC, ItemRegistry.GELATIN_GLUE,
				0.7f, 200, "gelatin_glue");

		for (String s : shaped_recipes.keySet()) {
			ShapedRecipeJsonBuilder recipe = shaped_recipes.get(s);
			recipe.offerTo(exporter, s);
		}

		for (String s : shapeless_recipes.keySet()) {
			ShapelessRecipeJsonBuilder recipe = shapeless_recipes.get(s);
			recipe.offerTo(exporter, s);
		}
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Blocks.STICKY_PISTON).pattern("s").pattern("p")
				.input('s', BeetleItemTagGenerator.SLIMEBALLS).input('p', Items.PISTON)
				.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.LEAD, 2).pattern("ss ").pattern("sg ").pattern("  s")
				.input('g', BeetleItemTagGenerator.SLIMEBALLS).input('s', Items.STRING)
				.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemRegistry.SUGAR_GELATIN).pattern("sg").pattern("gs")
				.input('s', Items.SUGAR).input('g', ItemRegistry.GELATIN)
				.criterion(RecipeProvider.hasItem(ItemRegistry.GELATIN),
						RecipeProvider.conditionsFromItem(ItemRegistry.GELATIN))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BOILER).pattern("b b")
				.pattern("bcb").pattern("bib").input('b', Items.BRICK).input('c', Items.CAULDRON)
				.input('i', Items.IRON_BARS)
				.criterion(RecipeProvider.hasItem(Blocks.CAULDRON), RecipeProvider.conditionsFromItem(Blocks.CAULDRON))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BlockRegistry.TANK).pattern("iii").pattern("gjg")
				.pattern("iii").input('i', Items.IRON_INGOT).input('g', Items.GLASS_PANE)
				.input('j', ItemRegistry.BEETLE_JAR).criterion(RecipeProvider.hasItem(ItemRegistry.NET),
						RecipeProvider.conditionsFromItem(ItemRegistry.NET))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.BEETLE_JAR).pattern(" l ").pattern("gjg")
				.pattern(" g ")
				.input('l', Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "logs"))))
				.input('g', Items.GLASS_PANE).input('j', Items.LEAD)
				.criterion(RecipeProvider.hasItem(ItemRegistry.NET),
						RecipeProvider.conditionsFromItem(ItemRegistry.NET))
				.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BlockRegistry.LEG_TANK)
		.pattern("idi")
		.pattern("gjg")
		.pattern("idi")
		.input('i', Items.GOLD_INGOT)
		.input('d', Items.DIAMOND)
		.input('g', Items.GLASS_PANE)
		.input('j', ItemRegistry.LEG_BEETLE_JAR)
		.criterion(RecipeProvider.hasItem(ItemRegistry.NET),
				RecipeProvider.conditionsFromItem(ItemRegistry.NET))
		.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.LEG_BEETLE_JAR)
		.pattern("dod")
		.pattern("eje")
		.pattern("ded")
		.input('o', Items.OBSIDIAN)
		.input('e', Items.ENDER_EYE)
		.input('d', Items.DIAMOND)
		.input('j', ItemRegistry.BEETLE_JAR)
		.criterion(RecipeProvider.hasItem(ItemRegistry.NET),
				RecipeProvider.conditionsFromItem(ItemRegistry.NET))
		.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.NET).pattern(" sw").pattern(" ss")
				.pattern("s  ").input('s', Items.STICK)
				.input('w', Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "wool"))))
				.criterion(RecipeProvider.hasItem(Items.STICK), RecipeProvider.conditionsFromItem(Items.STICK))
				.offerTo(exporter);
		
		RecipeProvider.generateFamily(exporter, BlockRegistry.ASH_FAMILY);

        RecipeProvider.offerPlanksRecipe2(exporter, BlockRegistry.ASH_PLANKS, BeetleItemTagGenerator.ASH_LOGS, 4);
        RecipeProvider.offerBarkBlockRecipe(exporter, BlockRegistry.ASH_WOOD, BlockRegistry.ASH_LOG);
        RecipeProvider.offerBarkBlockRecipe(exporter, BlockRegistry.ASH_WOOD_STRIPPED, BlockRegistry.ASH_LOG_STRIPPED);
		
	}
}
