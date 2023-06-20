package volbot.beetlebox.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import volbot.beetlebox.registry.ItemRegistry;

public class UpgradeUsageRecipe extends ShapelessRecipe {
    final ItemStack output;
    final DefaultedList<Ingredient> input;

	public UpgradeUsageRecipe(Identifier id, String group, CraftingRecipeCategory category, ItemStack output,
			DefaultedList<Ingredient> input) {
		super(id, group, category, addNbt(input, output), input);
		this.output = addNbt(input, output);
		this.input = input;
	}
	
	public static ItemStack addNbt(DefaultedList<Ingredient> input, ItemStack output) {
		for(Ingredient i : input) {

			if (i.test(ItemRegistry.UPGRADE_H_ATTACK.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_helmet_attack", true);
			}
			if (i.test(ItemRegistry.UPGRADE_L_CLIMB.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_legs_wallclimb", true);
			}
			if (i.test(ItemRegistry.UPGRADE_B_FALLDAM.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_boots_falldamage", true);
			}
			if (i.test(ItemRegistry.UPGRADE_C_ELYTRA.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_chest_elytra", true);
			}
			
		}
		return output;
	}

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
    
    public static class Serializer
    implements RecipeSerializer<UpgradeUsageRecipe> {
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();

        public static final Identifier ID = new Identifier("beetlebox","upgrade_usage");

    	@SuppressWarnings("deprecation")
		@Override
        public UpgradeUsageRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            CraftingRecipeCategory craftingRecipeCategory = CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", null), CraftingRecipeCategory.MISC);
            DefaultedList<Ingredient> defaultedList = Serializer.getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }
            if (defaultedList.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new UpgradeUsageRecipe(identifier, string, craftingRecipeCategory, itemStack, defaultedList);
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }
    	
        @Override
        public UpgradeUsageRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            CraftingRecipeCategory craftingRecipeCategory = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            for (int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new UpgradeUsageRecipe(identifier, string, craftingRecipeCategory, itemStack, defaultedList);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, UpgradeUsageRecipe jellyMixRecipe) {
            packetByteBuf.writeString(jellyMixRecipe.getGroup());
            packetByteBuf.writeEnumConstant(jellyMixRecipe.getCategory());
            packetByteBuf.writeVarInt(jellyMixRecipe.input.size());
            for (Ingredient ingredient : jellyMixRecipe.input) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(jellyMixRecipe.output);
        }
    }
	

}
