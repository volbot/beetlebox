package volbot.beetlebox.entity.mobstorage;

public interface IMobContainerTE {
	
	public abstract ContainedEntity popContained();
	
	public abstract void pushContained(ContainedEntity e);
	
	public boolean canPush();
	
}
