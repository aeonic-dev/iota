package design.aeonic.iota.registry;

import design.aeonic.iota.Iota;
import design.aeonic.iota.content.kiln.KilnRecipe;
import design.aeonic.iota.registry.IotaRegistrate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IotaRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = IotaRegistrate.deferredRegister(ForgeRegistries.RECIPE_SERIALIZERS, FMLJavaModLoadingContext.get().getModEventBus());

    public static final RecipeType<KilnRecipe> KILN = RecipeType.register(Iota.MOD_ID + ":kiln");
    public static final RegistryObject<SimpleCookingSerializer<KilnRecipe>> KILN_SERIALIZER = SERIALIZERS.register(
            "kiln",
            () -> new SimpleCookingSerializer<>(KilnRecipe::new, 100));

    public static ResourceLocation getKey(RecipeType<? extends Recipe<?>> type) {
        return Registry.RECIPE_TYPE.getKey(type);
    }

    public static void register() {}

}
