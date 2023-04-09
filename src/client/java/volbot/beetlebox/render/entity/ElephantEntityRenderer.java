package volbot.beetlebox.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.JRBEntity;

public class ElephantEntityRenderer extends MobEntityRenderer<JRBEntity, JRBEntityModel> {
	
	public ElephantEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new JRBEntityModel(context.getPart(BeetleBoxClient.MODEL_JRB_LAYER)), 0.27f);
	}
	
    @Override
    public Identifier getTexture(JRBEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/jrb.png");
    }
}