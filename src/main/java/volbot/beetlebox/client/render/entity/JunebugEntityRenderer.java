package volbot.beetlebox.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.JunebugEntity;

public class JunebugEntityRenderer extends MobEntityRenderer<JunebugEntity, JunebugEntityModel> {
	
	public JunebugEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new JunebugEntityModel(context.getPart(BeetleBoxClient.MODEL_JUNEBUG_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(JunebugEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/junebug.png");
    }
}