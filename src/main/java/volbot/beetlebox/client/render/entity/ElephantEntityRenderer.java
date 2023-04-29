package volbot.beetlebox.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import volbot.beetlebox.client.BeetleBoxClient;
import volbot.beetlebox.entity.beetle.ElephantEntity;

@Environment(EnvType.CLIENT)
public class ElephantEntityRenderer extends MobEntityRenderer<ElephantEntity, ElephantEntityModel> {
	
	public ElephantEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ElephantEntityModel(context.getPart(BeetleBoxClient.MODEL_ELEPHANT_LAYER)), 0.27f);
	}
	
    @Override
    public Identifier getTexture(ElephantEntity entity) {
    	this.shadowRadius = 0.27f*(entity.getSize()/10f);
        return new Identifier("beetlebox", "textures/entity/beetle/elephant.png");
    }
}