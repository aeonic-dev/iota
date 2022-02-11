package design.aeonic.iota.mixin;

import design.aeonic.iota.Iota;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Parrot.class)
abstract class ParrotMixin extends ShoulderRidingEntity {

    protected ParrotMixin(EntityType<? extends ShoulderRidingEntity> p_29893_, Level p_29894_) {
        super(p_29893_, p_29894_);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    protected void registerTemptGoal(CallbackInfo ci) {
        if (Iota.serverConfig.parrotsFollowSeeds().get())
            this.goalSelector.addGoal(1, new TemptGoal(this, 2.25d, Ingredient.of(Tags.Items.SEEDS), false));
    }
}
