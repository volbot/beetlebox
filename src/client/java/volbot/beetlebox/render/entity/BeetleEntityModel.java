package volbot.beetlebox.render.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import volbot.beetlebox.entity.beetle.BeetleEntity;

public abstract class BeetleEntityModel<T extends BeetleEntity> extends EntityModel<T> {
   
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleLeg;
    private final ModelPart leftMiddleLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightElytron;
    private final ModelPart leftElytron;
    
	private static final float BABY_SCALE = 0.4f;
	private static final float BABY_Y_OFFSET = 2.3f;

	private static final float STANDARD_SCALE = 0.8f;
	private static final float STANDARD_Y_OFFSET = 0.37f;

    public BeetleEntityModel(ModelPart root) {
		this.root = root.getChild(EntityModelPartNames.ROOT);
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.rightFrontLeg = this.body.getChild("right_arms").getChild("right_front_arm");
		this.leftFrontLeg = this.body.getChild("left_arms").getChild("left_front_arm");
		this.rightMiddleLeg = this.body.getChild("right_arms").getChild("right_mid_arm");
		this.leftMiddleLeg = this.body.getChild("left_arms").getChild("left_mid_arm");
		this.rightHindLeg = this.body.getChild("right_arms").getChild("right_hind_arm");
		this.leftHindLeg = this.body.getChild("left_arms").getChild("left_hind_arm");
		this.rightWing = this.body.getChild("wings").getChild("right_wing");
		this.leftWing = this.body.getChild("wings").getChild("left_wing");
		this.rightElytron = this.body.getChild("wings").getChild("right_elytron");
		this.leftElytron = this.body.getChild("wings").getChild("left_elytron");
		
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
     
        if(entity.isFlying()) {
        	if(!entity.isOnGround()) {
        		this.body.pitch = headPitch * ((float)Math.PI / 180) + -0.4727F;
        		
                this.rightWing.pitch = 1.5966F+(0.9336F*1.3F)*MathHelper.sin(animationProgress*(float)Math.PI/2.4F);
                this.rightWing.yaw = -0.60875F+(0.24065F*1.3F)*MathHelper.sin(animationProgress*(float)Math.PI/2.4F);
                this.rightWing.roll = -1.6421F-(0.8506F*1.3F)*MathHelper.sin(animationProgress*(float)Math.PI/2.4F);
                this.leftWing.pitch = this.rightWing.pitch;
                this.leftWing.yaw = -this.rightWing.yaw;
                this.leftWing.roll = -this.rightWing.roll;
        	} else {
            	this.body.pitch = 0F;
            	
            	this.rightWing.pitch = 1.5966F;
                this.rightWing.yaw = -0.60875F;
                this.rightWing.roll = -1.6421F;
                this.leftWing.pitch = this.rightWing.pitch;
                this.leftWing.yaw = -this.rightWing.yaw;
                this.leftWing.roll = -this.rightWing.roll;
        	}
            
        	
        	this.rightElytron.pitch = 1.309F;
        	this.rightElytron.yaw = -0.5061F;
        	this.rightElytron.roll = -0.6109F;
        	this.leftElytron.pitch = 1.309F;
        	this.leftElytron.yaw = 0.5061F;
        	this.leftElytron.roll = 0.6109F;
        } else {
        	this.rightWing.pitch = 0F;
        	this.rightWing.yaw = 0F;
        	this.rightWing.roll = -0.2618F;
        	this.leftWing.pitch = 0F;
        	this.leftWing.yaw = 0F;
        	this.leftWing.roll = 0.2618F;
        	
        	this.rightElytron.pitch = 0F;
        	this.rightElytron.yaw = 0F;
        	this.rightElytron.roll = -0.2618F;
        	this.leftElytron.pitch = 0F;
        	this.leftElytron.yaw = 0F;
        	this.leftElytron.roll = 0.2618F;
        	
        	this.body.pitch = 0F;
        }
    	
    	this.head.yaw = headYaw * ((float)Math.PI / 260);
        this.head.pitch = headPitch * ((float)Math.PI / 180);
        
        this.rightHindLeg.roll = -0.0436F;
        this.leftHindLeg.roll = 0.0436F;
        this.rightMiddleLeg.roll = -0.0436F;
        this.leftMiddleLeg.roll = 0.0436F;
        this.rightFrontLeg.roll = -0.0436F;
        this.leftFrontLeg.roll = 0.0436F;
        this.rightHindLeg.yaw = 1.0908F;
        this.leftHindLeg.yaw = -1.0908F;
        this.rightMiddleLeg.yaw = 0.6981F;
        this.leftMiddleLeg.yaw = -0.6981F;
        this.rightFrontLeg.yaw = 0.0436F;
        this.leftFrontLeg.yaw = -0.0436F;
        
        float i = -(MathHelper.cos((float)(limbAngle * 0.6662f * 2.0f + 0.0f)) * 0.4f) * limbDistance;
        float j = -(MathHelper.cos((float)(limbAngle * 0.6662f * 2.0f + (float)Math.PI)) * 0.4f) * limbDistance;
        float l = -(MathHelper.cos((float)(limbAngle * 0.6662f * 2.0f + 4.712389f)) * 0.4f) * limbDistance;
        float m = Math.abs(MathHelper.sin((float)(limbAngle * 0.6662f + 0.0f)) * 0.4f) * limbDistance;
        float n = Math.abs(MathHelper.sin((float)(limbAngle * 0.6662f + (float)Math.PI)) * 0.4f) * limbDistance;
        float p = Math.abs(MathHelper.sin((float)(limbAngle * 0.6662f + 4.712389f)) * 0.4f) * limbDistance;
        this.rightHindLeg.yaw += i;
        this.leftHindLeg.yaw += -l;
        this.rightMiddleLeg.yaw += j;
        this.leftMiddleLeg.yaw += -i;
        this.rightFrontLeg.yaw += l;
        this.leftFrontLeg.yaw += -j;
        this.rightHindLeg.roll += m;
        this.leftHindLeg.roll += -m;
        this.rightMiddleLeg.roll += n;
        this.leftMiddleLeg.roll += -n;
        this.rightFrontLeg.roll += p;
        this.leftFrontLeg.roll += -p;
    }
 
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
    	if(this.child) {
			matrices.push();
			matrices.scale(BABY_SCALE, BABY_SCALE, BABY_SCALE);
			matrices.translate(0.0F, BABY_Y_OFFSET, 0.0F);
			this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			matrices.pop();
		}
		else {
			matrices.push();
			matrices.scale(STANDARD_SCALE, STANDARD_SCALE, STANDARD_SCALE);
			matrices.translate(0.0F, STANDARD_Y_OFFSET, 0.0F);
			this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			matrices.pop();
		}

    }
}
