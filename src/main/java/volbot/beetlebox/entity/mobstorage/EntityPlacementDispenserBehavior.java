package volbot.beetlebox.entity.mobstorage;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import volbot.beetlebox.item.tools.BeetleJarItem;
import volbot.beetlebox.item.tools.LarvaJarItem;

public class EntityPlacementDispenserBehavior extends FallibleItemDispenserBehavior {
	@Override
	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		this.setSuccess(false);
		Item item = stack.getItem();
		if (item instanceof BeetleJarItem || item instanceof LarvaJarItem) {
			Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
			BlockPos blockPos = pointer.getPos().offset(direction);
			this.setSuccess(BeetleJarItem.trySpawnFromJar(stack, blockPos, pointer.getWorld()).isPresent());
		}
		return stack;
	}
}
