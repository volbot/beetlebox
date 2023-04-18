package volbot.beetlebox.client.render.entity;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import volbot.beetlebox.entity.beetle.AtlasEntity;

public class AtlasEntityModel extends BeetleEntityModel<AtlasEntity> {

	public AtlasEntityModel(ModelPart root) {
		super(root);
	}
	
	public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
    	ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
        
		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -3.5F, -5.5F, 5.0F, 3.0F, 8.0F), ModelTransform.pivot(0.0F, 22.0F, 1.0F));

		ModelPartData wings = body.addChild("wings", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, -1.0F));

		wings.addChild("right_wing", ModelPartBuilder.create().uv(10, 0).mirrored().cuboid(-0.9F, 0.25F, -1.0F, 3.0F, 0.0F, 8.0F).mirrored(false), ModelTransform.of(-1.9743F, -5.6084F, -2.5F, 0.0F, 0.0F, -0.2618F));

		wings.addChild("left_wing", ModelPartBuilder.create().uv(10, 0).cuboid(-2.1F, 0.25F, -1.0F, 3.0F, 0.0F, 8.0F), ModelTransform.of(1.9743F, -5.6084F, -2.5F, 0.0F, 0.0F, 0.2618F));

		wings.addChild("right_elytron", ModelPartBuilder.create().uv(0, 11).mirrored().cuboid(-1.4F, -0.25F, -0.5F, 3.0F, 1.0F, 8.0F).mirrored(false), ModelTransform.of(-1.5F, -5.6F, -3.4F, 0.0F, 0.0F, -0.2618F));

		wings.addChild("left_elytron", ModelPartBuilder.create().uv(0, 11).cuboid(-1.6F, -0.25F, -0.5F, 3.0F, 1.0F, 8.0F), ModelTransform.of(1.5F, -5.6F, -3.4F, 0.0F, 0.0F, 0.2618F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(14, 11).cuboid(-2.0F, -1.4706F, -2.5F, 4.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, -2.5294F, -4.75F));

		ModelPartData horn = head.addChild("horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.7059F, -3.7634F));

		ModelPartData bot = horn.addChild("bot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		bot.addChild("cube_r3", ModelPartBuilder.create().uv(20, 17).cuboid(-0.5F, -0.75F, -1.75F, 1.0F, 1.0F, 2.0F), ModelTransform.of(0.0F, -0.7419F, 1.662F, -0.3491F, 0.0F, 0.0F));

		bot.addChild("cube_r4", ModelPartBuilder.create().uv(18, 16).cuboid(-0.5F, -0.25F, -1.5F, 1.0F, 1.0F, 4.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		bot.addChild("cube_r5", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.75F, -1.5F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, 0.0596F, -1.9756F, -0.7854F, 0.0F, 0.0F));

		ModelPartData left = horn.addChild("left", ModelPartBuilder.create(), ModelTransform.of(2.1621F, -2.796F, -0.7084F, 0.1745F, 0.0F, -2.0071F));

		left.addChild("cube_r6", ModelPartBuilder.create().uv(18,16).cuboid(-0.5F, -0.25F, -1.5F, 1.0F, 1.0F, 4.0F), ModelTransform.of(0.0F, -0.0374F, 0.7084F, 0.4363F, 0.0F, 0.0F));

		left.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F), ModelTransform.of(0.0F, -0.4943F, -2.7616F, -0.6545F, 0.0F, 0.0F));

		ModelPartData right = horn.addChild("right", ModelPartBuilder.create(), ModelTransform.of(-2.1621F, -2.796F, -0.7084F, 0.1745F, 0.0F, 2.0071F));

		right.addChild("cube_r8", ModelPartBuilder.create().uv(18,16).mirrored().cuboid(-0.5F, -0.25F, -1.5F, 1.0F, 1.0F, 4.0F).mirrored(false), ModelTransform.of(0.0F, -0.0374F, 0.7084F, 0.4363F, 0.0F, 0.0F));

		right.addChild("cube_r9", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F).mirrored(false), ModelTransform.of(0.0F, -0.4943F, -2.7616F, -0.6545F, 0.0F, 0.0F));

		ModelPartData left_arms = body.addChild("left_arms", ModelPartBuilder.create(), ModelTransform.pivot(0.25F, 2.0F, 0.5F));

		ModelPartData left_front_arm = left_arms.addChild("left_front_arm", ModelPartBuilder.create(), ModelTransform.of(0.25F, 0.0F, -3.25F, 0.0F, -0.2618F, 0.0436F));

		left_front_arm.addChild("RUA2_r1", ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F).mirrored(false), ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_front_arm.addChild("RLA2_r1", ModelPartBuilder.create().uv(0, 6).mirrored().cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F).mirrored(false), ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData left_mid_arm = left_arms.addChild("left_mid_arm", ModelPartBuilder.create(), ModelTransform.of(0.25F, 0.0F, -1.25F, 0.0F, -0.6981F, 0.0436F));

		left_mid_arm.addChild("RUA1_r1", ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F).mirrored(false), ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_mid_arm.addChild("RLA1_r1", ModelPartBuilder.create().uv(0, 6).mirrored().cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F).mirrored(false), ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData left_hind_arm = left_arms.addChild("left_hind_arm", ModelPartBuilder.create(), ModelTransform.of(0.25F, 0.0F, 0.75F, 0.0F, -1.1781F, 0.0436F));

		left_hind_arm.addChild("RUA2_r2", ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F).mirrored(false), ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.2391F, 0.5194F, 0.4565F));

		left_hind_arm.addChild("RLA2_r2", ModelPartBuilder.create().uv(0, 6).mirrored().cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F).mirrored(false), ModelTransform.of(2.7114F, -2.1791F, -2.1678F, 0.481F, 0.3133F, 1.0374F));

		ModelPartData right_arms = body.addChild("right_arms", ModelPartBuilder.create(), ModelTransform.pivot(-0.25F, 2.0F, 0.5F));

		ModelPartData right_front_arm = right_arms.addChild("right_front_arm", ModelPartBuilder.create(), ModelTransform.of(-0.25F, 0.0F, -3.25F, 0.0F, 0.2618F, -0.0436F));

		right_front_arm.addChild("RUA3_r1", ModelPartBuilder.create().uv(0, 4).cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		right_front_arm.addChild("RLA3_r1", ModelPartBuilder.create().uv(0, 6).cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData right_mid_arm = right_arms.addChild("right_mid_arm", ModelPartBuilder.create(), ModelTransform.of(-0.25F, 0.0F, -1.25F, 0.0F, 0.6981F, -0.0436F));

		right_mid_arm.addChild("RUA2_r3", ModelPartBuilder.create().uv(0, 4).cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		right_mid_arm.addChild("RLA2_r3", ModelPartBuilder.create().uv(0, 6).cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

		ModelPartData right_hind_arm = right_arms.addChild("right_hind_arm", ModelPartBuilder.create(), ModelTransform.of(-0.25F, 0.0F, 0.75F, 0.0F, 1.1781F, -0.0436F));

		right_hind_arm.addChild("RUA3_r2", ModelPartBuilder.create().uv(0, 4).cuboid(-0.2518F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.2391F, -0.5194F, -0.4565F));

		right_hind_arm.addChild("RLA3_r2", ModelPartBuilder.create().uv(0, 6).cuboid(-2.7482F, -0.8638F, -0.5F, 3.0F, 1.0F, 1.0F), ModelTransform.of(-2.7114F, -2.1791F, -2.1678F, 0.481F, -0.3133F, -1.0374F));

        
        
        return TexturedModelData.of(modelData, 32, 32);
    }

}
