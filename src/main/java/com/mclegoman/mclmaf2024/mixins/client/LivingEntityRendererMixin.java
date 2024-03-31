package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.common.Main;
import com.mclegoman.mclmaf2024.common.registry.EasterEggsRegistry;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
	// If the entity is wearing an item with a with custom name that is in the EasterEggRegistry.flippedEasterEggNames list or mclmaf2024:flipped component is true, they will be flipped upside down.
	@Inject(method = "shouldFlipUpsideDown", at = @At("RETURN"), cancellable = true)
	private static void mclmaf2024$flipPlayerEasterEgg(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		try {
			if (entity instanceof PlayerEntity) {
				ItemStack headArmor = ((PlayerEntity)entity).getInventory().getArmorStack(3);
				if (EasterEggsRegistry.isFlippedEasterEgg(headArmor)) {
					cir.setReturnValue(true);
				}
			}
		} catch (Exception error) {
			Main.logger.error("An error occurred whilst trying to flip player: " + error);
		}
	}
}
