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
public class StandardHelmetModel<T extends LivingEntity> extends BeetleArmorEntityModel<T> {

	public StandardHelmetModel(String texture_id) {
		super(getTexturedModelData().createModel(), texture_id);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = PlayerEntityModel.getModelData(new Dilation(1F), 0);
		ModelPartData root = modelData.getRoot();

		Dilation d2 = new Dilation(1.0F);

		@SuppressWarnings("unused")
		ModelPartData head = root.addChild("head",
				ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, d2),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}
}
