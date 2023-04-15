package volbot.beetlebox.render.armor;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;

public class AtlasHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T>{
	
	public AtlasHelmetModel() {
		super(getTexturedModelData().createModel(), "atlas");
	}

	public static TexturedModelData getTexturedModelData() {
	ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
	ModelPartData root = modelData.getRoot();

	Dilation d = new Dilation(1.0F);
	ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0)
			.cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F));

	ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(), ModelTransform.of(0.5F, 32.0F, 0.0F, -0.48F, 0.0F, 0.0F));

	ModelPartData bot = horn.addChild("bot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.8236F, -22.5134F));

	bot.addChild("cube_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, -0.75F, -2.75F, 2.0F, 2.0F, 3.0F), ModelTransform.of(0.0F, -30.7419F, 1.662F, -0.3491F, 0.0F, 0.0F));

	bot.addChild("cube_r2", ModelPartBuilder.create().uv(14, 16).cuboid(-1.5F, 0.25F, -3.5F, 2.0F, 2.0F, 6.0F), ModelTransform.of(0.0F, -30.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

	bot.addChild("cube_r3", ModelPartBuilder.create().uv(24, 16).cuboid(-1.5F, 1.25F, -2.5F, 2.0F, 2.0F, 4.0F), ModelTransform.of(0.0F, -30.0474F, -1.2819F, -0.7854F, 0.0F, 0.0F));

	ModelPartData right = horn.addChild("right", ModelPartBuilder.create(), ModelTransform.of(-5.1988F, -37.9721F, -21.4396F, 0.1855F, 0.3435F, 2.0702F));

	right.addChild("cube_r4", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(0.1553F, -1.1631F, -0.8756F, 2.0F, 2.0F, 10.0F).mirrored(false), ModelTransform.of(0.0F, 0.5908F, 0.6731F, 0.4363F, 0.0F, 0.0F));

	right.addChild("cube_r5", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-27.534F, 8.858F, 3.7138F, 2.0F, 2.0F, 5.0F).mirrored(false), ModelTransform.of(27.6892F, -12.1995F, -0.0699F, -0.6545F, 0.0F, 0.0F));

	ModelPartData left = horn.addChild("left", ModelPartBuilder.create(), ModelTransform.of(4.1988F, -37.9721F, -21.4396F, 0.1855F, -0.3435F, -2.0702F));

	left.addChild("cube_r6", ModelPartBuilder.create().uv(0, 16).cuboid(-2.1553F, -1.1631F, -0.8756F, 2.0F, 2.0F, 10.0F), ModelTransform.of(0.0F, 0.5908F, 0.6731F, 0.4363F, 0.0F, 0.0F));

	left.addChild("cube_r7", ModelPartBuilder.create().uv(24, 0).cuboid(25.534F, 8.858F, 3.7138F, 2.0F, 2.0F, 5.0F), ModelTransform.of(-27.6892F, -12.1995F, -0.0699F, -0.6545F, 0.0F, 0.0F));
	
    return TexturedModelData.of(modelData, 64, 64);
	}
}
