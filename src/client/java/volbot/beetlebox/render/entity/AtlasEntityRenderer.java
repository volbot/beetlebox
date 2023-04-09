package volbot.beetlebox.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.AtlasEntity;

public class AtlasEntityRenderer extends MobEntityRenderer<AtlasEntity, AtlasEntityModel> {
	
	public AtlasEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AtlasEntityModel(context.getPart(BeetleBoxClient.MODEL_ATLAS_LAYER)), 0.27f);
	} 
	
    @Override
    public Identifier getTexture(AtlasEntity entity) {
        return new Identifier("beetlebox", "textures/entity/beetle/atlas.png");
    }
}