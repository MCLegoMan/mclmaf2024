package com.mclegoman.mclmaf2024.mixins.common;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method = "setFrozenTicks", at = @At("HEAD"), cancellable = true)
	private void mclmaf2024$disableSetFrozenTicks(CallbackInfo ci) {
		// We override this in LivingEntityMixin due to Kappa Easter Egg.
		ci.cancel();
	}
}
