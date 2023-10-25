package volbot.beetlebox.data.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import volbot.beetlebox.registry.BlockRegistry;

public class BeetleBlockLootGenerator extends FabricBlockLootTableProvider {
	public BeetleBlockLootGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate() {
		this.addDrop(BlockRegistry.ASH_LEAVES,
				leavesDrops(BlockRegistry.ASH_LEAVES, BlockRegistry.ASH_SAPLING, SAPLING_DROP_CHANCE));
		this.addDrop(BlockRegistry.ASH_PLANKS);
		this.addDrop(BlockRegistry.ASH_LOG);
		this.addDrop(BlockRegistry.ASH_LOG_STRIPPED);
		this.addDrop(BlockRegistry.ASH_WOOD);
		this.addDrop(BlockRegistry.ASH_WOOD_STRIPPED);
		this.addDrop(BlockRegistry.ASH_PLATE);
		this.addDrop(BlockRegistry.ASH_BUTTON);
		this.addDrop(BlockRegistry.ASH_TRAPDOOR);
		this.addDrop(BlockRegistry.ASH_FENCE);
		this.addDrop(BlockRegistry.ASH_FENCE_GATE);
		this.addDrop(BlockRegistry.ASH_SLAB);
		this.addDrop(BlockRegistry.ASH_STAIRS);
		this.addDrop(BlockRegistry.ASH_SAPLING);
		this.addDrop(BlockRegistry.BOILER);
		this.addDrop(BlockRegistry.TANK);
		this.addDrop(BlockRegistry.LEG_TANK);
		this.addDrop(BlockRegistry.EMIGRATOR);
		this.addDrop(BlockRegistry.IMMIGRATOR);
		this.addDrop(BlockRegistry.INCUBATOR);
	}

}
