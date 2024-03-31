package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.registry.EasterEggsRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity {
	protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "initGoals", at = @At("RETURN"))
	private void mclmaf2024$kappaAngerIronGolem(CallbackInfo ci) {
		// If an entity is wearing something in EquipmentSlot.HEAD that makes isKappaEasterEgg true, Iron Golems will target them, even if they remove the helmet.
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, LivingEntity.class, 5, false, false, (entity) -> entity != null && entity.isAlive() && EasterEggsRegistry.isKappaEasterEgg(getHelmet(entity))));
	}
	@Unique
	private ItemStack getHelmet(LivingEntity entity) {
		return entity != null && entity.isAlive() && this.isMobOrPlayer() ? (entity.getArmorItems() != null ? entity.getEquippedStack(EquipmentSlot.HEAD) : null) : null;
	}
}
