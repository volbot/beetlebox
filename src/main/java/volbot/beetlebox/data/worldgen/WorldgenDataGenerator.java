package volbot.beetlebox.data.worldgen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class WorldgenDataGenerator extends FabricDynamicRegistryProvider {

	public WorldgenDataGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public String getName() {
		return "beetlebox";
	}

	@Override
	protected void configure(WrapperLookup registries, Entries entries) {
		entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
		entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
	}
	
	public static void generateModWorldGen() {
		BeetleTreeGenerator.generateTrees();
	}

}
