package design.aeonic.iota.common.reg;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import design.aeonic.iota.Iota;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * A class to store the mod's items.
 * @author aeonic-dev
 */
@EventBusSubscriber(modid = Iota.MOD_ID, bus = Bus.MOD)
public class IotaItems {

	public static final ItemEntry<Item> TEST_ITEM = Iota.REGISTRATE.object("test_item")
			.item(Item::new)
					.defaultModel()
					.recipe((ctx, prv) -> prv.singleItem(DataIngredient.items(IotaBlocks.TEST_BLOCK), () -> ctx.get(), 1, 9))
					.register();

}
