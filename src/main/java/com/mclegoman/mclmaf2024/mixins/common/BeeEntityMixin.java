package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.entity.goals.PollinateMoobloomGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends PathAwareEntity {
	protected BeeEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}
	@Shadow public abstract GoalSelector getGoalSelector();
	@Inject(method = "initGoals", at = @At("HEAD"))
	private void mclmaf2024$initBeeGoals(CallbackInfo ci) {
		this.getGoalSelector().add(10, new PollinateMoobloomGoal(this, 1.00, false));
	}
}
