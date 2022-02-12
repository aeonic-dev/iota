package design.aeonic.iota.event;

import design.aeonic.iota.Iota;
import design.aeonic.iota.config.condition.KilnConfigCondition;
import design.aeonic.iota.event.setup.DispenserBehaviors;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Iota.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // Main thread
        event.enqueueWork(() -> {
            DispenserBehaviors.register();
        });
    }

    // Recipe conditions
    @SubscribeEvent
    public static void registerConditionSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(new KilnConfigCondition().serializer());
    }
}
