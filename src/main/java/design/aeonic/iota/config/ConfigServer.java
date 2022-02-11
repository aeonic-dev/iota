package design.aeonic.iota.config;

import design.aeonic.iota.base.misc.ConfigHelper;
import design.aeonic.iota.base.misc.ConfigHelper.ConfigObject;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.Tags;

public record ConfigServer(
        // Vanilla tweaks
        ConfigValue<Boolean> parrotsFollowSeeds,
        ConfigValue<Boolean> snowGolemsFollowSnowballs,
        ConfigValue<Boolean> villagersFollowEmeralds,
        ConfigValue<Boolean> wanderingTradersFollowEmeralds,

        ConfigValue<Boolean> villagersCanBeLeashed,
        ConfigValue<Boolean> wanderingTradersCanBeLeashed,
        ConfigValue<Boolean> turtlesCanBeLeashed,

        ConfigValue<Boolean> speedUpCowFollowing,
        ConfigValue<Double> cowFollowingSpeedMultiplier,
        ConfigValue<Boolean> speedUpPigFollowing,
        ConfigValue<Double> pigFollowingSpeedMultiplier,
        ConfigValue<Boolean> speedUpSheepFollowing,
        ConfigValue<Double> sheepFollowingSpeedMultiplier,
        ConfigValue<Boolean> speedUpChickenFollowing,
        ConfigValue<Double> chickenFollowingSpeedMultiplier,

        // Additions
        ConfigValue<Double> kilnSmeltingSpeedMultiplier,
        ConfigObject<IotaConfigHelper.TagList> kilnSmeltingIngredientTags,
        ConfigObject<IotaConfigHelper.TagList> kilnSmeltingResultTags
) {

    public static ConfigServer create(ForgeConfigSpec.Builder builder) {

        builder.comment("Iota Vanilla tweaks").push("tweaks");
            builder.comment("Enable or disable Iota's mob tempting/following tweaks.").push("followItems");
                var parrotsFollowSeeds = IotaConfigHelper.configVar(builder,
                        "parrotsFollowSeeds",
                        "Whether parrots should follow players holding a `forge:seeds` item",
                        (b, s) -> b.define(s, true)
                );
                var snowGolemsFollowSnowballs = IotaConfigHelper.configVar(builder,
                        "snowGolemsFollowSnowballs",
                        "Whether snow golems should follow players holding snowballs",
                        (b, s) -> b.define(s, true)
                );
                var villagersFollowEmeralds = IotaConfigHelper.configVar(builder,
                        "villagersFollowEmeralds",
                        "Whether villagers should follow players holding emeralds",
                        (b, s) -> b.define(s, true)
                );
                var wanderingTradersFollowEmeralds = IotaConfigHelper.configVar(builder,
                        "wanderingTradersFollowEmeralds",
                        "Whether wandering traders should follow players holding emeralds",
                        (b, s) -> b.define(s, true)
                );
            builder.pop();

            builder.comment("Enable or disable Iota's leash tweaks.").push("addedLeashMobs");
                var villagersCanBeLeashed = IotaConfigHelper.configVar(builder,
                        "villagersCanBeLeashed",
                        "Whether to make villagers leashable",
                        (b, s) -> b.define(s, true)
                );
                var wanderingTradersCanBeLeashed = IotaConfigHelper.configVar(builder,
                        "wanderingTradersCanBeLeashed",
                        "Whether to make wandering traders leashable",
                        (b, s) -> b.define(s, true)
                );
                var turtlesCanBeLeashed = IotaConfigHelper.configVar(builder,
                        "turtlesCanBeLeashed",
                        "Whether to make turtles leashable",
                        (b, s) -> b.define(s, true)
                );
            builder.pop();

            builder.comment("Configure Iota's mob following speed tweaks").push("mobFollowSpeedTweaks");
                var speedUpCowFollowing = IotaConfigHelper.configVar(builder,
                        "speedUpCowFollowing",
                        "Whether to speed up cows following players",
                        (b, s) -> b.define(s, true)
                );
                var cowFollowingSpeedMultiplier = IotaConfigHelper.configVar(builder,
                        "cowFollowingSpeedMultiplier",
                        "The amount by which to speed up cows following players",
                        (b, s) -> b.defineInRange(s, 1.3, .1, Double.MAX_VALUE)
                );
                var speedUpPigFollowing = IotaConfigHelper.configVar(builder,
                        "speedUpPigFollowing",
                        "Whether to speed up pigs following players",
                        (b, s) -> b.define(s, true)
                );
                var pigFollowingSpeedMultiplier = IotaConfigHelper.configVar(builder,
                        "pigFollowingSpeedMultiplier",
                        "The amount by which to speed up pigs following players",
                        (b, s) -> b.defineInRange(s, 1.3, .1, Double.MAX_VALUE)
                );
                var speedUpSheepFollowing = IotaConfigHelper.configVar(builder,
                        "speedUpSheepFollowing",
                        "Whether to speed up sheep following players",
                        (b, s) -> b.define(s, true)
                );
                var sheepFollowingSpeedMultiplier = IotaConfigHelper.configVar(builder,
                        "sheepFollowingSpeedMultiplier",
                        "The amount by which to speed up sheep following players",
                        (b, s) -> b.defineInRange(s, 1.3, .1, Double.MAX_VALUE)
                );
                var speedUpChickenFollowing = IotaConfigHelper.configVar(builder,
                        "speedUpChickenFollowing",
                        "Whether to speed up chickens following players",
                        (b, s) -> b.define(s, true)
                );
                var chickenFollowingSpeedMultiplier = IotaConfigHelper.configVar(builder,
                        "chickenFollowingSpeedMultiplier",
                        "The amount by which to speed up chickens following players",
                        (b, s) -> b.defineInRange(s, 1.3, .1, Double.MAX_VALUE)
                );
            builder.pop();
        builder.pop();

        builder.comment("Iota content additions").comment("Features can be enabled in disabled in the common config. Configure them here.").push("content");
            builder.comment("Settings for the Kiln").push("kiln");
                var kilnSmeltingSpeedMultiplier = IotaConfigHelper.configVar(builder,
                        "kilnSmeltingSpeedMultiplier",
                        "The amount by which to speed up vanilla furnace recipes that are added to the kiln, NOT datapack recipes",
                        (b, s) -> b.defineInRange(s, 2, .1, Double.MAX_VALUE)
                );
                var kilnSmeltingIngredientTags = IotaConfigHelper.configObj(builder,
                        "kilnSmeltingIngredientTags",
                        "Any smelting recipes with an ingredient in one of these tags will be automatically converted to a kiln recipe.",
                        (b, s) -> ConfigHelper.defineObject(b, s, IotaConfigHelper.TagList.CODEC, new IotaConfigHelper.TagList(
                                Tags.Items.COBBLESTONE,
                                Tags.Items.STONE,
                                ItemTags.STONE_TOOL_MATERIALS,
                                ItemTags.STONE_CRAFTING_MATERIALS,
                                Tags.Items.SAND,
                                ItemTags.LOGS_THAT_BURN,
                                ItemTags.TERRACOTTA
                )));
                var kilnSmeltingResultTags = IotaConfigHelper.configObj(builder,
                        "kilnSmeltingResultTagsTags",
                        "Any smelting recipes with a result item in one of these tags will be automatically converted to a kiln recipe.",
                        (b, s) -> ConfigHelper.defineObject(b, s, IotaConfigHelper.TagList.CODEC, new IotaConfigHelper.TagList(
                                Tags.Items.DYES,
                                Tags.Items.STONE,
                                Tags.Items.GLASS,
                                Tags.Items.INGOTS_BRICK,
                                Tags.Items.INGOTS_NETHER_BRICK,
                                ItemTags.TERRACOTTA
                )));
            builder.pop();
        builder.pop();

        return new ConfigServer(
            parrotsFollowSeeds,
            snowGolemsFollowSnowballs,
            villagersFollowEmeralds,
            wanderingTradersFollowEmeralds,

            villagersCanBeLeashed,
            wanderingTradersCanBeLeashed,
            turtlesCanBeLeashed,

            speedUpCowFollowing,
            cowFollowingSpeedMultiplier,
            speedUpPigFollowing,
            pigFollowingSpeedMultiplier,
            speedUpSheepFollowing,
            sheepFollowingSpeedMultiplier,
            speedUpChickenFollowing,
            chickenFollowingSpeedMultiplier,

            kilnSmeltingSpeedMultiplier,
            kilnSmeltingIngredientTags,
            kilnSmeltingResultTags
        );
    }

}
