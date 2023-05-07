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
import volbot.beetlebox.registry.BeetleRegistry;

public class JellyMixRecipe extends ShapelessRecipe {
    final ItemStack output;
    final DefaultedList<Ingredient> input;

	public JellyMixRecipe(Identifier id, String group, CraftingRecipeCategory category, ItemStack output,
			DefaultedList<Ingredient> input) {
		super(id, group, category, addNbt(input, output), input);
		this.output = addNbt(input, output);
		this.input = input;
	}
	
	public static ItemStack addNbt(DefaultedList<Ingredient> input, ItemStack output) {
		for(Ingredient i : input) {
			if(i.test(BeetleRegistry.GELATIN.getDefaultStack())) {
				continue;
			}
			if(i.test(BeetleRegistry.APPLE_SYRUP.getDefaultStack())) {
				output.getOrCreateNbt().putString("FruitType", "apple");
			}
			else if(i.test(BeetleRegistry.MELON_SYRUP.getDefaultStack())) {
				output.getOrCreateNbt().putString("FruitType", "melon");
			}
			else if(i.test(BeetleRegistry.BERRY_SYRUP.getDefaultStack())) {
				output.getOrCreateNbt().putString("FruitType", "berry");
			}
			else if(i.test(BeetleRegistry.SUGAR_SYRUP.getDefaultStack())) {
				output.getOrCreateNbt().putString("FruitType", "sugar");
			}
			else if(i.test(BeetleRegistry.CACTUS_SYRUP.getDefaultStack())) {
				output.getOrCreateNbt().putString("FruitType", "cactus");
			}
			
			
			if(i.test(Items.TALL_GRASS.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("Increase", true);
			}
			else if(i.test(Items.GRASS.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("Increase", false);
			}
			
			if(i.test(Items.IRON_INGOT.getDefaultStack())) {
				output.getOrCreateNbt().putInt("Magnitude", 1);
			}
			else if(i.test(Items.GOLD_INGOT.getDefaultStack())) {
				output.getOrCreateNbt().putInt("Magnitude", 5);
			}
			else if(i.test(Items.DIAMOND.getDefaultStack())) {
				output.getOrCreateNbt().putInt("Magnitude", 10);
			}
			else if(i.test(Items.ANCIENT_DEBRIS.getDefaultStack())) {
				output.getOrCreateNbt().putInt("Magnitude", 25);
			}
			
		}
		return output;
	}

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
    
    public static class Serializer
    implements RecipeSerializer<JellyMixRecipe> {
    	 // Define ExampleRecipeSerializer as a singleton by making its constructor private and exposing an instance.
        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();

        // This will be the "type" field in the json
        public static final Identifier ID = new Identifier("beetlebox","jelly_recipe");

       // [...]
    	@SuppressWarnings("deprecation")
		@Override
        public JellyMixRecipe read(Identifier identifier, JsonObject jsonObject) {
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
            return new JellyMixRecipe(identifier, string, craftingRecipeCategory, itemStack, defaultedList);
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
        public JellyMixRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            CraftingRecipeCategory craftingRecipeCategory = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            for (int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new JellyMixRecipe(identifier, string, craftingRecipeCategory, itemStack, defaultedList);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, JellyMixRecipe jellyMixRecipe) {
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
