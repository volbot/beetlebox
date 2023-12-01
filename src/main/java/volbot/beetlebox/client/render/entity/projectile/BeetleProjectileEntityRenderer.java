package volbot.beetlebox.client.render.entity.projectile;

import org.joml.Quaternionf;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.projectile.BeetleProjectileEntity;

public class BeetleProjectileEntityRenderer<T extends BeetleProjectileEntity> extends EntityRenderer<T> {

	Quaternionf rot = new Quaternionf();

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
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
				MathHelper.lerp(tickDelta, ((PersistentProjectileEntity) entity).prevYaw, ((Entity) entity).getYaw())
						));//- 90.0f));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(
				MathHelper.lerp(tickDelta, ((PersistentProjectileEntity) entity).prevPitch, ((Entity) entity).getPitch())));
		if (entity.entity != null) {
			EntityType<?> entityType2 = EntityType.get(entity.entity.contained_id).orElse(null);
			if (entityType2 == null) {
				matrices.pop();
				super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
				return;
			}

			LivingEntity temp = (LivingEntity) entityType2.create(entity.getWorld());
			temp.readCustomDataFromNbt(entity.entity.entity_data);
			if (temp instanceof BeetleEntity) {
				((BeetleEntity) temp).setSize(5);
			}
			if (!entity.entity.custom_name.isEmpty()) {
				entity.setCustomName(Text.of(entity.entity.custom_name));
			}

			MinecraftClient.getInstance().getEntityRenderDispatcher().render(temp, 0, 0, 0, yaw, tickDelta, matrices,
					vertexConsumers, light);
		}
		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(T e) {
		return new Identifier("beetlebox", "textures/entity/beetle/actaeon.png");
	}
}
