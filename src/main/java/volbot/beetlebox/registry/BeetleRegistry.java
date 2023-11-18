package volbot.beetlebox.registry;

import java.util.function.Predicate;
import java.util.Vector;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.beetle.HercEntity;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.beetle.TityusEntity;
import volbot.beetlebox.entity.projectile.BeetleProjectileEntity;
import volbot.beetlebox.entity.beetle.ActaeonEntity;
import volbot.beetlebox.entity.beetle.AtlasEntity;
import volbot.beetlebox.entity.beetle.ElephantEntity;
import volbot.beetlebox.entity.beetle.JRBEntity;
import volbot.beetlebox.entity.beetle.JunebugEntity;

public class BeetleRegistry {

	public static final EntityType<BeetleProjectileEntity> BEETLE_PROJECTILE = FabricEntityTypeBuilder
			.<BeetleProjectileEntity>create(SpawnGroup.MISC, BeetleProjectileEntity::new).trackedUpdateRate(20)
			.trackRangeBlocks(4).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build();

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

	public static Vector<EntityType<? extends BeetleEntity>> beetles = new Vector<EntityType<? extends BeetleEntity>>();

	public static void register() {
		Registry.register(Registries.ENTITY_TYPE, new Identifier("beetlebox", "beetle_projectile"), BEETLE_PROJECTILE);
		// ENTITIES
		BeetleUtils.registerBeetle(JRB, "jrb", "Kabutomushi", "flip", 0x110b0b, 0x180f0f);
		BeetleUtils.registerBeetle(HERC, "hercules", "Hercules Beetle", "pinch", 0xa99859, 0x150f10);
		BeetleUtils.registerBeetle(TITAN, "titanus", "Titanus", "pinch", 0x0e0f10, 0x363840);
		BeetleUtils.registerBeetle(ATLAS, "atlas", "Atlas Beetle", "flip", 0x080904, 0x22270d);
		BeetleUtils.registerBeetle(ELEPHANT, "elephant", "Elephant Beetle", "flip", 0x5e3924, 0x180f06);
		BeetleUtils.registerBeetle(TITYUS, "tityus", "Tityus", "pinch", 0x9a8666, 0x1b1612);
		BeetleUtils.registerBeetle(JUNEBUG, "junebug", "June Beetle", "headbutt", 0x112612, 0x343419);
		BeetleUtils.registerBeetle(ACTAEON, "actaeon", "Actaeon Beetle", "flip", 0x115181b, 0x20252a);

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

	}
}
