package volbot.beetlebox.entity.beetle;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
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
import net.minecraft.entity.passive.TameableEntity;
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
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import volbot.beetlebox.entity.ai.BeetleFlyToTreeGoal;
import volbot.beetlebox.entity.ai.BeetleFlyWithPlayerGoal;
import volbot.beetlebox.registry.ItemRegistry;

public abstract class BeetleEntity extends TameableEntity {

	private static final TrackedData<Byte> CLIMBING = DataTracker.registerData(BeetleEntity.class,
			TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Byte> FLYING = DataTracker.registerData(BeetleEntity.class,
			TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Integer> SIZE = DataTracker.registerData(BeetleEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Float> DAMAGE = DataTracker.registerData(BeetleEntity.class,
			TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> SPEED = DataTracker.registerData(BeetleEntity.class,
			TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> MAXHEALTH = DataTracker.registerData(BeetleEntity.class,
			TrackedDataHandlerRegistry.FLOAT);

	private Multimap<EntityAttribute, EntityAttributeModifier> current_modifiers = HashMultimap.create();

	private boolean isLandNavigator;
	public boolean unSynced = true;
	public int size_cached = 0;
	public float damage_cached = 1.0f;
	public float speed_cached = 1.0f;
	public float maxhealth_cached = 1.0f;
	public int tame_progress = 0;

	public int timeFlying = 0;

	public Ingredient healing_ingredient = Ingredient.ofItems(Items.SUGAR_CANE);
	
	public BeetleEntity(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
		switchNavigator(false);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new EscapeDangerGoal(this, 1.0));
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.add(2, new BeetleFlyWithPlayerGoal(this, 1.0));
		this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 6.0f, 2.0f, false));
		this.goalSelector.add(4, new BeetleFlyToTreeGoal(this, 0.75));
		this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(0, new TrackOwnerAttackerGoal(this));
		this.targetSelector.add(1, new AttackWithOwnerGoal(this));
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
			if (unSynced) {
				if (this.size_cached != 0) {
					this.setSize(size_cached);
				} else {
					this.sendPacket();
				}
				this.refreshAttributes();
			}
		}
	}

	@Override
	protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
		if (stack.isOf(Items.SUGAR_CANE)) {
			this.heal(2.5f);
		}
		if (stack.isOf(ItemRegistry.UPGRADE_DORMANT)) {
			NbtCompound item_nbt = stack.getOrCreateNbt();
			if (item_nbt.contains("beetle_helmet_attack")) {
				this.dropItem(ItemRegistry.UPGRADE_H_ATTACK);
			}
			if (item_nbt.contains("beetle_helmet_nv")) {
				this.dropItem(ItemRegistry.UPGRADE_H_NV);
			}
			if (item_nbt.contains("beetle_chest_elytra")) {
				this.dropItem(ItemRegistry.UPGRADE_C_ELYTRA);
			}
			if (item_nbt.contains("beetle_chest_boost")) {
				this.dropItem(ItemRegistry.UPGRADE_C_BOOST);
			}
			if (item_nbt.contains("beetle_legs_wallclimb")) {
				this.dropItem(ItemRegistry.UPGRADE_L_CLIMB);
			}
			if (item_nbt.contains("beetle_legs_2jump")) {
				this.dropItem(ItemRegistry.UPGRADE_L_2JUMP);
			}
			if (item_nbt.contains("beetle_boots_falldamage")) {
				this.dropItem(ItemRegistry.UPGRADE_B_FALLDAM);
			}
			if (item_nbt.contains("beetle_boots_speed")) {
				this.dropItem(ItemRegistry.UPGRADE_B_SPEED);
			}
			if (item_nbt.contains("beetle_boots_step")) {
				this.dropItem(ItemRegistry.UPGRADE_B_STEP);
			}
		}
		super.eat(player, hand, stack);
	}

	@Override
	public EntityView method_48926() {
		return this.getWorld();
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ItemRegistry.NET) || itemStack.isOf(ItemRegistry.BEETLE_JAR) || itemStack.isOf(ItemRegistry.SUBSTRATE_JAR)) {
			return ActionResult.PASS;
		}
		if (itemStack.isOf(ItemRegistry.UPGRADE_DORMANT)) {
			if (!this.world.isClient && this.canEat()) {
				this.eat(player, hand, itemStack);
				/*
				 * if (this.isBreedingItem(itemStack)) { if (this.getHealth() <
				 * this.getMaxHealthMult()) { this.heal(2.0f); return ActionResult.SUCCESS; }
				 * int i = this.getBreedingAge(); if (!this.world.isClient && i == 0 &&
				 * this.canEat()) { this.lovePlayer(player); } if (this.isBaby()) {
				 * this.growUp(AnimalEntity.toGrowUpAge(-i), true); } } if (this.isBaby()) {
				 * return ActionResult.FAIL; }
				 */
				if (this.world.isClient) {
					return ActionResult.CONSUME;
				}
				return ActionResult.SUCCESS;
			}
		}
		if (this.isHealingItem(itemStack)) {
			if(this.getHealth() < this.getMaxHealth()) {
				this.eat(player, hand, itemStack);
				if (this.world.isClient) {
					return ActionResult.CONSUME;
				}
				return ActionResult.SUCCESS;
			}
		}
		if (this.isTamed()) {
		}
		return super.interactMob(player, hand);
	}
	
	public boolean isHealingItem(ItemStack stack) {
		return healing_ingredient.test(stack);
	}

	@Override
	public EntityDimensions getDimensions(EntityPose p) {
		int size = this.getSize() / 10;
		return EntityDimensions.fixed(0.4f * size, 0.4f * size);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.ARTHROPOD;
	}

	// --------------------
	// MOVEMENT UTILITIES
	// --------------------

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.isOf(DamageTypes.CACTUS) || source.isOf(DamageTypes.FALL)) {
			return false;
		}
        if (!this.world.isClient) {
            this.setSitting(false);
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
		} catch (NullPointerException e) {
			this.unSynced = true;
			return this.size_cached;
		}
	}

	public float getSpeedMult() {
		try {
			return this.dataTracker.get(SPEED);
		} catch (NullPointerException e) {
			this.unSynced = true;
			return this.speed_cached;
		}
	}

	public float getDamageMult() {
		try {
			return this.dataTracker.get(DAMAGE);
		} catch (NullPointerException e) {
			this.unSynced = true;
			return this.damage_cached;
		}
	}

	public float getMaxHealthMult() {
		try {
			return this.dataTracker.get(MAXHEALTH);
		} catch (NullPointerException e) {
			this.unSynced = true;
			return this.maxhealth_cached;
		}
	}

	public int getSizeUnsafe() {
		return this.dataTracker.get(SIZE);
	}

	public void setSize(int size) {
		if (size < 10) {
			size = 10;
		}
		size_cached = size;
		this.dataTracker.set(SIZE, size_cached);
		if (!(this.world.isClient)) {
			this.sendPacket();
		}
		this.calculateDimensions();
	}

	public void setDamageMult(float damage) {
		if (damage < 0.1f) {
			damage = 0.1f;
		}
		this.dataTracker.set(DAMAGE, damage_cached);
		if (!(this.world.isClient)) {
			this.sendPacket();
		}
		this.refreshAttributes();
	}

	public void setMaxHealthMult(float maxhealth) {
		if (maxhealth < 0.1f) {
			maxhealth = 0.1f;
		}
		maxhealth_cached = maxhealth;
		this.dataTracker.set(MAXHEALTH, maxhealth_cached);
		if (!(this.world.isClient)) {
			this.sendPacket();
		}
		this.refreshAttributes();
	}

	public void setSpeedMult(float speed) {
		if (speed < 0.5f) {
			speed = 0.5f;
		}
		speed_cached = speed;
		this.dataTracker.set(SPEED, speed_cached);
		if (!(this.world.isClient)) {
			this.sendPacket();
		}
		this.refreshAttributes();
	}

	public void setClimbingWall(boolean climbing) {
		byte b = this.dataTracker.get(CLIMBING);
		b = climbing ? (byte) (b | 1) : (byte) (b & 0xFFFFFFFE);
		this.dataTracker.set(CLIMBING, b);
	}

	public boolean isFlying() {
		return (this.dataTracker.get(FLYING) & 1) != 0;
	}

	public void setFlying(boolean flying) {
		byte b = this.dataTracker.get(FLYING);
		b = flying ? (byte) (b | 1) : (byte) (b & 0xFFFFFFFE);
		this.dataTracker.set(FLYING, (byte) b);
		this.switchNavigator(!flying);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(CLIMBING, (byte) 0);
		this.dataTracker.startTracking(FLYING, (byte) 0);
		if (!this.world.isClient) {
			if (size_cached != 0) {
				this.dataTracker.startTracking(SIZE, size_cached);
			} else {
				int size = this.random.nextInt(20) + 10;

				this.size_cached = size;
				this.dataTracker.startTracking(SIZE, size_cached);
			}

			if (damage_cached != 0) {
				this.dataTracker.startTracking(DAMAGE, damage_cached);
			} else {
				float damage = (this.random.nextInt(15) - 5) * 0.1f + 1.0f; // between 0.5x and 2.0x default

				this.damage_cached = damage;
				this.dataTracker.startTracking(DAMAGE, damage_cached);
			}

			if (speed_cached != 0) {
				this.dataTracker.startTracking(SPEED, speed_cached);
			} else {
				float speed = (this.random.nextInt(4) - 2) * 0.1f + 1.0f; // between 0.8x and 1.4x default

				this.speed_cached = speed;
				this.dataTracker.startTracking(SPEED, speed_cached);
			}

			if (maxhealth_cached != 0) {
				this.dataTracker.startTracking(MAXHEALTH, maxhealth_cached);
			} else {
				float maxhealth = (this.random.nextInt(10) - 5) * 0.1f + 1.0f; // between 0.5x and 1.5x default

				this.maxhealth_cached = maxhealth;
				this.dataTracker.startTracking(MAXHEALTH, maxhealth_cached);
			}
			this.sendPacket();
		} else {
			this.dataTracker.startTracking(SIZE, size_cached);
			this.dataTracker.startTracking(DAMAGE, damage_cached);
			this.dataTracker.startTracking(SPEED, speed_cached);
			this.dataTracker.startTracking(MAXHEALTH, maxhealth_cached);
			this.unSynced = true;
		}
	}

	public void sendPacket() {
		if (this.world.getPlayers().size() == 0) {
			this.unSynced = true;
			return;
		}
		ServerPlayerEntity p = (ServerPlayerEntity) this.world.getPlayers().get(0);
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(this.getSize());
		buf.writeFloat(this.getDamageMult());
		buf.writeFloat(this.getSpeedMult());
		buf.writeFloat(this.getMaxHealthMult());
		buf.writeUuid(this.getUuid());
		ServerPlayNetworking.send(p, new Identifier("beetlebox/beetle_packet"), buf);
		this.unSynced = false;
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
			this.navigation.setSpeed(10.0f);
		} else {
			this.moveControl = new FlightMoveControl(this, 6, false);
			this.navigation = new BirdNavigation(this, this.getEntityWorld());
			this.isLandNavigator = false;
			this.navigation.setSpeed(1.0f);
		}
	}

	public static DefaultAttributeContainer.Builder createBeetleAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5);
	}

	public void refreshAttributes() {
		AttributeContainer attributes = this.getAttributes();
		float maxhealthOld = this.getMaxHealth();
		attributes.removeModifiers(current_modifiers);
		Multimap<EntityAttribute, EntityAttributeModifier> multimap = HashMultimap.create();
		multimap.put(EntityAttributes.GENERIC_MAX_HEALTH,
				new EntityAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey(),
						this.getMaxHealthMult(), EntityAttributeModifier.Operation.MULTIPLY_BASE));
		multimap.put(EntityAttributes.GENERIC_MOVEMENT_SPEED,
				new EntityAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED.getTranslationKey(),
						this.getSpeedMult(), EntityAttributeModifier.Operation.MULTIPLY_BASE));
		multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE.getTranslationKey(),
						this.getDamageMult(), EntityAttributeModifier.Operation.MULTIPLY_BASE));
		attributes.addTemporaryModifiers(multimap);
		float maxhealthNew = this.getMaxHealth();
		if (maxhealthOld < maxhealthNew) {
			this.heal(maxhealthNew - maxhealthOld);
		} else if (this.getHealth() > maxhealthNew) {
			this.setHealth(maxhealthNew);
		}
		this.current_modifiers = multimap;
	}

	protected void generateGeneticStats(BeetleEntity a, BeetleEntity b) {
		double sdev;
		double mean;
		sdev = 2;
		mean = (a.getSize() + b.getSize() / 2.0);
		int new_size = (int)Math.round(a.getRandom().nextGaussian() * sdev + mean);
		sdev = 1.5;
		mean = (a.getDamageMult() + b.getDamageMult() / 2.0);
		float new_damage = (float) (a.getRandom().nextGaussian() * sdev + mean);
		sdev = 1.5;
		mean = (a.getMaxHealthMult() + b.getMaxHealthMult() / 2.0);
		float new_maxhealth = (float) (a.getRandom().nextGaussian() * sdev + mean);
		sdev = 1.5;
		mean = (a.getSpeedMult() + b.getSpeedMult() / 2.0);
		float new_speed = (float) (a.getRandom().nextGaussian() * sdev + mean);
		
		this.size_cached = new_size;
		this.damage_cached = new_damage;
		this.maxhealth_cached = new_maxhealth;
		this.speed_cached = new_speed;
	}

	// --------------------
	// BEHAVIOR UTILITIES
	// --------------------

	@Override
	public abstract PassiveEntity createChild(ServerWorld var1, PassiveEntity var2);

	@Override
	public void writeCustomDataToNbt(NbtCompound compound) {
		super.writeCustomDataToNbt(compound);
		compound.putBoolean("Flying", this.isFlying());
		compound.putBoolean("Climbing", this.isClimbing());
		compound.putInt("Size", this.getSizeUnsafe());
		compound.putFloat("Damage", this.getDamageMult());
		compound.putFloat("MaxHealth", this.getMaxHealthMult());
		compound.putFloat("Speed", this.getSpeedMult());
		compound.putInt("TameProgress", tame_progress);
	}

	public void readCustomDataFromNbt(NbtCompound compound) {
		super.readCustomDataFromNbt(compound);
		this.setFlying(compound.getBoolean("Flying"));
		this.setClimbingWall(compound.getBoolean("Climbing"));
		if (compound.contains("Size")) {
			this.setSize(compound.getInt("Size"));
		}
		if (compound.contains("Damage")) {
			this.setDamageMult(compound.getFloat("Damage"));
		}
		if (compound.contains("MaxHealth")) {
			this.setMaxHealthMult(compound.getFloat("MaxHealth"));
		}
		if (compound.contains("Speed")) {
			this.setSpeedMult(compound.getFloat("Speed"));
		}
		tame_progress = compound.getInt("TameProgress");
	}
}