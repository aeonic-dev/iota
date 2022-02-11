package design.aeonic.iota.registry;

import design.aeonic.iota.Iota;
import design.aeonic.iota.config.condition.KilnConfigCondition;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Iota.MOD_ID)
public class IotaRecipeConditions {

    @SubscribeEvent
    public static void registerConditionSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(new KilnConfigCondition().serializer());
    }

}
