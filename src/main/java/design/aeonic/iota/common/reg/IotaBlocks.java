package design.aeonic.iota.common.reg;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;

import design.aeonic.iota.Iota;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * A class to store the mod's blocks.
 * @author aeonic-dev
 */
@EventBusSubscriber(modid = Iota.MOD_ID, bus = Bus.MOD)
public class IotaBlocks {
	
	public static final BlockEntry<GlassBlock> TEST_BLOCK = Iota.REGISTRATE.object("test_block")
			.block(GlassBlock::new).addLayer(() -> RenderType::cutout)
					.properties(p -> p.sound(SoundType.AMETHYST).noOcclusion().friction(.7f).lightLevel((state) -> 8).strength(1.5F, 8.0F))
            		.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            		.blockstate((ctx, prv) -> prv.simpleBlock(ctx.get()))
				.item()
					.model((ctx, provider) -> provider.blockItem(() -> ctx.get()))
					.recipe((ctx, prv) -> prv.singleItem(DataIngredient.items(Items.DIAMOND), () -> ctx.get(), 1, 1))
					.recipe((ctx, prv) -> prv.singleItem(DataIngredient.items(IotaItems.TEST_ITEM), () -> ctx.get(), 9, 1))
			.build().register();
	
}
