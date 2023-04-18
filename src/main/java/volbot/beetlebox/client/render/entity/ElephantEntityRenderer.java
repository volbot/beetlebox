package volbot.beetlebox.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.ElephantEntity;

public class ElephantEntityRenderer extends MobEntityRenderer<ElephantEntity, ElephantEntityModel> {
	
	public ElephantEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ElephantEntityModel(context.getPart(BeetleBoxClient.MODEL_ELEPHANT_LAYER)), 0.27f);
	}
	
    @Override
    public Identifier getTexture(ElephantEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/elephant.png");
    }
}