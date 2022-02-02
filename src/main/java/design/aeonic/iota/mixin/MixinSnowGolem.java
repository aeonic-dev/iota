package design.aeonic.iota.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolem.class)
abstract class MixinSnowGolem extends AbstractGolem {

    protected MixinSnowGolem(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
        super(p_27508_, p_27509_);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    protected void registerTemptGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25d, Ingredient.of(Items.SNOWBALL), false));
    }
}
