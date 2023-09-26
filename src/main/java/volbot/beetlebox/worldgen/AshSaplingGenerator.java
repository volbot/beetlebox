package volbot.beetlebox.worldgen;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import volbot.beetlebox.registry.ConfiguredFeatureRegistry;

public class AshSaplingGenerator extends SaplingGenerator {
	 @Nullable
	    @Override
	    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
	        return ConfiguredFeatureRegistry.ASH_TREE;
	    }
}
