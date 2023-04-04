package volbot.beetlebox.render.entity;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import volbot.beetlebox.entity.beetle.JRBEntity;

public class JRBEntityModel extends BeetleEntityModel<JRBEntity> {

	public JRBEntityModel(ModelPart root) {
		super(root);
	}
	
	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
    	ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
        
        ModelPartData body = root.addChild("body",
        		ModelPartBuilder.create()
        		.uv(0, 0).cuboid(-2.5F, -3.5F, -5.5F, 5.0F, 3.0F, 9.0F),
        		ModelTransform.pivot(0.0F, 22.0F, 1.0F));
        ModelPartData wings = body.addChild("wings", ModelPartBuilder.create(),
        		ModelTransform.pivot(0.0F, 2.0F, -1.0F));
        wings.addChild("right_wing",
        		ModelPartBuilder.create()
        		.uv(10, 0).cuboid(-0.9F, 0.25F, -1.0F, 3.0F, 0.1F, 9.0F),
        		ModelTransform.of(-1.9743F, -5.6084F, -2.5F, 0.0F, 0.0F, -0.2618F));
        wings.addChild("left_wing",
        		ModelPartBuilder.create()
        		.uv(10, 0).mirrored().cuboid(-2.1F, 0.25F, -1.0F, 3.0F, 0.1F, 9.0F),
        		ModelTransform.of(1.9743F, -5.6084F, -2.5F, 0.0F, 0.0F, 0.2618F));
        wings.addChild("right_elytron",
        		ModelPartBuilder.create()
        		.uv(0, 12).cuboid(-1.4F, -0.25F, -0.5F, 3.0F, 1.0F, 9.0F),
        		ModelTransform.of(-1.5F, -5.6F, -3.4F, 0.0F, 0.0F, -0.2618F));
        wings.addChild("left_elytron",
        		ModelPartBuilder.create()
        		.uv(0, 12).mirrored().cuboid(-1.6F, -0.25F, -0.5F, 3.0F, 1.0F, 9.0F),
        		ModelTransform.of(1.5F, -5.6F, -3.4F, 0.0F, 0.0F, 0.2618F));
       
        ModelPartData head = body.addChild("head",
        		ModelPartBuilder.create()
        		.uv(0, 22).cuboid(-2.0F, -1.4706F, -2.5F, 4.0F, 3.0F, 2.0F),
        		ModelTransform.pivot(0.0F, -2.5294F, -4.0F));
        ModelPartData adorn = head.addChild("headpiece", ModelPartBuilder.create(), 
        		ModelTransform.pivot(0.0F, 4.5294F, 3.0F));
        adorn.addChild("adornment",
        		ModelPartBuilder.create()
        		.uv(0, 18).cuboid(-1.0F, -1.5F, -2.0F, 2.0F, 1.0F, 1.0F)
        		.uv(0, 0).cuboid(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F),
        		ModelTransform.of(0.0F, -5.1256F, -4.7876F, -0.2618F, 0.0F, 0.0F));
        
        ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(), 
        		ModelTransform.of(0.0F, 1.1294F, -2.0691F, -0.3927F, 0.0F, 0.0F));
        horn.addChild("horn_base", 
        		ModelPartBuilder.create()
        		.uv(15, 12).cuboid(-1.0F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F),
        		ModelTransform.of(0.0F, 0.4F, -2.4309F, 0.2618F, 0.0F, 0.0F));
        horn.addChild("d1", 
        		ModelPartBuilder.create()
        		.uv(0, 15).cuboid(-1.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F),
        		ModelTransform.of(-0.7349F, 1.15F, -6.3454F, 0.0F, 1.1345F, 0.0F));
        horn.addChild("d2", 
        		ModelPartBuilder.create()
        		.uv(0, 15).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F),
        		ModelTransform.of(-0.7349F, 1.15F, -5.8454F, 0.0F, 0.3491F, 0.0F));
        horn.addChild("d3", 
        		ModelPartBuilder.create()
        		.uv(0, 15).cuboid(0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F),
        		ModelTransform.of(0.7349F, 1.15F, -6.3454F, 0.0F, -1.1345F, 0.0F));
        horn.addChild("d4", 
        		ModelPartBuilder.create()
        		.uv(0, 15).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F),
        		ModelTransform.of(0.7349F, 1.15F, -5.8454F, 0.0F, -0.3491F, 0.0F));
        
        ModelPartData rightarms = body.addChild("right_arms", ModelPartBuilder.create(), 
        		ModelTransform.pivot(-0.25F, 2.0F, 0.5F));
        
        ModelPartData RA1 = rightarms.addChild("right_front_arm", ModelPartBuilder.create(), 
        		ModelTransform.of(0.25F, 0.0F, -3.25F, 0.0F, 0.2618F, -0.0436F));

		RA1.addChild("RUA1_r1", 
				ModelPartBuilder.create()
				.uv(0, 4).cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		RA1.addChild("RLA1_r1", 
				ModelPartBuilder.create()
				.uv(0, 6).cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData RA2 = rightarms.addChild("right_mid_arm", ModelPartBuilder.create(), 
				ModelTransform.of(-0.25F, 0.0F, -1.25F, 0.0F, 0.6981F, -0.0436F));

		RA2.addChild("RUA1_r2", ModelPartBuilder.create()
				.uv(0, 4).cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		RA2.addChild("RLA1_r2", ModelPartBuilder.create()
				.uv(0, 6).cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData RA3 = rightarms.addChild("right_hind_arm", ModelPartBuilder.create(), 
				ModelTransform.of(-0.25F, 0.0F, 0.75F, 0.0F, 1.1781F, -0.0436F));

		RA3.addChild("RUA1_r3", ModelPartBuilder.create()
				.uv(0, 4).cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		RA3.addChild("RLA1_r3", ModelPartBuilder.create()
				.uv(0, 6).cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData left_arms = body.addChild("left_arms", ModelPartBuilder.create(), 
				ModelTransform.pivot(0.25F, 2.0F, 0.5F));

		ModelPartData left_front_arm = left_arms.addChild("left_front_arm", ModelPartBuilder.create(), 
				ModelTransform.of(0.25F, 0.0F, -3.25F, 0.0F, -0.2618F, 0.0436F));

		left_front_arm.addChild("RUA2_r1", ModelPartBuilder.create()
				.uv(0, 4).mirrored().cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_front_arm.addChild("RLA2_r1", ModelPartBuilder.create()
				.uv(0, 6).mirrored().cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData left_mid_arm = left_arms.addChild("left_mid_arm", ModelPartBuilder.create(), 
				ModelTransform.of(0.25F, 0.0F, -1.25F, 0.0F, -0.6981F, 0.0436F));

		left_mid_arm.addChild("RUA1_r1", ModelPartBuilder.create()
				.uv(0, 4).mirrored().cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_mid_arm.addChild("RLA1_r1", ModelPartBuilder.create()
				.uv(0, 6).mirrored().cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData left_hind_arm = left_arms.addChild("left_hind_arm", ModelPartBuilder.create(), 
				ModelTransform.of(0.25F, 0.0F, 0.75F, 0.0F, -1.1781F, 0.0436F));

		left_hind_arm.addChild("RUA2_r2", ModelPartBuilder.create()
				.uv(0, 4).mirrored().cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_hind_arm.addChild("RLA2_r2", ModelPartBuilder.create()
				.uv(0, 6).mirrored().cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), 
				ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		
        
        return TexturedModelData.of(modelData, 32, 32);
    }

}
