package volbot.beetlebox.worldgen;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import volbot.beetlebox.registry.ConfiguredFeatureRegistry;

public class AshSaplingGenerator extends SaplingGenerator {
	 @Nullable
	    @Override
	    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
	        return ConfiguredFeatureRegistry.ASH_KEY;
	    }
}
