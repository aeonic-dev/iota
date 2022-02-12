package design.aeonic.iota.mixin;

import design.aeonic.iota.registry.IotaAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Inject(method = "take", at = @At("HEAD"))
    public void take(Entity pEntity, int pQuantity, CallbackInfo ci) {
        var player = ((ServerPlayer) ((Object) this));
        if (pEntity instanceof ItemEntity ie) {
            if (ie.getTags().contains("RecordDroppedOnDeath") &&
                    !ie.getTags().contains("DroppedBy " + player.getName().getString())) {
                player.getAdvancements().award(IotaAdvancements.PIRACY.get(), "never");
            }
        }
    }
}