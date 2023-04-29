package volbot.beetlebox.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.AtlasEntity;

public class AtlasEntityRenderer extends MobEntityRenderer<AtlasEntity, AtlasEntityModel> {
	
	public AtlasEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AtlasEntityModel(context.getPart(BeetleBoxClient.MODEL_ATLAS_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(AtlasEntity entity) {
    	this.shadowRadius = 0.27f*(entity.getSize()/10f);
        return new Identifier("beetlebox", "textures/entity/beetle/atlas.png");
    }
}