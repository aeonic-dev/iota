package design.aeonic.iota.mixin.players;

import design.aeonic.iota.base.mixin.IPlayerSpawnCheck;
import design.aeonic.iota.base.mixin.MixinCallbacks;
import design.aeonic.iota.registry.IotaMiscLang;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements IPlayerSpawnCheck {

    @Shadow
    BlockPos respawnPosition;

    @Shadow
    abstract void displayClientMessage(Component pChatComponent, boolean pActionBar);

    @Inject(method = "take", at = @At("HEAD"))
    public void take(Entity pEntity, int pQuantity, CallbackInfo ci) {
        MixinCallbacks.serverPlayerTake(((ServerPlayer) ((Object) this)), pEntity);
    }

    @Override
    public void checkSpawnBlockBroken(BlockPos pos) {
        if (respawnPosition.equals(pos)) {
            displayClientMessage(IotaMiscLang.SPAWN_REMOVED, false);
        }
    }
}