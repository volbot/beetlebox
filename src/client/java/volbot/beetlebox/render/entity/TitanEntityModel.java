package volbot.beetlebox.render.entity;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import volbot.beetlebox.entity.beetle.TitanEntity;

public class TitanEntityModel extends BeetleEntityModel<TitanEntity> {

	public TitanEntityModel(ModelPart root) {
		super(root);
	}
	
	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
    	ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), 
        		ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
        
        ModelPartData body = root.addChild("body", ModelPartBuilder.create()
        		.uv(0, 0).cuboid(-2.5F, -3.5F, -5.0F, 5.0F, 2.0F, 8.0F), 
        		ModelTransform.pivot(0.0F, 23.0F, 1.0F));

		ModelPartData wings = body.addChild("wings", ModelPartBuilder.create(), 
				ModelTransform.pivot(0.0F, 2.0F, -1.0F));

		wings.addChild("right_wing", ModelPartBuilder.create()
				.uv(10, 0).mirrored().cuboid(-0.9F, 0.25F, -1.0F, 3.0F, 0.0F, 8.0F).mirrored(false), 
				ModelTransform.of(-1.9743F, -5.6084F, -2.5F, 0.0F, 0.0F, -0.2618F));

		wings.addChild("left_wing", ModelPartBuilder.create()
				.uv(10, 0).cuboid(-2.1F, 0.25F, -1.0F, 3.0F, 0.0F, 8.0F), 
				ModelTransform.of(1.9743F, -5.6084F, -2.5F, 0.0F, 0.0F, 0.2618F));

		wings.addChild("right_elytron", ModelPartBuilder.create()
				.uv(0, 10).mirrored().cuboid(-1.4F, -0.25F, -0.5F, 3.0F, 1.0F, 8.0F).mirrored(false), 
				ModelTransform.of(-1.5F, -5.6F, -3.4F, 0.0F, 0.0F, -0.2618F));

		wings.addChild("left_elytron", ModelPartBuilder.create()
				.uv(0, 10).cuboid(-1.6F, -0.25F, -0.5F, 3.0F, 1.0F, 8.0F), 
				ModelTransform.of(1.5F, -5.6F, -3.4F, 0.0F, 0.0F, 0.2618F));

		ModelPartData left_arms = body.addChild("left_arms", ModelPartBuilder.create(), 
				ModelTransform.pivot(0.25F, 2.0F, 0.5F));

		ModelPartData left_front_arm = left_arms.addChild("left_front_arm", ModelPartBuilder.create(), 
				ModelTransform.of(0.25F, 0.0F, -3.25F, 0.0F, -0.2618F, 0.0436F));

		left_front_arm.addChild("RUA2_r1", ModelPartBuilder.create()
				.uv(0, 0).mirrored().cuboid(-3.169F, -1.7709F, -0.5131F, 3.0F, 1.0F, 1.0F).mirrored(false), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_front_arm.addChild("RLA2_r1", ModelPartBuilder.create()
				.uv(0, 0).mirrored().cuboid(-1.094F, -1.4028F, -0.5131F, 3.0F, 1.0F, 1.0F).mirrored(false), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData left_mid_arm = left_arms.addChild("left_mid_arm", ModelPartBuilder.create(), 
				ModelTransform.of(0.25F, 0.0F, -1.25F, 0.0F, -0.6981F, 0.0436F));

		left_mid_arm.addChild("RUA1_r1", ModelPartBuilder.create()
				.uv(0, 0).mirrored().cuboid(-3.2308F, -1.7453F, -0.99F, 3.0F, 1.0F, 1.0F).mirrored(false), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_mid_arm.addChild("RLA1_r1", ModelPartBuilder.create()
				.uv(0, 0).mirrored().cuboid(-1.1324F, -1.348F, -0.99F, 3.0F, 1.0F, 1.0F).mirrored(false), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData left_hind_arm = left_arms.addChild("left_hind_arm", ModelPartBuilder.create(), 
				ModelTransform.of(0.25F, 0.0F, 0.75F, 0.0F, -1.1781F, 0.0436F));

		left_hind_arm.addChild("RUA2_r2", ModelPartBuilder.create()
				.uv(0, 0).mirrored().cuboid(-3.6935F, -1.5537F, -1.2941F, 3.0F, 1.0F, 1.0F).mirrored(false), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_hind_arm.addChild("RLA2_r2", ModelPartBuilder.create()
				.uv(0, 0).mirrored().cuboid(-1.4196F, -0.9377F, -1.2941F, 3.0F, 1.0F, 1.0F).mirrored(false), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData right_arms = body.addChild("right_arms", ModelPartBuilder.create(), 
				ModelTransform.pivot(-0.25F, 2.0F, 0.5F));

		ModelPartData right_front_arm = right_arms.addChild("right_front_arm", ModelPartBuilder.create(), 
				ModelTransform.of(-0.25F, 0.0F, -3.25F, 0.0F, 0.2618F, -0.0436F));

		right_front_arm.addChild("RUA3_r1", ModelPartBuilder.create()
				.uv(0, 0).cuboid(0.169F, -1.7709F, -0.5131F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		right_front_arm.addChild("RLA3_r1", ModelPartBuilder.create()
				.uv(0, 0).cuboid(-1.906F, -1.4028F, -0.5131F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData right_mid_arm = right_arms.addChild("right_mid_arm", ModelPartBuilder.create(), 
				ModelTransform.of(-0.25F, 0.0F, -1.25F, 0.0F, 0.6981F, -0.0436F));

		right_mid_arm.addChild("RUA2_r3", ModelPartBuilder.create()
				.uv(0, 0).cuboid(0.2308F, -1.7453F, -0.99F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		right_mid_arm.addChild("RLA2_r3", ModelPartBuilder.create()
				.uv(0, 0).cuboid(-1.8676F, -1.348F, -0.99F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData right_hind_arm = right_arms.addChild("right_hind_arm", ModelPartBuilder.create(), 
				ModelTransform.of(-0.25F, 0.0F, 0.75F, 0.0F, 1.1781F, -0.0436F));

		right_hind_arm.addChild("RUA3_r2", ModelPartBuilder.create()
				.uv(0, 0).cuboid(0.6935F, -1.5537F, -1.2941F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		right_hind_arm.addChild("RLA3_r2", ModelPartBuilder.create()
				.uv(0, 0).cuboid(-1.5804F, -0.9377F, -1.2941F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData head2 = body.addChild("head", ModelPartBuilder.create().uv(14, 10).
				cuboid(-2.5F, -1.4706F, -4.0F, 5.0F, 2.0F, 3.0F), ModelTransform.pivot(0.0F, -2.2794F, -4.0F));

		ModelPartData horn2 = head2.addChild("horn2", ModelPartBuilder.create(), 
				ModelTransform.of(0.75F, -0.3346F, -6.4113F, 0.48F, 0.0F, -1.6581F));

		horn2.addChild("cube_r3", ModelPartBuilder.create()
				.uv(0, 2).cuboid(-0.251F, -1.0695F, -0.2443F, 1.0F, 1.0F, 2.0F), ModelTransform.of(0.0F, 1.4974F, -1.8342F, -0.6545F, 0.0F, 0.0F));

		horn2.addChild("cube_r4", ModelPartBuilder.create()
				.uv(17, 15).cuboid(-0.251F, -0.7096F, -2.0802F, 1.0F, 1.0F, 5.0F), ModelTransform.of(0.0F, 2.0405F, 1.648F, 0.0873F, 0.0F, 0.0F));

		ModelPartData horn3 = head2.addChild("horn3", ModelPartBuilder.create(), 
				ModelTransform.of(-0.75F, -0.3346F, -6.4113F, 0.48F, 0.0F, 1.6581F));

		horn3.addChild("cube_r5", ModelPartBuilder.create()
				.uv(0, 2).mirrored().cuboid(-0.749F, -1.0695F, -0.2443F, 1.0F, 1.0F, 2.0F).mirrored(false), 
				ModelTransform.of(0.0F, 1.4974F, -1.8342F, -0.6545F, 0.0F, 0.0F));

		horn3.addChild("cube_r6", ModelPartBuilder.create()
				.uv(17, 15).mirrored().cuboid(-0.749F, -0.7096F, -2.0802F, 1.0F, 1.0F, 5.0F).mirrored(false), 
				ModelTransform.of(0.0F, 2.0405F, 1.648F, 0.0873F, 0.0F, 0.0F));
		
        
        return TexturedModelData.of(modelData, 32, 32);
    }

}
