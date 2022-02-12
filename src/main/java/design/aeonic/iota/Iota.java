package design.aeonic.iota;

import design.aeonic.iota.base.misc.ConfigHelper;
import design.aeonic.iota.config.ConfigCommon;
import design.aeonic.iota.config.ConfigServer;
import design.aeonic.iota.compat.jei.DefaultItemInfo;
import design.aeonic.iota.registry.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Iota.MOD_ID)
public class Iota {
	
    public static final String MOD_ID = "iota";
    public static final String MOD_NAME = "Iota";
    
    public static final IotaRegistrate REG = IotaRegistrate.INSTANCE;
    public static final ConfigServer serverConfig = ConfigHelper.register(ModConfig.Type.SERVER, ConfigServer::create);
    public static final ConfigCommon commonConfig = ConfigHelper.register(ModConfig.Type.COMMON, ConfigCommon::create);

    public Iota() {
        IotaBlocks.register();
        IotaItems.register();
        IotaAdvancements.register();
        IotaRecipeTypes.register();
        DefaultItemInfo.register();
        IotaMiscLang.register();
    }

}
