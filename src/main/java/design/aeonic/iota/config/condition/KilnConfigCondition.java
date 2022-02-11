package design.aeonic.iota.config.condition;

import design.aeonic.iota.Iota;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public class KilnConfigCondition extends ConfigCondition<Boolean> {

    @Override
    public ConfigCondition<Boolean> create() {
        return new KilnConfigCondition();
    }

    @Override
    protected ForgeConfigSpec.ConfigValue<Boolean> getConfigField() {
        return Iota.commonConfig.enableKiln();
    }

    @Override
    boolean checkValue(Boolean value) {
        return value;
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(Iota.MOD_ID, "kiln_config");
    }

}
