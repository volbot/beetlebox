package volbot.beetlebox.data.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import volbot.beetlebox.data.tags.BeetleItemTagGenerator;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleRecipeGenerator extends FabricRecipeProvider {

	public BeetleRecipeGenerator(FabricDataGenerator output) {
		super(output);
	}
	
	public static HashMap<String, ShapedRecipeJsonBuilder> shaped_recipes = new HashMap<>();

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		offerSmelting(exporter,
				List.of(BeetleRegistry.SUGAR_GELATIN),
				BeetleRegistry.GELATIN_GLUE,
                0.7f, 200, "gelatin_glue");
		
		for(String s : shaped_recipes.keySet()) {
			System.out.println(s);
			ShapedRecipeJsonBuilder recipe = shaped_recipes.get(s);
			recipe.offerTo(exporter, s);
		}
		
		ShapedRecipeJsonBuilder.create(Blocks.STICKY_PISTON)
			.pattern("s")
			.pattern("p")
			.input('s', BeetleItemTagGenerator.SLIMEBALLS)
			.input('p', Items.PISTON)
		.criterion("has_any_slimeball", net.minecraft.data.server.RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(Items.LEAD, 2)
			.pattern("ss ")
			.pattern("sg ")
			.pattern("  s")
			.input('g', BeetleItemTagGenerator.SLIMEBALLS)
			.input('s', Items.STRING)
		.criterion("has_any_slimeball", net.minecraft.data.server.RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
		.offerTo(exporter);
	
		
		ShapedRecipeJsonBuilder.create(BeetleRegistry.SUGAR_GELATIN)
			.pattern("sg")
			.pattern("gs")
			.input('s', Items.SUGAR)
			.input('g', BeetleRegistry.GELATIN)
		.criterion(net.minecraft.data.server.RecipeProvider.hasItem(BeetleRegistry.GELATIN), net.minecraft.data.server.RecipeProvider.conditionsFromItem(BeetleRegistry.GELATIN))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(BeetleRegistry.BOILER)
			.pattern("b b")
			.pattern("bcb")
			.pattern("bib")
			.input('b', Items.BRICK)
			.input('c', Items.CAULDRON)
			.input('i', Items.IRON_BARS)
		.criterion(net.minecraft.data.server.RecipeProvider.hasItem(Blocks.CAULDRON), net.minecraft.data.server.RecipeProvider.conditionsFromItem(Blocks.CAULDRON))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(BeetleRegistry.TANK)
			.pattern("iii")
			.pattern("gjg")
			.pattern("iii")
			.input('i', Items.IRON_INGOT)
			.input('g', Items.GLASS_PANE)
			.input('j', BeetleRegistry.BEETLE_JAR)
		.criterion(net.minecraft.data.server.RecipeProvider.hasItem(BeetleRegistry.BEETLE_JAR), net.minecraft.data.server.RecipeProvider.conditionsFromItem(BeetleRegistry.BEETLE_JAR))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(BeetleRegistry.BEETLE_JAR)
			.pattern(" l ")
			.pattern("gjg")
			.pattern(" g ")
			.input('l', Ingredient.fromTag(net.minecraft.tag.TagKey.of(Registry.ITEM_KEY, new Identifier("minecraft","logs"))))
			.input('g', Items.GLASS_PANE)
			.input('j', Items.LEAD)
		.criterion(net.minecraft.data.server.RecipeProvider.hasItem(BeetleRegistry.NET), net.minecraft.data.server.RecipeProvider.conditionsFromItem(BeetleRegistry.NET))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(BeetleRegistry.NET)
			.pattern(" sw")
			.pattern(" ss")
			.pattern("s  ")
			.input('s', Items.STICK)
			.input('w', Ingredient.fromTag(net.minecraft.tag.TagKey.of(Registry.ITEM_KEY, new Identifier("minecraft","wool"))))
		.criterion(net.minecraft.data.server.RecipeProvider.hasItem(Items.STICK), net.minecraft.data.server.RecipeProvider.conditionsFromItem(Items.STICK))
		.offerTo(exporter);
		
	}

}
