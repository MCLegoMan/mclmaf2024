package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.client.ClientMain;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
	@Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$getLeftText(CallbackInfoReturnable<List<String>> cir) {
		List<String> leftText = cir.getReturnValue();
		if (ClientMain.client.player != null) leftText.add("getGravity: " + ClientMain.client.player.getGravity() + " getScale: " + ClientMain.client.player.getScale());
		cir.setReturnValue(leftText);
	}
}
