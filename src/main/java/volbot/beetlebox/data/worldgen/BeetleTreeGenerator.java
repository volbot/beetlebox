package volbot.beetlebox.data.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.world.gen.GenerationStep;
import volbot.beetlebox.registry.PlacedFeatureRegistry;

public class BeetleTreeGenerator {
	public static void generateTrees() {
		BiomeModifications.addFeature(
				BiomeSelectors.tag(ConventionalBiomeTags.FLORAL).or(BiomeSelectors.tag(ConventionalBiomeTags.TREE_DECIDUOUS)).or(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE)),
				GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeatureRegistry.ASH_PLACED_KEY);
	}
}
