package volbot.beetlebox.item.tools;

import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import volbot.beetlebox.entity.beetle.BeetleEntity;
import volbot.beetlebox.registry.BeetleRegistry;

public class NetItem extends Item {

	public NetItem(Settings settings) {
		super(settings.maxCount(1));
	}
	
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack saved = null;
    	for(ItemStack itemStack : user.getInventory().main) {
        	if(itemStack.getItem() instanceof BeetleJarItem) {
        		NbtCompound nbt = itemStack.getOrCreateNbt();
        		boolean beetle = entity instanceof BeetleEntity;
        		BeetleJarItem item = (BeetleJarItem) itemStack.getItem();
        		if(nbt.contains("EntityType") || ((!beetle)&&item.beetlesOnly)) {
        			continue;
        		}
        		if(nbt.contains("EntityType") || (beetle&&(!item.beetlesOnly))) {
        			if(saved == null){
        				saved = itemStack;
        			}
        			continue;
        		}
        		saved = itemStack;
        	}
        }
    	if(saved != null) {
    		NbtCompound nbt = saved.getNbt();
    		NbtCompound comp1 = new NbtCompound();
    		comp1 = entity.writeNbt(comp1);
    		nbt.put("EntityTag",comp1);
    		nbt.putString("EntityType", EntityType.getId(entity.getType()).toString());
    		saved.setNbt(nbt);
    		entity.remove(RemovalReason.CHANGED_DIMENSION);
    		return ActionResult.SUCCESS;
    	}
    	return ActionResult.FAIL;
    }
	
	

}
