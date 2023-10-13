package volbot.beetlebox.entity.beetle;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import volbot.beetlebox.registry.BeetleRegistry;

public class ActaeonEntity extends BeetleEntity {

	public ActaeonEntity(EntityType<? extends BeetleEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
		BeetleEntity e = BeetleRegistry.ACTAEON.create(this.getEntityWorld());
		e.generateGeneticStats(this, (BeetleEntity)other);
        return e;
	}
}
