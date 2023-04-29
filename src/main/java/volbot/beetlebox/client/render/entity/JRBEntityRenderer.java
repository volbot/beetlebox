package volbot.beetlebox.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.JRBEntity;

public class JRBEntityRenderer extends MobEntityRenderer<JRBEntity, JRBEntityModel> {
	
	public JRBEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new JRBEntityModel(context.getPart(BeetleBoxClient.MODEL_JRB_LAYER)), 0.27f);
	}
	
    @Override
    public Identifier getTexture(JRBEntity entity) {
    	this.shadowRadius = 0.27f*(entity.getSize()/10f);
        return new Identifier("beetlebox", "textures/entity/beetle/jrb.png");
    }
}