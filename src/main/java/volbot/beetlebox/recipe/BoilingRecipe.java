package volbot.beetlebox.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;
import volbot.beetlebox.registry.BeetleRegistry;

public class BoilingRecipe extends AbstractCookingRecipe {

	public BoilingRecipe(Identifier id, String group, CookingRecipeCategory category,
			Ingredient input, ItemStack output, float experience, int cookTime) {
		super(BeetleRegistry.BOILING_RECIPE_TYPE, id, group, category, input, output, experience, cookTime);
	}

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BeetleRegistry.BOILER);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BeetleRegistry.BOILING_RECIPE_SERIALIZER;
    }

}
