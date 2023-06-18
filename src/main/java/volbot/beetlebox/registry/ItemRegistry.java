package volbot.beetlebox.registry;

import java.util.Vector;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.item.BeetleJelly;
import volbot.beetlebox.item.FruitSyrup;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.NetItem;

public class ItemRegistry {

	public static Vector<Item> beetle_helmets = new Vector<>();
	public static Vector<Item> spawn_eggs = new Vector<>();
	public static Vector<Item> armor_sets = new Vector<>();
	public static Vector<Item> beetle_drops = new Vector<>();

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

	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier("beetlebox", "beetlebox"))
			.icon(() -> new ItemStack(BEETLE_JELLY)).build();

	public static void register() {

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

		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(content -> {
			content.add(BEETLE_JAR);
			content.add(LEG_BEETLE_JAR);

			content.add(NET);

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
