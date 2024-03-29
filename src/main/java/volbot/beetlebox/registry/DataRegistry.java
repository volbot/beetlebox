package volbot.beetlebox.registry;

import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import volbot.beetlebox.recipe.BoilingRecipe;
import volbot.beetlebox.recipe.JellyMixRecipe;
import volbot.beetlebox.recipe.UpgradeRecipe;

public class DataRegistry {

	public static RecipeSerializer<BoilingRecipe> BOILING_RECIPE_SERIALIZER;
	public static final RecipeType<BoilingRecipe> BOILING_RECIPE_TYPE=Registry.register(Registries.RECIPE_TYPE,new Identifier("beetlebox","boiling_recipe"),new RecipeType<BoilingRecipe>(){@Override public String toString(){return"boiling_recipe";}});

	public static final RecipeType<JellyMixRecipe> JELLY_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE,
			new Identifier("beetlebox", "jelly_recipe"), new RecipeType<JellyMixRecipe>() {
				@Override
				public String toString() {
					return "jelly_recipe";
				}
			});
	
	public static final RecipeType<UpgradeRecipe> UPGRADE_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE,
			new Identifier("beetlebox", "upgrade_recipe"), new RecipeType<UpgradeRecipe>() {
				@Override
				public String toString() {
					return "upgrade_recipe";
				}
			});
	
	public static void register() {

		BOILING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER,
				new Identifier("beetlebox", "boiling_recipe"), new CookingRecipeSerializer<>(BoilingRecipe::new, 50));
		Registry.register(Registries.RECIPE_SERIALIZER, JellyMixRecipe.Serializer.ID,
				JellyMixRecipe.Serializer.INSTANCE);
		Registry.register(Registries.RECIPE_SERIALIZER, UpgradeRecipe.Serializer.ID,
				UpgradeRecipe.Serializer.INSTANCE);

	}
}
