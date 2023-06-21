package volbot.beetlebox.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.ActaeonEntity;

@Environment(EnvType.CLIENT)
public class ActaeonEntityRenderer extends MobEntityRenderer<ActaeonEntity, ActaeonEntityModel> {
	
	public ActaeonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ActaeonEntityModel(context.getPart(BeetleBoxClient.MODEL_ACTAEON_LAYER)), 0.27f);
	}
	
    @Override
    public Identifier getTexture(ActaeonEntity entity) {
    	this.shadowRadius = 0.27f*(entity.getSize()/10f);
        return new Identifier("beetlebox", "textures/entity/beetle/actaeon.png");
    }
}