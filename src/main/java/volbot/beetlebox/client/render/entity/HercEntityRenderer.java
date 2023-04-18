package volbot.beetlebox.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.HercEntity;

public class HercEntityRenderer extends MobEntityRenderer<HercEntity, HercEntityModel> {
	
	public HercEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HercEntityModel(context.getPart(BeetleBoxClient.MODEL_HERC_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(HercEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/herc.png");
    }
}