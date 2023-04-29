package volbot.beetlebox.client.render.armor;

import net.minecraft.entity.LivingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ArmorEntityModel;

@Environment(EnvType.CLIENT)
public abstract class BeetleArmorEntityModel<T extends LivingEntity> extends ArmorEntityModel<T>{

	private String name;
	
	public BeetleArmorEntityModel(ModelPart part, String name) {
		super(part);
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

}
