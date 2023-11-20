package volbot.beetlebox.entity.projectile;

import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.entity.mobstorage.ContainedEntity;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.registry.BeetleRegistry;
import volbot.beetlebox.registry.ItemRegistry;

public class BeetleProjectileEntity extends PersistentProjectileEntity implements FlyingItemEntity {

	public ContainedEntity entity;
	public boolean landed = false;
	public boolean unSynced = true;

	public BeetleProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public BeetleProjectileEntity(World world) {
		super(BeetleRegistry.BEETLE_PROJECTILE, world);
	}

	public BeetleProjectileEntity(World world, double x, double y, double z) {
		super(BeetleRegistry.BEETLE_PROJECTILE, x, y, z, world);
	}

	public BeetleProjectileEntity(World world, LivingEntity owner, ItemStack jar_stack) {
		super(BeetleRegistry.BEETLE_PROJECTILE, owner, world);
		entity = BeetleJarItem.getContained(jar_stack);
		this.sendPacket();
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemRegistry.BEETLE_JAR.getDefaultStack();
	}

	@Override
	public ItemStack getStack() {
		return asItemStack();
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.world.isClient) {
			if (unSynced) {
				if (this.entity == null) {
					this.unSynced = false;
				} else {
					this.sendPacket();
				}
			}
		}
	}
	
	@Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.landed = true;
    }

	public void sendPacket() {
		World world = getEntityWorld();
		if (this.world.getPlayers().size() == 0) {
			this.unSynced = true;
			return;
		}
		if (!world.isClient && entity != null && this.getOwner() instanceof PlayerEntity) {
			ServerPlayerEntity p = (ServerPlayerEntity) this.getOwner();
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeBoolean(landed);
			buf.writeString(entity.getContainedId());
			buf.writeNbt(entity.getEntityData());
			buf.writeString(entity.CustomName());
			UUID entity_id = this.getUuid();
			buf.writeUuid(entity_id);
			ServerPlayNetworking.send(p, new Identifier("beetlebox", "beetle_proj_packet"), buf);
		}
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putBoolean("Landed", this.landed);
		nbt.putBoolean("UnSynced", this.unSynced);
		if (entity != null) {
			nbt.putString("EntityType", entity.contained_id);
			nbt.putString("EntityName", entity.custom_name);
			if (entity.entity_data != null) {
				nbt.put("EntityTag", entity.entity_data);
			}
		}
		return nbt;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.landed = nbt.getBoolean("Landed");
		this.unSynced = nbt.getBoolean("UnSynced");
		if (nbt.contains("EntityType")) {
			entity = new ContainedEntity(nbt.getString("EntityType"), nbt.getCompound("EntityTag"),
					nbt.getString("EntityName"));
		} else {
			entity = null;
		}
	}

}
