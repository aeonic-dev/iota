package design.aeonic.iota.mixin;

import design.aeonic.iota.Iota;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Villager.class)
abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    public boolean canBeLeashed(Player p_35272_) {
        return Iota.serverConfig.villagersCanBeLeashed().get();
    }

    protected void registerGoals() {
        if (Iota.serverConfig.villagersFollowEmeralds().get()) {
            this.goalSelector.addGoal(3, new TemptGoal(this, .5D, Ingredient.of(Items.EMERALD), false));
            super.registerGoals();
        }
    }
}
