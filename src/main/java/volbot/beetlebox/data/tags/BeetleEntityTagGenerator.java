package volbot.beetlebox.data.tags;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.EntityTypeTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.registry.BeetleRegistry;

public class BeetleEntityTagGenerator extends EntityTypeTagProvider {

	public BeetleEntityTagGenerator(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}
	
	public static final TagKey<EntityType<?>> BEETLES = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("beetlebox", "beetles"));

	@Override
	protected void configure(WrapperLookup arg) {
		FabricTagBuilder tag_builder;
		
		tag_builder = getOrCreateTagBuilder(BEETLES);
		for(EntityType<? extends BeetleEntity> beetle : BeetleRegistry.beetles) {
			tag_builder.add(beetle);
		}
	}

}
