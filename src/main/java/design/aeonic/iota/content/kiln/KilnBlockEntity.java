package design.aeonic.iota.content.kiln;

import design.aeonic.iota.base.block.entity.ItemHandlerBlockEntity;
import design.aeonic.iota.base.block.menu.SimpleMenu;
import design.aeonic.iota.registry.IotaRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class KilnBlockEntity extends ItemHandlerBlockEntity<KilnBlockEntity, KilnMenu> {

    /**
     * Literally only a class so I can collapse this in my IDE, I'm not actually this organized<br /><br />
     * "jUsT mOvE iT tO tHe BoTtOm" no <3
     */
    public static final class Slots {

        public static final SlotType KILN_INPUT = new SlotType(
                Sides.TOP,
                KilnBlockEntity::itemIsSmeltable,
                SlotType.INPUT_TOP.insertItemFunc(),
                SlotType.INPUT_TOP.extractItemFunc(),
                SlotType.INPUT_TOP.onPlayerTookSlot()
        );

        public static final SlotType KILN_FUEL = new SlotType(
                SlotType.FUEL_SIDES.automationAccessSides(),
                (__, stack) -> getBurnTime(stack) > 0,
                (__, stack, ___) -> getBurnTime(stack) > 0 ? null : stack,
                SlotType.FUEL_SIDES.extractItemFunc(),
                SlotType.FUEL_SIDES.onPlayerTookSlot()
        );

        public static final SlotType KILN_RESULT = new SlotType(
                Sides.BOTTOM,
                SlotType.RESULT_DOWN.isItemValidFunc(),
                SlotType.RESULT_DOWN.insertItemFunc(),
                SlotType.RESULT_DOWN.extractItemFunc(),
                KilnBlockEntity::onResultTaken);
    }

    public static final SlotType[] STRUCTURE = new SlotType[] {
            Slots.KILN_INPUT,
            Slots.KILN_FUEL,
            Slots.KILN_RESULT
    };

    protected final ContainerData dataHolder = new ContainerData() {
        public int getCount() { return 4; }

        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> litTime;
                case 1 -> litDuration;
                case 2 -> cookingProgress;
                case 3 -> cookingTotalTime;
                default -> -1;
            };
        }

        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> litTime = pValue;
                case 1 -> litDuration = pValue;
                case 2 -> cookingProgress = pValue;
                case 3 -> cookingTotalTime = pValue;
            }
        }
    };

    private static final int SLOT_INPUT = 0;
    private static final int SLOT_FUEL = 1;
    private static final int SLOT_RESULT = 2;

    protected int litDuration;
    protected int litTime;
    protected int cookingProgress;
    protected int cookingTotalTime;

    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public KilnBlockEntity(BlockEntityType<KilnBlockEntity> type, BlockPos pos, BlockState state, Supplier<MenuType<KilnMenu>> menuType, SimpleMenu.ServerMenuFactory<KilnBlockEntity, KilnMenu> menuFactory) {
        super(type, pos, state, menuType, menuFactory);
    }

    @Override
    public ContainerData serverDataHolder() {
        return dataHolder;
    }

    @Override
    public int numDataSlots() {
        return dataHolder.getCount();
    }

    @Override
    public void tick() {
        if (level == null || !(level instanceof ServerLevel)) return;
        var oldLit = isLit();
        if (oldLit) litTime--;

        var recipe = getCurrentRecipe();
        var inputStack = itemHandler.getStackInSlot(SLOT_INPUT);
        var resultStack = itemHandler.getStackInSlot(SLOT_RESULT);

        if (recipe.isEmpty()) {
            // No recipe present; can't burn
            cookingProgress = 0;
        }
        else if (litTime > 0) {
            // Already burning
            if (!canStartOrKeepBurning(recipe.get(), resultStack)) {
                // Cannot keep burning
                cookingProgress = Math.max(cookingProgress, cookingProgress - 2);
            }
        }
        else {
            if (!startBurning(recipe)) {
                // Did not start burning
                cookingProgress = Math.max(cookingProgress, cookingProgress - 2);
            }
        }

        if (isLit() && recipe.isPresent() && ++cookingProgress >= cookingTotalTime) {
            // Finished cooking
            cookingProgress = 0;
            var recipeResult = recipe.get().assemble(getRecipeWrapper());
            inputStack.shrink(1);

            recipesUsed.addTo(recipe.get().getId(), 1);

            if (inputStack.isEmpty())
                itemHandler.setStackInSlot(SLOT_INPUT, inputStack.getContainerItem());

            if (resultStack.isEmpty())
                itemHandler.setStackInSlot(SLOT_RESULT, recipeResult);

            else if (resultStack.is(recipeResult.getItem()))
                resultStack.grow(recipeResult.getCount());
        }

        if (oldLit != isLit())
            level.setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, isLit()), 3);
    }

    protected static void onResultTaken(ItemHandlerBlockEntity<?, ?> be, ServerPlayer player, ItemStack stack) {
        if (be instanceof KilnBlockEntity be1) {
            be1.awardUsedRecipesAndXP(player, player.getLevel());
        }
    }

    protected void awardUsedRecipesAndXP(ServerPlayer player, ServerLevel level) {
        List<Recipe<?>> recipes = new ArrayList<>();
        for (var entry: recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe -> {
                recipes.add(recipe);
                createExperience(level, player.position(), entry.getIntValue(), ((AbstractCookingRecipe) recipe).getExperience() );
            });
        }
        player.awardRecipes(recipes);
        recipesUsed.clear();
    }

    protected static void createExperience(ServerLevel p_154999_, Vec3 p_155000_, int p_155001_, float p_155002_) {
        int i = Mth.floor((float)p_155001_ * p_155002_);
        float f = Mth.frac((float)p_155001_ * p_155002_);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrb.award(p_154999_, p_155000_, i);
    }

    public boolean isLit() { return litTime > 0; }

    protected boolean startBurning(Optional<KilnRecipe> recipe) {
        // Assumes the block is not already burning
        if (!hasFuel()) return false;

        var fuelStack = itemHandler.getStackInSlot(SLOT_FUEL);
        var resultStack = itemHandler.getStackInSlot(SLOT_RESULT);

        if (recipe.isPresent()) {
            if (canStartOrKeepBurning(recipe.get(), resultStack)) {
                litDuration = getBurnTime(fuelStack);
                litTime = litDuration;

                if (fuelStack.getCount() == 1) {
                    itemHandler.setStackInSlot(SLOT_FUEL, fuelStack.getContainerItem());
                }
                else fuelStack.shrink(1);

                return true;
            }
        }
        return false;
    }

    protected boolean canStartOrKeepBurning(KilnRecipe recipe, ItemStack resultStack) {
        return resultStack.isEmpty() || resultStack.is(recipe.getResultItem().getItem()) &&
                resultStack.getCount() + recipe.getResultItem().getCount() <= resultStack.getMaxStackSize();
    }

    protected boolean hasFuel() {
        return getBurnTime(itemHandler.getStackInSlot(SLOT_FUEL)) > 0;
    }

    protected static int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, IotaRecipeTypes.KILN) / 2;
    }

    protected static boolean itemIsSmeltable(BlockEntity be, ItemStack stack) {
        return be.getLevel() != null && KilnRecipeLoader.INSTANCE.getRecipeFor(be.getLevel().getRecipeManager(),
                new SimpleContainer(stack), be.getLevel()).isPresent();
    }

    protected Optional<KilnRecipe> getCurrentRecipe() {
        if (level == null) return Optional.empty();
        return KilnRecipeLoader.INSTANCE.getRecipeFor(level.getRecipeManager(),
                    getRecipeWrapper(), level);
    }

    protected RecipeWrapper getRecipeWrapper() {
        return new RecipeWrapper(itemHandler);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        litTime = pTag.getInt("BurnTime");
        cookingProgress = pTag.getInt("CookTime");
        cookingTotalTime = pTag.getInt("CookTimeTotal");
        litDuration = getBurnTime(itemHandler.getStackInSlot(SLOT_FUEL));

        CompoundTag compoundtag = pTag.getCompound("RecipesUsed");
        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putInt("BurnTime", litTime);
        pTag.putInt("CookTime", cookingProgress);
        pTag.putInt("CookTimeTotal", cookingTotalTime);

        CompoundTag compound = new CompoundTag();
        this.recipesUsed.forEach((loc, val) -> compound.putInt(loc.toString(), val));
        pTag.put("RecipesUsed", compound);
    }

    @Override
    public SlotType[] getSlotStructure() {
        return STRUCTURE;
    }
}
