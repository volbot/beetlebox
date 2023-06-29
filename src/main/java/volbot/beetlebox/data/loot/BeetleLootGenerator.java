package volbot.beetlebox.data.loot;

import java.util.function.BiConsumer;
import java.util.HashMap;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

public class BeetleLootGenerator extends SimpleFabricLootTableProvider {
	public BeetleLootGenerator(FabricDataOutput output) {
		super(output, LootContextTypes.ENTITY);
	}

	public static HashMap<String, Builder> beetle_loot = new HashMap<String, Builder>();
	
	@Override
	public void accept(BiConsumer<Identifier, Builder> t) {
		for(String s : beetle_loot.keySet()) {
			t.accept(new Identifier("beetlebox","entities/"+s), beetle_loot.get(s));
		}
	}

}
