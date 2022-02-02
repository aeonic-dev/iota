package design.aeonic.iota.mixin;

import design.aeonic.iota.common.util.IAmSpeed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {Cow.class, Pig.class, Sheep.class, Chicken.class, Rabbit.class})
abstract class MixinTemptMobs extends Animal implements IAmSpeed {
    protected MixinTemptMobs(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }
}