package design.aeonic.iota.mixin.players;

import design.aeonic.iota.base.mixin.MixinCallbacks;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Shadow Player player;
    @Shadow List<NonNullList<ItemStack>> compartments;

    public void dropAll() {
        MixinCallbacks.inventoryDropAll(player, compartments);
    }
}
