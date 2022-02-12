package design.aeonic.iota.compat.jei;

import design.aeonic.iota.Iota;
import design.aeonic.iota.registry.IotaBlocks;

import java.util.Map;

public class DefaultItemInfo {

    public static void register() {
        Map.of(
                IotaBlocks.KILN.block,
                "Smelts certain recipes twice as fast as a default furnace, with the same automation options." +
                        "\nParallels the vanilla smoker and blast furnace."
        ).forEach(Iota.REG::queueItemInfo);
    }

}
