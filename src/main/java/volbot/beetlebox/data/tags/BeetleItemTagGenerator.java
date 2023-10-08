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
		getOrCreateTagBuilder(SLIMEBALLS).add(ItemRegistry.GELATIN_GLUE);

		getOrCreateTagBuilder(ItemTags.LOGS).add(BlockRegistry.ASH_LOG.asItem())
				.add(BlockRegistry.ASH_LOG_STRIPPED.asItem()).add(BlockRegistry.ASH_WOOD.asItem())
				.add(BlockRegistry.ASH_WOOD_STRIPPED.asItem());
		getOrCreateTagBuilder(ItemTags.LEAVES).add(BlockRegistry.ASH_LEAVES.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(BlockRegistry.ASH_SLAB.asItem());

		getOrCreateTagBuilder(ItemTags.PLANKS).add(BlockRegistry.ASH_PLANKS.asItem());
		getOrCreateTagBuilder(ASH_LOGS).add(BlockRegistry.ASH_LOG.asItem()).add(BlockRegistry.ASH_LOG_STRIPPED.asItem())
				.add(BlockRegistry.ASH_WOOD.asItem()).add(BlockRegistry.ASH_WOOD_STRIPPED.asItem());
		
		getOrCreateTagBuilder(ItemTags.FENCES).add(BlockRegistry.ASH_FENCE.asItem());
		getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(BlockRegistry.ASH_FENCE_GATE.asItem());
		getOrCreateTagBuilder(ItemTags.STAIRS).add(BlockRegistry.ASH_STAIRS.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(BlockRegistry.ASH_STAIRS.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockRegistry.ASH_PLATE.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(BlockRegistry.ASH_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.BUTTONS).add(BlockRegistry.ASH_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.DOORS).add(BlockRegistry.ASH_DOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(BlockRegistry.ASH_TRAPDOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(BlockRegistry.ASH_DOOR.asItem());
		
		getOrCreateTagBuilder(ItemTags.SAPLINGS).add(BlockRegistry.ASH_SAPLING.asItem());
	}
}
