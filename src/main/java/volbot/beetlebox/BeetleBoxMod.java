package volbot.beetlebox;

import net.fabricmc.api.ModInitializer;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.DataRegistry;
import volbot.beetlebox.registry.ItemRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeetleBoxMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("beetlebox");
    
	@Override
	public void onInitialize() {
		
		LOGGER.info("loading insects, bugs and the like...");

        BeetleRegistry.register();
        ItemRegistry.register();
        BlockRegistry.register();
        DataRegistry.register();
    }
}