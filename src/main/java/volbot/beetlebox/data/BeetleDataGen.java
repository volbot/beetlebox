package volbot.beetlebox.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import volbot.beetlebox.data.lang.BeetleEnglishProvider;
import volbot.beetlebox.data.loot.BeetleLootGenerator;
import volbot.beetlebox.data.models.BeetleModelGenerator;
import volbot.beetlebox.data.recipe.BeetleRecipeGenerator;
import volbot.beetlebox.data.tags.BeetleItemTagGenerator;

public class BeetleDataGen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator pack) {
		pack.addProvider(BeetleItemTagGenerator::new);
		pack.addProvider(BeetleRecipeGenerator::new);
		pack.addProvider(BeetleLootGenerator::new);
		pack.addProvider(BeetleModelGenerator::new);
		pack.addProvider(BeetleEnglishProvider::new);
	}

}
