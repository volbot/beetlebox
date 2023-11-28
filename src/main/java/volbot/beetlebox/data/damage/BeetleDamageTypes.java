package volbot.beetlebox.data.damage;

import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BeetleDamageTypes {
	
    public static final RegistryKey<DamageType> BEETLE_PROJ = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("beetlebox","beetle_proj"));
    
    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(BEETLE_PROJ, new DamageType("beetleProj", 0.1f, DamageEffects.HURT));
    }
    
    public static DamageSource of(World world, RegistryKey<DamageType> key) {
    	DamageSource source = new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    	return source;
    }
}
