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
	}
}
