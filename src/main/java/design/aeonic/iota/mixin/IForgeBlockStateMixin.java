package design.aeonic.iota.mixin;

import design.aeonic.iota.base.misc.MixinCallbacks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IForgeBlockState.class)
public interface IForgeBlockStateMixin extends IForgeBlockState {
    default boolean isPortalFrame(BlockGetter world, BlockPos pos)
    {
        var state = (BlockState) this;
        return MixinCallbacks.isNetherPortalFrame(state, world, pos) || state.getBlock().isPortalFrame(state, world, pos);
    }
}
