package design.aeonic.iota.registry;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import design.aeonic.iota.Iota;
import design.aeonic.iota.base.block.MenuBlock;
import design.aeonic.iota.base.block.SimpleEntityBlock;
import design.aeonic.iota.base.block.entity.MenuBlockEntity;
import design.aeonic.iota.base.block.entity.SimpleBlockEntity;
import design.aeonic.iota.base.block.menu.SimpleMenu;
import design.aeonic.iota.base.block.menu.SimpleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.function.TriFunction;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

public class IotaRegistrate extends AbstractRegistrate<IotaRegistrate> {

    public static IotaRegistrate create() {
        return new IotaRegistrate().registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> deferredRegister(IForgeRegistry<T> registry, IEventBus bus) {
        var reg = DeferredRegister.create(registry, Iota.MOD_ID);
        reg.register(bus);
        return reg;
    }

    protected IotaRegistrate() { super(Iota.MOD_ID); }

    /**
     * REGISTER ALL OF THE THINGS!
     * @param name the entry name
     * @param blockFactory the block factory
     * @param material the block material
     * @param blockCallback a function for running extra customization on the blockbuilder
     * @param entityFactory the blockentity factory
     * @param clientMenuFactory the menu to construct on the client
     * @param serverMenuFactory the menu to construct on the server
     * @param screenFactory the screen factory
     * @param <B> the block class
     * @param <E> the blockentity class
     * @param <M> the menu class
     * @param <S> the screen class
     */
    public <B extends MenuBlock<E, M>, E extends MenuBlockEntity<E, M>, M extends SimpleMenu<E, M>, S extends SimpleScreen<E, M>> TheWholeNineYards<B, E, M, S> bazinga(
            String name,
            MenuBlock.MenuBlockFactory<B, E, M> blockFactory,
            Material material,
            Function<BlockBuilder<B, IotaRegistrate>, BlockBuilder<B, IotaRegistrate>> blockCallback,
            MenuBlockEntity.MenuBlockEntityFactory<E, M> entityFactory,
            MenuBuilder.ForgeMenuFactory<M> clientMenuFactory,
            SimpleMenu.ServerMenuFactory<E, M> serverMenuFactory,
            NonNullSupplier<MenuBuilder.ScreenFactory<M, S>> screenFactory
            ) {

        TheWholeNineYards<B, E, M, S> entry = new TheWholeNineYards<>(this, name);

        entry.block = (blockCallback.apply(block(this, name, material, p -> blockFactory.create(p, entry::entity, entityFactory, entry::menu, serverMenuFactory))).register());

        entry.entity = (blockEntity(this, name, (BlockEntityType<E> t, BlockPos p, BlockState s) -> entityFactory.create(t, p, s, entry::menu, serverMenuFactory))
                .validBlock(entry::block)
                .register());

        entry.menu = menu(entry.name, clientMenuFactory, screenFactory)//(MenuBlockEntity<E, M>) inv.player.getCommandSenderWorld().getBlockEntity(buf.readBlockPos())), screenFactory)
                .lang(e -> String.format("container.%s.%s", getModid(), name))
                .register();

        return entry;
    }

    public <B extends SimpleEntityBlock<E>, E extends SimpleBlockEntity> EntityBlockEntry<B, E> blockAndEntity(
            String name,
            TriFunction<BlockBehaviour.Properties, Supplier<BlockEntityType<E>>, BlockEntityBuilder.BlockEntityFactory<E>, B> blockFactory,
            Material material,
            Function<BlockBuilder<B, IotaRegistrate>, BlockBuilder<B, IotaRegistrate>> blockCallback,
            BlockEntityBuilder.BlockEntityFactory<E> entityFactory) {

        EntityBlockEntry<B, E> entry = new EntityBlockEntry<>(this, name);
        entry.block = (blockCallback.apply(block(this, name, material, p -> blockFactory.apply(p, entry::entity, entityFactory))).register());
        entry.entity = (blockEntity(this, name, entityFactory)
                .validBlock(entry::block)
                .register());

        return entry;
    }

    public static class EntityBlockEntry<B extends SimpleEntityBlock<E>, E extends SimpleBlockEntity> {
        protected final IotaRegistrate parent;
        public final String name;

        public BlockEntry<B> block;
        public BlockEntityEntry<E> entity;

        public EntityBlockEntry(IotaRegistrate parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        public @Nonnull B block() { return block.orElseThrow(() -> new AssertionError(String.format("Block %s has not yet been registered!", this.block.getId().getPath()))); }
        public @Nonnull BlockEntityType<E> entity() { return entity.orElseThrow(() -> new AssertionError(String.format("BlockEntity %s has not yet been registered!", this.entity.getId().getPath()))); }
    }

    public static class TheWholeNineYards<B extends MenuBlock<E, M>, E extends MenuBlockEntity<E, M>, M extends SimpleMenu<E, M>, S extends SimpleScreen<E, M>> extends EntityBlockEntry<B, E> {
        public MenuEntry<M> menu;

        public TheWholeNineYards(EntityBlockEntry<B, E> entityBlockEntry) {
            this(entityBlockEntry.parent, entityBlockEntry.name);
            this.block = entityBlockEntry.block;
            this.entity = entityBlockEntry.entity;
        }

        public TheWholeNineYards(IotaRegistrate parent, String name) {
            super(parent, name);
        }

        public @Nonnull MenuType<M> menu() { return menu.orElseThrow(() -> new AssertionError(String.format("MenuType %s has not yet been registered!", this.menu.getId().getPath()))); }
    }

}
