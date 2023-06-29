package volbot.beetlebox.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class BeetleItemTagGenerator extends ItemTagProvider {

	public BeetleItemTagGenerator(FabricDataGenerator output) {
		super(output);
	}
	
	public static final TagKey<Item> SLIMEBALLS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "slime_balls"));
	
	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(SLIMEBALLS)
        .add(ItemRegistry.GELATIN_GLUE);
	}
}
