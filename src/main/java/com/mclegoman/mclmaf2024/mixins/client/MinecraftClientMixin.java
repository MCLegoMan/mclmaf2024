package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.common.registry.SoundEventRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "getMusicType", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$replaceMenuMusic(CallbackInfoReturnable<MusicSound> cir) {
		if (cir.getReturnValue() == MusicType.MENU) cir.setReturnValue(SoundEventRegistry.musicMenu);
	}
}
