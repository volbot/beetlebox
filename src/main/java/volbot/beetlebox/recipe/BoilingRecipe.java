package volbot.beetlebox.recipe;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;
import volbot.beetlebox.registry.BeetleRegistry;

public class BoilingRecipe extends AbstractCookingRecipe {

	public final FluidVariant fluid_in;
	public final long fluid_droplets;
	
	public BoilingRecipe(Identifier id, String group, CookingRecipeCategory category,
			Ingredient input, ItemStack output, float exp, int cookTime) {
		super(BeetleRegistry.BOILING_RECIPE_TYPE, id, group, category, input, output, 0f, cookTime);
		this.fluid_in = FluidVariant.of(Fluids.WATER);
		this.fluid_droplets = 100;
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