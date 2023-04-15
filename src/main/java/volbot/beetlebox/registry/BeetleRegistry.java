package volbot.beetlebox.registry;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.BlockItem;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.entity.beetle.AtlasEntity;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.beetle.ElephantEntity;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.BeetleElytraItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.NetItem;
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
	public static final EntityType<ElephantEntity> ELEPHANT = FabricEntityTypeBuilder.createMob()
        	.entityFactory(ElephantEntity::new)
        	.spawnGroup(SpawnGroup.CREATURE)
        	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
        	.build();	

	public static final Item JRB_SHELL = new Item(new FabricItemSettings());
	public static final Item HERC_SHELL = new Item(new FabricItemSettings());
	public static final Item TITAN_SHELL = new Item(new FabricItemSettings());
	public static final Item ATLAS_SHELL = new Item(new FabricItemSettings());
	public static final Item ELEPHANT_SHELL = new Item(new FabricItemSettings());

	public static final Item JRB_ELYTRA = new BeetleElytraItem(new ChitinMaterial.JRBChitinMaterial(), new FabricItemSettings());
	public static final Item HERC_ELYTRA = new BeetleElytraItem(new ChitinMaterial.HercChitinMaterial(), new FabricItemSettings());
	public static final Item TITAN_ELYTRA = new BeetleElytraItem(new ChitinMaterial.TitanChitinMaterial(), new FabricItemSettings());
	public static final Item ATLAS_ELYTRA = new BeetleElytraItem(new ChitinMaterial.AtlasChitinMaterial(), new FabricItemSettings());
	public static final Item ELEPHANT_ELYTRA = new BeetleElytraItem(new ChitinMaterial.ElephantChitinMaterial(), new FabricItemSettings());
	
	public static final Item JRB_HELMET = new BeetleArmorItem(new ChitinMaterial.JRBChitinMaterial(), Type.HELMET, new FabricItemSettings());
	public static final Item HERC_HELMET = new BeetleArmorItem(new ChitinMaterial.HercChitinMaterial(), Type.HELMET, new FabricItemSettings());
	public static final Item TITAN_HELMET = new BeetleArmorItem(new ChitinMaterial.TitanChitinMaterial(), Type.HELMET, new FabricItemSettings());
	public static final Item ATLAS_HELMET = new BeetleArmorItem(new ChitinMaterial.AtlasChitinMaterial(), Type.HELMET, new FabricItemSettings());
	public static final Item ELEPHANT_HELMET = new BeetleArmorItem(new ChitinMaterial.ElephantChitinMaterial(), Type.HELMET, new FabricItemSettings());
    
	public static final Item JRB_LEGS = new BeetleArmorItem(new ChitinMaterial.JRBChitinMaterial(), Type.LEGGINGS, new FabricItemSettings());
	public static final Item HERC_LEGS = new BeetleArmorItem(new ChitinMaterial.HercChitinMaterial(), Type.LEGGINGS, new FabricItemSettings());
	public static final Item TITAN_LEGS = new BeetleArmorItem(new ChitinMaterial.TitanChitinMaterial(), Type.LEGGINGS, new FabricItemSettings());
	public static final Item ATLAS_LEGS = new BeetleArmorItem(new ChitinMaterial.AtlasChitinMaterial(), Type.LEGGINGS, new FabricItemSettings());
	public static final Item ELEPHANT_LEGS = new BeetleArmorItem(new ChitinMaterial.ElephantChitinMaterial(), Type.LEGGINGS, new FabricItemSettings());
	
	public static final Item JRB_BOOTS = new BeetleArmorItem(new ChitinMaterial.JRBChitinMaterial(), Type.BOOTS, new FabricItemSettings());
	public static final Item HERC_BOOTS = new BeetleArmorItem(new ChitinMaterial.HercChitinMaterial(), Type.BOOTS, new FabricItemSettings());
	public static final Item TITAN_BOOTS = new BeetleArmorItem(new ChitinMaterial.TitanChitinMaterial(), Type.BOOTS, new FabricItemSettings());
	public static final Item ATLAS_BOOTS = new BeetleArmorItem(new ChitinMaterial.AtlasChitinMaterial(), Type.BOOTS, new FabricItemSettings());
	public static final Item ELEPHANT_BOOTS = new BeetleArmorItem(new ChitinMaterial.ElephantChitinMaterial(), Type.BOOTS, new FabricItemSettings());
	
	public static final Item JRB_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.JRB, 0x110b0b, 0x180f0f, new FabricItemSettings());
    public static final Item HERC_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.HERC, 0xa99859, 0x150f10, new FabricItemSettings());
    public static final Item TITAN_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.TITAN, 0x0e0f10, 0x363840, new FabricItemSettings());
    public static final Item ATLAS_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.ATLAS, 0x080904, 0x22270d, new FabricItemSettings());
    public static final Item ELEPHANT_SPAWN_EGG = new SpawnEggItem(BeetleRegistry.ELEPHANT, 0x5e3924, 0x180f06, new FabricItemSettings());
    
    public static final Item NET = new NetItem(new FabricItemSettings());

    public static final Item BEETLE_JAR = new BeetleJarItem<BeetleEntity>(new FabricItemSettings(), BeetleEntity.class);
    public static final Item LEG_BEETLE_JAR = new BeetleJarItem<LivingEntity>(new FabricItemSettings().rarity(Rarity.UNCOMMON), LivingEntity.class);
    
    public static final Block TANK = new BeetleTankBlock<BeetleEntity>(FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), BeetleEntity.class);
    public static final Block LEG_TANK = new BeetleTankBlock<LivingEntity>(FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), LivingEntity.class);
    
	public static final BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY = Registry.register(
	        Registries.BLOCK_ENTITY_TYPE,
	        new Identifier("beetlebox", "tank_block_entity"),
	        FabricBlockEntityTypeBuilder.create(TankBlockEntity::new,TANK, LEG_TANK).build()
	    );
	
	private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier("beetlebox", "beetlebox"))
			.icon(() -> new ItemStack(JRB_ELYTRA))
			.build();
	
	public static void register() {
		
		//ENTITIES
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","jrb"), JRB);
		FabricDefaultAttributeRegistry.register(JRB, JRBEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","hercules"), HERC);
		FabricDefaultAttributeRegistry.register(HERC, HercEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","titanus"), TITAN);
		FabricDefaultAttributeRegistry.register(TITAN, TitanEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","atlas"), ATLAS);
		FabricDefaultAttributeRegistry.register(ATLAS, AtlasEntity.createBeetleAttributes());
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox","elephant"), ELEPHANT);
		FabricDefaultAttributeRegistry.register(ELEPHANT, ElephantEntity.createBeetleAttributes());
		
		Predicate<BiomeSelectionContext> forests = BiomeSelectors.tag(BiomeTags.IS_FOREST);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, JRB, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITAN, 16, 1, 2);

		Predicate<BiomeSelectionContext> jungles = BiomeSelectors.tag(BiomeTags.IS_JUNGLE);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, HERC, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, ATLAS, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, TITAN, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, ELEPHANT, 16, 1, 2);
		
		//ITEMS
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "beetle_jar"), BEETLE_JAR);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "leg_beetle_jar"), LEG_BEETLE_JAR);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "net"), NET);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_spawn_egg"), JRB_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_spawn_egg"), HERC_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_spawn_egg"), TITAN_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_spawn_egg"), ATLAS_SPAWN_EGG);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "elephant_spawn_egg"), ELEPHANT_SPAWN_EGG);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_elytra"), HERC_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_elytra"), TITAN_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_elytra"), JRB_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_elytra"), ATLAS_ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "elephant_elytra"), ELEPHANT_ELYTRA);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_helmet"), JRB_HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_helmet"), HERC_HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_helmet"), TITAN_HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_helmet"), ATLAS_HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "elephant_helmet"), ELEPHANT_HELMET);

		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_legs"), JRB_LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_legs"), HERC_LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_legs"), TITAN_LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_legs"), ATLAS_LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "elephant_legs"), ELEPHANT_LEGS);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_boots"), JRB_BOOTS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_boots"), HERC_BOOTS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_boots"), TITAN_BOOTS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_boots"), ATLAS_BOOTS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "elephant_boots"), ELEPHANT_BOOTS);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jrb_elytron"), JRB_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "herc_elytron"), HERC_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "titan_elytron"), TITAN_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "atlas_elytron"), ATLAS_SHELL);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "elephant_elytron"), ELEPHANT_SHELL);
		
        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "tank"), TANK);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "tank"), new BlockItem(TANK, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "leg_tank"), LEG_TANK);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "leg_tank"), new BlockItem(LEG_TANK, new FabricItemSettings().rarity(Rarity.UNCOMMON)));

		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.add(BEETLE_JAR);
			content.add(NET);
			content.add(TANK);
			
	      	content.add(JRB_SHELL);
	      	content.addAfter(JRB_SHELL, HERC_SHELL);
	      	content.addAfter(HERC_SHELL, TITAN_SHELL);
	      	content.addAfter(TITAN_SHELL, ATLAS_SHELL);
	      	content.addAfter(ATLAS_SHELL, ELEPHANT_SHELL);
	      	
	      	content.addAfter(ELEPHANT_SHELL, JRB_HELMET);
	      	content.addAfter(JRB_HELMET, HERC_HELMET);
	      	content.addAfter(HERC_HELMET, TITAN_HELMET);
	      	content.addAfter(TITAN_HELMET, ATLAS_HELMET);
	      	content.addAfter(ATLAS_HELMET, ELEPHANT_HELMET);
	      	
	      	content.addAfter(ELEPHANT_HELMET, JRB_ELYTRA);
	      	content.addAfter(JRB_ELYTRA, HERC_ELYTRA);
	      	content.addAfter(HERC_ELYTRA, TITAN_ELYTRA);
	      	content.addAfter(TITAN_ELYTRA, ATLAS_ELYTRA);
	      	content.addAfter(ATLAS_ELYTRA, ELEPHANT_ELYTRA);
	      	
	      	content.addAfter(ELEPHANT_ELYTRA, JRB_LEGS);
	      	content.addAfter(JRB_LEGS, HERC_LEGS);
	      	content.addAfter(HERC_LEGS, TITAN_LEGS);
	      	content.addAfter(TITAN_LEGS, ATLAS_LEGS);
	      	content.addAfter(ATLAS_LEGS, ELEPHANT_LEGS);
	      	
	      	content.addAfter(ELEPHANT_LEGS, JRB_BOOTS);
	      	content.addAfter(JRB_BOOTS, HERC_BOOTS);
	      	content.addAfter(HERC_BOOTS, TITAN_BOOTS);
	      	content.addAfter(TITAN_BOOTS, ATLAS_BOOTS);
	      	content.addAfter(ATLAS_BOOTS, ELEPHANT_BOOTS);

			content.addAfter(ELEPHANT_BOOTS, JRB_SPAWN_EGG);
	      	content.addAfter(JRB_SPAWN_EGG, HERC_SPAWN_EGG);
	      	content.addAfter(HERC_SPAWN_EGG, TITAN_SPAWN_EGG);
	      	content.addAfter(TITAN_SPAWN_EGG, ATLAS_SPAWN_EGG);
	      	content.addAfter(ATLAS_SPAWN_EGG, ELEPHANT_SPAWN_EGG);
		});
	}
}
