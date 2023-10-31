package volbot.beetlebox.client.render.item;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public class JarModels {

	public static ModelPartData jar_base_mpd = new ModelData().getRoot()
			.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE)
			.addChild("cube",
					ModelPartBuilder.create().cuboid("cube", -6.25F, -10.001F, 0.75F, 4.5F, 6.0F, 4.5F).uv(0, 0)
							.cuboid("cube", -5.75F, -10.5001F, 1.25F, 3.5F, 0.5F, 3.5F).uv(0, 0)
							.cuboid("cube", -6.0F, -11.0F, 1.0F, 4.0F, 0.5F, 4.0F).uv(0, 0),
					ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
	public static ModelPart jar_base = jar_base_mpd.createPart(16, 16);

	public static ModelPartData cork_mpd = new ModelData().getRoot()
			.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE)
			.addChild("cube", ModelPartBuilder.create().cuboid("cube", -5.5F, -11.75F, 1.5F, 3.0F, 1.8F, 3.0F).uv(0, 0),
					ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
	public static ModelPart cork = cork_mpd.createPart(10, 10);

	public static ModelPartData substrate_mpd = new ModelData().getRoot()
			.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE)
			.addChild("cube",
					ModelPartBuilder.create().cuboid("cube", -6.25F, -10.001F, 0.75F, 4.5F, 6.0F, 4.5F).uv(0, 0),
					ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
	public static ModelPart substrate = substrate_mpd.createPart(16, 16);

}
