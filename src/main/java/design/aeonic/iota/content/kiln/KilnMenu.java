package design.aeonic.iota.content.kiln;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.iota.base.block.entity.ItemHandlerBlockEntity;
import design.aeonic.iota.base.block.menu.ItemHandlerMenu;
import design.aeonic.iota.base.block.menu.SimpleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

public class KilnMenu extends ItemHandlerMenu<KilnBlockEntity, KilnMenu> {

    public KilnMenu(MenuType<KilnMenu> pMenuType, int pContainerId, Inventory inv, FriendlyByteBuf buf) {
        // Client constructor
        this(pMenuType, pContainerId, inv, inv.player.getCommandSenderWorld().getBlockEntity(buf.readBlockPos()));
    }

    public KilnMenu(MenuType<KilnMenu> pMenuType, int pContainerId, Inventory inv, @Nullable BlockEntity be) {
        // Server constructor
        super(pMenuType, pContainerId, inv, (KilnBlockEntity) be);
    }

    @Override
    protected SlotPosition[] getSlotPositions(ItemHandlerBlockEntity.SlotType[] structure) {
        return new SlotPosition[] {
                new SlotPosition(56, 17),
                new SlotPosition(56, 53),
                new SlotPosition(116, 35)
        };
    }

    protected int getBurnProgress() {
        int time = dataHolder.get(2);
        int total = dataHolder.get(3);
        return time != 0 && total != 0 ? time * 24 / total : 0;
    }

    protected int getLitProgress() {
        int time = dataHolder.get(0);
        int total = dataHolder.get(1);
        return time * 13 / (total == 0 ? 100 : total);
    }

    protected boolean isLit() {
        return dataHolder.get(0) > 0;
    }
}