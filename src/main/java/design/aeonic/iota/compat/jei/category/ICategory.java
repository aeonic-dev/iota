package design.aeonic.iota.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;

public interface ICategory<C extends IRecipeCategory<R>, R extends Recipe<?>> extends IRecipeCategory<R> {

    /**
     * Creates the category.
     */
    C create(IGuiHelper helper);

    default void registerRecipes(IRecipeRegistration reg, RecipeManager manager) {}

    default void registerGuiHandlers(IGuiHandlerRegistration reg) {}

    default void registerRecipeTransferHandlers(IRecipeTransferRegistration reg) {}

    default void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {}
}
