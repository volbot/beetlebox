package volbot.beetlebox.entity.beetle;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.entity.ai.BeetleFlyToTreeGoal;
import volbot.beetlebox.registry.BeetleRegistry;

public abstract class BeetleEntity extends AnimalEntity {
	
	private static final TrackedData<Byte> CLIMBING = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Byte> FLYING = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Integer> SIZE = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Float> DAMAGE = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> SPEED = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> MAXHEALTH = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private  Multimap<EntityAttribute, EntityAttributeModifier> current_modifiers = HashMultimap.create();
	
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(BeetleRegistry.BEETLE_JELLY);
	private static final Ingredient HEALING_INGREDIENT = Ingredient.ofItems(Items.SUGAR_CANE);
	
    private boolean isLandNavigator;
    public boolean unSynced = true;
    public int size_cached = 0;
    public float damage_cached = 3.0f;
    public float speed_cached = 0.3f;
    public float maxhealth_cached = 8.0f;

    public int timeFlying = 0;
	
	public BeetleEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
        switchNavigator(false);
	}
	
	@Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, HEALING_INGREDIENT, false));
        this.goalSelector.add(5, new BeetleFlyToTreeGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
	}
	
	@Override
    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.setClimbingWall(this.horizontalCollision && !this.isFlying());
            final boolean isFlying = isFlying();
            if (isFlying && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (isFlying) {
                timeFlying++;
                this.setNoGravity(true);
                if (this.isInLove()) {
                    this.setFlying(false);
                }
            } else {
                timeFlying = 0;
                this.setNoGravity(false);
            }
            if(unSynced && this.size_cached!=0) {
            	this.setSize(size_cached);
            } else {
	            this.sendPacket();
            }
        }
    }
	
	@Override
    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
		if(stack.isOf(BeetleRegistry.BEETLE_JELLY)) {
			NbtCompound nbt = stack.getNbt();
			switch(nbt.getString("FruitType")) {
			case "apple":
				int i = this.getBreedingAge();
	            if (!this.world.isClient && i == 0 && this.canEat()) {
	                this.lovePlayer(player);
	            }
	            if (this.isBaby()) {
	                this.growUp(AnimalEntity.toGrowUpAge(-i), true);
	            }
				break;
			case "melon":
				this.setSize((int)(this.getSize()+(nbt.getBoolean("Increase")?1.0:-1.0)*nbt.getFloat("Magnitude")));
				break;
			case "sugar":
				this.setSpeed(this.getSpeed()+(nbt.getBoolean("Increase")?0.01f:-0.01f)*nbt.getFloat("Magnitude"));
				break;
			case "cactus":
				this.setDamage(this.getDamage()+(nbt.getBoolean("Increase")?0.5f:-0.5f)*nbt.getFloat("Magnitude"));
				break;
			case "berry":
				this.setMaxHealth(this.getMaxHealth()+(nbt.getBoolean("Increase")?0.5f:-0.5f)*nbt.getFloat("Magnitude"));
				break;
			}
		}
		super.eat(player, hand, stack);
    }
	
	@Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(BeetleRegistry.BEETLE_JELLY) || this.isHealingItem(itemStack)) {
            if (!this.world.isClient && this.canEat()) {
                this.eat(player, hand, itemStack);
                return ActionResult.SUCCESS;
            }
            if (this.isBaby()) {
            	return ActionResult.FAIL;
            }
            if (this.world.isClient) {
                return ActionResult.CONSUME;
            }
        }
        return super.interactMob(player, hand);
    }
	
	@Override
	public EntityDimensions getDimensions(EntityPose p) {
		int size = this.getSize()/10;
		return EntityDimensions.fixed(
				0.4f*size,
				0.4f*size);
	}
	
	
	
	//--------------------
	// MOVEMENT UTILITIES
	//--------------------
    
    @Override
    public boolean damage(DamageSource source, float amount) {
    	if(source.isOf(DamageTypes.CACTUS) || source.isOf(DamageTypes.FALL)) {
    		return false;
    	}
    	return super.damage(source, amount);
    }
	
	public boolean isOverWater() {
        BlockPos position = this.getBlockPos();
        while (position.getY() > -64 && this.world.isAir(position)) {
            position = position.down();
        }
        return !this.world.getFluidState(position).isEmpty();
    }
	
	@Override
    public boolean isClimbing() {
        return this.isClimbingWall();
    }
	
	public boolean isClimbingWall() {
        return (this.dataTracker.get(CLIMBING) & 1) != 0;
    }
	
	public int getSize() {
		try {
			return this.dataTracker.get(SIZE);
		} catch(NullPointerException e) {
			this.unSynced = true;
			return this.size_cached;
		}
    }
	
	public float getSpeed() {
		try {
			return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		} catch(NullPointerException e) {
			this.unSynced = true;
			return this.speed_cached;
		}
    }
	
	public float getDamage() {
		try {
			return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		} catch(NullPointerException e) {
			this.unSynced = true;
			return this.damage_cached;
		}
    }
	
	public float getMaxHealthSafe() {
		try {
			return (float)this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
		} catch(NullPointerException e) {
			this.unSynced = true;
			return this.maxhealth_cached;
		}
    }
	
	public float getSpeedUnsafe() {
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }
	
	public float getDamageUnsafe() {
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }
	
	public int getSizeUnsafe() {
		return this.dataTracker.get(SIZE);
	}
	
	public void setSize(int size) {
		size_cached = size;
		this.dataTracker.set(SIZE, size_cached);
        if(!(this.world.isClient)) {
            this.sendPacket();
        }
        this.calculateDimensions();
    }
	
	public void setDamage(float damage) {
		damage_cached = damage;
		this.dataTracker.set(DAMAGE, damage_cached);
        if(!(this.world.isClient)) {
            this.sendPacket();
        }
        this.refreshAttributes();
    }
	
	public void setMaxHealth(float maxhealth) {
		maxhealth_cached = maxhealth;
		this.dataTracker.set(MAXHEALTH, maxhealth_cached);
        if(!(this.world.isClient)) {
            this.sendPacket();
        }
        this.refreshAttributes();
    }
	
	public void setSpeed(float speed) {
		speed_cached = speed;
		this.dataTracker.set(SPEED, speed_cached);
        if(!(this.world.isClient)) {
            this.sendPacket();
        }
        this.refreshAttributes();
    }

    public void setClimbingWall(boolean climbing) {
        byte b = this.dataTracker.get(CLIMBING);
        b = climbing ? (byte)(b | 1) : (byte)(b & 0xFFFFFFFE);
        this.dataTracker.set(CLIMBING, b);
    }
    
    public boolean isFlying() {
    	return (this.dataTracker.get(FLYING) & 1) != 0;
    }
    
    public void setFlying(boolean flying) {
        byte b = this.dataTracker.get(FLYING);
        b = flying ? (byte)(b | 1) : (byte)(b & 0xFFFFFFFE);
        this.dataTracker.set(FLYING, (byte)b);
    }
	
	@Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CLIMBING, (byte)0);
        this.dataTracker.startTracking(FLYING, (byte)0);
    	this.dataTracker.startTracking(DAMAGE, damage_cached);
    	this.dataTracker.startTracking(SPEED, speed_cached);
    	this.dataTracker.startTracking(MAXHEALTH, maxhealth_cached);
        if(!this.world.isClient) {
            if(size_cached != 0) {
            	this.dataTracker.startTracking(SIZE, size_cached);
            } else {
            	int size = this.random.nextInt(20)+10;

            	this.size_cached = size;
            	this.dataTracker.startTracking(SIZE, size_cached);
            }
            this.sendPacket();
        } else {
        	this.dataTracker.startTracking(SIZE, size_cached);
            this.unSynced=true;
        }
    }
	
	public void sendPacket() {
		if(this.world.getPlayers().size()==0) {
			this.unSynced=true;
			return;
		}
	    ServerPlayerEntity p = (ServerPlayerEntity)this.world.getPlayers().get(0);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(this.getSize());
        buf.writeFloat(this.getDamage());
        buf.writeFloat(this.getSpeed());
        buf.writeFloat(this.getMaxHealthSafe());
        buf.writeInt(this.getId());
        ServerPlayNetworking.send(p, new Identifier("beetlebox/beetle_packet"), buf);
        this.unSynced=false;
	}
	  
    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }
    
    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SpiderNavigation(this, this.getEntityWorld());
            this.isLandNavigator = true;
            this.navigation.setSpeed(1.0);
        } else {
            this.moveControl = new FlightMoveControl(this, 30, false);
            this.navigation = new BirdNavigation(this, this.getEntityWorld());
            this.isLandNavigator = false;
            this.navigation.setSpeed(5.0);
        }
    }
	
	public static DefaultAttributeContainer.Builder createBeetleAttributes() {
        return MobEntity.createMobAttributes()
        		.add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
        		.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
        		.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
        		.add(EntityAttributes.GENERIC_FLYING_SPEED, 3.0);
    }
	
	public void refreshAttributes() {
		this.getAttributes().removeModifiers(current_modifiers);
        Multimap<EntityAttribute, EntityAttributeModifier> multimap = HashMultimap.create();
        multimap.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(
        		EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey(), 
        		maxhealth_cached-8.0f, 
        		EntityAttributeModifier.Operation.ADDITION));
        multimap.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(
        		EntityAttributes.GENERIC_MOVEMENT_SPEED.getTranslationKey(), 
        		speed_cached-0.3f, 
        		EntityAttributeModifier.Operation.ADDITION));
        multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(
        		EntityAttributes.GENERIC_ATTACK_DAMAGE.getTranslationKey(), 
        		damage_cached-3.0f, 
        		EntityAttributeModifier.Operation.ADDITION));
        this.getAttributes().addTemporaryModifiers(multimap);
        this.current_modifiers=multimap;

	}
	
	
    //--------------------
    // BEHAVIOR UTILITIES
    //--------------------
	
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }
    
    public boolean isHealingItem(ItemStack stack) {
        return HEALING_INGREDIENT.test(stack);
    }

	@Override
	public abstract PassiveEntity createChild(ServerWorld var1, PassiveEntity var2);

	@Override
	public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putBoolean("Climbing", this.isClimbing());
        compound.putInt("Size", this.getSizeUnsafe());
        compound.putFloat("Damage", this.getDamageUnsafe());
        compound.putFloat("MaxHealth", this.getMaxHealth());
        compound.putFloat("Speed", this.getSpeedUnsafe());
    }
	
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.setClimbingWall(compound.getBoolean("Climbing"));
        if(compound.contains("Size")) {
        	this.setSize(compound.getInt("Size"));
        }
        if(compound.contains("Damage")) {
        	this.setDamage(compound.getFloat("Damage"));
        }
        if(compound.contains("MaxHealth")) {
        	this.setMaxHealth(compound.getFloat("MaxHealth"));
        }
        if(compound.contains("Speed")) {
        	this.setSpeed(compound.getFloat("Speed"));
        }
    }
}
