package volbot.beetlebox.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryBuilder.BootstrapFunction;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import volbot.beetlebox.data.lang.BeetleEnglishProvider;
import volbot.beetlebox.data.loot.BeetleBlockLootGenerator;
import volbot.beetlebox.data.loot.BeetleLootGenerator;
import volbot.beetlebox.data.models.BeetleModelGenerator;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.data.tags.BeetleItemTagGenerator;
import volbot.beetlebox.data.tags.BeetleBlockTagGenerator;
import volbot.beetlebox.data.tags.BeetleEntityTagGenerator;
import volbot.beetlebox.data.worldgen.WorldgenDataGenerator;
import volbot.beetlebox.registry.ConfiguredFeatureRegistry;
import volbot.beetlebox.registry.PlacedFeatureRegistry;

public class BeetleDataGen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(BeetleModelGenerator::new);
		pack.addProvider(BeetleItemTagGenerator::new);
		pack.addProvider(BeetleBlockTagGenerator::new);
		pack.addProvider(BeetleEntityTagGenerator::new);
		pack.addProvider(BeetleRecipeGenerator::new);
		pack.addProvider(BeetleLootGenerator::new);
		pack.addProvider(BeetleBlockLootGenerator::new);
		pack.addProvider(BeetleEnglishProvider::new);
		pack.addProvider(WorldgenDataGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ConfiguredFeatureRegistry::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, PlacedFeatureRegistry::bootstrap);
	}

}
