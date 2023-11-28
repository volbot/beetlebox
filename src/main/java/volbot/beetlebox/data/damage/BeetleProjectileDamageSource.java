package volbot.beetlebox.data.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;

public class BeetleProjectileDamageSource extends DamageSource {

	public BeetleProjectileDamageSource(RegistryEntry<DamageType> type, Entity attacker) {
		super(type, attacker);
	}
	
	public BeetleProjectileDamageSource(RegistryEntry<DamageType> type, Entity attacker, Entity owner) {
		super(type, attacker, owner);
	}

}
