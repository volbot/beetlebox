package volbot.beetlebox.data.models;

import java.util.Vector;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleModelGenerator extends FabricModelProvider {

	public static Vector<Item> items = new Vector<>();
	
	public BeetleModelGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		items.addAll(ItemRegistry.armor_sets);
		items.addAll(ItemRegistry.beetle_drops);
		items.add(ItemRegistry.GELATIN);
		items.add(ItemRegistry.SUGAR_GELATIN);
		items.add(ItemRegistry.GELATIN_GLUE);
		items.add(ItemRegistry.BEETLE_JELLY);
		for(Item item : items ) {
			itemModelGenerator.register(item, Models.GENERATED);
		}
	}

}
