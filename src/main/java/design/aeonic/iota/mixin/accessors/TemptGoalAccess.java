package design.aeonic.iota.mixin.accessors;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TemptGoal.class)
public interface TemptGoalAccess {
    @Accessor
    PathfinderMob getMob();

    @Accessor
    double getSpeedModifier();
}
