package volbot.beetlebox.item.tools;

import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class NetItem extends Item {

	public NetItem(Settings settings) {
		super(settings.maxCount(1));
	}
	
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack saved = null;
        PlayerInventory inv = user.getInventory();
    	for(ItemStack itemStack : inv.main) {
        	if(itemStack.getItem() instanceof BeetleJarItem<?>) {
        		NbtCompound nbt = itemStack.getOrCreateNbt();
        		BeetleJarItem<?> item = (BeetleJarItem<?>) itemStack.getItem();
        		if(nbt.contains("EntityType") || !item.canStore(entity)) {
        			continue;
        		}
        		if(!nbt.contains("EntityType") && item.canStore(entity)) {
        			if(saved == null || 
        					(!PlayerInventory.isValidHotbarIndex(inv.main.indexOf(saved))
        					&& PlayerInventory.isValidHotbarIndex(inv.main.indexOf(itemStack)))
        				){
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
    		entity.writeCustomDataToNbt(comp1);
    		nbt.put("EntityTag",comp1);
    		Text custom_name = entity.getCustomName();
    		if(custom_name!=null && !custom_name.getString().isEmpty()) {
    			nbt.putString("EntityName", custom_name.getString());
    		}
    		nbt.putString("EntityType", EntityType.getId(entity.getType()).toString());
    		saved.setNbt(nbt);
    		entity.remove(RemovalReason.CHANGED_DIMENSION);
    		return ActionResult.SUCCESS;
    	}
    	return ActionResult.FAIL;
    }
	
	

}
