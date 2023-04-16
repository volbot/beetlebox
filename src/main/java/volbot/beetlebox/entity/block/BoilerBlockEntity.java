package volbot.beetlebox.entity.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import volbot.beetlebox.registry.BeetleRegistry;

public class BoilerBlockEntity extends BlockEntity implements SidedInventory,
RecipeUnlocker,
RecipeInputProvider{
	
    protected static final int INPUT_SLOT_INDEX = 0;
    protected static final int OUTPUT_SLOT_INDEX = 1;

    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    int cookTime;
    int cookTimeTotal;

	public BoilerBlockEntity(BlockPos pos, BlockState state) {
		super(BeetleRegistry.BOILER_BLOCK_ENTITY, pos, state);
	}
	
	protected final PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return BoilerBlockEntity.this.cookTime;
                }
                case 1: {
                    return BoilerBlockEntity.this.cookTimeTotal;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    BoilerBlockEntity.this.cookTime = value;
                    break;
                }
                case 1: {
                	BoilerBlockEntity.this.cookTimeTotal = value;
                    break;
                }
            }
        }

		@Override
		public int size() {
			return 2;
		}
	};
    private final RecipeManager.MatchGetter<Inventory, ? extends AbstractCookingRecipe> matchGetter = RecipeManager.createCachedMatchGetter(BeetleRegistry.BOILING_RECIPE_TYPE);

    public static void tick(World world, BlockPos pos, BlockState state, BoilerBlockEntity blockEntity) {
       	
        boolean bl2 = false;
        boolean bl3 = !blockEntity.inventory.get(0).isEmpty();
        if (!blockEntity.getStack(0).isEmpty()) {
        	System.out.println("Grappa");
            Recipe<?> recipe = bl3 ? (Recipe<?>)blockEntity.matchGetter.getFirstMatch(blockEntity, world).orElse(null) : null;
            int i = blockEntity.getMaxCountPerStack();
            if (BoilerBlockEntity.canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory, i)) {
                ++blockEntity.cookTime;
                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    blockEntity.cookTimeTotal = BoilerBlockEntity.getCookTime(world, blockEntity);
                    BoilerBlockEntity.craftRecipe(world.getRegistryManager(), recipe, blockEntity.inventory, i);
                    bl2 = true;
                }
            } else {
                blockEntity.cookTime = 0;
            }
        } else if (blockEntity.cookTime > 0) {
            blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
        }
        if (bl2) {
        	BoilerBlockEntity.markDirty(world, pos, state);
        }
    }
    
    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe == null || !BoilerBlockEntity.canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            return false;
        }
        ItemStack itemStack = slots.get(0);
        ItemStack itemStack2 = recipe.getOutput(registryManager);
        ItemStack itemStack3 = slots.get(1);
        if (itemStack3.isEmpty()) {
            slots.set(1, itemStack2.copy());
        } else if (itemStack3.isOf(itemStack2.getItem())) {
            itemStack3.increment(1);
        }
        itemStack.decrement(1);
        return true;
    }
    
    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (slots.get(0).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack itemStack = recipe.getOutput(registryManager);
        if (itemStack.isEmpty()) {
            return false;
        }
        ItemStack itemStack2 = slots.get(1);
        if (itemStack2.isEmpty()) {
            return true;
        }
        if (!itemStack2.isItemEqual(itemStack)) {
            return false;
        }
        if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
            return true;
        }
        return itemStack2.getCount() < itemStack.getMaxCount();
    }
    
    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }
    
    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areNbtEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == 0 && !bl) {
            this.cookTimeTotal = BoilerBlockEntity.getCookTime(this.world, this);
            this.cookTime = 0;
            this.markDirty();
        }
    }
    
    private static int getCookTime(World world, BoilerBlockEntity boiler) {
        return boiler.matchGetter.getFirstMatch(boiler, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

	@Override
	public int size() {
		return 2;
	}
	
    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }
    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        if (side == Direction.UP) {
            return TOP_SLOTS;
        }
        return SIDE_SLOTS;
    }

	@Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
	
	@Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void setLastRecipe(@Nullable Recipe<?> recipe) { }

    @Override
    @Nullable
    public Recipe<?> getLastRecipe() {
        return null;
    }
    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }
    }
}
