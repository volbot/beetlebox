package volbot.beetlebox.client.render.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BeetleElytraRenderer implements ArmorRenderer {
	
	private ArmorElytraEntityModel<LivingEntity> model;
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack,
			LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {

		if (model == null) {
			model = new ArmorElytraEntityModel<LivingEntity>(ArmorElytraEntityModel.getTexturedModelData().createModel());
		}
		contextModel.copyBipedStateTo(model);
		ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new Identifier("beetlebox", "elytrachestplate"));
	}

}
