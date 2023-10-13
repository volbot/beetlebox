package volbot.beetlebox.data.models;

import java.util.Vector;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleModelGenerator extends FabricModelProvider {

	public static Vector<Item> items = new Vector<>();

	public BeetleModelGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerLog(BlockRegistry.ASH_LOG).log(BlockRegistry.ASH_LOG)  
				.wood(BlockRegistry.ASH_WOOD);
		blockStateModelGenerator.registerLog(BlockRegistry.ASH_WOOD);
		blockStateModelGenerator.registerLog(BlockRegistry.ASH_LOG_STRIPPED).log(BlockRegistry.ASH_LOG_STRIPPED)
				.wood(BlockRegistry.ASH_WOOD_STRIPPED);
		blockStateModelGenerator.registerLog(BlockRegistry.ASH_WOOD_STRIPPED);

		blockStateModelGenerator.registerCubeAllModelTexturePool(BlockRegistry.ASH_PLANKS).family(BlockRegistry.ASH_FAMILY);

		blockStateModelGenerator.registerTintableCross(BlockRegistry.ASH_SAPLING,
				BlockStateModelGenerator.TintType.NOT_TINTED);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		items.addAll(ItemRegistry.armor_sets);
		items.addAll(ItemRegistry.beetle_drops);
		items.add(ItemRegistry.SUBSTRATE);
		items.add(ItemRegistry.SUBSTRATE_JAR);
		items.add(ItemRegistry.LARVA_JAR);
		items.add(ItemRegistry.GELATIN);
		items.add(ItemRegistry.SUGAR_GELATIN);
		items.add(ItemRegistry.GELATIN_GLUE);
		items.add(ItemRegistry.BEETLE_JELLY);
		items.add(ItemRegistry.BEETLE_HUSK);
		items.add(ItemRegistry.UPGRADE_DORMANT);
		items.addAll(ItemRegistry.helmet_upgrades);
		items.addAll(ItemRegistry.chest_upgrades);
		items.addAll(ItemRegistry.legs_upgrades);
		items.addAll(ItemRegistry.boots_upgrades);
		for (Item item : items) {
			itemModelGenerator.register(item, Models.GENERATED);
		}
	}

}
