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
				ModelPartBuilder.create().uv(48, 48).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0f)),
				ModelTransform.pivot(0.0f, 0.0f + 0f, 0.0f));

		ModelPartData backpackcenter = body.addChild("backpackcenter", ModelPartBuilder.create().uv(26, 5)
				.cuboid(-0.2166F, 4.5945F, -3.3937F, 1.0F, 1.8F, 2.65F, new Dilation(0.0F)),
				ModelTransform.pivot(-0.2834F, 4.3162F, 3.8234F));

		backpackcenter
				.addChild(
						"cube_r1", ModelPartBuilder.create().uv(32, 11).cuboid(-1.0687F, -5.4559F, -1.4776F, 2.2F, 1.5F,
								1.5F, new Dilation(0.0F)),
						ModelTransform.of(0.252F, -5.4125F, 2.3037F, 1.6389F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r2",
				ModelPartBuilder.create().uv(7, 27).cuboid(0.0F, -0.8F, -1.65F, 1.0F, 1.8F, 2.65F, new Dilation(0.0F)),
				ModelTransform.of(-0.2166F, 5.0015F, 0.5912F, 0.2618F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r3",
				ModelPartBuilder.create().uv(7, 33).cuboid(0.0F, -0.8F, -0.25F, 1.0F, 1.8F, 1.25F, new Dilation(0.0F)),
				ModelTransform.of(-0.2166F, 5.0251F, 1.0444F, 1.0908F, 0.0F, 0.0F));

		backpackcenter
				.addChild(
						"cube_r4", ModelPartBuilder.create().uv(13, 27).cuboid(-0.5687F, -0.7559F, -1.1776F, 1.2F, 1.0F,
								1.4F, new Dilation(0.0F)),
						ModelTransform.of(0.252F, -3.1356F, 1.9017F, 1.2287F, 0.0F, 0.0F));

		backpackcenter
				.addChild(
						"cube_r5", ModelPartBuilder.create().uv(30, 32).cuboid(-0.4962F, -0.0312F, -1.8206F, 1.0F, 1.0F,
								1.8F, new Dilation(0.0F)),
						ModelTransform.of(0.2796F, 3.6309F, 2.3968F, 0.0175F, 0.0F, 0.0F));

		backpackcenter
				.addChild("cube_r6",
						ModelPartBuilder.create().uv(16, 29).cuboid(-0.5313F, -4.0559F, -1.7776F, 1.0F, 4.1F, 1.8F,
								new Dilation(0.0F)),
						ModelTransform.of(0.3147F, -3.1737F, 1.8885F, 1.0629F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r7", ModelPartBuilder.create().uv(33, 3).cuboid(-0.7313F,
				-3.65F, 0.3F, 1.2F, 0.9F, 1.5F, new Dilation(0.0F)),
				ModelTransform.of(0.4147F, 0.5184F, 0.7023F, 0.0796F, 0.0F, 0.0F));

		backpackcenter.addChild("cube_r8",
				ModelPartBuilder.create().uv(0, 0)
						.cuboid(-0.2313F, -3.45F, 0.95F, 0.4F, 1.0F, 0.75F, new Dilation(0.0F)).uv(0, 25)
						.cuboid(-0.5313F, -3.45F, -0.35F, 1.0F, 6.5F, 1.75F, new Dilation(0.0F)),
				ModelTransform.of(0.3147F, 0.6856F, 0.7408F, 0.0796F, 0.0F, 0.0F));

		ModelPartData backpack_left = body.addChild("backpack_left", ModelPartBuilder.create(),
				ModelTransform.pivot(2.4688F, 4.6028F, 2.8758F));

		backpack_left
				.addChild("cube_r9",
						ModelPartBuilder.create().uv(35, 0).cuboid(1.5F, -0.2345F, -0.2488F, 0.5F, 0.8F, 1.45F,
								new Dilation(0.0F)),
						ModelTransform.of(-0.2264F, 5.085F, 2.2136F, 0.8774F, 0.8435F, -0.3546F));

		backpack_left
				.addChild("cube_r10",
						ModelPartBuilder.create().uv(30, 27).cuboid(0.9313F, -5.4559F, -1.4776F, 2.2F, 1.5F, 1.5F,
								new Dilation(0.0F)),
						ModelTransform.of(-2.1866F, -6.1667F, 3.2194F, 1.6298F, -0.034F, 0.5226F));

		backpack_left
				.addChild("cube_r11",
						ModelPartBuilder.create().uv(22, 35).cuboid(-0.8F, -1.4F, -0.275F, 1.6F, 1.8F, 0.55F,
								new Dilation(0.0F)),
						ModelTransform.of(0.4651F, 3.6508F, 1.8419F, 1.405F, 0.402F, 0.0F));

		backpack_left
				.addChild("cube_r12",
						ModelPartBuilder.create().uv(20, 25).cuboid(-1.5F, -1.2345F, -0.2488F, 3.6F, 1.8F, 1.35F,
								new Dilation(0.0F)),
						ModelTransform.of(-0.9899F, 4.7037F, 1.7423F, 1.0908F, 0.402F, 0.0F));

		backpack_left
				.addChild("cube_r13",
						ModelPartBuilder.create().uv(11, 0).cuboid(-1.5F, -0.9F, -1.625F, 3.6F, 1.3F, 2.25F,
								new Dilation(0.0F)),
						ModelTransform.of(-1.0897F, 4.8132F, 1.5075F, 0.0436F, 0.402F, 0.0F));

		backpack_left
				.addChild("cube_r14",
						ModelPartBuilder.create().uv(0, 0).cuboid(-1.7F, -1.0345F, -5.3012F, 3.2F, 1.6F, 4.05F,
								new Dilation(0.0F)),
						ModelTransform.of(2.724F, 4.6355F, -0.0854F, 0.1309F, 1.309F, 0.0F));

		backpack_left
				.addChild("cube_r15",
						ModelPartBuilder.create().uv(24, 0).cuboid(-1.7F, -1.2345F, -0.8488F, 3.2F, 1.8F, 1.85F,
								new Dilation(0.0F)),
						ModelTransform.of(1.4912F, 4.3454F, -0.4157F, 1.0908F, 1.309F, 0.0F));

		backpack_left.addChild("cube_r16",
				ModelPartBuilder.create().uv(17, 36).cuboid(-0.5F, -1.625F, 0.7F, 0.75F, 2.65F, 0.5F,
						new Dilation(0.0F)),
				ModelTransform.of(-1.1701F, -2.5494F, 2.1439F, 0.7985F, -1.0722F, 0.7177F));

		backpack_left.addChild("cube_r17",
				ModelPartBuilder.create().uv(35, 7).cuboid(-1.0F, -0.65F, -0.2F, 1.0F, 2.05F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(-0.9688F, 4.178F, 2.0476F, 0.3496F, 0.005F, 1.4532F));

		backpack_left
				.addChild("cube_r18",
						ModelPartBuilder.create().uv(12, 35).cuboid(-1.0F, -0.65F, -0.2F, 1.0F, 2.05F, 1.0F,
								new Dilation(0.0F)),
						ModelTransform.of(0.8086F, 3.4825F, 1.3767F, 0.3285F, 0.1221F, 1.1235F));

		backpack_left.addChild("cube_r19",
				ModelPartBuilder.create().uv(23, 12).cuboid(1.25F, -0.5F, -1.5F, 1.2F, 1.5F, 3.4F, new Dilation(0.0F)),
				ModelTransform.of(-0.6368F, -4.9307F, 0.5353F, -0.0643F, 0.5893F, 0.7661F));

		backpack_left.addChild("cube_r20",
				ModelPartBuilder.create().uv(10, 15).cuboid(-1.75F, -0.5F, -1.5F, 3.3F, 1.5F, 3.4F, new Dilation(0.0F)),
				ModelTransform.of(-1.0049F, -3.9436F, 0.1802F, -0.4876F, 0.351F, -0.1141F));

		backpack_left
				.addChild("cube_r21",
						ModelPartBuilder.create().uv(11, 21).cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F,
								new Dilation(0.0F)),
						ModelTransform.of(1.0684F, -0.0945F, 1.1258F, 0.3927F, 0.7854F, 0.0F));

		backpack_left
				.addChild("cube_r22",
						ModelPartBuilder.create().uv(11, 21).cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F,
								new Dilation(0.0F)),
						ModelTransform.of(0.6399F, -2.8522F, 0.6973F, 0.3927F, 0.7854F, 0.0F));

		backpack_left
				.addChild("cube_r23",
						ModelPartBuilder.create().uv(11, 21).cuboid(-1.25F, -1.15F, -1.25F, 2.5F, 2.5F, 2.5F,
								new Dilation(0.0F)),
						ModelTransform.of(1.411F, 2.6725F, 1.4685F, 0.3927F, 0.7854F, 0.0F));

		backpack_left
				.addChild("cube_r24",
						ModelPartBuilder.create().uv(23, 29).cuboid(-0.5F, -1.425F, -1.2F, 0.75F, 2.65F, 2.4F,
								new Dilation(0.0F)),
						ModelTransform.of(-1.2184F, -3.0345F, 1.5868F, 0.3499F, -0.036F, 1.4383F));

		backpack_left.addChild("cube_r25",
				ModelPartBuilder.create().uv(13, 5).cuboid(-2.45F, -3.5F, -0.3F, 3.8F, 6.7F, 1.8F, new Dilation(0.0F)),
				ModelTransform.of(-0.3651F, 0.0094F, 0.687F, 0.036F, 0.351F, -0.1141F));

		backpack_left.addChild("cube_r26",
				ModelPartBuilder.create().uv(7, 25).cuboid(-1.65F, 0.5F, 0.4F, 0.4F, 2.7F, 1.1F, new Dilation(0.0F)),
				ModelTransform.of(-1.4846F, 0.1377F, 1.0996F, 0.036F, 0.351F, -0.1141F));

		backpack_left.addChild("cube_r27",
				ModelPartBuilder.create().uv(32, 21).cuboid(-0.25F, -1.5F, -0.5F, 1.5F, 1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.6767F, -3.3908F, 0.1425F, 1.2654F, 1.309F, 0.0F));

		backpack_left
				.addChild("cube_r28",
						ModelPartBuilder.create().uv(30, 16).cuboid(-0.25F, -0.95F, -1.4F, 2.0F, 1.7F, 1.8F,
								new Dilation(0.0F)),
						ModelTransform.of(0.5914F, -3.7459F, -0.398F, 1.0472F, 1.309F, 0.0F));

		backpack_left.addChild("cube_r29",
				ModelPartBuilder.create().uv(0, 7).cuboid(-1.25F, -3.75F, -1.4F, 3.5F, 7.5F, 1.8F, new Dilation(0.0F)),
				ModelTransform.of(1.3723F, 0.0528F, 0.3289F, 0.1309F, 1.309F, 0.0F));

		ModelPartData backpack_right = body.addChild("backpack_right", ModelPartBuilder.create(),
				ModelTransform.pivot(-2.4688F, 4.6028F, 2.8758F));

		backpack_right
				.addChild("cube_r30",
						ModelPartBuilder.create().uv(35, 0).cuboid(-2.0F, -0.2345F, -0.2488F, 0.5F, 0.8F, 1.45F,
								new Dilation(0.0F)),
						ModelTransform.of(0.2264F, 5.085F, 2.2136F, 0.8774F, -0.8435F, 0.3546F));

		backpack_right
				.addChild("cube_r31",
						ModelPartBuilder.create().uv(30, 27).cuboid(-3.1313F, -5.4559F, -1.4776F, 2.2F, 1.5F, 1.5F,
								new Dilation(0.0F)),
						ModelTransform.of(2.1866F, -6.1667F, 3.2194F, 1.6298F, 0.034F, -0.5226F));

		backpack_right
				.addChild("cube_r32",
						ModelPartBuilder.create().uv(22, 35).cuboid(-0.8F, -1.4F, -0.275F, 1.6F, 1.8F, 0.55F,
								new Dilation(0.0F)),
						ModelTransform.of(-0.4651F, 3.6508F, 1.8419F, 1.405F, -0.402F, 0.0F));

		backpack_right
				.addChild("cube_r33",
						ModelPartBuilder.create().uv(20, 25).cuboid(-2.1F, -1.2345F, -0.2488F, 3.6F, 1.8F, 1.35F,
								new Dilation(0.0F)),
						ModelTransform.of(0.9899F, 4.7037F, 1.7423F, 1.0908F, -0.402F, 0.0F));

		backpack_right
				.addChild("cube_r34",
						ModelPartBuilder.create().uv(11, 0).cuboid(-2.1F, -0.9F, -1.625F, 3.6F, 1.3F, 2.25F,
								new Dilation(0.0F)),
						ModelTransform.of(1.0897F, 4.8132F, 1.5075F, 0.0436F, -0.402F, 0.0F));

		backpack_right
				.addChild("cube_r35",
						ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.0345F, -5.3012F, 3.2F, 1.6F, 4.05F,
								new Dilation(0.0F)),
						ModelTransform.of(-2.724F, 4.6355F, -0.0854F, 0.1309F, -1.309F, 0.0F));

		backpack_right
				.addChild("cube_r36",
						ModelPartBuilder.create().uv(24, 0).cuboid(-1.5F, -1.2345F, -0.8488F, 3.2F, 1.8F, 1.85F,
								new Dilation(0.0F)),
						ModelTransform.of(-1.4912F, 4.3454F, -0.4157F, 1.0908F, -1.309F, 0.0F));

		backpack_right
				.addChild("cube_r37",
						ModelPartBuilder.create().uv(17, 36).cuboid(-0.25F, -1.625F, 0.7F, 0.75F, 2.65F, 0.5F,
								new Dilation(0.0F)),
						ModelTransform.of(1.1701F, -2.5494F, 2.1439F, 0.7985F, 1.0722F, -0.7177F));

		backpack_right.addChild("cube_r38",
				ModelPartBuilder.create().uv(35, 7).cuboid(0.0F, -0.65F, -0.2F, 1.0F, 2.05F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.9688F, 4.178F, 2.0476F, 0.3496F, -0.005F, -1.4532F));

		backpack_right.addChild("cube_r39",
				ModelPartBuilder.create().uv(12, 35).cuboid(0.0F, -0.65F, -0.2F, 1.0F, 2.05F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(-0.8086F, 3.4825F, 1.3767F, 0.3285F, -0.1221F, -1.1235F));

		backpack_right.addChild("cube_r40",
				ModelPartBuilder.create().uv(23, 12).cuboid(-2.45F, -0.5F, -1.5F, 1.2F, 1.5F, 3.4F, new Dilation(0.0F)),
				ModelTransform.of(0.6368F, -4.9307F, 0.5353F, -0.0643F, -0.5893F, -0.7661F));

		backpack_right.addChild("cube_r41",
				ModelPartBuilder.create().uv(10, 15).cuboid(-1.55F, -0.5F, -1.5F, 3.3F, 1.5F, 3.4F, new Dilation(0.0F)),
				ModelTransform.of(1.0049F, -3.9436F, 0.1802F, -0.4876F, -0.351F, 0.1141F));

		backpack_right
				.addChild("cube_r42",
						ModelPartBuilder.create().uv(11, 21).cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F,
								new Dilation(0.0F)),
						ModelTransform.of(-1.0684F, -0.0945F, 1.1258F, 0.3927F, -0.7854F, 0.0F));

		backpack_right
				.addChild("cube_r43",
						ModelPartBuilder.create().uv(11, 21).cuboid(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F,
								new Dilation(0.0F)),
						ModelTransform.of(-0.6399F, -2.8522F, 0.6973F, 0.3927F, -0.7854F, 0.0F));

		backpack_right
				.addChild("cube_r44",
						ModelPartBuilder.create().uv(11, 21).cuboid(-1.25F, -1.15F, -1.25F, 2.5F, 2.5F, 2.5F,
								new Dilation(0.0F)),
						ModelTransform.of(-1.411F, 2.6725F, 1.4685F, 0.3927F, -0.7854F, 0.0F));

		backpack_right
				.addChild("cube_r45",
						ModelPartBuilder.create().uv(23, 29).cuboid(-0.25F, -1.425F, -1.2F, 0.75F, 2.65F, 2.4F,
								new Dilation(0.0F)),
						ModelTransform.of(1.2184F, -3.0345F, 1.5868F, 0.3499F, 0.036F, -1.4383F));

		backpack_right.addChild("cube_r46",
				ModelPartBuilder.create().uv(13, 5).cuboid(-1.35F, -3.5F, -0.3F, 3.8F, 6.7F, 1.8F, new Dilation(0.0F)),
				ModelTransform.of(0.3651F, 0.0094F, 0.687F, 0.036F, -0.351F, 0.1141F));

		backpack_right.addChild("cube_r47",
				ModelPartBuilder.create().uv(7, 25).cuboid(1.25F, 0.5F, 0.4F, 0.4F, 2.7F, 1.1F, new Dilation(0.0F)),
				ModelTransform.of(1.4846F, 0.1377F, 1.0996F, 0.036F, -0.351F, 0.1141F));

		backpack_right.addChild("cube_r48",
				ModelPartBuilder.create().uv(32, 21).cuboid(-1.25F, -1.5F, -0.5F, 1.5F, 1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(-0.6767F, -3.3908F, 0.1425F, 1.2654F, -1.309F, 0.0F));

		backpack_right
				.addChild("cube_r49",
						ModelPartBuilder.create().uv(30, 16).cuboid(-1.75F, -0.95F, -1.4F, 2.0F, 1.7F, 1.8F,
								new Dilation(0.0F)),
						ModelTransform.of(-0.5914F, -3.7459F, -0.398F, 1.0472F, -1.309F, 0.0F));

		backpack_right.addChild("cube_r50",
				ModelPartBuilder.create().uv(0, 7).cuboid(-2.25F, -3.75F, -1.4F, 3.5F, 7.5F, 1.8F, new Dilation(0.0F)),
				ModelTransform.of(-1.3723F, 0.0528F, 0.3289F, 0.1309F, -1.309F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}