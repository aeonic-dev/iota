package design.aeonic.iota.config;

import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;

public record ConfigCommon(
        ForgeConfigSpec.ConfigValue<Boolean> enableKiln
){

    public static ConfigCommon create(ForgeConfigSpec.Builder builder) {

        builder.comment("Iota content additions").push("content");
            var enableKiln = IotaConfigHelper.configVar(builder,
                    "enableKiln",
                    "Whether to enable the Kiln",
                    (b, s) -> b.define(s, true)
            );
        builder.pop();

        return new ConfigCommon(
                enableKiln
        );
    }

}
