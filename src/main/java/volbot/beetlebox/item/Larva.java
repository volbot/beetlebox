package volbot.beetlebox.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.registry.BeetleRegistry;

public class Larva {
	
	public String type;
	
	public int size;
	public float damage;
	public float speed;
	public float maxhealth;
	
	public Larva(String type, int size, float damage, float speed, float maxhealth) {
		this.type=type;
		this.size=size;
		this.damage=damage;
		this.speed=speed;
		this.maxhealth=maxhealth;
	}
	
	public Larva(LivingEntity a, LivingEntity b) {
		this.type = EntityType.getId(a.getType()).toString();
		if(a instanceof BeetleEntity) {
			generateGeneticStats((BeetleEntity)a,(BeetleEntity)b);
		}
	}
	
	public Larva(ContainedEntity a_contained, ContainedEntity b_contained, World world) {
		EntityType<?> a_type = EntityType.get(a_contained.getContainedId()).orElse(null);
		LivingEntity a = (LivingEntity) a_type.create(world);
		a.readNbt(a_contained.getEntityData());
		a.readCustomDataFromNbt(a_contained.getEntityData());
		
		this.type = EntityType.getId(a.getType()).toString();
		
		if(a instanceof BeetleEntity) {
			EntityType<?> b_type = EntityType.get(b_contained.getContainedId()).orElse(null);
			LivingEntity b = (LivingEntity) b_type.create(world);
			b.readNbt(b_contained.getEntityData());
			b.readCustomDataFromNbt(b_contained.getEntityData());
			
			generateGeneticStats((BeetleEntity)a,(BeetleEntity)b);
		}
	}
	
	protected void generateGeneticStats(BeetleEntity a, BeetleEntity b) {
		BeetleEntity current;
		boolean sw;

		sw = a.getRandom().nextBoolean();
		current = sw ? a : b;
		this.size = current.getSize();
		sw = a.getRandom().nextBoolean();
		current = sw ? a : b;
		this.maxhealth = current.getMaxHealthMult();
		sw = a.getRandom().nextBoolean();
		current = sw ? a : b;
		this.speed = current.getSpeedMult();
		sw = a.getRandom().nextBoolean();
		current = sw ? a : b;
		this.damage = current.getDamageMult();
	}
}
