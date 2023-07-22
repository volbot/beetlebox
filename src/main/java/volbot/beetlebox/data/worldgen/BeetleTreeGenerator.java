package volbot.beetlebox.data.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import volbot.beetlebox.registry.PlacedFeatureRegistry;

public class BeetleTreeGenerator {
	public static void generateTrees() {
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(BiomeKeys.FOREST),
				GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeatureRegistry.ASH_PLACED_KEY);
	}
}
