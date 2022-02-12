package design.aeonic.iota.mixin.entities;

import design.aeonic.iota.Iota;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Turtle.class)
abstract class TurtleMixin extends Animal {

    protected TurtleMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public boolean canBeLeashed(Player p_35272_) {
        return Iota.serverConfig.turtlesCanBeLeashed().get();
    }
}
