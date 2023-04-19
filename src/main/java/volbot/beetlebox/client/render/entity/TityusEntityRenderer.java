package volbot.beetlebox.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.TitanEntity;
import volbot.beetlebox.entity.beetle.TityusEntity;

public class TityusEntityRenderer extends MobEntityRenderer<TityusEntity, TityusEntityModel> {
	
	public TityusEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TityusEntityModel(context.getPart(BeetleBoxClient.MODEL_TITYUS_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(TityusEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/tityus.png");
    }
}