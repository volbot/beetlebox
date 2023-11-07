package volbot.beetlebox.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.compat.trinkets.BeetlepackTrinketRenderer;
import volbot.beetlebox.item.equipment.BeetleArmorAbilities;
import volbot.beetlebox.item.equipment.BeetleArmorItem;
import volbot.beetlebox.item.equipment.BeetlepackItem;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
		if (FabricLoader.getInstance().isModLoaded("trinkets")) {
			if (newStack.getItem() instanceof BeetlepackItem) {
				if (BeetlepackTrinketRenderer.getBackStack((PlayerEntity) (Object) this)
						.getItem() instanceof BeetlepackItem) {
					PlayerEntity user = ((PlayerEntity) (Object) this);
					if(user.getWorld().isClient()) {
						super.onEquipStack(slot, newStack, oldStack);
						return;
					}
					
					if (user.getInventory().getEmptySlot() == -1) {
						ItemStack newNewStack = newStack.copy();
						newStack.setCount(0);
						user.dropStack(newNewStack);
					} else {
						user.giveItemStack(newStack);
					}
					super.onEquipStack(slot, newStack, oldStack);
					return;
				}
			}
		}
		super.onEquipStack(slot, newStack, oldStack);
	}

	@Inject(method = "attack", at = @At(value = "RETURN"))
	private void attack(Entity target, CallbackInfo info) {
		ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);
		if (helmet.getItem() instanceof BeetleArmorItem) {
			Multimap<EntityAttribute, EntityAttributeModifier> attributes = this.getMainHandStack().getItem()
					.getAttributeModifiers(EquipmentSlot.MAINHAND);
			Collection<EntityAttributeModifier> attackModifiers = attributes
					.get(EntityAttributes.GENERIC_ATTACK_DAMAGE);

			if (!attackModifiers.isEmpty() && target instanceof LivingEntity) {
				BeetleArmorAbilities.helmetAttack((LivingEntity) (Object) this, (LivingEntity) target, helmet);
			}
		}
	}

	@Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE"), cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);
		if ((boots.getItem() instanceof BeetleArmorItem)
				&& (boots.getOrCreateNbt().contains("beetle_boots_falldamage"))) {
			if (source.isOf(DamageTypes.FALL) || source.isOf(DamageTypes.FLY_INTO_WALL)) {
				info.setReturnValue(true);
			}
		}
	}

	@Override
	protected void setFlag(int index, boolean value) {
		super.setFlag(index, value);
		if (this.getAttacker() == null) {
			if (index == Entity.FALL_FLYING_FLAG_INDEX) {
				if (value) {
					BeetlepackItem.deployBeetles(((PlayerEntity) (Object) this));
				} else {
					BeetlepackItem.recallBeetles(((PlayerEntity) (Object) this));
				}
			}
		}
	}

	@Override
	public void setAttacker(@Nullable LivingEntity attacker) {
		super.setAttacker(attacker);
		if (this.isFallFlying() && attacker != null) {
			ItemStack beetlepack = BeetlepackItem.getBeetlepackOnPlayer((PlayerEntity) (Object) this);
			if (!beetlepack.isEmpty() && beetlepack.getOrCreateNbt().contains("FlightSpawn")) {
				BeetlepackItem.recallBeetles(((PlayerEntity) (Object) this));
			}
		}
	}

	@Override
	protected void applyMovementEffects(BlockPos pos) {
		if (!this.getEquippedStack(EquipmentSlot.FEET).getOrCreateNbt().contains("beetle_boots_speed")) {
			this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
					.removeModifier(BeetleArmorItem.speed_boost_attribute);
		} else {
			EntityAttributeInstance instance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			if (!instance.hasModifier(BeetleArmorItem.speed_boost_attribute)) {
				instance.addTemporaryModifier(BeetleArmorItem.speed_boost_attribute);
			}
		}
		super.applyMovementEffects(pos);
	}

}