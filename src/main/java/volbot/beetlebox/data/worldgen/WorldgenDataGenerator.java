package volbot.beetlebox.data.worldgen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.Entries;

public class WorldgenDataGenerator extends FabricDynamicRegistryProvider {

	public WorldgenDataGenerator(FabricDataGenerator output) {
		super(output);
	}

	@Override
	protected void configure(WrapperLookup registries, Entries entries) {
		entries.addAll(registries.getWrapperOrThrow(Registry.CONFIGURED_FEATURE_KEY));
		entries.addAll(registries.getWrapperOrThrow(Registry.PLACED_FEATURE_KEY));
	}
	
	public static void generateModWorldGen() {
		BeetleTreeGenerator.generateTrees();
	}

}
