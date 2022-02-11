package design.aeonic.iota.base.misc;

import design.aeonic.iota.Iota;
import design.aeonic.iota.config.ConfigServer;
import design.aeonic.iota.registry.IotaTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class MixinCallbacks {

    public static boolean isNetherPortalFrame(BlockState state, BlockGetter world, BlockPos pos) {
        if (!Iota.serverConfig.useNetherPortalFrameTag().get())
            return false;
        return IotaTags.Blocks.NETHER_PORTAL_FRAME.contains(state.getBlock());
    }

}
