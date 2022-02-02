package design.aeonic.iota.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Parrot.class)
abstract class MixinParrot extends ShoulderRidingEntity {

    protected MixinParrot(EntityType<? extends ShoulderRidingEntity> p_29893_, Level p_29894_) {
        super(p_29893_, p_29894_);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    protected void registerTemptGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new TemptGoal(this, 2.25d, Ingredient.of(Tags.Items.SEEDS), false));
    }
}