package volbot.beetlebox.entity.beetle;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import volbot.beetlebox.registry.BeetleRegistry;

public class TityusEntity extends BeetleEntity {

	public TityusEntity(EntityType<? extends BeetleEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
        return BeetleRegistry.TITYUS.create(this.getEntityWorld());
	}

}
