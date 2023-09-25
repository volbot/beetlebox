package volbot.beetlebox.data.tags;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleItemTagGenerator extends ItemTagProvider {

	public BeetleItemTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	public static final TagKey<Item> SLIMEBALLS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "slime_balls"));
	public static final TagKey<Item> ASH_LOGS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "ash_logs"));

	@Override
	protected void configure(WrapperLookup arg) {
		getOrCreateTagBuilder(SLIMEBALLS)
        .add(ItemRegistry.GELATIN_GLUE);

		getOrCreateTagBuilder(ItemTags.LOGS).add(BlockRegistry.ASH_LOG.asItem()).add(BlockRegistry.ASH_LOG_STRIPPED.asItem())
				.add(BlockRegistry.ASH_WOOD.asItem()).add(BlockRegistry.ASH_WOOD_STRIPPED.asItem());		
		getOrCreateTagBuilder(ASH_LOGS).add(BlockRegistry.ASH_LOG.asItem()).add(BlockRegistry.ASH_LOG_STRIPPED.asItem())
				.add(BlockRegistry.ASH_WOOD.asItem()).add(BlockRegistry.ASH_WOOD_STRIPPED.asItem());

		
		getOrCreateTagBuilder(ItemTags.PLANKS).add(BlockRegistry.ASH_PLANKS.asItem());
	}
}
