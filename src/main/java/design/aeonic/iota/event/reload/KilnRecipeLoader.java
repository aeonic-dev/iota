package design.aeonic.iota.event.reload;

import com.google.common.collect.Streams;
import design.aeonic.iota.Iota;
import design.aeonic.iota.base.misc.ILog;
import design.aeonic.iota.content.kiln.KilnRecipe;
import design.aeonic.iota.registry.IotaRecipeTypes;
import design.aeonic.iota.config.IotaConfigHelper;
import net.minecraft.Util;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.tags.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KilnRecipeLoader implements ResourceManagerReloadListener, ILog {

    public static KilnRecipeLoader INSTANCE = new KilnRecipeLoader();

    // track recipe reloads
    private static int current = 0;
    private static int last = -1;
    private static List<KilnRecipe> cachedRecipes = new ArrayList<>();

    public Optional<KilnRecipe> getRecipeFor(RecipeManager manager, Container pInventory, Level pLevel) {
        return getRecipes(manager).stream().flatMap((r) -> Util.toStream(IotaRecipeTypes.KILN.tryMatch(r, pLevel, pInventory))).findFirst();
    }

    public List<KilnRecipe> getRecipes(RecipeManager manager) {
        if (!Iota.commonConfig.enableKiln().get())
            return Collections.emptyList();
        if (current != last) {
            last = current;

            Stream<KilnRecipe> fromSmelting = manager.getAllRecipesFor(RecipeType.SMELTING).stream()
                    .filter(this::smeltingRecipeFilter)
                    .map(KilnRecipe::new);
            Stream<KilnRecipe> fromData = manager.getAllRecipesFor(IotaRecipeTypes.KILN).stream()
                    .filter(Objects::nonNull);

            cachedRecipes = Streams.concat(fromSmelting, fromData).collect(Collectors.toList());
        }
        return cachedRecipes;
    }

    private boolean smeltingRecipeFilter(SmeltingRecipe recipe) {
        List<Tag<Item>> ingredientTags = IotaConfigHelper.TagListCache.of(Iota.serverConfig.kilnSmeltingIngredientTags().get()).get(current);

        List<Tag<Item>> resultTags = IotaConfigHelper.TagListCache.of(Iota.serverConfig.kilnSmeltingResultTags().get()).get(current);

        for (var tag: ingredientTags) {
            if (tag.contains(recipe.getIngredients().get(0).getItems()[0].getItem())) return true;
        }
        for (var tag: resultTags) {
            if (tag.contains(recipe.getResultItem().getItem())) return true;
        }

        return false;
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        KilnRecipeLoader.current ++;
    }
}
