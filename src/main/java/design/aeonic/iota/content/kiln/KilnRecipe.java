package design.aeonic.iota.content.kiln;

import design.aeonic.iota.Iota;
import design.aeonic.iota.registry.IotaBlocks;
import design.aeonic.iota.registry.IotaRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class KilnRecipe extends AbstractCookingRecipe {

    public KilnRecipe(SmeltingRecipe recipe) {
        this(recipe.getId(), recipe.getGroup(), recipe.getIngredients().get(0), recipe.getResultItem(), recipe.getExperience(), recipe.getCookingTime() / 2);
    }

    public KilnRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime) {
        super(IotaRecipeTypes.KILN, pId, pGroup, pIngredient, pResult, pExperience, pCookingTime);
    }

    @Override
    public ItemStack getToastSymbol() {
        return IotaBlocks.KILN.block.asStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IotaRecipeTypes.KILN_SERIALIZER.get();
    }
}
