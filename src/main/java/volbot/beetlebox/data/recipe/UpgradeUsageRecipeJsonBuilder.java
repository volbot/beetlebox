package volbot.beetlebox.data.recipe;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import volbot.beetlebox.recipe.JellyMixRecipe;
import volbot.beetlebox.recipe.UpgradeUsageRecipe;

public class UpgradeUsageRecipeJsonBuilder 
extends ShapelessRecipeJsonBuilder{

    private final Advancement.Builder advancementBuilder = Advancement.Builder.create();
    private final RecipeCategory category;
    private final Item output;
    private final int count;
    private final List<Ingredient> inputs = Lists.newArrayList();
    @Nullable
    private String group;
	
	public UpgradeUsageRecipeJsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
		super(category,output,count);
		this.output = output.asItem();
		this.count=count;
		this.category=category;
	}
	
	@Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        this.validate(recipeId);
        this.advancementBuilder.parent(ROOT).criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR);
        exporter.accept(new UpgradeUsageRecipeJsonProvider(recipeId, this.output, this.count, this.group == null ? "" : this.group, ShapelessRecipeJsonBuilder.getCraftingCategory(this.category), this.inputs, this.advancementBuilder, recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }
	
    private void validate(Identifier recipeId) {
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
    
    @Override
    public UpgradeUsageRecipeJsonBuilder criterion(String string, CriterionConditions criterionConditions) {
        this.advancementBuilder.criterion(string, criterionConditions);
        return this;
    }

    @Override
    public UpgradeUsageRecipeJsonBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }
    
    public static UpgradeUsageRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output) {
        return new UpgradeUsageRecipeJsonBuilder(category, output, 1);
    }

    public static UpgradeUsageRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
        return new UpgradeUsageRecipeJsonBuilder(category, output, count);
    }
    
    public UpgradeUsageRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public UpgradeUsageRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }
        return this;
    }

    public UpgradeUsageRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public UpgradeUsageRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    
    public static class UpgradeUsageRecipeJsonProvider
    extends RecipeJsonBuilder.CraftingRecipeJsonProvider {
    	private final Identifier recipeId;
        private final Item output;
        private final int count;
        private final String group;
        private final List<Ingredient> inputs;
        private final Advancement.Builder advancementBuilder;
        private final Identifier advancementId;

        public UpgradeUsageRecipeJsonProvider(Identifier recipeId, Item output, int outputCount, String group, CraftingRecipeCategory craftingCategory, List<Ingredient> inputs, Advancement.Builder advancementBuilder, Identifier advancementId) {
            super(craftingCategory);
            this.recipeId = recipeId;
            this.output = output;
            this.count = outputCount;
            this.group = group;
            this.inputs = inputs;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
        }

        @Override
        public void serialize(JsonObject json) {
            super.serialize(json);
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            JsonArray jsonArray = new JsonArray();
            for (Ingredient ingredient : this.inputs) {
                jsonArray.add(ingredient.toJson());
            }
            json.add("ingredients", jsonArray);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registries.ITEM.getId(this.output).toString());
            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }
            json.add("result", jsonObject);
        }

        @Override
        public Identifier getRecipeId() {
            return this.recipeId;
        }

        @Override
        @Nullable
        public JsonObject toAdvancementJson() {
            return this.advancementBuilder.toJson();
        }

        @Override
        @Nullable
        public Identifier getAdvancementId() {
            return this.advancementId;
        }

		@Override
		public RecipeSerializer<?> getSerializer() {
			return UpgradeUsageRecipe.Serializer.INSTANCE;
		}
    }


}
