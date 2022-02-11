package design.aeonic.iota.compat.jei.category;

import design.aeonic.iota.Iota;
import design.aeonic.iota.base.block.entity.MenuBlockEntity;
import design.aeonic.iota.base.jei.AbstractCookingCategory;
import design.aeonic.iota.content.kiln.KilnMenu;
import design.aeonic.iota.content.kiln.KilnRecipe;
import design.aeonic.iota.content.kiln.KilnRecipeLoader;
import design.aeonic.iota.content.kiln.KilnScreen;
import design.aeonic.iota.registry.IotaBlocks;
import design.aeonic.iota.registry.IotaRecipeTypes;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.List;

public class KilnCategory extends AbstractCookingCategory<KilnRecipe> implements ICategory<KilnCategory, KilnRecipe> {
    public KilnCategory(IGuiHelper guiHelper) {
        super(guiHelper,
                IotaBlocks.KILN.block(),
                MenuBlockEntity.getDisplayName(IotaBlocks.KILN.block()).getString(),
                100);
    }

    @Override
    public void registerRecipes(IRecipeRegistration reg, RecipeManager manager) {
        if (Iota.commonConfig.enableKiln().get())
            reg.addRecipes(KilnRecipeLoader.INSTANCE.getRecipes(manager), getUid());
        else
            reg.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, List.of(IotaBlocks.KILN.block.asStack()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration reg) {
        reg.addRecipeClickArea(
                KilnScreen.class, 78, 32, 28, 23,
                getUid(), VanillaRecipeCategoryUid.FUEL);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration reg) {
        reg.addRecipeTransferHandler(
                KilnMenu.class, getUid(), 0, 1, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        if (Iota.commonConfig.enableKiln().get())
            reg.addRecipeCatalyst(IotaBlocks.KILN.block.asStack(), getUid(), VanillaRecipeCategoryUid.FUEL);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return IotaRecipeTypes.getKey(IotaRecipeTypes.KILN);
    }

    @Nonnull
    @Override
    public Class<KilnRecipe> getRecipeClass() {
        return KilnRecipe.class;
    }

    @Override
    public KilnCategory create(IGuiHelper helper) {
        return null;
    }
}
