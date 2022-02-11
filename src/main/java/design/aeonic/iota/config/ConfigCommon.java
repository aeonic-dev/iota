package design.aeonic.iota.config;

import design.aeonic.iota.base.misc.ConfigHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;

public record ConfigCommon(
        ForgeConfigSpec.ConfigValue<Boolean> enableKiln,
        ConfigHelper.ConfigObject<IotaConfigHelper.ItemList> mobBucketItems
){

    public static ConfigCommon create(ForgeConfigSpec.Builder builder) {

        builder.comment("Iota content additions").push("content");
            var enableKiln = IotaConfigHelper.configVar(builder,
                    "enableKiln",
                    "Whether to enable the Kiln",
                    (b, s) -> b.define(s, true)
            );
        builder.pop();

        builder.comment("Iota tweaks").push("content");
        var mobBucketItems = IotaConfigHelper.configObj(builder,
                "mobBucketItems",
                "A list of mob buckets dispensers can fill cauldrons with if the server config option is enabled",
                (b, s) -> ConfigHelper.defineObject(b, s, IotaConfigHelper.ItemList.CODEC, new IotaConfigHelper.ItemList(
                        Items.PUFFERFISH_BUCKET,
                        Items.SALMON_BUCKET,
                        Items.COD_BUCKET,
                        Items.TROPICAL_FISH_BUCKET,
                        Items.AXOLOTL_BUCKET
                )));
        builder.pop();

        return new ConfigCommon(
                enableKiln,
                mobBucketItems
        );
    }

}
