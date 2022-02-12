package design.aeonic.iota.mixin.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobBucketItem.class)
public interface MobBucketItemAccess extends BucketItemAccess {

    @Invoker
    void callSpawn(ServerLevel pServerLevel, ItemStack pBucketedMobStack, BlockPos pPos);

}
