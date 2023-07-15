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

	}

}
