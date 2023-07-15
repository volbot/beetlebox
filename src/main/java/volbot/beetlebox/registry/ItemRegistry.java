package volbot.beetlebox.registry;

import java.util.Vector;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.item.BeetleJelly;
import volbot.beetlebox.item.FruitSyrup;
import volbot.beetlebox.item.equipment.materials.BeetleItemUpgrade;
import volbot.beetlebox.item.equipment.materials.DormantUpgrade;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.NetItem;

public class ItemRegistry {

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier("beetlebox", "beetlebox"))
			.icon(() -> new ItemStack(Items.ELYTRA)).build();

	public static Vector<Item> beetle_helmets = new Vector<>();
	public static Vector<Item> spawn_eggs = new Vector<>();
	public static Vector<Item> armor_sets = new Vector<>();
	public static Vector<Item> beetle_drops = new Vector<>();

	public static Vector<Item> helmet_upgrades = new Vector<>();
	public static Vector<Item> chest_upgrades = new Vector<>();
	public static Vector<Item> legs_upgrades = new Vector<>();
	public static Vector<Item> boots_upgrades = new Vector<>();

	public static final Item GELATIN = new Item(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item SUGAR_GELATIN = new Item(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item GELATIN_GLUE = new Item(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));

	public static final Item APPLE_SYRUP = new FruitSyrup(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item MELON_SYRUP = new FruitSyrup(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item BERRY_SYRUP = new FruitSyrup(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item SUGAR_SYRUP = new FruitSyrup(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item CACTUS_SYRUP = new FruitSyrup(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item BEETLE_JELLY = new BeetleJelly(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));

	public static final Item UPGRADE_DORMANT = new DormantUpgrade(new FabricItemSettings());
	public static final Item UPGRADE_H_ATTACK = new BeetleItemUpgrade("beetle_helmet_attack", EquipmentSlot.HEAD, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_C_ELYTRA = new BeetleItemUpgrade("beetle_chest_elytra", EquipmentSlot.CHEST, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_L_CLIMB = new BeetleItemUpgrade("beetle_legs_wallclimb", EquipmentSlot.LEGS, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_B_FALLDAM = new BeetleItemUpgrade("beetle_boots_falldamage", EquipmentSlot.FEET, new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_H_NV = new BeetleItemUpgrade("beetle_helmet_nv", EquipmentSlot.HEAD,
			new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_C_BOOST = new BeetleItemUpgrade("beetle_chest_boost", EquipmentSlot.CHEST,
			new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_L_2JUMP = new BeetleItemUpgrade("beetle_legs_2jump", EquipmentSlot.LEGS,
			new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_B_SPEED = new BeetleItemUpgrade("beetle_boots_speed", EquipmentSlot.FEET,
			new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));
	public static final Item UPGRADE_B_STEP = new BeetleItemUpgrade("beetle_boots_step", EquipmentSlot.FEET,
			new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));

	public static final Item NET = new NetItem(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP));

	public static final Item BEETLE_JAR = new BeetleJarItem<BeetleEntity>(new FabricItemSettings().group(ItemRegistry.ITEM_GROUP), BeetleEntity.class);
	public static final Item LEG_BEETLE_JAR = new BeetleJarItem<LivingEntity>(
			new FabricItemSettings().group(ItemRegistry.ITEM_GROUP).rarity(Rarity.UNCOMMON), LivingEntity.class);

	public static void register() {

		helmet_upgrades.add(UPGRADE_H_ATTACK);
		helmet_upgrades.add(UPGRADE_H_NV);
		chest_upgrades.add(UPGRADE_C_ELYTRA);
		chest_upgrades.add(UPGRADE_C_BOOST);
		legs_upgrades.add(UPGRADE_L_CLIMB);
		legs_upgrades.add(UPGRADE_L_2JUMP);
		boots_upgrades.add(UPGRADE_B_FALLDAM);
		
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "beetle_jar"), BEETLE_JAR);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "leg_beetle_jar"), LEG_BEETLE_JAR);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "net"), NET);
		boots_upgrades.add(UPGRADE_B_SPEED);
		boots_upgrades.add(UPGRADE_B_STEP);

		Registry.register(Registry.ITEM, new Identifier("beetlebox", "gelatin"), GELATIN);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "sugar_gelatin"), SUGAR_GELATIN);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "gelatin_glue"), GELATIN_GLUE);

		Registry.register(Registry.ITEM, new Identifier("beetlebox", "apple_syrup"), APPLE_SYRUP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "melon_syrup"), MELON_SYRUP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "berry_syrup"), BERRY_SYRUP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "sugar_syrup"), SUGAR_SYRUP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "cactus_syrup"), CACTUS_SYRUP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "beetle_jelly"), BEETLE_JELLY);

		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_dormant"), UPGRADE_DORMANT);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_h_attack"), UPGRADE_H_ATTACK);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_h_nv"), UPGRADE_H_NV);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_c_elytra"), UPGRADE_C_ELYTRA);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_c_boost"), UPGRADE_C_BOOST);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_l_climb"), UPGRADE_L_CLIMB);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_l_2jump"), UPGRADE_L_2JUMP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_b_falldam"), UPGRADE_B_FALLDAM);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_b_step"), UPGRADE_B_STEP);
		Registry.register(Registry.ITEM, new Identifier("beetlebox", "upgrade_b_speed"), UPGRADE_B_SPEED);

	}

}
