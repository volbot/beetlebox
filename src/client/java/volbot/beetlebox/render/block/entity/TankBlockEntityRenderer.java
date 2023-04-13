package volbot.beetlebox.render.block.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.client.render.block.entity.MobSpawnerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.entity.tile.TankBlockEntity;

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
		//System.out.println(tile_entity.contained_id);
		if(tile_entity.contained_id != "") {
			// Calculate the current offset in the y value
	        double offset = Math.sin((tile_entity.getWorld().getTime() + f) / 8.0) / 8.0;
	        // Move the item
	        matrices.translate(0.5, 0.25 + offset, 0.5);
	        matrices.scale(0.6f, 0.6f, 0.6f);
	 
	        // Rotate the item
	        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((tile_entity.getWorld().getTime() + f) * 4));
	        
	        EntityType<?> entityType2 = EntityType.get(tile_entity.contained_id).orElse(null);
	        if(entityType2 != null) {
		        Entity temp = entityType2.create(tile_entity.getWorld());
	            this.entityRenderDispatcher.render(temp, 0.0, 0.0, 0.0, 0.0f, f, matrices, vertex_consumer, i);
	        }
		}
        matrices.pop();
	}
	
	

}
