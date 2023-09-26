package volbot.beetlebox.registry;

import java.util.List;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureEntry;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class ConfiguredFeatureRegistry {

	public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ASH_TREE = ConfiguredFeatures
			.register("ash_tree", Feature.TREE,
					new TreeFeatureConfig.Builder(BlockStateProvider.of(BlockRegistry.ASH_LOG),
							new StraightTrunkPlacer(5, 6, 3), BlockStateProvider.of(BlockRegistry.ASH_LEAVES),
							new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
							new TwoLayersFeatureSize(1, 0, 2)).build());
	
    public static final RegistryEntry<PlacedFeature> ASH_CHECKED = PlacedFeatures.register("ash_checked",
            ASH_TREE, List.of(PlacedFeatures.wouldSurvive(BlockRegistry.ASH_SAPLING)));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> ASH_SPAWN =
            ConfiguredFeatures.register("ash_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(ASH_CHECKED, 0.5f)),
                            ASH_CHECKED));
}
