package volbot.beetlebox.client.render.armor;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

public class HercHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T> {

	public HercHelmetModel() {
		super(getTexturedModelData().createModel(), "herc");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
		ModelPartData root = modelData.getRoot();

		new Dilation(1.0F);
		new Dilation(0.25F);

		ModelPartData Head = root.addChild("Head",
				ModelPartBuilder.create().uv(0, 0)
						.cuboid(-3.9636F, -7.9781F, -3.5835F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)).uv(0, 0)
						.cuboid(-3.9636F, -7.9781F, -3.5835F, 8.0F, 8.0F, 8.0F, new Dilation(1.5F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData horn = Head.addChild("horn", ModelPartBuilder.create(),
				ModelTransform.of(0.0F, -10.9388F, -7.0998F, -0.5672F, 0.0F, 0.0F));

		horn.addChild("cube_r1",
				ModelPartBuilder.create().uv(21, 21).cuboid(-1.5F, -1.5F, -0.75F, 2.0F, 2.0F, 3.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.5F, 1.8139F, -1.3194F, -0.6545F, 0.0F, 0.0F));

		horn.addChild("cube_r2",
				ModelPartBuilder.create().uv(18, 18).cuboid(-1.5F, -1.25F, -2.5F, 2.0F, 2.0F, 6.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.5F, 2.6153F, 2.5864F, 0.0873F, 0.0F, 0.0F));

		horn.addChild("cube_r3",
				ModelPartBuilder.create().uv(16, 16).cuboid(-1.5F, -1.25F, -3.25F, 2.0F, 2.0F, 8.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.5F, -3.1023F, -3.8409F, 0.2182F, 0.0F, 0.0F));

		horn.addChild("cube_r4",
				ModelPartBuilder.create().uv(2, 18).cuboid(-2.0F, -3.05F, -1.5F, 3.0F, 3.0F, 8.0F,
						new Dilation(0.0F)),
				ModelTransform.of(0.5F, -2.1283F, 0.5524F, -0.4363F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}
}
