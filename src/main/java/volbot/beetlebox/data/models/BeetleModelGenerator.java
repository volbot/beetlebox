package volbot.beetlebox.data.models;

import java.util.Vector;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import volbot.beetlebox.registry.BeetleRegistry;

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
		items.addAll(BeetleRegistry.armor_sets);
		items.addAll(BeetleRegistry.beetle_drops);
		items.add(BeetleRegistry.GELATIN);
		items.add(BeetleRegistry.SUGAR_GELATIN);
		items.add(BeetleRegistry.GELATIN_GLUE);
		items.add(BeetleRegistry.BEETLE_JELLY);
		items.add(BeetleRegistry.JELLY_TREAT);
		for(Item item : items ) {
			itemModelGenerator.register(item, Models.GENERATED);
		}
	}

}
