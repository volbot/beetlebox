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
import net.minecraft.recipe.book.RecipeCategory;
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
			.input('p', Blocks.PISTON)
		.criterion("has_any_slimeball", RecipeProvider.conditionsFromTag(BeetleItemTagGenerator.SLIMEBALLS))
		.offerTo(exporter);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BeetleRegistry.SUGAR_GELATIN)
			.pattern("sg")
			.pattern("gs")
			.input('s', Items.SUGAR)
			.input('g', BeetleRegistry.GELATIN)
		.criterion(RecipeProvider.hasItem(BeetleRegistry.GELATIN), RecipeProvider.conditionsFromItem(BeetleRegistry.GELATIN))
		.offerTo(exporter);
	}

}
