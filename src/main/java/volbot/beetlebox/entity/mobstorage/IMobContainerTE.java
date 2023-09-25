package volbot.beetlebox.entity.mobstorage;

import net.minecraft.nbt.NbtCompound;

public interface IMobContainerTE {
	
	public abstract String getContained();

	public abstract NbtCompound getEntityData();

	public abstract String getEntityCustomName();

	public abstract void setContained(String id);

	public abstract void setEntityData(NbtCompound nbt);

	public abstract void setEntityCustomName(String custom_name);
	
}
