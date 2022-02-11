package design.aeonic.iota.content.kiln;

import design.aeonic.iota.base.block.ItemHandlerBlock;
import design.aeonic.iota.base.block.entity.MenuBlockEntity;
import design.aeonic.iota.base.block.menu.SimpleMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Supplier;

public class KilnBlock extends ItemHandlerBlock<KilnBlockEntity, KilnMenu> {

    public KilnBlock(Properties blockProps, Supplier<BlockEntityType<KilnBlockEntity>> blockEntityType, MenuBlockEntity.MenuBlockEntityFactory<KilnBlockEntity, KilnMenu> entityFactory, Supplier<MenuType<KilnMenu>> menuType, SimpleMenu.ServerMenuFactory<KilnBlockEntity, KilnMenu> menuFactory) {
        super(blockProps, blockEntityType, entityFactory, menuType, menuFactory);
    }

    @Override
    protected StateProperty<?, ?>[] getStateProperties() {
        return new StateProperty[]{
                StateProperty.of(BlockStateProperties.LIT, ctx -> false),
                StateProperty.of(BlockStateProperties.HORIZONTAL_FACING, ctx -> ctx.getHorizontalDirection().getOpposite())
        };
    }
}
