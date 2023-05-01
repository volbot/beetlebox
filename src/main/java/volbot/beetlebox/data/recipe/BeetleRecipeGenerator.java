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

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		offerSmelting(exporter,
				List.of(BeetleRegistry.SUGAR_GELATIN), 
				RecipeCategory.MISC, 
				BeetleRegistry.GELATIN_GLUE,
                0.7f, 200, "gelatin_glue");
		
		for(String s : shaped_recipes.keySet()) {
			System.out.println(s);
			ShapedRecipeJsonBuilder recipe = shaped_recipes.get(s);
			recipe.offerTo(exporter, s);
		}
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Blocks.STICKY_PISTON)
			.pattern("s")
			.pattern("p")
			.input('s', BeetleItemTagGenerator.SLIMEBALLS)
			.input('p', Items.PISTON)
		.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.LEAD, 2)
			.pattern("ss ")
			.pattern("sg ")
			.pattern("  s")
			.input('g', BeetleItemTagGenerator.SLIMEBALLS)
			.input('s', Items.STRING)
		.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
		.offerTo(exporter);
	
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BeetleRegistry.SUGAR_GELATIN)
			.pattern("sg")
			.pattern("gs")
			.input('s', Items.SUGAR)
			.input('g', BeetleRegistry.GELATIN)
		.criterion(RecipeProvider.hasItem(BeetleRegistry.GELATIN), RecipeProvider.conditionsFromItem(BeetleRegistry.GELATIN))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BeetleRegistry.BOILER)
			.pattern("b b")
			.pattern("bcb")
			.pattern("bib")
			.input('b', Items.BRICK)
			.input('c', Items.CAULDRON)
			.input('i', Items.IRON_BARS)
		.criterion(RecipeProvider.hasItem(Blocks.CAULDRON), RecipeProvider.conditionsFromItem(Blocks.CAULDRON))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeetleRegistry.TANK)
			.pattern("iii")
			.pattern("gjg")
			.pattern("iii")
			.input('i', Items.IRON_INGOT)
			.input('g', Items.GLASS_PANE)
			.input('j', BeetleRegistry.BEETLE_JAR)
		.criterion(RecipeProvider.hasItem(BeetleRegistry.BEETLE_JAR), RecipeProvider.conditionsFromItem(BeetleRegistry.BEETLE_JAR))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeetleRegistry.BEETLE_JAR)
			.pattern(" l ")
			.pattern("gjg")
			.pattern(" g ")
			.input('l', Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft","logs"))))
			.input('g', Items.GLASS_PANE)
			.input('j', Items.LEAD)
		.criterion(RecipeProvider.hasItem(BeetleRegistry.NET), RecipeProvider.conditionsFromItem(BeetleRegistry.NET))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BeetleRegistry.NET)
			.pattern(" sw")
			.pattern(" ss")
			.pattern("s  ")
			.input('s', Items.STICK)
			.input('w', Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft","wool"))))
		.criterion(RecipeProvider.hasItem(Items.STICK), RecipeProvider.conditionsFromItem(Items.STICK))
		.offerTo(exporter);
	}

}
