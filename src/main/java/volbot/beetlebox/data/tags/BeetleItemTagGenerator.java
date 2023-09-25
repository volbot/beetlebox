package volbot.beetlebox.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleItemTagGenerator extends ItemTagProvider {

	public BeetleItemTagGenerator(FabricDataGenerator output) {
		super(output);
	}
	public static final TagKey<Item> SLIMEBALLS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "slime_balls"));
	public static final TagKey<Item> ASH_LOGS = TagKey.of(RegistryKeys.ITEM_KEY, new Identifier("c", "ash_logs"));
	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(SLIMEBALLS).add(ItemRegistry.GELATIN_GLUE);

		getOrCreateTagBuilder(ItemTags.LOGS).add(BlockRegistry.ASH_LOG.asItem()).add(BlockRegistry.ASH_LOG_STRIPPED.asItem())
				.add(BlockRegistry.ASH_WOOD.asItem()).add(BlockRegistry.ASH_WOOD_STRIPPED.asItem());		
		getOrCreateTagBuilder(ASH_LOGS).add(BlockRegistry.ASH_LOG.asItem()).add(BlockRegistry.ASH_LOG_STRIPPED.asItem())
				.add(BlockRegistry.ASH_WOOD.asItem()).add(BlockRegistry.ASH_WOOD_STRIPPED.asItem());

		
		getOrCreateTagBuilder(ItemTags.PLANKS).add(BlockRegistry.ASH_PLANKS.asItem());
	}
}
