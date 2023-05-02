package volbot.beetlebox.registry;

import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Vector;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.item.BlockItem;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
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
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.item.FruitSyrup;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.beetle.TityusEntity;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.block.BoilerBlock;
import volbot.beetlebox.entity.beetle.AtlasEntity;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.beetle.ElephantEntity;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.NetItem;
import volbot.beetlebox.entity.beetle.JRBEntity;
import volbot.beetlebox.entity.beetle.JunebugEntity;
import volbot.beetlebox.recipe.BoilingRecipe;
import volbot.beetlebox.recipe.JellyMixRecipe;

public class BeetleRegistry {
	public static Vector<Item> beetle_helmets = new Vector<>();
	public static Vector<Item> spawn_eggs = new Vector<>();
	public static Vector<Item> armor_sets = new Vector<>();
	public static Vector<Item> beetle_drops = new Vector<>();
	
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
	public static final EntityType<TityusEntity> TITYUS = FabricEntityTypeBuilder.createMob()
        	.entityFactory(TityusEntity::new)
        	.spawnGroup(SpawnGroup.CREATURE)
        	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
        	.build();
	public static final EntityType<JunebugEntity> JUNEBUG = FabricEntityTypeBuilder.createMob()
        	.entityFactory(JunebugEntity::new)
        	.spawnGroup(SpawnGroup.CREATURE)
        	.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
        	.build();
	
	public static final Item GELATIN = new Item(new FabricItemSettings());
	public static final Item SUGAR_GELATIN = new Item(new FabricItemSettings());
	public static final Item GELATIN_GLUE = new Item(new FabricItemSettings());
	
	public static final Item FRUIT_SYRUP = new FruitSyrup(new FabricItemSettings());
	public static final Item JELLY_MIX = new Item(new FabricItemSettings());
	
    public static final Item NET = new NetItem(new FabricItemSettings());

    public static final Item BEETLE_JAR = new BeetleJarItem<BeetleEntity>(new FabricItemSettings(), BeetleEntity.class);
    public static final Item LEG_BEETLE_JAR = new BeetleJarItem<LivingEntity>(new FabricItemSettings().rarity(Rarity.UNCOMMON), LivingEntity.class);
    
    public static final Block TANK = new BeetleTankBlock<BeetleEntity>(FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), BeetleEntity.class);
    public static final Item TANK_ITEM = new BlockItem(TANK, new FabricItemSettings());
    public static final Block LEG_TANK = new BeetleTankBlock<LivingEntity>(FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), LivingEntity.class);
    public static final Item LEG_TANK_ITEM = new BlockItem(LEG_TANK, new FabricItemSettings().rarity(Rarity.UNCOMMON));
   
    public static final Block BOILER = new BoilerBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f));
    public static final Item BOILER_ITEM = new BlockItem(BOILER, new FabricItemSettings());
    
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
    
    public static RecipeSerializer<JellyMixRecipe> JELLY_RECIPE_SERIALIZER;
    public static final RecipeType<JellyMixRecipe> JELLY_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier("beetlebox", "jelly_mix"), new RecipeType<JellyMixRecipe>() {
        @Override
        public String toString() {return "jelly_mix";}
    });
	
	public static final BlockEntityType<BoilerBlockEntity> BOILER_BLOCK_ENTITY = Registry.register(
	        Registries.BLOCK_ENTITY_TYPE,
	        new Identifier("beetlebox", "furnace_boiler_block_entity"),
	        FabricBlockEntityTypeBuilder.create(BoilerBlockEntity::new, BOILER).build()
	    );
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier("beetlebox", "beetlebox"))
			.icon(() -> new ItemStack(armor_sets.get(0)))
			.build();
	
	public static void register() {
		
		BOILING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("beetlebox", "boiling_recipe"), new CookingRecipeSerializer<>(BoilingRecipe::new, 50));
		
		FluidStorage.SIDED.registerForBlockEntity((boiler, direction) -> boiler.fluidStorage, BOILER_BLOCK_ENTITY);
		
		//ENTITIES
		BeetleUtils.registerBeetle(JRB, "jrb", "Kabutomushi", 0x110b0b, 0x180f0f);
		BeetleUtils.registerBeetle(HERC, "hercules", "Hercules Beetle", 0xa99859, 0x150f10);
		BeetleUtils.registerBeetle(TITAN, "titanus", "Titanus", 0x0e0f10, 0x363840);
		BeetleUtils.registerBeetle(ATLAS, "atlas", "Atlas Beetle", 0x080904, 0x22270d);
		BeetleUtils.registerBeetle(ELEPHANT, "elephant", "Elephant Beetle", 0x5e3924, 0x180f06);
		BeetleUtils.registerBeetle(TITYUS, "tityus", "Tityus", 0x9a8666, 0x1b1612);
		BeetleUtils.registerBeetle(JUNEBUG, "junebug", "Junebug", 0x112612, 0x343419);

		Predicate<BiomeSelectionContext> forests = BiomeSelectors.tag(ConventionalBiomeTags.TREE_DECIDUOUS);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, JRB, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITAN, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, TITYUS, 16, 1, 2);
		BiomeModifications.addSpawn(forests, SpawnGroup.CREATURE, JUNEBUG, 16, 1, 2);
		
		Predicate<BiomeSelectionContext> desert = BiomeSelectors.tag(ConventionalBiomeTags.CLIMATE_HOT);
		BiomeModifications.addSpawn(desert, SpawnGroup.CREATURE, JUNEBUG, 16, 1, 2);
		
		Predicate<BiomeSelectionContext> plains = BiomeSelectors.tag(ConventionalBiomeTags.PLAINS);
		BiomeModifications.addSpawn(plains, SpawnGroup.CREATURE, TITYUS, 16, 1, 2);
		BiomeModifications.addSpawn(plains, SpawnGroup.CREATURE, JUNEBUG, 16, 1, 2);

		Predicate<BiomeSelectionContext> jungles = BiomeSelectors.tag(ConventionalBiomeTags.TREE_JUNGLE);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, HERC, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, ATLAS, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, TITAN, 16, 1, 2);
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, ELEPHANT, 16, 1, 2);
		
		Predicate<BiomeSelectionContext> floral = BiomeSelectors.tag(ConventionalBiomeTags.FLORAL);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, TITAN, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, JRB, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, JUNEBUG, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, TITYUS, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, HERC, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, ATLAS, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, ELEPHANT, 16, 1, 2);

		//ITEMS
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "beetle_jar"), BEETLE_JAR);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "leg_beetle_jar"), LEG_BEETLE_JAR);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "net"), NET);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "gelatin"), GELATIN);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "sugar_gelatin"), SUGAR_GELATIN);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "gelatin_glue"), GELATIN_GLUE);

		Registry.register(Registries.ITEM, new Identifier("beetlebox", "fruit_syrup"), FRUIT_SYRUP);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "jelly_mix"), JELLY_MIX);
		
        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "tank"), TANK);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "tank"), TANK_ITEM);
        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "leg_tank"), LEG_TANK);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "leg_tank"), LEG_TANK_ITEM);

        Registry.register(Registries.BLOCK, new Identifier("beetlebox", "boiler"), BOILER);
        Registry.register(Registries.ITEM, new Identifier("beetlebox", "boiler"), BOILER_ITEM);
        
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.add(BEETLE_JAR);
			content.add(LEG_BEETLE_JAR);

			content.add(NET);
			content.add(TANK);
			content.add(LEG_TANK);
			
			content.add(BOILER);
			content.add(GELATIN);
			content.add(SUGAR_GELATIN);
			content.add(GELATIN_GLUE);
			
			ItemStack apple_syrup = new ItemStack(FRUIT_SYRUP);
			apple_syrup.getOrCreateNbt().putString("FruitType", "apple");
			content.add(apple_syrup);
			
			ItemStack melon_syrup = new ItemStack(FRUIT_SYRUP);
			melon_syrup.getOrCreateNbt().putString("FruitType", "melon");
			content.add(melon_syrup);
			
			ItemStack berry_syrup = new ItemStack(FRUIT_SYRUP);
			berry_syrup.getOrCreateNbt().putString("FruitType", "berry");
			content.add(berry_syrup);
			
			ItemStack sugar_syrup = new ItemStack(FRUIT_SYRUP);
			sugar_syrup.getOrCreateNbt().putString("FruitType", "sugar");
			content.add(sugar_syrup);
			
			ItemStack glow_syrup = new ItemStack(FRUIT_SYRUP);
			glow_syrup.getOrCreateNbt().putString("FruitType", "glow");
			content.add(glow_syrup);
			
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
