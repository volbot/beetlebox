package volbot.beetlebox.registry;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.beetle.JRBEntity;

public class BeetleRegistry {
	//ENTITY TYPES
	public static final EntityType<JRBEntity> JRB = FabricEntityTypeBuilder.createMob()
            	.entityFactory(JRBEntity::new)
            	.spawnGroup(SpawnGroup.CREATURE)
            	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
            	.build();
	public static final EntityType<HercEntity> HERC = FabricEntityTypeBuilder.createMob()
            	.entityFactory(HercEntity::new)
            	.spawnGroup(SpawnGroup.CREATURE)
            	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
            	.build();
	public static final EntityType<TitanEntity> TITAN = FabricEntityTypeBuilder.createMob()
            	.entityFactory(TitanEntity::new)
            	.spawnGroup(SpawnGroup.CREATURE)
            	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
            	.build();

	public static final Item JRB_SHELL = new Item(new FabricItemSettings());
	public static final Item HERC_SHELL = new Item(new FabricItemSettings());
	public static final Item TITAN_SHELL = new Item(new FabricItemSettings());
    
	public static final Item JRB_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.JRB, 0xc4c4c4, 0xadadad, new FabricItemSettings());
    public static final Item HERC_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.HERC, 0xc4c4c4, 0xadadad, new FabricItemSettings());
    public static final Item TITAN_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.TITAN, 0xc4c4c4, 0xadadad, new FabricItemSettings());

	
	public static void register() {
		
		//ENTITIES
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","kabutomushi"), JRB);
		FabricDefaultAttributeRegistry.register(JRB, JRBEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","hercules"), HERC);
		FabricDefaultAttributeRegistry.register(HERC, HercEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","kuwagata"), TITAN);
		FabricDefaultAttributeRegistry.register(TITAN, HercEntity.createBeetleAttributes());
		
		Predicate<BiomeSelectionContext> forests = BiomeSelectors.tag(BiomeTags.IS_FOREST);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, JRB, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITAN, 16, 1, 20);

		Predicate<BiomeSelectionContext> jungles = BiomeSelectors.tag(BiomeTags.IS_JUNGLE);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, HERC, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITAN, 16, 1, 20);
		
		//ITEMS
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_spawn_egg"), JRB_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_spawn_egg"), HERC_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_spawn_egg"), TITAN_SPAWN_EGG);

		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_elytron"), JRB_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_elytron"), HERC_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_elytron"), TITAN_SHELL);
		
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
		    if(source.isBuiltin()) {
		    	if (JRB.getLootTableId().equals(id)) {
		    		LootPool.Builder poolBuilder = LootPool.builder()
		    				.with(ItemEntry.builder(JRB_SHELL));
		            tableBuilder.pool(poolBuilder);
		    	}
		    	else if (HERC.getLootTableId().equals(id)) {
		    		LootPool.Builder poolBuilder = LootPool.builder()
		    				.with(ItemEntry.builder(HERC_SHELL));
		            tableBuilder.pool(poolBuilder);
		    	}
		    	else if (TITAN.getLootTableId().equals(id)) {
		    		LootPool.Builder poolBuilder = LootPool.builder()
		    				.with(ItemEntry.builder(TITAN_SHELL));
		            tableBuilder.pool(poolBuilder);
		    	}
		    }
		});
		
		//ITEM GROUPS
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
			content.addAfter(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, JRB_SPAWN_EGG);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
        	content.addAfter(JRB_SPAWN_EGG, HERC_SPAWN_EGG);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
        	content.addAfter(HERC_SPAWN_EGG, TITAN_SPAWN_EGG);
        });
	}
}
