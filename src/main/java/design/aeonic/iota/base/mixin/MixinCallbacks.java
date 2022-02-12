package design.aeonic.iota.base.mixin;

import design.aeonic.iota.Iota;
import design.aeonic.iota.config.ConfigServer;
import design.aeonic.iota.registry.IotaAdvancements;
import design.aeonic.iota.registry.IotaTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class MixinCallbacks {

    public static boolean isNetherPortalFrame(BlockState state, BlockGetter world, BlockPos pos) {
        if (!Iota.serverConfig.useNetherPortalFrameTag().get())
            return false;
        return IotaTags.Blocks.NETHER_PORTAL_FRAME.contains(state.getBlock());
    }

    public static void inventoryDropAll(Player player, List<NonNullList<ItemStack>> compartments) {
        for(List<ItemStack> list : compartments) {
            for(int i = 0; i < list.size(); ++i) {
                ItemStack itemstack = list.get(i);
                if (!itemstack.isEmpty()) {
                    var entity = player.drop(itemstack, true, false);
                    if (entity != null && !player.isAlive() && itemstack.getItem() instanceof RecordItem) {
                        entity.addTag("RecordDroppedOnDeath");
                        entity.addTag("DroppedBy " + player.getName().getString());
                    }
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public static void spawnBlockBroken(BlockPos pos, Level level) {
        if (level instanceof ServerLevel serverLevel && Iota.serverConfig.alertPlayerWhenSpawnpointRemoved().get()) {
            serverLevel.getServer().getPlayerList().getPlayers().forEach(
                    p -> ((IPlayerSpawnCheck) ((Object) p)).checkSpawnBlockBroken(pos));
        }
    }

    public static void serverPlayerTake(ServerPlayer player, Entity entity) {;
        if (entity instanceof ItemEntity ie) {
            if (ie.getTags().contains("RecordDroppedOnDeath") &&
                    !ie.getTags().contains("DroppedBy " + player.getName().getString())) {
                player.getAdvancements().award(IotaAdvancements.PIRACY.get(), "never");
            }
        }
    }

}
