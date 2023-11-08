package volbot.beetlebox.registry;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class ConfiguredFeatureRegistry {
	public static final RegistryKey<ConfiguredFeature<?,?>> ASH_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier("beetlebox", "ash_tree"));

	@SuppressWarnings("unchecked")
	public static <FC extends FeatureConfig, F extends Feature<FC>> void bootstrap(
			Registerable<ConfiguredFeature<?, ?>> context) {
		context.register(ASH_KEY, (ConfiguredFeature<FC, F>) createConfiguredFeature());
	}

	private static ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>> createConfiguredFeature() {
		return new ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>>(Feature.TREE,
				new TreeFeatureConfig.Builder(BlockStateProvider.of(BlockRegistry.ASH_LOG),
						new StraightTrunkPlacer(5, 6, 3), BlockStateProvider.of(BlockRegistry.ASH_LEAVES),
						new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
						new TwoLayersFeatureSize(1, 0, 2)).build());
	}
}
