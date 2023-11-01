package volbot.beetlebox.client.render.armor.beetle;

import net.minecraft.client.model.ModelData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class ElephantHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T> {

	public ElephantHelmetModel() {
		super(getTexturedModelData().createModel(), "elephant");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
		ModelPartData root = modelData.getRoot();
		new Dilation(0.25F);
		Dilation d2 = new Dilation(1.0F);

		ModelPartData head = root.addChild("head",
				ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d2),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(),
				ModelTransform.of(-0.5F, -9.1F, -2.8191F, -0.829F, 0.0F, 0.0F));

		horn.addChild("cube_r1",
				ModelPartBuilder.create().uv(12, 17).cuboid(-0.5F, -1.5F, -2.0F, 2.0F, 2.0F, 3.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.0F, 1.7284F, -1.6932F, -0.5236F, 0.0F, 0.0F));

		horn.addChild("cube_r2",
				ModelPartBuilder.create().uv(20, 19).cuboid(-0.5F, -1.75F, -0.75F, 2.0F, 2.0F, 3.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.0F, 3.3552F, -1.2856F, 0.2182F, 0.0F, 0.0F));

		horn.addChild("cube_r3",
				ModelPartBuilder.create().uv(24, 0).mirrored()
						.cuboid(0.5F, -1.5F, 1.5F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false),
				ModelTransform.of(-5.5F, 3.0018F, -3.1564F, -0.4532F, 0.2297F, -1.125F));

		horn.addChild("cube_r4",
				ModelPartBuilder.create().uv(24, 0).cuboid(-2.5F, -1.5F, 1.5F, 2.0F, 2.0F, 3.0F,
						new Dilation(0.0F)),
				ModelTransform.of(6.5F, 3.0018F, -3.1564F, -0.4532F, -0.2297F, 1.125F));

		horn.addChild("cube_r5",
				ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -2.25F, -1.5F, 2.0F, 2.0F, 2.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.0F, 4.1139F, -2.3977F, 0.2182F, 0.0F, 0.0F));

		horn.addChild("cube_r6",
				ModelPartBuilder.create().uv(4, 20).mirrored()
						.cuboid(-1.5F, -2.25F, -3.5F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false),
				ModelTransform.of(1.0F, 3.681F, -7.3874F, -0.2817F, 0.6784F, -0.1797F));

		horn.addChild("cube_r7",
				ModelPartBuilder.create().uv(4, 20).cuboid(-0.5F, -2.25F, -3.5F, 2.0F, 2.0F, 3.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.0F, 3.681F, -7.3874F, -0.2817F, -0.6784F, 0.1797F));

		horn.addChild("cube_r8",
				ModelPartBuilder.create().uv(2, 18).cuboid(-0.5F, -2.25F, -1.5F, 2.0F, 2.0F, 5.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.0F, 3.681F, -7.3874F, -0.2182F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}
}
