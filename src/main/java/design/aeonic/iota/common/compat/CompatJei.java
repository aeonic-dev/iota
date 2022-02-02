package design.aeonic.iota.common.compat;

import design.aeonic.iota.Iota;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

/**
 * A class for the mod's JEI integration.
 * @author aeonic-dev
 */
@JeiPlugin
public class CompatJei implements IModPlugin{
	
	private static final ResourceLocation UID = new ResourceLocation(Iota.MOD_ID, "jei_plugin");
	
	@Override
	public void registerRecipes(IRecipeRegistration reg) {
		var keys = Compat.Jei.getItemInfoKeys();

		for (var key: keys) {
			reg.addIngredientInfo(key.getLeft(), VanillaTypes.ITEM, key.getRight());
		}
	}
	
	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

}
