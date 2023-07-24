package volbot.beetlebox.registry;

import net.minecraft.block.Blocks;
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
	public static final RegistryKey<ConfiguredFeature<?, ?>> ASH_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
			new Identifier("beetlebox", "ash_tree"));

	public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context) {
		register(context, ASH_KEY, Feature.TREE,
				new TreeFeatureConfig.Builder(BlockStateProvider.of(BlockRegistry.ASH_LOG),
						new StraightTrunkPlacer(5, 6, 3), BlockStateProvider.of(BlockRegistry.ASH_LEAVES),
						new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
						new TwoLayersFeatureSize(1, 0, 2)).build());
	}

	private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
			Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature,
			FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}
