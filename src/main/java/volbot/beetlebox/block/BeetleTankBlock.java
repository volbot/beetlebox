package volbot.beetlebox.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import volbot.beetlebox.entity.block.TankBlockEntity;

public class BeetleTankBlock<T extends LivingEntity> extends BlockWithEntity {

	public BeetleTankBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TankBlockEntity(pos, state);
	}
	
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    
    public boolean canStore(Entity entity) {
    	  try{
    		    @SuppressWarnings({ "unchecked", "unused" })
				T test = (T)entity;
    		    return true;
    		  }catch(ClassCastException e){
    		    return false;
    		  }
    }

}
