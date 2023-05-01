package volbot.beetlebox.client.render.armor;

import net.minecraft.entity.LivingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

@Environment(EnvType.CLIENT)
public abstract class BeetleArmorEntityModel<T extends LivingEntity> extends BipedEntityModel<T>{

	private String name;
	
	public BeetleArmorEntityModel(ModelPart part, String name) {
		super(part);
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

}
