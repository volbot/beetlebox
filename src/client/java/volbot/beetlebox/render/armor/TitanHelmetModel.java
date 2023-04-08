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

public class TitanHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T>{
	
	public TitanHelmetModel() {
		super(getTexturedModelData().createModel(), "titan");
	}

	public static TexturedModelData getTexturedModelData() {
	ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
	ModelPartData root = modelData.getRoot();
	Dilation d = new Dilation(0.25F);

	ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0)
			.cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F));

	ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(), ModelTransform.of(-0.5F, -12.2989F, -7.8422F, -0.48F, 0.0F, 0.0F));

	ModelPartData head2 = head.addChild("head2", ModelPartBuilder.create().uv(0, 16).cuboid(-3.5F, -2.5F, -1.5F, 7.0F, 4.0F, 9.0F), ModelTransform.of(0.0F, -9.3664F, -3.5989F, -0.3927F, 0.0F, 0.0F));

	ModelPartData horn3 = head2.addChild("horn3", ModelPartBuilder.create(), ModelTransform.of(-0.75F, 4.136F, -3.9113F, 0.48F, 0.0F, 1.6581F));

	ModelPartData cube_r1 = horn3.addChild("cube_r1", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-5.7338F, 0.2738F, -2.9338F, 2.0F, 2.0F, 4.0F).mirrored(false), ModelTransform.of(0.0F, 1.4974F, -1.8342F, -0.6545F, 0.0F, 0.0F));

	ModelPartData cube_r2 = horn3.addChild("cube_r2", ModelPartBuilder.create().uv(23, 16).mirrored().cuboid(-5.7338F, -0.4156F, -4.2675F, 2.0F, 2.0F, 7.0F).mirrored(false), ModelTransform.of(0.0F, 2.0405F, 1.648F, 0.0873F, 0.0F, 0.0F));

	ModelPartData horn2 = head2.addChild("horn2", ModelPartBuilder.create(), ModelTransform.of(0.75F, 4.136F, -3.9113F, 0.48F, 0.0F, -1.6581F));

	ModelPartData cube_r3 = horn2.addChild("cube_r3", ModelPartBuilder.create().uv(24, 0).cuboid(3.7338F, 0.2738F, -2.9338F, 2.0F, 2.0F, 4.0F), ModelTransform.of(0.0F, 1.4974F, -1.8342F, -0.6545F, 0.0F, 0.0F));

	ModelPartData cube_r4 = horn2.addChild("cube_r4", ModelPartBuilder.create().uv(23, 16).cuboid(3.7338F, -0.4156F, -4.2675F, 2.0F, 2.0F, 7.0F), ModelTransform.of(0.0F, 2.0405F, 1.648F, 0.0873F, 0.0F, 0.0F));
	
    return TexturedModelData.of(modelData, 64, 64);
	}
}
