package volbot.beetlebox.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.TitanEntity;

public class TitanEntityRenderer extends MobEntityRenderer<TitanEntity, TitanEntityModel> {
	
	public TitanEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TitanEntityModel(context.getPart(BeetleBoxClient.MODEL_TITAN_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(TitanEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/titan.png");
    }
}