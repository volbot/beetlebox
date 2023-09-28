package volbot.beetlebox.entity.mobstorage;

import net.minecraft.nbt.NbtCompound;

public class ContainedEntity {
	
	public String contained_id = "";
	public String custom_name = "";
	public NbtCompound entity_data;
	
	public ContainedEntity() {
		this("",null);
	}
	
	public ContainedEntity(String id, NbtCompound data) {
		this(id,data,"");
	}
	
	public ContainedEntity(String id, NbtCompound data, String custom_name) {
		this.contained_id = id;
		this.custom_name = custom_name;
		this.entity_data = data;
	}
	
	public String getContainedId() {
		return contained_id;
	}

	public void setContainedId(String contained_id) {
		this.contained_id = contained_id;
	}

	public String CustomName() {
		return custom_name;
	}

	public void setCustomName(String custom_name) {
		this.custom_name = custom_name;
	}

	public NbtCompound getEntityData() {
		return entity_data;
	}

	public void setEntityData(NbtCompound entity_data) {
		this.entity_data = entity_data;
	}
	
}
