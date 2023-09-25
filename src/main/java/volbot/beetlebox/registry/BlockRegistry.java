package volbot.beetlebox.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import volbot.beetlebox.block.BeetleTankBlock;
import volbot.beetlebox.block.BoilerBlock;
import volbot.beetlebox.block.EmigratorBlock;
import volbot.beetlebox.block.ImmigratorBlock;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.block.BoilerBlockEntity;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.entity.mobstorage.EntityPlacementDispenserBehavior;
import volbot.beetlebox.entity.block.EmigratorBlockEntity;
import volbot.beetlebox.entity.block.ImmigratorBlockEntity;
import volbot.beetlebox.worldgen.AshSaplingGenerator;

public class BlockRegistry {
	public static final Block TANK = new BeetleTankBlock<BeetleEntity>(
			FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), BeetleEntity.class);
	public static final Item TANK_ITEM = new BlockItem(TANK, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Block LEG_TANK = new BeetleTankBlock<LivingEntity>(
			FabricBlockSettings.of(Material.GLASS).strength(4.0f).nonOpaque(), LivingEntity.class);
	public static final Item LEG_TANK_ITEM = new BlockItem(LEG_TANK, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP).rarity(Rarity.UNCOMMON));

	public static final Block BOILER = registerBlock("boiler",
			new BoilerBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F)));

	public static final Block EMIGRATOR = registerBlock("emigrator",new EmigratorBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F)));
	public static final Block IMMIGRATOR = registerBlock("immigrator",new ImmigratorBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F)));

	public static final Block ASH_SAPLING = registerBlock("ash_sapling",
			new SaplingBlock(new AshSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)));

	public static final Block ASH_LOG = registerBlock("ash_log",
			new PillarBlock(FabricBlockSettings.copy(Blocks.BIRCH_LOG)));
	public static final Block ASH_WOOD = registerBlock("ash_wood",
			new PillarBlock(FabricBlockSettings.copy(Blocks.BIRCH_WOOD)));
	public static final Block ASH_LOG_STRIPPED = registerBlock("ash_log_stripped",
			new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_BIRCH_LOG)));
	public static final Block ASH_WOOD_STRIPPED = registerBlock("ash_wood_stripped",
			new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_BIRCH_WOOD)));

	public static final Block ASH_PLANKS = registerBlock("ash_planks",
			new Block(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS)));
	public static final Block ASH_LEAVES = registerBlock("ash_leaves",
			new LeavesBlock(FabricBlockSettings.copy(Blocks.BIRCH_LEAVES)));

	public static final Block ASH_STAIRS = registerBlock("ash_stairs",
			new StairsBlock(ASH_PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_STAIRS)));
	public static final Block ASH_SLAB = registerBlock("ash_slab",
			new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)));
	public static final Block ASH_FENCE = registerBlock("ash_fence",
			new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE)));
	public static final Block ASH_FENCE_GATE = registerBlock("ash_fence_gate",
			new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE)));
	public static final Block ASH_DOOR = registerBlock("ash_door",
			new DoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR)));
	public static final Block ASH_BUTTON = registerBlock("ash_button",
			new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON)));
	public static final Block ASH_PLATE = registerBlock("ash_plate", new PressurePlateBlock(ActivationRule.EVERYTHING,
			FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE)));
	public static final Block ASH_TRAPDOOR = registerBlock("ash_trapdoor",
			new TrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR)));

	public static final BlockFamily ASH_FAMILY = BlockFamilies.register(ASH_PLANKS).button(ASH_BUTTON).fence(ASH_FENCE)
			.fenceGate(ASH_FENCE_GATE).door(ASH_DOOR).pressurePlate(ASH_PLATE).slab(ASH_SLAB).stairs(ASH_STAIRS)
			.trapdoor(ASH_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();

	public static final BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE, new Identifier("beetlebox", "tank_block_entity"),
			FabricBlockEntityTypeBuilder.create(TankBlockEntity::new, TANK, LEG_TANK).build());

	public static final BlockEntityType<BoilerBlockEntity> BOILER_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE, new Identifier("beetlebox", "boiler_block_entity"),
			FabricBlockEntityTypeBuilder.create(BoilerBlockEntity::new, BOILER).build());

	public static final BlockEntityType<EmigratorBlockEntity> EMIGRATOR_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE, new Identifier("beetlebox", "emigrator_block_entity"),
			FabricBlockEntityTypeBuilder.create(EmigratorBlockEntity::new, EMIGRATOR).build());

	public static final BlockEntityType<ImmigratorBlockEntity> IMMIGRATOR_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE, new Identifier("beetlebox", "immigrator_block_entity"),
			FabricBlockEntityTypeBuilder.create(ImmigratorBlockEntity::new, IMMIGRATOR).build());

	
	public static void register() {

		FluidStorage.SIDED.registerForBlockEntity((boiler, direction) -> boiler.fluidStorage, BOILER_BLOCK_ENTITY);

		Registry.register(Registry.BLOCK, new Identifier("beetlebox", "tank"), TANK);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "tank"), TANK_ITEM);
		Registry.register(Registry.BLOCK, new Identifier("beetlebox", "leg_tank"), LEG_TANK);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "leg_tank"), LEG_TANK_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("beetlebox", "boiler"), BOILER);
		DispenserBlock.registerBehavior(ItemRegistry.BEETLE_JAR, new EntityPlacementDispenserBehavior());
		DispenserBlock.registerBehavior(ItemRegistry.LEG_BEETLE_JAR, new EntityPlacementDispenserBehavior());

	}

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		return Registry.register(Registry.BLOCK, new Identifier("beetlebox", name), block);
	}

	private static Item registerBlockItem(String name, Block block) {
		Item item = Registry.register(Registry.ITEM, new Identifier("beetlebox", name),
				new BlockItem(block, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP)));
		return item;
	}

}
