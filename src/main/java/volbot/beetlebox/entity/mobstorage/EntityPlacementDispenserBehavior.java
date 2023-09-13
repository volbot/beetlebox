package volbot.beetlebox.entity.mobstorage;

import com.mojang.logging.LogUtils;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import volbot.beetlebox.item.tools.BeetleJarItem;

import org.slf4j.Logger;

public class EntityPlacementDispenserBehavior
extends FallibleItemDispenserBehavior {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(false);
        Item item = stack.getItem();
        if (item instanceof BeetleJarItem) {
            Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockPos = pointer.getPos().offset(direction);
            
            NbtCompound nbt = stack.getOrCreateNbt();
            if(nbt.getString("EntityType")==""){
                this.setSuccess(false);
                return stack;
            }
			EntityType<?> entityType = EntityType.get(nbt.getString("EntityType")).orElse(null);
			LivingEntity temp = (LivingEntity) entityType.create(pointer.getWorld());
			temp.readNbt(nbt.getCompound("EntityTag"));
			temp.readCustomDataFromNbt(nbt.getCompound("EntityTag"));
			if (nbt.contains("EntityName")) {
				temp.setCustomName(Text.of(nbt.getString("EntityName")));
			} else {
				temp.setCustomName(null);
			}
			temp.teleport(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
			
			boolean success = pointer.getWorld().spawnEntity(temp);
            this.setSuccess(success);
            if(success) {
            	nbt.remove("EntityType");
            	nbt.remove("EntityTag");
            	nbt.remove("EntityName");

            }
        }
        return stack;
    }
}

