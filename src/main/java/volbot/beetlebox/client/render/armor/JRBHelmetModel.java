package volbot.beetlebox.client.render.armor;

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
public class JRBHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T> {

	public JRBHelmetModel() {
		super(getTexturedModelData().createModel(), "jrb");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
		ModelPartData root = modelData.getRoot();

		Dilation d2 = new Dilation(1.0F);
		Dilation d = new Dilation(0.25F);

		ModelPartData head = root.addChild("head",
				ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d2),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData adorn = head.addChild("adorn", ModelPartBuilder.create(),
				ModelTransform.pivot(0.0364F, 24.0219F, 0.4165F));

		adorn.addChild("cube_r1",
				ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -29.5F, -6.25F, 1.0F, 1.0F, 1.0F, d).uv(0, 0)
						.cuboid(1.0F, -29.5F, -6.25F, 1.0F, 1.0F, 1.0F, d),
				ModelTransform.of(0.0F, -5.6522F, -4.9504F, -0.2618F, 0.0F, 0.0F));

		adorn.addChild("cube_r2", ModelPartBuilder.create().uv(14, 19).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 6.0F, d),
				ModelTransform.of(0.0F, -33.2068F, -1.4043F, -0.5236F, 0.0F, 0.0F));

		ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(),
				ModelTransform.of(0.0364F, -12.1392F, -6.561F, -1.1345F, 0.0F, 0.0F));

		horn.addChild("horn_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, -1.0F, -3.5F, 3.0F, 2.0F, 7.0F, d),
				ModelTransform.of(0.0053F, 0.0124F, 2.2155F, 0.2618F, 0.0F, 0.0F));

		horn.addChild("d1_r1", ModelPartBuilder.create().uv(24, 0).cuboid(-3.25F, -0.5F, -2.5F, 2.0F, 2.0F, 3.0F, d),
				ModelTransform.of(-0.7349F, 0.15F, -1.3454F, 0.0F, 1.1345F, 0.0F));

		horn.addChild("d2_r1", ModelPartBuilder.create().uv(0, 25).mirrored()
				.cuboid(-0.5F, -0.5F, -2.0F, 2.0F, 2.0F, 3.0F, d).mirrored(false),
				ModelTransform.of(0.662F, 0.15F, -0.8454F, 0.0F, -0.3491F, 0.0F));

		horn.addChild("d1_r2", ModelPartBuilder.create().uv(24, 0).mirrored()
				.cuboid(1.25F, -0.5F, -2.5F, 2.0F, 2.0F, 3.0F, d).mirrored(false),
				ModelTransform.of(0.662F, 0.15F, -1.3454F, 0.0F, -1.1345F, 0.0F));

		horn.addChild("d2_r2", ModelPartBuilder.create().uv(0, 25).cuboid(-1.5F, -0.5F, -2.0F, 2.0F, 2.0F, 3.0F, d),
				ModelTransform.of(-0.7349F, 0.15F, -0.8454F, 0.0F, 0.3491F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}
}
