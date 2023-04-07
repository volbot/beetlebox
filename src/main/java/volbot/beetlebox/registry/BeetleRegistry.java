package volbot.beetlebox.registry;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.beetle.AtlasEntity;
import volbot.beetlebox.item.equipment.AtlasElytraItem;
import volbot.beetlebox.item.equipment.HercElytraItem;
import volbot.beetlebox.item.equipment.HercHelmetItem;
import volbot.beetlebox.item.equipment.JRBElytraItem;
import volbot.beetlebox.item.equipment.JRBHelmetItem;
import volbot.beetlebox.item.equipment.TitanElytraItem;
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
	public static final EntityType<AtlasEntity> ATLAS = FabricEntityTypeBuilder.createMob()
        	.entityFactory(AtlasEntity::new)
        	.spawnGroup(SpawnGroup.CREATURE)
        	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
        	.build();

	public static final Item JRB_SHELL = new Item(new FabricItemSettings());
	public static final Item HERC_SHELL = new Item(new FabricItemSettings());
	public static final Item TITAN_SHELL = new Item(new FabricItemSettings());
	public static final Item ATLAS_SHELL = new Item(new FabricItemSettings());

	public static final Item HERC_ELYTRA = new HercElytraItem(new FabricItemSettings().rarity(Rarity.UNCOMMON));
	public static final Item TITAN_ELYTRA = new TitanElytraItem(new FabricItemSettings().rarity(Rarity.UNCOMMON));
	public static final Item JRB_ELYTRA = new JRBElytraItem(new FabricItemSettings().rarity(Rarity.UNCOMMON));
	public static final Item ATLAS_ELYTRA = new AtlasElytraItem(new FabricItemSettings().rarity(Rarity.UNCOMMON));
	
	public static final Item JRB_HELMET = new JRBHelmetItem(new FabricItemSettings());
	public static final Item HERC_HELMET = new HercHelmetItem(new FabricItemSettings());
    
	public static final Item JRB_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.JRB, 0x110b0b, 0x180f0f, new FabricItemSettings());
    public static final Item HERC_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.HERC, 0xa99859, 0x150f10, new FabricItemSettings());
    public static final Item TITAN_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.TITAN, 0x0e0f10, 0x363840, new FabricItemSettings());
    public static final Item ATLAS_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.ATLAS, 0x080904, 0x22270d, new FabricItemSettings());

	
	public static void register() {
		
		//ENTITIES
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","kabutomushi"), JRB);
		FabricDefaultAttributeRegistry.register(JRB, JRBEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","hercules"), HERC);
		FabricDefaultAttributeRegistry.register(HERC, HercEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","titanus"), TITAN);
		FabricDefaultAttributeRegistry.register(TITAN, TitanEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","atlas"), ATLAS);
		FabricDefaultAttributeRegistry.register(ATLAS, AtlasEntity.createBeetleAttributes());
		
		Predicate<BiomeSelectionContext> forests = BiomeSelectors.tag(BiomeTags.IS_FOREST);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, JRB, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITAN, 16, 1, 2);

		Predicate<BiomeSelectionContext> jungles = BiomeSelectors.tag(BiomeTags.IS_JUNGLE);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, HERC, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, ATLAS, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITAN, 16, 1, 2);
		
		//ITEMS
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_spawn_egg"), JRB_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_spawn_egg"), HERC_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_spawn_egg"), TITAN_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_spawn_egg"), ATLAS_SPAWN_EGG);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_elytra"), HERC_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_elytra"), TITAN_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_elytra"), JRB_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_elytra"), ATLAS_ELYTRA);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_helmet"), JRB_HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_helmet"), HERC_HELMET);

		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_elytron"), JRB_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_elytron"), HERC_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_elytron"), TITAN_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_elytron"), ATLAS_SHELL);
		
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
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
        	content.addAfter(TITAN_SPAWN_EGG, ATLAS_SPAWN_EGG);
        });
		
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
			content.addAfter(Items.SCUTE, JRB_SHELL);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
        	content.addAfter(JRB_SHELL, HERC_SHELL);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
        	content.addAfter(HERC_SHELL, TITAN_SHELL);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
        	content.addAfter(TITAN_SHELL, ATLAS_SHELL);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
        	content.addAfter(Items.ELYTRA, JRB_ELYTRA);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
        	content.addAfter(JRB_ELYTRA, HERC_ELYTRA);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
        	content.addAfter(HERC_ELYTRA, TITAN_ELYTRA);
        });
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
        	content.addAfter(TITAN_ELYTRA, ATLAS_ELYTRA);
        });
	}
}
