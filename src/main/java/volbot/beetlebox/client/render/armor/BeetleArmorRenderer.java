package volbot.beetlebox.client.render.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BeetleArmorRenderer <T extends BeetleArmorEntityModel<LivingEntity>>
implements ArmorRenderer {
	
	private T armorModel;
	
	public BeetleArmorRenderer(T model) {
		super();
		armorModel = model;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack,
		LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		contextModel.copyStateTo(armorModel);
		armorModel.setVisible(false);
		armorModel.head.roll = contextModel.head.roll;
		armorModel.head.pitch = contextModel.head.pitch;
		armorModel.head.yaw = contextModel.head.yaw;
		armorModel.head.visible = true;
		ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel,
				new Identifier("minecraft","textures/models/armor/"+armorModel.getName()+"_helmet.png"));
			
	}
}
