package design.aeonic.iota.common.compat;

import design.aeonic.iota.common.util.ILog;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to store information for loading by optional dependencies.
 * This data is loaded individually by the plugins themselves. Like reflection, but with less existential misery.
 * @author aeonic-dev
 */
public class Compat {
	
	/**
	 * The mod's JEI layer.
	 * @author aeonic-dev
	 */
	public static class Jei {

		private static final ILog log = ILog.LogStatic(Compat.Jei.class);

		public static List<Pair<ItemStack, Component[]>> getItemInfoKeys() {
			List<Pair<ItemStack, Component[]>> itemInfoQueue = new ArrayList<>();
			var languageData = Language.getInstance().getLanguageData();
			List<Pair<ResourceLocation, String>> infoKeys = new ArrayList<>();

			for (var item: ForgeRegistries.ITEMS.getKeys()) {
				var key = String.format("info.item.%s.%s", item.getNamespace(), item.getPath());
				if (languageData.containsKey(key)) {
					log.info("Found JEI item info key " + key);
					infoKeys.add(new ImmutablePair<>(item, key));
				}
			}

			log.info("Queuing JEI info for {} items...", infoKeys.size());
			for (var key: infoKeys) {
				itemInfoQueue.add(new ImmutablePair<>(
						new ItemStack(ForgeRegistries.ITEMS.getValue(key.getLeft())),
						new Component[] { new TranslatableComponent(key.getRight()) }
				));
			}
			return itemInfoQueue;
		}

	}

}
