package volbot.beetlebox.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.tag.BlockTags;
import volbot.beetlebox.registry.BlockRegistry;

public class BeetleBlockTagGenerator extends FabricTagProvider<Block> {

	public BeetleBlockTagGenerator(FabricDataGenerator output) {
		super(output, Registry.BLOCK);
	}

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(BlockRegistry.BOILER).add(BlockRegistry.TANK)
				.add(BlockRegistry.LEG_TANK);

		getOrCreateTagBuilder(BlockTags.LOGS).add(BlockRegistry.ASH_LOG).add(BlockRegistry.ASH_LOG_STRIPPED)
				.add(BlockRegistry.ASH_WOOD).add(BlockRegistry.ASH_WOOD_STRIPPED);

		getOrCreateTagBuilder(BlockTags.LEAVES).add(BlockRegistry.ASH_LEAVES);

		getOrCreateTagBuilder(BlockTags.PLANKS).add(BlockRegistry.ASH_PLANKS);

		getOrCreateTagBuilder(BlockTags.FENCES).add(BlockRegistry.ASH_FENCE);
		getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(BlockRegistry.ASH_FENCE);
		getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(BlockRegistry.ASH_FENCE_GATE);

		getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(BlockRegistry.ASH_DOOR);
		getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(BlockRegistry.ASH_STAIRS);

		getOrCreateTagBuilder(BlockTags.BUTTONS).add(BlockRegistry.ASH_BUTTON);
		getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(BlockRegistry.ASH_BUTTON);

		getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(BlockRegistry.ASH_TRAPDOOR);
		getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(BlockRegistry.ASH_TRAPDOOR);

		getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).add(BlockRegistry.ASH_PLATE);
		getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockRegistry.ASH_PLATE);

	}

}
