package volbot.beetlebox.render.armor;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

public class JRBHelmetModel<T extends LivingEntity> extends ArmorEntityModel<T>{
	
	public JRBHelmetModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static TexturedModelData getTexturedModelData() {
	ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
	ModelPartData root = modelData.getRoot();
	Dilation d = new Dilation(0.25F);

	ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0)
			.cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F));

	ModelPartData rightHorn = head.addChild("right_horn_1",
			ModelPartBuilder.create()
					.uv(24, 0)
					.cuboid(-5.5F, -1.5F, -1.5F, 5, 3, 3, d),
			ModelTransform.of(-4.0F, -6.5F, 0.0F,
					0.0F, -15F / (180F / (float) Math.PI), 10F / (180F / (float) Math.PI)));

	rightHorn.addChild("right_horn_2",
			ModelPartBuilder.create()
					.uv(54, 16)
					.cuboid(-3.5F, -1.0F, -1.0F, 3, 2, 2, d),
			ModelTransform.of(-4.5F, 0.0F, 0.0F,
					0.0F, 0.0F, 10F / (180F / (float) Math.PI)));

	ModelPartData leftHorn = head.addChild("left_horn_1",
			ModelPartBuilder.create()
					.uv(24, 0).mirrored()
					.cuboid(0.5F, -1.5F, -1.5F, 5, 3, 3, d),
			ModelTransform.of(4.0F, -6.5F, 0.0F,
					0.0F, 15F / (180F / (float) Math.PI), -10F / (180F / (float) Math.PI)));

	leftHorn.addChild("left_horn_2",
			ModelPartBuilder.create()
					.uv(54, 16)
					.cuboid(0.5F, -1.0F, -1.0F, 3, 2, 2, d),
			ModelTransform.of(4.5F, 0.0F, 0.0F,
					0.0F, 0.0F, -10F / (180F / (float) Math.PI)));
	
    return TexturedModelData.of(modelData, 32, 32);
	}
}
