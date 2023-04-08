package volbot.beetlebox.render.armor;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import volbot.beetlebox.item.equipment.materials.ChitinMaterial;

public class HercHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T>{
	
	public HercHelmetModel() {
		super(getTexturedModelData().createModel(), "herc");
	}

	public static TexturedModelData getTexturedModelData() {
	ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
	ModelPartData root = modelData.getRoot();
	Dilation d = new Dilation(0.25F);

	ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0)
			.cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F));

	ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(), ModelTransform.of(-0.5F, -12.2989F, -7.8422F, -0.48F, 0.0F, 0.0F));

	horn.addChild("cube_r1", ModelPartBuilder.create().uv(21, 21).cuboid(-1.5F, -1.0F, -1.75F, 2.0F, 2.0F, 3.0F, d), ModelTransform.of(0.5F, 2.7223F, -0.7036F, -0.6545F, 0.0F, 0.0F));

	horn.addChild("cube_r2", ModelPartBuilder.create().uv(18, 18).cuboid(-1.5F, -1.0F, -3.5F, 2.0F, 2.0F, 6.0F, d), ModelTransform.of(0.5F, 2.9753F, 3.0788F, 0.0873F, 0.0F, 0.0F));

	horn.addChild("cube_r3", ModelPartBuilder.create().uv(16, 16).cuboid(-1.0F, -1.25F, -10.25F, 2.0F, 2.0F, 8.0F, d), ModelTransform.of(0.0F, -3.8133F, 3.2304F, 0.2182F, 0.0F, 0.0F));

	horn.addChild("cube_r4", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -3.05F, -4.5F, 3.0F, 3.0F, 10.0F, d), ModelTransform.of(0.5F, -0.2682F, 3.5448F, -0.4363F, 0.0F, 0.0F));
	
    return TexturedModelData.of(modelData, 64, 64);
	}
}
