package volbot.beetlebox.client.render.armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;
import volbot.beetlebox.client.render.item.JarRenderer;

@Environment(EnvType.CLIENT)
public class BeetlepackRenderer<T extends BeetlepackModel<LivingEntity>> implements ArmorRenderer {

	protected T armorModel;

	public BeetlepackRenderer(T model) {
		super();
		armorModel = model;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack,
			LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		contextModel.copyBipedStateTo(armorModel);
		armorModel.setVisible(false);
		armorModel.body.visible = true;
		float scale_factor = 1.185f;
		matrices.scale(scale_factor, scale_factor, scale_factor);
		ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel,
				new Identifier("minecraft", "textures/models/armor/beetlepack.png"));
		matrices.push();
		scale_factor = 0.52f;
		matrices.scale(scale_factor, scale_factor, scale_factor);
		matrices.translate(0.0f, 0.0f, 0.0f); // for standing
		if (entity.isInSneakingPose()) {
			matrices.translate(0.0f, 0.45f, -0.02f); // for crouching
		}
		matrices.multiply(RotationAxis.POSITIVE_X.rotation(armorModel.body.pitch));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotation(armorModel.body.yaw));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotation(armorModel.body.roll));
		DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
		NbtCompound stack_nbt = stack.getOrCreateNbt();
		if(!stack_nbt.getCompound("Inventory").isEmpty()) {
			Inventories.readNbt(stack_nbt.getCompound("Inventory"),inventory);
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 2; l++) {
					matrices = translateForNextJar(matrices, l + k * 2);
					if(!inventory.get(l + k * 2).isEmpty()) {
						JarRenderer.renderJar(matrices, vertexConsumers, light);
					}
				}
			}
		}
		matrices.pop();
	}

	public MatrixStack translateForNextJar(MatrixStack matrices, int slot) {
		switch (slot) {
		case 0:
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45f));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(67.5f));
			matrices.translate(0.665f, -0.025f, -0.155f);
			break;
		case 5:
			matrices.translate(-0.00, 0.055, .065);
		case 3:
			matrices.translate(-0.033, 0.06, 0.06);
		case 1:
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-67.5f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45f));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(67.5f));
			matrices.translate(0.16f, -0.4f, 0.05f);
			break;
		case 4:
			matrices.translate(0, 0.06, -0.04);
		case 2:
			matrices.translate(-0.16f, 0.4f, -0.05f);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-67.5f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45f));
			matrices.translate(0.0, 0.36, 0.0);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45f));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(67.5f));
			matrices.translate(0, -0.08, 0);
			break;
		}
		return matrices;
	}
}
