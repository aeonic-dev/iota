package design.aeonic.iota.mixin.entities;

import design.aeonic.iota.Iota;
import design.aeonic.iota.mixin.accessors.TemptGoalAccess;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TemptGoal.class)
abstract class TemptGoalMixin {

    @Redirect(method = {"tick"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/ai/goal/TemptGoal;speedModifier:D", opcode = Opcodes.GETFIELD))
    private double multiplyTemptGoalSpeed(TemptGoal instance) {
        var inter = ((TemptGoalAccess) instance);
        var oldValue = inter.getSpeedModifier();
        var mob = inter.getMob();

        if (mob instanceof Cow)
            return oldValue * Iota.serverConfig.cowFollowingSpeedMultiplier().get();

        if (mob instanceof Pig)
            return oldValue * Iota.serverConfig.pigFollowingSpeedMultiplier().get();

        if (mob instanceof Sheep)
            return oldValue * Iota.serverConfig.sheepFollowingSpeedMultiplier().get();

        if (mob instanceof Chicken)
            return oldValue * Iota.serverConfig.chickenFollowingSpeedMultiplier().get();

        return oldValue;
    }
}