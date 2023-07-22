package volbot.beetlebox.data.tags;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import volbot.beetlebox.registry.BlockRegistry;

public class BeetleBlockTagGenerator extends FabricTagProvider<Block> {

	public BeetleBlockTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.BLOCK, registriesFuture);
	}

	@Override
	protected void configure(WrapperLookup arg) {
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(BlockRegistry.BOILER).add(BlockRegistry.TANK)
				.add(BlockRegistry.LEG_TANK);

		getOrCreateTagBuilder(BlockTags.LOGS).add(BlockRegistry.ASH_LOG).add(BlockRegistry.ASH_LOG_STRIPPED)
				.add(BlockRegistry.ASH_WOOD).add(BlockRegistry.ASH_WOOD_STRIPPED);

	}

}
