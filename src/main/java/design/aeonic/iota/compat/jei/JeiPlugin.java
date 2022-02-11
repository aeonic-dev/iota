package design.aeonic.iota.compat.jei;

import design.aeonic.iota.Iota;
import design.aeonic.iota.base.misc.ILog;
import design.aeonic.iota.compat.jei.category.KilnCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * A class for the mod's JEI integration.
 * @author aeonic-dev
 */
@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin, ILog {
	private static final ResourceLocation UID = new ResourceLocation(Iota.MOD_ID, "jei_plugin");

	private KilnCategory kilnCategory;

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registration.addRecipeCategories(kilnCategory = new KilnCategory(guiHelper));
	}

	@Override
	public void registerRecipes(@Nonnull IRecipeRegistration reg) {
		// Item info
		var keys = ItemInfo.getItemInfoKeys();
		for (var key: keys) {
			reg.addIngredientInfo(key.getLeft(), VanillaTypes.ITEM, key.getRight());
		}

		var level = Minecraft.getInstance().level;
		if (level == null) return;
		var manager = level.getRecipeManager();

		kilnCategory.registerRecipes(reg, manager);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		kilnCategory.registerGuiHandlers(registration);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		kilnCategory.registerRecipeTransferHandlers(registration);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		kilnCategory.registerRecipeCatalysts(registration);
	}

	@Nonnull
	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

}
