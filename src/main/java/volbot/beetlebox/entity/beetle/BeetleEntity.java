package volbot.beetlebox.entity.beetle;

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
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import volbot.beetlebox.entity.ai.BeetleFlyToTreeGoal;

public abstract class BeetleEntity extends AnimalEntity {
	
	private static final TrackedData<Byte> CLIMBING = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Byte> FLYING = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Integer> SIZE = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.INTEGER);

	
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SUGAR_CANE);
	private static final Ingredient HEALING_INGREDIENT = Ingredient.ofItems(Items.SUGAR_CANE);
	
    private boolean isLandNavigator;
    public boolean unSynced = true;
    public int size_cached = 0;

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
	            this.sendSizePacket();
            }
        }
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
    	if(source.equals(DamageSource.CACTUS) || source.equals(DamageSource.FALL)) {
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
	
	public int getSizeUnsafe() {
		return this.dataTracker.get(SIZE);
	}
	
	public void setSize(int size) {
		size_cached = size;
		this.dataTracker.set(SIZE, size_cached);
        if(!(this.world.isClient)) {
            this.sendSizePacket();
        }
        this.calculateDimensions();
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
        if(!this.world.isClient) {
            if(size_cached != 0) {
            	this.dataTracker.startTracking(SIZE, size_cached);
            } else {
            	int size = this.random.nextInt(20)+10;

            	System.out.println(size);
            	this.size_cached = size;
            	this.dataTracker.startTracking(SIZE, size_cached);
            }        	
            this.sendSizePacket();
        } else {
        	this.dataTracker.startTracking(SIZE, size_cached);
            this.unSynced=true;
        }
    }
	
	public void sendSizePacket() {
		if(this.world.getPlayers().size()==0) {
			this.unSynced=true;
			return;
		}
	    ServerPlayerEntity p = (ServerPlayerEntity)this.world.getPlayers().get(0);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(this.getSize());
        buf.writeInt(this.getId());
        ServerPlayNetworking.send(p, new Identifier("beetlebox/beetle_size"), buf);
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
    }
	
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.setClimbingWall(compound.getBoolean("Climbing"));
        if(compound.contains("Size")) {
        	this.setSize(compound.getInt("Size"));
        }
    }
}
