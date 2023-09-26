package volbot.beetlebox.data.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import volbot.beetlebox.registry.BlockRegistry;

public class BeetleBlockLootGenerator extends FabricBlockLootTableProvider {

	public BeetleBlockLootGenerator(FabricDataGenerator output) {
		super(output);
	}
	
    @Override
    public void generateBlockLootTables() {
    	this.addDrop(BlockRegistry.ASH_LEAVES, leavesDrop(BlockRegistry.ASH_LEAVES, BlockRegistry.ASH_SAPLING, 0.05f));
    	this.addDrop(BlockRegistry.ASH_PLANKS);
    	this.addDrop(BlockRegistry.ASH_LOG);
    	this.addDrop(BlockRegistry.ASH_LOG_STRIPPED);
    	this.addDrop(BlockRegistry.ASH_WOOD);
    	this.addDrop(BlockRegistry.ASH_WOOD_STRIPPED);
    	this.addDrop(BlockRegistry.ASH_PLATE);
    	this.addDrop(BlockRegistry.ASH_BUTTON);
    	this.addDrop(BlockRegistry.ASH_DOOR);
    	this.addDrop(BlockRegistry.ASH_TRAPDOOR);
    	this.addDrop(BlockRegistry.ASH_FENCE);
    	this.addDrop(BlockRegistry.ASH_FENCE_GATE);
    	this.addDrop(BlockRegistry.ASH_SLAB);
    	this.addDrop(BlockRegistry.ASH_STAIRS);
    	this.addDrop(BlockRegistry.ASH_SAPLING);
    }

}