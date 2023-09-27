package volbot.beetlebox.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.entity.block.TankBlockEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;

@Environment(EnvType.CLIENT)
public class TankBlockEntityRenderer
implements BlockEntityRenderer<TankBlockEntity>{
	
    private final EntityRenderDispatcher entityRenderDispatcher;

    public TankBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();
    }

	@Override
	public void render(TankBlockEntity tile_entity, float f, MatrixStack matrices, VertexConsumerProvider vertex_consumer, int i,
			int j) {
		matrices.push();
		if(tile_entity.getContained(0) != null) {
			System.out.println(tile_entity.getContained(0).getContainedId());
			ContainedEntity e = tile_entity.getContained(0);
	        EntityType<?> entityType2 = EntityType.get(e.contained_id).orElse(null);
	        if(entityType2 != null) {
		        LivingEntity entity = (LivingEntity) entityType2.create(tile_entity.getWorld());
		        entity.readCustomDataFromNbt(e.entity_data);
		        if(!e.custom_name.isEmpty()) {
		        	entity.setCustomName(Text.of(e.custom_name));
		        }
				float g = 0.53125f;
	            float h = Math.max(entity.getWidth(), entity.getHeight());
	            if ((double)h > 1.0) {
	                g /= h;
	            }
		        matrices.translate(0.25, 0.2, 0.25);
	            matrices.scale(g, g, g);
		        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));

	            this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vertex_consumer, i);
	        }
		}
        matrices.pop();
		matrices.push();
		if(tile_entity.getContained(1) != null) {
			ContainedEntity e = tile_entity.getContained(1);
	        EntityType<?> entityType2 = EntityType.get(e.contained_id).orElse(null);
	        if(entityType2 != null) {
		        LivingEntity entity = (LivingEntity) entityType2.create(tile_entity.getWorld());
		        entity.readCustomDataFromNbt(e.entity_data);
		        if(!e.custom_name.isEmpty()) {
		        	entity.setCustomName(Text.of(e.custom_name));
		        }
				float g = 0.53125f;
	            float h = Math.max(entity.getWidth(), entity.getHeight());
	            if ((double)h > 1.0) {
	                g /= h;
	            }
		        matrices.translate(0.75, 0.2, 0.75);
	            matrices.scale(g, g, g);
		        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));

	            this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vertex_consumer, i);
	        }
		}
        matrices.pop();
	}
	
	

}
