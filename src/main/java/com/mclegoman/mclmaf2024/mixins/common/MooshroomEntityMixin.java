package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.entity.helper.Knockback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MooshroomEntity.class)
public abstract class MooshroomEntityMixin extends LivingEntity {
	protected MooshroomEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	// When Mooshrooms are sheared, knockback nearby entities upwards and display wind charge particles. (This is identical to Mooblooms.)
	@Inject(method = "sheared", at = @At("HEAD"))
	private void mclmaf2024$sheared(SoundCategory shearedSoundCategory, CallbackInfo ci) {
		this.getWorld().addParticle(ParticleTypes.GUST_EMITTER_SMALL, this.getX(), this.getY(), this.getZ(), 1.0, 0.0, 0.0);
		Knockback.knockbackNearbyEntities(this.getWorld(), this, 2.5F, 0.6F, shearedSoundCategory);
	}
}
