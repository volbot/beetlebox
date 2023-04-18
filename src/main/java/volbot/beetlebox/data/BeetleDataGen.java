package volbot.beetlebox.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import volbot.beetlebox.data.tags.BeetleItemTagGenerator;

public class BeetleDataGen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(BeetleItemTagGenerator::new);
	}

}
