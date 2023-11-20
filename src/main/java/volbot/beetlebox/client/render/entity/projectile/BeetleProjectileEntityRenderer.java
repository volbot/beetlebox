package volbot.beetlebox.client.render.entity.projectile;

import java.util.Optional;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3i;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.projectile.BeetleProjectileEntity;

public class BeetleProjectileEntityRenderer<T extends BeetleProjectileEntity> extends EntityRenderer<T> {

	public BeetleProjectileEntityRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
		super(ctx);
	}

	public BeetleProjectileEntityRenderer(EntityRendererFactory.Context context) {
		this(context, 1.0f, false);
	}

	@Override
	public void render(T entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		if (((Entity) entity).age < 2
				&& this.dispatcher.camera.getFocusedEntity().squaredDistanceTo((Entity) entity) < 12.25) {
			return;
		}
		matrices.push();
		matrices.multiply(this.dispatcher.getRotation());
		//matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
		// System.out.println("looking for entity");
		if (entity.entity != null) {
			EntityType<?> entityType2 = EntityType.get(entity.entity.contained_id).orElse(null);
			if (entityType2 == null) {
				matrices.pop();
				super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
				return;
			}
			//System.out.println("entity found");

			LivingEntity temp = (LivingEntity) entityType2.create(entity.getWorld());
			temp.readCustomDataFromNbt(entity.entity.entity_data);
			if (temp instanceof BeetleEntity) {
				((BeetleEntity) temp).setSize(5);
			}
			if (!entity.entity.custom_name.isEmpty()) {
				entity.setCustomName(Text.of(entity.entity.custom_name));
			}

			MinecraftClient.getInstance().getEntityRenderDispatcher().render(temp, 0,0,0, yaw, tickDelta, matrices,
					vertexConsumers, light);
		}
		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(T e) {
		return null;
	}
}
