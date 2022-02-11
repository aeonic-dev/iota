package design.aeonic.iota.config;

import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;

public record ConfigCommon(
        ForgeConfigSpec.ConfigValue<Boolean> dispenserCanEmptyCauldron,
        ForgeConfigSpec.ConfigValue<Boolean> dispenserCanEmptyAnimalCauldron,
        ForgeConfigSpec.ConfigValue<Boolean> dispenserCanFillWaterCauldron,
        ForgeConfigSpec.ConfigValue<Boolean> dispenserCanFillLavaCauldron,
        ForgeConfigSpec.ConfigValue<Boolean> dispenserCanFillPowderSnowCauldron,
        ForgeConfigSpec.ConfigValue<Boolean> dispenserCanFillAnimalCauldron,
        ForgeConfigSpec.ConfigValue<Boolean> enableKiln
){

    public static ConfigCommon create(ForgeConfigSpec.Builder builder) {

        builder.comment("Iota tweaks").push("content");
            builder.comment("Iota tweaks").push("content");
                var dispenserCanEmptyCauldron = IotaConfigHelper.configVar(builder,
                        "dispenserCanEmptyCauldron",
                        "Whether dispensers can fill empty buckets from full cauldrons of water, snow or lava",
                        (b, s) -> b.define(s, true)
                );
                var dispenserCanEmptyAnimalCauldron = IotaConfigHelper.configVar(builder,
                        "dispenserCanEmptyAnimalCauldron",
                        "Whether dispensers can fill empty buckets with bucketable animals from full water cauldrons",
                        (b, s) -> b.define(s, true)
                );

                var dispenserCanFillWaterCauldron = IotaConfigHelper.configVar(builder,
                        "dispenserCanFillWaterCauldron",
                        "Whether dispensers can fill cauldrons from water buckets",
                        (b, s) -> b.define(s, true)
                );
                var dispenserCanFillLavaCauldron = IotaConfigHelper.configVar(builder,
                        "dispenserCanFillLavaCauldron",
                        "Whether dispensers can fill cauldrons from lava buckets",
                        (b, s) -> b.define(s, true)
                );
                var dispenserCanFillPowderSnowCauldron = IotaConfigHelper.configVar(builder,
                        "dispenserCanFillPowderSnowCauldron",
                        "Whether dispensers can fill cauldrons from powder snow buckets",
                        (b, s) -> b.define(s, true)
                );
                var dispenserCanFillAnimalCauldron = IotaConfigHelper.configVar(builder,
                        "dispenserCanFillAnimalCauldron",
                        "Whether dispensers can fill cauldrons from full water animal buckets",
                        (b, s) -> b.define(s, true)
                );
            builder.pop();
        builder.pop();

        builder.comment("Iota content additions").push("content");
            var enableKiln = IotaConfigHelper.configVar(builder,
                    "enableKiln",
                    "Whether to enable the Kiln",
                    (b, s) -> b.define(s, true)
            );
        builder.pop();

        return new ConfigCommon(
                dispenserCanEmptyCauldron,
                dispenserCanEmptyAnimalCauldron,
                dispenserCanFillWaterCauldron,
                dispenserCanFillLavaCauldron,
                dispenserCanFillPowderSnowCauldron,
                dispenserCanFillAnimalCauldron,
                enableKiln
        );
    }

}
