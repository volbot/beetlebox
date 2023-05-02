package volbot.beetlebox.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import volbot.beetlebox.registry.BeetleRegistry;

public class JellyMixRecipe extends ShapelessRecipe {

	public JellyMixRecipe(Identifier id, String group, CraftingRecipeCategory category, ItemStack output,
			DefaultedList<Ingredient> input) {
		super(id, group, category, addNbt(input, output), input);
	}
	
	public static ItemStack addNbt(DefaultedList<Ingredient> input, ItemStack output) {
		return output;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BeetleRegistry.JELLY_RECIPE_SERIALIZER;
	}

}
