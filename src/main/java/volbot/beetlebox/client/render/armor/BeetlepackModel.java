package volbot.beetlebox.client.render.armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import volbot.beetlebox.client.render.armor.beetle.BeetleArmorEntityModel;

@Environment(EnvType.CLIENT)
public class BeetlepackModel<T extends LivingEntity> extends BeetleArmorEntityModel<T> {

	public BeetlepackModel() {
		super(getTexturedModelData().createModel(), "beetlepack");
	}

	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
		ModelPartData root = modelData.getRoot();

		ModelPartData body = root.addChild(EntityModelPartNames.BODY,
				ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0f)),
				ModelTransform.pivot(0.0f, 0.0f + 0f, 0.0f));

		ModelPartData backpack_left = body.addChild("backpack_left", ModelPartBuilder.create(), ModelTransform.pivot(2.4688F, 4.6028F, 2.8758F));

		ModelPartData body1 = backpack_left.addChild("body1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body1.addChild("cube_r1", ModelPartBuilder.create().uv(15, 25).cuboid(-1.75F, -0.5F, -1.9F, 3.5F, 1.0F, 3.8F, new Dilation(0.0F)), ModelTransform.of(-1.0049F, -3.9436F, 0.1802F, -0.4876F, 0.351F, -0.1141F));

		body1.addChild("cube_r2", ModelPartBuilder.create().uv(29, 12).cuboid(-1.25F, -1.0F, -2.0F, 2.5F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0881F, 5.0849F, -0.2527F, 0.2182F, 1.309F, 0.0F));

		body1.addChild("cube_r3", ModelPartBuilder.create().uv(28, 27).cuboid(-1.25F, -1.0F, -2.0F, 2.5F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.5062F, 4.2192F, -0.1407F, -0.2182F, 1.309F, 0.0F));

		body1.addChild("cube_r4", ModelPartBuilder.create().uv(0, 25).cuboid(-1.75F, -3.5F, -1.5F, 3.5F, 7.5F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.3651F, 0.0094F, 0.687F, 0.036F, 0.351F, -0.1141F));

		body1.addChild("cube_r5", ModelPartBuilder.create().uv(17, 17).cuboid(-1.75F, -1.0F, -2.5F, 3.5F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-0.7363F, 4.6904F, -0.3364F, 0.2433F, 0.4019F, 0.21F));

		body1.addChild("cube_r6", ModelPartBuilder.create().uv(39, 25).cuboid(-1.25F, 0.0F, -1.5F, 2.5F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0654F, -4.9471F, -0.8298F, 1.3963F, 1.309F, 0.0F));

		body1.addChild("cube_r7", ModelPartBuilder.create().uv(25, 0).cuboid(-0.5F, -4.5F, -1.5F, 2.5F, 7.5F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.5925F, 0.8972F, 0.6589F, 0.1309F, 1.309F, 0.0F));

		ModelPartData slots1 = backpack_left.addChild("slots1", ModelPartBuilder.create(), ModelTransform.pivot(1.3544F, 0.3972F, 1.4119F));

		slots1.addChild("cube_r8", ModelPartBuilder.create().uv(0, 37).cuboid(-1.25F, -1.15F, -1.25F, 2.5F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(0.0566F, 2.2753F, 0.0566F, 0.3927F, 0.7854F, 0.0F));

		slots1.addChild("cube_r9", ModelPartBuilder.create().uv(0, 37).cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(-0.2861F, -0.4917F, -0.2861F, 0.3927F, 0.7854F, 0.0F));

		slots1.addChild("cube_r10", ModelPartBuilder.create().uv(0, 37).cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(-0.7145F, -3.2494F, -0.7145F, 0.3927F, 0.7854F, 0.0F));

		ModelPartData backpack_right = body.addChild("backpack_right", ModelPartBuilder.create(), ModelTransform.pivot(-2.4688F, 4.6028F, 2.8758F));

		ModelPartData body2 = backpack_right.addChild("body2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body2.addChild("cube_r11", ModelPartBuilder.create().uv(28, 27).mirrored().cuboid(-1.25F, -1.0F, -2.0F, 2.5F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5062F, 4.2192F, -0.1407F, -0.2182F, -1.309F, 0.0F));

		body2.addChild("cube_r12", ModelPartBuilder.create().uv(0, 25).mirrored().cuboid(-1.75F, -3.5F, -1.5F, 3.5F, 7.5F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.3651F, 0.0094F, 0.687F, 0.036F, -0.351F, 0.1141F));

		body2.addChild("cube_r13", ModelPartBuilder.create().uv(39, 25).mirrored().cuboid(-1.25F, 0.0F, -1.5F, 2.5F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0654F, -4.9471F, -0.8298F, 1.3963F, -1.309F, 0.0F));

		body2.addChild("cube_r14", ModelPartBuilder.create().uv(29, 12).mirrored().cuboid(-1.25F, -1.0F, -2.0F, 2.5F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.0881F, 5.0849F, -0.2527F, 0.2182F, -1.309F, 0.0F));

		body2.addChild("cube_r15", ModelPartBuilder.create().uv(17, 17).mirrored().cuboid(-1.75F, -1.0F, -2.5F, 3.5F, 2.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.7363F, 4.6904F, -0.3364F, 0.2433F, -0.4019F, -0.21F));

		body2.addChild("cube_r16", ModelPartBuilder.create().uv(25, 0).mirrored().cuboid(-2.0F, -4.5F, -1.5F, 2.5F, 7.5F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5925F, 0.8972F, 0.6589F, 0.1309F, -1.309F, 0.0F));

		ModelPartData slots2 = backpack_right.addChild("slots2", ModelPartBuilder.create(), ModelTransform.pivot(-1.3544F, 0.3972F, 1.4119F));

		slots2.addChild("cube_r17", ModelPartBuilder.create().uv(0, 37).mirrored().cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.2861F, -0.4917F, -0.2861F, 0.3927F, -0.7854F, 0.0F));

		slots2.addChild("cube_r18", ModelPartBuilder.create().uv(0, 37).mirrored().cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.7145F, -3.2494F, -0.7145F, 0.3927F, -0.7854F, 0.0F));

		slots2.addChild("cube_r19", ModelPartBuilder.create().uv(0, 37).mirrored().cuboid(-1.25F, -1.15F, -1.25F, 2.5F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.0566F, 2.2753F, 0.0566F, 0.3927F, -0.7854F, 0.0F));

		ModelPartData body3 = backpack_right.addChild("body3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body3.addChild("cube_r20", ModelPartBuilder.create().uv(15, 25).mirrored().cuboid(-1.75F, -0.5F, -1.9F, 3.5F, 1.0F, 3.8F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0049F, -3.9436F, 0.1802F, -0.4876F, -0.351F, 0.1141F));

		ModelPartData backpackcenter = body.addChild("backpackcenter", ModelPartBuilder.create().uv(37, 34).cuboid(-1.0F, -2.0F, -1.0F, 3.2F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.6038F, 11.1905F, 3.1076F));

		backpackcenter.addChild("cube_r21", ModelPartBuilder.create().uv(26, 34).cuboid(-2.2F, -1.0F, -1.25F, 3.2F, 2.0F, 2.25F, new Dilation(0.0F)), ModelTransform.of(1.2F, -1.591F, 1.1768F, 0.7854F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r22", ModelPartBuilder.create().uv(38, 0).cuboid(-2.2F, -1.0F, -1.0F, 3.2F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.2F, -2.6743F, 1.682F, -0.0873F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r23", ModelPartBuilder.create().uv(34, 19).cuboid(-1.45F, -1.0F, -1.5F, 2.9F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.6352F, -10.8373F, -1.3626F, -0.2182F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r24", ModelPartBuilder.create().uv(38, 5).cuboid(-1.45F, -1.25F, -1.0F, 2.9F, 2.5F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.6352F, -9.9482F, 0.7526F, 0.9495F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r25", ModelPartBuilder.create().uv(15, 31).cuboid(-1.45F, -3.75F, -1.0F, 2.9F, 6.8F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.6352F, -6.2378F, 1.5421F, 0.0506F, 0.0F, 0.0F));

		ModelPartData backpackcenter2 = body.addChild("backpackcenter2", ModelPartBuilder.create(), ModelTransform.pivot(0.6038F, 11.1905F, 3.1076F));

		backpackcenter2.addChild("cube_r26", ModelPartBuilder.create().uv(34, 19).mirrored().cuboid(-1.45F, -1.0F, -1.5F, 2.9F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.6352F, -10.8373F, -1.3626F, -0.2182F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}