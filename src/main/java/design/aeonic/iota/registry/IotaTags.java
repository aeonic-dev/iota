package design.aeonic.iota.registry;

import design.aeonic.iota.Iota;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;

public final class IotaTags {
    public static final class Blocks {
        public static final Tag.Named<Block> NETHER_PORTAL_FRAME = BlockTags.bind(Iota.MOD_ID + ":nether_portal_frame");
    }
    public static final class Items {

    }
}
