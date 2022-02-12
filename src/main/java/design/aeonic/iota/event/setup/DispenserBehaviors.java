package design.aeonic.iota.event.setup;

import design.aeonic.iota.Iota;
import design.aeonic.iota.mixin.accessors.BucketItemAccess;
import design.aeonic.iota.mixin.accessors.MobBucketItemAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Vec3i;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class DispenserBehaviors {

    public static final DefaultDispenseItemBehavior DEFAULT_EMPTY_BUCKET_BEHAVIOR = (DefaultDispenseItemBehavior) DispenserBlock.DISPENSER_REGISTRY.get(Items.BUCKET);
    public static final DefaultDispenseItemBehavior DEFAULT_FULL_BUCKET_BEHAVIOR = (DefaultDispenseItemBehavior) DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);

    public static void register() {
        DispenserBlock.registerBehavior(Items.BUCKET, EmptyBucketBehavior.INSTANCE);
        DispenserBlock.registerBehavior(Items.WATER_BUCKET, FullBucketBehavior.INSTANCE);
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, FullBucketBehavior.INSTANCE);
        DispenserBlock.registerBehavior(Items.POWDER_SNOW_BUCKET, FullBucketBehavior.INSTANCE);

        List<Item> mobBucketItems = Iota.commonConfig.mobBucketItems().get().getItems();
        for (var item: mobBucketItems) {
            DispenserBlock.registerBehavior(item, FullBucketBehavior.INSTANCE);
        }
    }

    public static class FullBucketBehavior extends DefaultDispenseItemBehavior {
        private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

        public static final FullBucketBehavior INSTANCE = new FullBucketBehavior();

        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            // TODO: find a mod-agnostic way to implement the rest of this
            //  Figure out what mods make changes to cauldron etc

            if (!(Iota.serverConfig.dispenserCanEmptyCauldron().get())) return DEFAULT_FULL_BUCKET_BEHAVIOR.dispense(source, stack);

            ServerLevel levelaccessor = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState state = levelaccessor.getBlockState(blockpos);

            BlockState newState = null;

            if (!state.is(Blocks.CAULDRON) || !(stack.getItem() instanceof BucketItemAccess item)) return DEFAULT_FULL_BUCKET_BEHAVIOR.dispense(source, stack);

            if (Iota.serverConfig.dispenserCanFillWaterCauldron().get() && item.getContent().is(FluidTags.WATER)) {
                newState = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            } else if (Iota.serverConfig.dispenserCanFillLavaCauldron().get() && item.getContent().is(FluidTags.LAVA)) {
                newState = Blocks.LAVA_CAULDRON.defaultBlockState();
            } else if (Iota.serverConfig.dispenserCanFillPowderSnowCauldron().get() &&
                    item instanceof SolidBucketItem solidBucketItem && solidBucketItem.getBlock() == Blocks.POWDER_SNOW) {
                newState = (Blocks.POWDER_SNOW_CAULDRON).defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            }

            if (newState != null) {
                levelaccessor.setBlock(blockpos, newState, 2);

                if (item instanceof MobBucketItemAccess mobBucketItem) {
                    LogManager.getLogger().info("aaa {}", mobBucketItem);
                    if (!Iota.serverConfig.dispenserCanFillAnimalCauldron().get()) { return DEFAULT_FULL_BUCKET_BEHAVIOR.dispense(source, stack); }
                    mobBucketItem.callSpawn(levelaccessor, stack, blockpos);
                }

                if (item instanceof SolidBucketItem bi)
                    levelaccessor.playSound(null, blockpos, bi.getBlock().getSoundType(bi.getBlock().defaultBlockState()).getPlaceSound(),
                            SoundSource.BLOCKS, 1f, 1f);
                else item.callPlayEmptySound(null, levelaccessor, blockpos);

                stack.shrink(1);
                if (stack.isEmpty()) {
                    return new ItemStack(Items.BUCKET);
                }
                else {
                    if (source.<DispenserBlockEntity>getEntity().addItem(new ItemStack(Items.BUCKET)) < 0) {
                        defaultBehavior.dispense(source, new ItemStack(Items.BUCKET));
                    }
                    return stack;
                }
            }

            return DEFAULT_FULL_BUCKET_BEHAVIOR.dispense(source, stack);
        }
    }

    public static class EmptyBucketBehavior extends DefaultDispenseItemBehavior {
        private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

        public static final EmptyBucketBehavior INSTANCE = new EmptyBucketBehavior();

        @Nonnull
        @Override
        public ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            LevelAccessor levelaccessor = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState state = levelaccessor.getBlockState(blockpos);

            ItemStack item;
            SoundEvent soundEvent = SoundEvents.BUCKET_FILL;

            if (state.is(Blocks.WATER_CAULDRON)) {
                Supplier<List<Entity>> entities = () -> levelaccessor.getEntitiesOfClass(
                        Entity.class,
                        AABB.ofSize(Vec3.atCenterOf(new Vec3i(source.x(), source.y(), source.z())), 2d, 2d, 2d), // slightly bigger than one block
                        EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(e -> e instanceof Bucketable));

                if ((Iota.serverConfig.dispenserCanEmptyAnimalCauldron().get()) && entities.get().size() > 0) {
                    var entity = entities.get().get(0);
                    var entityBucketable = ((Bucketable) entity);
                    var newStack = entityBucketable.getBucketItemStack();
                    entityBucketable.saveToBucketTag(newStack);
                    item = newStack;

                    entity.playSound(entityBucketable.getPickupSound(), 1f, 1f);
                    entity.discard();
                }
                else {
                    item = new ItemStack(Items.WATER_BUCKET);
                }
            }
            else if (state.is(Blocks.POWDER_SNOW_CAULDRON)) {
                item = new ItemStack(Items.POWDER_SNOW_BUCKET);
                soundEvent = SoundEvents.BUCKET_FILL_POWDER_SNOW;
            }
            else if (state.is(Blocks.LAVA_CAULDRON)) {
                item = new ItemStack(Items.LAVA_BUCKET);
                soundEvent = SoundEvents.BUCKET_FILL_LAVA;
            }
            else return DEFAULT_EMPTY_BUCKET_BEHAVIOR.dispense(source, stack);

            // why does the superclass for layered and lava cauldron blocks not have the isFull method bro what the hell
            if (state.getBlock() instanceof LavaCauldronBlock be ? be.isFull(state) :
                            ((LayeredCauldronBlock) state.getBlock()).isFull(state)) {
                levelaccessor.setBlock(blockpos, Blocks.CAULDRON.defaultBlockState(), 2);

                source.getLevel().playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);

                stack.shrink(1);
                if (stack.isEmpty()) {
                    return item;
                }
                else {
                    if (source.<DispenserBlockEntity>getEntity().addItem(item) < 0) {
                        defaultBehavior.dispense(source, item);
                    }
                    return stack;
                }
            }
            return DEFAULT_EMPTY_BUCKET_BEHAVIOR.dispense(source, stack);
        }
    }
}
