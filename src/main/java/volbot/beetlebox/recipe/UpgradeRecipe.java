package volbot.beetlebox.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import volbot.beetlebox.registry.ItemRegistry;

public class UpgradeRecipe extends ShapedRecipe {

	public UpgradeRecipe(Identifier id, String group, int width, int height,
			DefaultedList<Ingredient> input, ItemStack output) {
		super(id, group, width, height, input, addNbt(input, output));
		this.output = addNbt(input, output);
		this.input = input;
	}

	final ItemStack output;
	final DefaultedList<Ingredient> input;

	public static ItemStack addNbt(DefaultedList<Ingredient> input, ItemStack output) {
		for (Ingredient i : input) {
			if (i.test(ItemRegistry.GELATIN.getDefaultStack()) ||
					i.test(Items.OBSIDIAN.getDefaultStack()) ||
					i.test(Items.NETHER_WART.getDefaultStack())) {
				continue;
			}
			if (i.test(Items.DIAMOND_SWORD.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_helmet_attack", true);
			}
			if (i.test(Items.FERMENTED_SPIDER_EYE.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_legs_wallclimb", true);
			}
			if (i.test(Items.COBWEB.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_boots_falldamage", true);
			}
			if (i.test(Items.ELYTRA.getDefaultStack())) {
				output.getOrCreateNbt().putBoolean("beetle_chest_elytra", true);
			}
		}
		return output;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	public static class Serializer implements RecipeSerializer<UpgradeRecipe> {

		public static final Serializer INSTANCE = new Serializer();

		public static final Identifier ID = new Identifier("beetlebox", "upgrade_recipe");

		@Override
		public UpgradeRecipe read(Identifier identifier, JsonObject jsonObject) {
			ShapedRecipe r = Serializer.SHAPED.read(identifier, jsonObject);
			return new UpgradeRecipe(identifier, r.getGroup(), r.getWidth(), r.getHeight(),
					r.getIngredients(), ItemRegistry.UPGRADE_DORMANT.getDefaultStack());
		}

		@Override
		public UpgradeRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
			ShapedRecipe r = Serializer.SHAPED.read(identifier, packetByteBuf);
			return new UpgradeRecipe(identifier, r.getGroup(), r.getWidth(), r.getHeight(),
					r.getIngredients(), ItemRegistry.UPGRADE_DORMANT.getDefaultStack());
		}

		@Override
		public void write(PacketByteBuf packetByteBuf, UpgradeRecipe shapedRecipe) {
			Serializer.SHAPED.write(packetByteBuf, shapedRecipe);
		}
	}
}
