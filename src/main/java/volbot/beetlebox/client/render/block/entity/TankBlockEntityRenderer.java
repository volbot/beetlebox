package volbot.beetlebox.client.render.block.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3f;
import volbot.beetlebox.entity.block.TankBlockEntity;

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
		if(tile_entity.contained_id != "") {
	        EntityType<?> entityType2 = EntityType.get(tile_entity.contained_id).orElse(null);
	        if(entityType2 != null) {
	        	
		        LivingEntity entity = (LivingEntity) entityType2.create(tile_entity.getWorld());
		        entity.readCustomDataFromNbt(tile_entity.entity_data);
		        if(!tile_entity.custom_name.isEmpty()) {
		        	entity.setCustomName(Text.of(tile_entity.custom_name));
		        }
				float g = 0.53125f;
	            float h = Math.max(entity.getWidth(), entity.getHeight());
	            if ((double)h > 1.0) {
	                g /= h;
	            }
		        matrices.translate(0.5, (1-(g*entity.getHeight()))/2, 0.5);
	            matrices.scale(g, g, g);
		        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((tile_entity.getWorld().getTime() + f) * 4));

	            this.entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, f, matrices, vertex_consumer, i);
	        }
		}
        matrices.pop();
	}
	
	

}
