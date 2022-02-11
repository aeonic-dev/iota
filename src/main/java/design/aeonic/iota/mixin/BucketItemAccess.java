package design.aeonic.iota.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(BucketItem.class)
public interface BucketItemAccess {

    @Accessor
    Fluid getContent();

    @Invoker
    void callPlayEmptySound(@Nullable Player pPlayer, LevelAccessor pLevel, BlockPos pPos);

}
