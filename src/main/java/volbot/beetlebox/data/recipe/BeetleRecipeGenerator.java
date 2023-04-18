package volbot.beetlebox.data.recipe;

import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.book.RecipeCategory;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleRecipeGenerator extends FabricRecipeProvider{

	public BeetleRecipeGenerator(FabricDataOutput output) {
		super(output);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		offerSmelting(exporter,
				List.of(BeetleRegistry.SUGAR_GELATIN), 
				RecipeCategory.MISC, 
				BeetleRegistry.GELATIN_GLUE,
                0.7f, 200, "citrine");
	}

}
