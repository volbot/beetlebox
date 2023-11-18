package volbot.beetlebox.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleProjectileEntity extends PersistentProjectileEntity implements FlyingItemEntity {

	public BeetleProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public BeetleProjectileEntity(World world) {
		super(BeetleRegistry.BEETLE_PROJECTILE, world);
	}
	
    public BeetleProjectileEntity(World world, double x, double y, double z) {
        super(BeetleRegistry.BEETLE_PROJECTILE, x, y, z, world);
    }

    public BeetleProjectileEntity(World world, LivingEntity owner) {
        super(BeetleRegistry.BEETLE_PROJECTILE, owner, world);
    }

	@Override
	protected ItemStack asItemStack() {
		return ItemRegistry.BEETLE_JAR.getDefaultStack();
	}
	
	@Override
    public ItemStack getStack() {
    	return asItemStack();
    }

}
