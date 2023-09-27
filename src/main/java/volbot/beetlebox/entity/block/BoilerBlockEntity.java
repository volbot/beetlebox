package volbot.beetlebox.entity.block;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import volbot.beetlebox.recipe.BoilingRecipe;
import volbot.beetlebox.registry.BlockRegistry;
import volbot.beetlebox.registry.DataRegistry;

public class BoilerBlockEntity extends BlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

	protected static final int INPUT_SLOT_INDEX = 0;
	protected static final int OUTPUT_SLOT_INDEX = 1;
	
	private static final int[] TOP_SLOTS = new int[] { 0 };
	private static final int[] BOTTOM_SLOTS = new int[] { 1 };
	private static final int[] SIDE_SLOTS = new int[] { 1 };
	protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
	int cookTime;
	int cookTimeTotal;
	private final RecipeManager.MatchGetter<Inventory, ? extends BoilingRecipe> matchGetter = RecipeManager
			.createCachedMatchGetter(DataRegistry.BOILING_RECIPE_TYPE);

	// This field is going to contain the amount, and the fluid variant (more on
	// that in a bit).
	public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
		@Override
		protected FluidVariant getBlankVariant() {
			return FluidVariant.blank();
		}
		 
		@Override
		protected boolean canInsert(FluidVariant variant) {
			return variant.equals(FluidVariant.of(Fluids.WATER));
		}

		@Override
		protected long getCapacity(FluidVariant variant) {
			// Here, you can pick your capacity depending on the fluid variant.
			// For example, if we want to store 8 buckets of any fluid:
			return (8 * FluidConstants.BUCKET) / 81; // This will convert it to mB amount to be consistent;
		}

		@Override
		protected void onFinalCommit() {
			// Called after a successful insertion or extraction, markDirty to ensure the
			// new amount and variant will be saved properly.
			markDirty();
			if (!world.isClient) {
				var buf = PacketByteBufs.create();
				buf.writeBlockPos(pos);
				fluidStorage.variant.toPacket(buf);
				buf.writeLong(BoilerBlockEntity.this.fluidStorage.amount);
				PlayerLookup.tracking(BoilerBlockEntity.this).forEach(player -> {
					ServerPlayNetworking.send(player, new Identifier("beetlebox", "boiler_fluid"), buf);
				});
			}
		}
	};
	
	public BoilerBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.BOILER_BLOCK_ENTITY, pos, state);
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {

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
	
	public boolean fireLit() {
		BlockState below = this.getWorld().getBlockState(this.getPos().add(0, -1, 0));
		if (below.isOf(Blocks.CAMPFIRE) || below.isOf(Blocks.FIRE) || below.isOf(Blocks.LAVA) || below.isOf(Blocks.LAVA_CAULDRON)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canCook(BoilingRecipe recipe) {
		if (recipe == null) {
			return false;
		}
		if (!(recipe.fluid_in.equals(this.fluidStorage.variant)
				&& recipe.fluid_droplets <= this.fluidStorage.amount)) {
			return false;
		}
		return fireLit();
	}

	public static void tick(World world, BlockPos pos, BlockState state, BoilerBlockEntity blockEntity) {
		boolean bl2 = false;
		if (!blockEntity.getStack(0).isEmpty()) {
			BoilingRecipe recipe = (BoilingRecipe) blockEntity.matchGetter.getFirstMatch(blockEntity, world)
					.orElse(null);
			if(!blockEntity.canCook(recipe)) {
				return;
			}
			int i = blockEntity.getMaxCountPerStack();
			if (BoilerBlockEntity.canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory, i)) {
				++blockEntity.cookTime;
				if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
					blockEntity.cookTime = 0;
					blockEntity.cookTimeTotal = BoilerBlockEntity.getCookTime(world, blockEntity);
					BoilerBlockEntity.craftRecipe(world.getRegistryManager(), recipe, blockEntity.inventory,
							blockEntity.fluidStorage, i);
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

	private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable BoilingRecipe recipe,
			DefaultedList<ItemStack> slots, SingleVariantStorage<FluidVariant> fluidStorage, int count) {
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
		Transaction t = Transaction.openOuter();
		fluidStorage.extract(recipe.fluid_in, recipe.fluid_droplets, t);
		t.commit();
		t.close();
		itemStack.decrement(1);
		return true;
	}

	private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe,
			DefaultedList<ItemStack> slots, int count) {
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
		ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
		return stack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		ItemStack stack = Inventories.removeStack(this.inventory, slot);
		markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
		return stack;
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
		}
		this.markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	private static int getCookTime(World world, BoilerBlockEntity boiler) {
		return boiler.matchGetter.getFirstMatch(boiler, world).map(AbstractCookingRecipe::getCookTime).orElse(50);
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : this.inventory) {
			if (itemStack.isEmpty())
				continue;
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
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(),
				Block.NOTIFY_LISTENERS);
	}

	@Override
	public void setLastRecipe(@Nullable Recipe<?> recipe) {
	}

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

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		tag.putShort("CookTime", (short) this.cookTime);
		tag.putShort("CookTimeTotal", (short) this.cookTimeTotal);
		tag.put("fluidVariant", fluidStorage.variant.toNbt());
		tag.putLong("amount", fluidStorage.amount);
		Inventories.writeNbt(tag, this.inventory);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(tag, this.inventory);
		this.cookTime = tag.getShort("CookTime");
		this.cookTimeTotal = tag.getShort("CookTimeTotal");
		fluidStorage.variant = FluidVariant.fromNbt(tag.getCompound("fluidVariant"));
		fluidStorage.amount = tag.getLong("amount");
	}
}
