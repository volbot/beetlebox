package volbot.beetlebox.render.armor;

import net.minecraft.client.model.ModelPart;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.BeetleBoxClient;

public class JRBHelmetRenderer implements ArmorRenderer{
	
	private JRBHelmetModel<LivingEntity> armorModel;
	
	public JRBHelmetRenderer() {
		super();
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack,
		LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		if (armorModel == null) {
			ModelPart root = JRBHelmetModel.getTexturedModelData().createModel();
			armorModel = new JRBHelmetModel<LivingEntity>(root);
		}
		contextModel.copyBipedStateTo(armorModel);
		armorModel.setVisible(false);
		armorModel.head.visible = true;
		armorModel.hat.visible = true;
		ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, BeetleBoxClient.MODEL_JRB_HELMET_LAYER.getId());
			
	}
}

