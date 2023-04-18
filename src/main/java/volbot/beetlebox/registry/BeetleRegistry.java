package volbot.beetlebox.registry;

import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Vector;

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
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
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
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.block.BoilerBlock;
import volbot.beetlebox.entity.beetle.AtlasEntity;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.beetle.ElephantEntity;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.BeetleElytraItem;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.NetItem;
import volbot.beetlebox.entity.beetle.JRBEntity;
import volbot.beetlebox.recipe.BoilingRecipe;
import volbot.beetlebox.client.render.armor.BeetleArmorEntityModel;
import volbot.beetlebox.client.render.armor.JRBHelmetModel;
import volbot.beetlebox.client.render.armor.HercHelmetModel;
import volbot.beetlebox.client.render.armor.TitanHelmetModel;
import volbot.beetlebox.client.render.armor.AtlasHelmetModel;
import volbot.beetlebox.client.render.armor.ElephantHelmetModel;

public class BeetleRegistry {
	
	private static void registerBeetle(EntityType<? extends BeetleEntity> beetleType, String beetle_id, int color1, int color2, BeetleArmorEntityModel<?> helmet_model) {
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox",beetle_id), beetleType);
		FabricDefaultAttributeRegistry.register(beetleType, BeetleEntity.createBeetleAttributes());
		Item SPAWN_EGG = new SpawnEggItem(beetleType, color1, color2, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_spawn_egg"), SPAWN_EGG);
		spawn_eggs.add(SPAWN_EGG);
		Item ELYTRA = new BeetleElytraItem(new ChitinMaterial.JRBChitinMaterial(), new FabricItemSettings());
		Item HELMET = new BeetleArmorItem(new ChitinMaterial.JRBChitinMaterial(), Type.HELMET, new FabricItemSettings());
		Item LEGS = new BeetleArmorItem(new ChitinMaterial.JRBChitinMaterial(), Type.LEGGINGS, new FabricItemSettings());
		Item BOOTS = new BeetleArmorItem(new ChitinMaterial.JRBChitinMaterial(), Type.BOOTS, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_helmet"), HELMET);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_elytra"), ELYTRA);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_legs"), LEGS);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_boots"), BOOTS);
		armor_sets.add(ELYTRA);
		armor_sets.add(HELMET);
		beetle_helmets.put(HELMET, helmet_model);
		armor_sets.add(LEGS);
		armor_sets.add(BOOTS);
		Item ELYTRON = new Item(new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier("beetlebox", beetle_id+"_elytron"), ELYTRON);
		beetle_drops.add(ELYTRON);
	}

	public static HashMap<Item, BeetleArmorEntityModel<?>> beetle_helmets = new HashMap<>();
	protected static Vector<Item> spawn_eggs = new Vector<>();
	protected static Vector<Item> armor_sets = new Vector<>();
	protected static Vector<Item> beetle_drops = new Vector<>();
	
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
	
	public static final Item GELATIN = new Item(new FabricItemSettings());
	public static final Item SUGAR_GELATIN = new Item(new FabricItemSettings());
	public static final Item GELATIN_GLUE = new Item(new FabricItemSettings());
	
    public static final Item NET = new NetItem(new FabricItemSettings());

    public static final Item BEETLE_JAR = new BeetleJarItem<BeetleEntity>(new FabricItemSettings(), BeetleEntity.class);
    public static final Item LEG_BEETLE_JAR = new BeetleJarItem<LivingEntity>(new FabricItemSettings().rarity(Rarity.UNCOMMON), LivingEntity.class);
    
    public static final Block TANK = new BeetleTankBlock<BeetleEntity>(FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), BeetleEntity.class);
    public static final Block LEG_TANK = new BeetleTankBlock<LivingEntity>(FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), LivingEntity.class);
   
    public static final Block BOILER = new BoilerBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f));

	public static final BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY = Registry.register(
	        Registries.BLOCK_ENTITY_TYPE,
	        new Identifier("beetlebox", "tank_block_entity"),
	        FabricBlockEntityTypeBuilder.create(TankBlockEntity::new,TANK, LEG_TANK).build()
	    );
	
    public static RecipeSerializer<BoilingRecipe> BOILING_RECIPE_SERIALIZER;
    public static final RecipeType<BoilingRecipe> BOILING_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier("beetlebox", "boiling_recipe"), new RecipeType<BoilingRecipe>() {
        @Override
        public String toString() {return "boiling_recipe";}
    });
	
	public static final BlockEntityType<BoilerBlockEntity> BOILER_BLOCK_ENTITY = Registry.register(
	        Registries.BLOCK_ENTITY_TYPE,
	        new Identifier("beetlebox", "furnace_boiler_block_entity"),
	        FabricBlockEntityTypeBuilder.create(BoilerBlockEntity::new, BOILER).build()
	    );
	
	private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier("beetlebox", "beetlebox"))
			.icon(() -> new ItemStack(armor_sets.get(0)))
			.build();
	
	public static void register() {
		
		BOILING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("beetlebox", "boiling_recipe"), new CookingRecipeSerializer<>(BoilingRecipe::new, 50));
		
		FluidStorage.SIDED.registerForBlockEntity((myTank, direction) -> myTank.fluidStorage, BOILER_BLOCK_ENTITY);
		
		//ENTITIES
		BeetleRegistry.registerBeetle(JRB, "jrb", 0x110b0b, 0x180f0f, new JRBHelmetModel<>());
		BeetleRegistry.registerBeetle(HERC, "hercules", 0xa99859, 0x150f10, new HercHelmetModel<>());
		BeetleRegistry.registerBeetle(TITAN, "titanus", 0x0e0f10, 0x363840, new TitanHelmetModel<>());
		BeetleRegistry.registerBeetle(ATLAS, "atlas", 0x080904, 0x22270d, new AtlasHelmetModel<>());
		BeetleRegistry.registerBeetle(ELEPHANT, "elephant", 0x5e3924, 0x180f06, new ElephantHelmetModel<>());
		
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
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "gelatin"), GELATIN);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "sugar_gelatin"), SUGAR_GELATIN);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "gelatin_glue"), GELATIN_GLUE);
		
        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "tank"), TANK);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "tank"), new BlockItem(TANK, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "leg_tank"), LEG_TANK);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "leg_tank"), new BlockItem(LEG_TANK, new FabricItemSettings().rarity(Rarity.UNCOMMON)));

        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "boiler"), BOILER);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "boiler"), new BlockItem(BOILER, new FabricItemSettings()));
        
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.add(BEETLE_JAR);
			content.add(NET);
			content.add(TANK);
			
			content.add(BOILER);
			content.add(GELATIN);
			
			for(Item i : beetle_drops) {
	      		content.add(i);
	      	}
			
	      	for(Item i : armor_sets) {
	      		content.add(i);
	      	}
	      	
	      	for(Item i : spawn_eggs) {
	      		content.add(i);
	      	}
		});
	}
}
