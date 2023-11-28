package volbot.beetlebox.data.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.vanilla.VanillaDamageTypeTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.DamageTypeTags;
import volbot.beetlebox.data.damage.BeetleDamageTypes;

public class BeetleDamageTagGenerator extends VanillaDamageTypeTagProvider {

	public BeetleDamageTagGenerator(DataOutput output, CompletableFuture<WrapperLookup> future) {
		super(output, future);
	}
	
	@Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE).add(BeetleDamageTypes.BEETLE_PROJ);
    }
}

