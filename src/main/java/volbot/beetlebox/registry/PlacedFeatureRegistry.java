package volbot.beetlebox.registry;

import java.util.List;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

public class PlacedFeatureRegistry {

    public static final RegistryKey<PlacedFeature> ASH_PLACED_KEY = registerKey("ash_placed");
	
	public static void bootstrap(Registerable<PlacedFeature> context) {
		
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
		
		register(context, ASH_PLACED_KEY,
				configuredFeatureRegistryEntryLookup.getOrThrow(ConfiguredFeatureRegistry.ASH_KEY),
				VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1), BlockRegistry.ASH_SAPLING));
	}

	public static RegistryKey<PlacedFeature> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("beetlebox", name));
	}

	private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
			RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
	
	
}
