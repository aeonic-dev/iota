package design.aeonic.iota.mixin.players;

import design.aeonic.iota.base.mixin.MixinCallbacks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { BedBlock.class, RespawnAnchorBlock.class })
public abstract class SpawnBlockMixin extends Block {

    protected SpawnBlockMixin(Properties p_54120_) {
        super(p_54120_);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock()))
            MixinCallbacks.spawnBlockBroken(pPos, pLevel);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}