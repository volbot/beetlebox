package volbot.beetlebox.compat.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.client.render.armor.BeetleArmorEntityModel;
import volbot.beetlebox.client.render.armor.BeetlepackModel;
import volbot.beetlebox.client.render.armor.BeetlepackRenderer;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetlepackTrinketRenderer<T extends BeetleArmorEntityModel<LivingEntity>> extends BeetlepackRenderer<T>
		implements TrinketRenderer {

	public BeetlepackTrinketRenderer(T model) {
		super(model);
	}

	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
			MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch) {
		if(!(contextModel instanceof BipedEntityModel<? extends LivingEntity>)) {
			return;
		}
		matrices.push();
		super.render(matrices, vertexConsumers, stack, entity, EquipmentSlot.CHEST, light, (BipedEntityModel<LivingEntity>)contextModel);
		matrices.pop();

	}
	
	public static void register() {
		TrinketRendererRegistry.registerRenderer(ItemRegistry.BEETLEPACK, new BeetlepackTrinketRenderer(new BeetlepackModel<>()));
	}

}
