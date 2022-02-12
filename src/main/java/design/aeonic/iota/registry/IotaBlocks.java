package design.aeonic.iota.registry;

import design.aeonic.iota.Iota;
import design.aeonic.iota.config.condition.KilnConfigCondition;
import design.aeonic.iota.content.kiln.KilnBlock;
import design.aeonic.iota.content.kiln.KilnBlockEntity;
import design.aeonic.iota.content.kiln.KilnMenu;
import design.aeonic.iota.content.kiln.KilnScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.fml.common.Mod;

/**
 * A class to store the mod's blocks.
 * @author aeonic-dev
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Iota.MOD_ID)
public class IotaBlocks {

	public static final IotaRegistrate.TheWholeNineYards<KilnBlock, KilnBlockEntity, KilnMenu, KilnScreen> KILN = Iota.REG.bazinga("kiln",
			KilnBlock::new, Material.STONE, b -> b
					.addLayer(() -> RenderType::cutout)
					.properties(p -> BlockBehaviour.Properties.of(Material.STONE).noOcclusion().isViewBlocking((x, y, z) -> false).strength(3.5F).lightLevel(s -> s.getValue(BlockStateProperties.LIT) ? 13 : 0))
					.tag(BlockTags.MINEABLE_WITH_PICKAXE)
					.blockstate((c, p) -> {
						ModelFile.ExistingModelFile model = p.models().getExistingFile(new ResourceLocation(Iota.MOD_ID, "block/kiln"));
						ModelFile.ExistingModelFile model_on = p.models().getExistingFile(new ResourceLocation(Iota.MOD_ID, "block/kiln_on"));
						p.getVariantBuilder(c.get()).forAllStates(s ->
												ConfiguredModel.builder().modelFile(s.getValue(BlockStateProperties.LIT) ? model_on : model)
														.rotationY((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build());
					})
					.item()
						.tab(() -> CreativeModeTab.TAB_DECORATIONS)
						.recipe((ctx, prv) ->
								ConditionalRecipe.builder()
										.addCondition(new KilnConfigCondition())
										.addRecipe(ShapedRecipeBuilder.shaped(ctx.get())
												.define('c', Items.COPPER_INGOT)
												.define('f', Items.FURNACE)
												.define('s', Items.SMOOTH_STONE)
												.pattern("sss")
												.pattern("cfc")
												.pattern("ccc")
												.unlockedBy("has_copper", RecipeProvider.has(Items.COPPER_INGOT))::save
										).generateAdvancement().build(prv, ctx.getId()))
					.build(),
			KilnBlockEntity::new, KilnMenu::new, KilnMenu::new, () -> KilnScreen::new);

}
