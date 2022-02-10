package design.aeonic.iota.base.jei;

import design.aeonic.iota.base.jei.Constants;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;

/**
 * Repackaged from JEI source, under the MIT license.
 * https://github.com/mezz/JustEnoughItems/blob/1.18/src/main/java/mezz/jei/plugins/vanilla/cooking/FurnaceVariantCategory.java
 * @author mezz
 */
public abstract class FurnaceVariantCategory<T> implements IRecipeCategory<T> {
	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;

	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;

	public FurnaceVariantCategory(IGuiHelper guiHelper) {
		staticFlame = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
	}
}
