package volbot.beetlebox;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import volbot.beetlebox.registry.BeetleFlammableBlockRegistry;
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

        ItemRegistry.register();
        BeetleRegistry.register();
        BlockRegistry.register();
        DataRegistry.register();
        
        BeetleFlammableBlockRegistry.registerFlammableBlocks();
        StrippableBlockRegistry.register(BlockRegistry.ASH_LOG, BlockRegistry.ASH_LOG_STRIPPED);
        StrippableBlockRegistry.register(BlockRegistry.ASH_WOOD, BlockRegistry.ASH_WOOD_STRIPPED);
    }
}