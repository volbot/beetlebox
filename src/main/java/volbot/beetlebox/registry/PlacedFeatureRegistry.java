package volbot.beetlebox.registry;

import java.util.List;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class PlacedFeatureRegistry {

    public static final RegistryEntry<PlacedFeature> ASH_PLACED = PlacedFeatures.register("ash_placed",
            ConfiguredFeatureRegistry.ASH_SPAWN,
            VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.1f, 2)));
    
}