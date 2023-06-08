package volbot.beetlebox.registry;

import java.util.function.Predicate;
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
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.item.BeetleJelly;
import volbot.beetlebox.item.FruitSyrup;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.beetle.TityusEntity;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.block.BoilerBlock;
import volbot.beetlebox.entity.beetle.ActaeonEntity;
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

	// ENTITY TYPES
	public static final EntityType<JRBEntity> JRB = FabricEntityTypeBuilder.createMob().entityFactory(JRBEntity::new)
			.spawnGroup(SpawnGroup.CREATURE).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<HercEntity> HERC = FabricEntityTypeBuilder.createMob().entityFactory(HercEntity::new)
			.spawnGroup(SpawnGroup.CREATURE).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<TitanEntity> TITAN = FabricEntityTypeBuilder.createMob()
			.entityFactory(TitanEntity::new).spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<AtlasEntity> ATLAS = FabricEntityTypeBuilder.createMob()
			.entityFactory(AtlasEntity::new).spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<ElephantEntity> ELEPHANT = FabricEntityTypeBuilder.createMob()
			.entityFactory(ElephantEntity::new).spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<TityusEntity> TITYUS = FabricEntityTypeBuilder.createMob()
			.entityFactory(TityusEntity::new).spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<JunebugEntity> JUNEBUG = FabricEntityTypeBuilder.createMob()
			.entityFactory(JunebugEntity::new).spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();
	public static final EntityType<ActaeonEntity> ACTAEON = FabricEntityTypeBuilder.createMob()
			.entityFactory(ActaeonEntity::new).spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build();

	public static final Item GELATIN = new Item(new FabricItemSettings());
	public static final Item SUGAR_GELATIN = new Item(new FabricItemSettings());
	public static final Item GELATIN_GLUE = new Item(new FabricItemSettings());

	public static final Item APPLE_SYRUP = new FruitSyrup(new FabricItemSettings());
	public static final Item MELON_SYRUP = new FruitSyrup(new FabricItemSettings());
	public static final Item BERRY_SYRUP = new FruitSyrup(new FabricItemSettings());
	public static final Item SUGAR_SYRUP = new FruitSyrup(new FabricItemSettings());
	public static final Item CACTUS_SYRUP = new FruitSyrup(new FabricItemSettings());
	public static final Item BEETLE_JELLY = new BeetleJelly(new FabricItemSettings());

	public static final Item NET = new NetItem(new FabricItemSettings());

	public static final Item BEETLE_JAR = new BeetleJarItem<BeetleEntity>(new FabricItemSettings(), BeetleEntity.class);
	public static final Item LEG_BEETLE_JAR = new BeetleJarItem<LivingEntity>(
			new FabricItemSettings().rarity(Rarity.UNCOMMON), LivingEntity.class);

	public static final Block TANK = new BeetleTankBlock<BeetleEntity>(
			FabricBlockSettings.copyOf(Blocks.GLASS).strength(4.0f).nonOpaque(), BeetleEntity.class);
	public static final Item TANK_ITEM = new BlockItem(TANK, new FabricItemSettings());
	public static final Block LEG_TANK = new BeetleTankBlock<LivingEntity>(
			FabricBlockSettings.copyOf(Blocks.GLASS).strength(4.0f).nonOpaque(), LivingEntity.class);
	public static final Item LEG_TANK_ITEM = new BlockItem(LEG_TANK, new FabricItemSettings().rarity(Rarity.UNCOMMON));

	public static final Block BOILER = new BoilerBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON).strength(4.0f));
	public static final Item BOILER_ITEM = new BlockItem(BOILER, new FabricItemSettings());

	public static final BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE, new Identifier("beetlebox", "tank_block_entity"),
			FabricBlockEntityTypeBuilder.create(TankBlockEntity::new, TANK, LEG_TANK).build());

	public static RecipeSerializer<BoilingRecipe> BOILING_RECIPE_SERIALIZER;
	public static final RecipeType<BoilingRecipe> BOILING_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE,
			new Identifier("beetlebox", "boiling_recipe"), new RecipeType<BoilingRecipe>() {
				@Override
				public String toString() {
					return "boiling_recipe";
				}
			});

	public static final RecipeType<JellyMixRecipe> JELLY_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE,
			new Identifier("beetlebox", "jelly_recipe"), new RecipeType<JellyMixRecipe>() {
				@Override
				public String toString() {
					return "jelly_recipe";
				}
			});

	public static final BlockEntityType<BoilerBlockEntity> BOILER_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE, new Identifier("beetlebox", "boiler_block_entity"),
			FabricBlockEntityTypeBuilder.create(BoilerBlockEntity::new, BOILER).build());

	public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("beetlebox","item_group"));
	
	

	public static void register() {

		BOILING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER,
				new Identifier("beetlebox", "boiling_recipe"), new CookingRecipeSerializer<>(BoilingRecipe::new, 50));
		Registry.register(Registries.RECIPE_SERIALIZER, JellyMixRecipe.Serializer.ID,
				JellyMixRecipe.Serializer.INSTANCE);

		FluidStorage.SIDED.registerForBlockEntity((boiler, direction) -> boiler.fluidStorage, BOILER_BLOCK_ENTITY);

		// ENTITIES
		BeetleUtils.registerBeetle(JRB, "jrb", "Kabutomushi", 0x110b0b, 0x180f0f);
		BeetleUtils.registerBeetle(HERC, "hercules", "Hercules Beetle", 0xa99859, 0x150f10);
		BeetleUtils.registerBeetle(TITAN, "titanus", "Titanus", 0x0e0f10, 0x363840);
		BeetleUtils.registerBeetle(ATLAS, "atlas", "Atlas Beetle", 0x080904, 0x22270d);
		BeetleUtils.registerBeetle(ELEPHANT, "elephant", "Elephant Beetle", 0x5e3924, 0x180f06);
		BeetleUtils.registerBeetle(TITYUS, "tityus", "Tityus", 0x9a8666, 0x1b1612);
		BeetleUtils.registerBeetle(JUNEBUG, "junebug", "June Beetle", 0x112612, 0x343419);
		BeetleUtils.registerBeetle(ACTAEON, "actaeon", "Actaeon Beetle", 0x115181b, 0x20252a);

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
		BiomeModifications.addSpawn(jungles, SpawnGroup.CREATURE, ACTAEON, 16, 1, 2);

		Predicate<BiomeSelectionContext> floral = BiomeSelectors.tag(ConventionalBiomeTags.FLORAL);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, TITAN, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, JRB, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, JUNEBUG, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, TITYUS, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, HERC, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, ATLAS, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, ELEPHANT, 16, 1, 2);
		BiomeModifications.addSpawn(floral, SpawnGroup.CREATURE, ACTAEON, 16, 1, 2);

		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
					.displayName(Text.of("beetlebox"))
					.icon(() -> new ItemStack(BEETLE_JELLY))
					.build()
				);
		
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "beetle_jar"), BEETLE_JAR);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "leg_beetle_jar"), LEG_BEETLE_JAR);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "net"), NET);

		Registry.register(Registries.ITEM, new Identifier("beetlebox", "gelatin"), GELATIN);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "sugar_gelatin"), SUGAR_GELATIN);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "gelatin_glue"), GELATIN_GLUE);

		Registry.register(Registries.ITEM, new Identifier("beetlebox", "apple_syrup"), APPLE_SYRUP);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "melon_syrup"), MELON_SYRUP);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "berry_syrup"), BERRY_SYRUP);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "sugar_syrup"), SUGAR_SYRUP);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "cactus_syrup"), CACTUS_SYRUP);
		Registry.register(Registries.ITEM, new Identifier("beetlebox", "beetle_jelly"), BEETLE_JELLY);
		
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

			content.add(APPLE_SYRUP);
			content.add(MELON_SYRUP);
			content.add(BERRY_SYRUP);
			content.add(SUGAR_SYRUP);

			for (Item i : beetle_drops) {
				content.add(i);
			}

			for (Item i : armor_sets) {
				content.add(i);
			}

			for (Item i : spawn_eggs) {
				content.add(i);
			}
		});
	}
}
