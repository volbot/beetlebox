package volbot.beetlebox.registry;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class BeetleFlammableBlockRegistry {
	public static void registerFlammableBlocks() {
		FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

		registry.add(BlockRegistry.ASH_LOG, 5, 20);
		registry.add(BlockRegistry.ASH_LOG_STRIPPED, 5, 20);
		registry.add(BlockRegistry.ASH_WOOD, 5, 20);
		registry.add(BlockRegistry.ASH_WOOD_STRIPPED, 5, 20);

		registry.add(BlockRegistry.ASH_LEAVES, 5, 20);
		registry.add(BlockRegistry.ASH_PLANKS, 5, 20);
		
		registry.add(BlockRegistry.ASH_DOOR, 5, 20);
		registry.add(BlockRegistry.ASH_BUTTON, 5, 20);
		registry.add(BlockRegistry.ASH_TRAPDOOR, 5, 20);
		registry.add(BlockRegistry.ASH_PLATE, 5, 20);
		registry.add(BlockRegistry.ASH_STAIRS, 5, 20);
		registry.add(BlockRegistry.ASH_SLAB, 5, 20);
		registry.add(BlockRegistry.ASH_FENCE, 5, 20);
		registry.add(BlockRegistry.ASH_FENCE_GATE, 5, 20);
	}
}
