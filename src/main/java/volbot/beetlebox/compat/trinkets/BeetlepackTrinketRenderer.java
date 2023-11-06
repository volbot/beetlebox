package volbot.beetlebox.compat.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;

import net.minecraft.util.Pair;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import volbot.beetlebox.client.render.armor.BeetlepackModel;
import volbot.beetlebox.client.render.armor.BeetlepackRenderer;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetlepackTrinketRenderer<T extends BeetlepackModel<LivingEntity>> extends BeetlepackRenderer<T>
		implements TrinketRenderer {

	public BeetlepackTrinketRenderer(T model) {
		super(model);
	}

	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
			MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch) {
		if (!(contextModel instanceof BipedEntityModel<? extends LivingEntity>)) {
			return;
		}
		matrices.push();
		super.render(matrices, vertexConsumers, stack, entity, EquipmentSlot.CHEST, light,
				(BipedEntityModel<LivingEntity>) contextModel);
		matrices.pop();

	}

	public static void register() {
		TrinketRendererRegistry.registerRenderer(ItemRegistry.BEETLEPACK,
				new BeetlepackTrinketRenderer<>(new BeetlepackModel<>()));
	}

	public static ItemStack getBackStack(PlayerEntity playerEntity) {
		TrinketComponent tc = TrinketsApi.getTrinketComponent(playerEntity).get();
		if (tc.isEquipped(ItemRegistry.BEETLEPACK)) {
			return tc.getInventory().get("chest").get("back").getStack(0);
		} else {
			return ItemStack.EMPTY;
		}
	}

}
