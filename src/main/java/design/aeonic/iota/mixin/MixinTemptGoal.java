package design.aeonic.iota.mixin;

import design.aeonic.iota.common.util.IAmSpeed;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TemptGoal.class)
abstract class MixinTemptGoal {

    @Redirect(method = {"tick"},
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/ai/goal/TemptGoal;speedModifier:D", opcode = Opcodes.GETFIELD))
    private double multiplyTemptGoalSpeed(TemptGoal instance) {
        var inter = ((MixinTemptGoalAccess) instance);
        var oldValue = inter.getSpeedModifier();

        if (inter.getMob() instanceof IAmSpeed) {
            return oldValue * ((IAmSpeed) inter.getMob()).getTemptSpeedMultiplier();
        }
        return oldValue;
    }

}
