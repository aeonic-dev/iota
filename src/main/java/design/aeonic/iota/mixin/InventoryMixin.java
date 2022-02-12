package design.aeonic.iota.mixin;

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
}
