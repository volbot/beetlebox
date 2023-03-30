package volbot.beetlebox.client.render.armor;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class ArmorElytraEntityModel<T extends LivingEntity> extends ArmorEntityModel<T> {
	ModelPart left_wing;
	ModelPart right_wing;
	ModelPart body;
	ModelPart left_arm;
	ModelPart right_arm;
	
    public ArmorElytraEntityModel(ModelPart root) {
    	super(root);
    	this.body = root.getChild(EntityModelPartNames.BODY);
    	this.left_wing = body.getChild(EntityModelPartNames.LEFT_WING);
    	this.right_wing = body.getChild(EntityModelPartNames.RIGHT_WING);
    	this.left_arm = root.getChild(EntityModelPartNames.LEFT_ARM);
    	this.right_arm = root.getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPart head = root.getChild(EntityModelPartNames.HEAD);
        head.visible = false;
    }
   
    public static TexturedModelData getTexturedModelData() {
        Dilation dilation1 = new Dilation(1.0f);
        float pivotOffsetY = -1F;
        ModelData modelData = ArmorEntityModel.getModelData(dilation1, pivotOffsetY);
        ModelPartData root = modelData.getRoot();
        ModelPartData body = root.getChild(EntityModelPartNames.BODY);
        Dilation dilation2 = new Dilation(0.0f);
        body.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(22, 0).cuboid(-10.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, dilation2), ModelTransform.of(5.0f, 0.0f + pivotOffsetY, 0.0f, 0.2617994f, 0.0f, -0.2617994f));
        body.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(22, 0).mirrored().cuboid(0.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, dilation2), ModelTransform.of(-5.0f, 0.0f + pivotOffsetY, 0.0f, 0.2617994f, 0.0f, 0.2617994f));
        return TexturedModelData.of(modelData, 64, 32);
    }
    


    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.left_arm, this.right_arm);
    }
    

}
