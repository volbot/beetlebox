package volbot.beetlebox.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.HercEntity;

@Environment(EnvType.CLIENT)
public class HercEntityRenderer extends MobEntityRenderer<HercEntity, HercEntityModel> {
	
	public HercEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HercEntityModel(context.getPart(BeetleBoxClient.MODEL_HERC_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(HercEntity entity) {
    	this.shadowRadius = 0.27f*(entity.getSize()/10f);
        return new Identifier("beetlebox", "textures/entity/beetle/hercules.png");
    }
}