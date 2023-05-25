package volbot.beetlebox.data.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import volbot.beetlebox.data.tags.BeetleItemTagGenerator;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleRecipeGenerator extends FabricRecipeProvider {

	public BeetleRecipeGenerator(FabricDataOutput output) {
		super(output);
	}

	public static HashMap<String, ShapedRecipeJsonBuilder> shaped_recipes = new HashMap<>();
	public static HashMap<String, ShapelessRecipeJsonBuilder> shapeless_recipes = new HashMap<>();
	public static Item[] syrups = new Item[] { BeetleRegistry.APPLE_SYRUP, BeetleRegistry.MELON_SYRUP,
			BeetleRegistry.BERRY_SYRUP, BeetleRegistry.SUGAR_SYRUP };
	public static Item[] mags = new Item[] { Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.NETHERITE_SCRAP };

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {

		for (int syrup_dex = 0; syrup_dex < syrups.length; syrup_dex++) {
			for (int mag_dex = 0; mag_dex < mags.length; mag_dex++) {
				for (int dir_dex = 0; dir_dex < 2; dir_dex++) {
					JellyMixRecipeJsonBuilder recipe = JellyMixRecipeJsonBuilder
							.create(RecipeCategory.FOOD, BeetleRegistry.BEETLE_JELLY)
							.input(Ingredient.ofItems(BeetleRegistry.GELATIN))
							.criterion(RecipeProvider.hasItem(BeetleRegistry.GELATIN),
									RecipeProvider.conditionsFromItem(BeetleRegistry.GELATIN));
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

		offerSmelting(exporter, List.of(BeetleRegistry.SUGAR_GELATIN), RecipeCategory.MISC, BeetleRegistry.GELATIN_GLUE,
				0.7f, 200, "gelatin_glue");

		for (String s : shaped_recipes.keySet()) {
			ShapedRecipeJsonBuilder recipe = shaped_recipes.get(s);
			recipe.offerTo(exporter, s);
		}

		for (String s : shapeless_recipes.keySet()) {
			ShapelessRecipeJsonBuilder recipe = shapeless_recipes.get(s);
			recipe.offerTo(exporter, s);
		}

		Ingredient syrup = Ingredient.ofItems(syrups);
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BeetleRegistry.JELLY_TREAT).input(syrup).input(syrup)
				.input(syrup).input(BeetleRegistry.GELATIN).criterion(RecipeProvider.hasItem(BeetleRegistry.GELATIN),
						RecipeProvider.conditionsFromItem(BeetleRegistry.GELATIN))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Blocks.STICKY_PISTON).pattern("s").pattern("p")
				.input('s', BeetleItemTagGenerator.SLIMEBALLS).input('p', Items.PISTON)
				.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.LEAD, 2).pattern("ss ").pattern("sg ").pattern("  s")
				.input('g', BeetleItemTagGenerator.SLIMEBALLS).input('s', Items.STRING)
				.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BeetleRegistry.SUGAR_GELATIN).pattern("sg").pattern("gs")
				.input('s', Items.SUGAR).input('g', BeetleRegistry.GELATIN)
				.criterion(RecipeProvider.hasItem(BeetleRegistry.GELATIN),
						RecipeProvider.conditionsFromItem(BeetleRegistry.GELATIN))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BeetleRegistry.BOILER).pattern("b b")
				.pattern("bcb").pattern("bib").input('b', Items.BRICK).input('c', Items.CAULDRON)
				.input('i', Items.IRON_BARS)
				.criterion(RecipeProvider.hasItem(Blocks.CAULDRON), RecipeProvider.conditionsFromItem(Blocks.CAULDRON))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeetleRegistry.TANK).pattern("iii").pattern("gjg")
				.pattern("iii").input('i', Items.IRON_INGOT).input('g', Items.GLASS_PANE)
				.input('j', BeetleRegistry.BEETLE_JAR).criterion(RecipeProvider.hasItem(BeetleRegistry.BEETLE_JAR),
						RecipeProvider.conditionsFromItem(BeetleRegistry.BEETLE_JAR))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeetleRegistry.BEETLE_JAR).pattern(" l ").pattern("gjg")
				.pattern(" g ")
				.input('l', Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "logs"))))
				.input('g', Items.GLASS_PANE).input('j', Items.LEAD)
				.criterion(RecipeProvider.hasItem(BeetleRegistry.NET),
						RecipeProvider.conditionsFromItem(BeetleRegistry.NET))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeetleRegistry.NET).pattern(" sw").pattern(" ss")
				.pattern("s  ").input('s', Items.STICK)
				.input('w', Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "wool"))))
				.criterion(RecipeProvider.hasItem(Items.STICK), RecipeProvider.conditionsFromItem(Items.STICK))
				.offerTo(exporter);
	}

}
