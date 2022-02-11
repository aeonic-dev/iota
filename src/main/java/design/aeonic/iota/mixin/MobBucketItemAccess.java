package design.aeonic.iota.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(MobBucketItem.class)
public interface MobBucketItemAccess extends BucketItemAccess {

    @Invoker
    void callSpawn(ServerLevel pServerLevel, ItemStack pBucketedMobStack, BlockPos pPos);

}
