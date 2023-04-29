package volbot.beetlebox.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.TitanEntity;

@Environment(EnvType.CLIENT)
public class TitanEntityRenderer extends MobEntityRenderer<TitanEntity, TitanEntityModel> {
	
	public TitanEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TitanEntityModel(context.getPart(BeetleBoxClient.MODEL_TITAN_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(TitanEntity entity) {
    	this.shadowRadius = 0.27f*(entity.getSize()/10f);
        return new Identifier("beetlebox", "textures/entity/beetle/titanus.png");
    }
}