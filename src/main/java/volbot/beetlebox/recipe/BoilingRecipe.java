package volbot.beetlebox.recipe;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.DataRegistry;

public class BoilingRecipe extends AbstractCookingRecipe {

	public final FluidVariant fluid_in;
	public final long fluid_droplets;
	
	public BoilingRecipe(Identifier id, String group,
			Ingredient input, ItemStack output, float exp, int cookTime) {
		super(DataRegistry.BOILING_RECIPE_TYPE, id, group, input, output, 0f, cookTime);
		this.fluid_in = FluidVariant.of(Fluids.WATER);
		this.fluid_droplets = 100;
	}

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BlockRegistry.BOILER);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DataRegistry.BOILING_RECIPE_SERIALIZER;
    }

}
