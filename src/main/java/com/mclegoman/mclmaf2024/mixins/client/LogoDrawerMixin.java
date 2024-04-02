package com.mclegoman.mclmaf2024.mixins.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {
	@Shadow @Final private boolean minceraft;
	@Shadow @Final public static Identifier MINCERAFT_TEXTURE;
	@Shadow @Final public static Identifier LOGO_TEXTURE;
	@Shadow @Final private boolean ignoreAlpha;
	@Inject(at = @At("HEAD"), method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", cancellable = true)
	private void mclmaf2025$draw(DrawContext context, int screenWidth, float alpha, int y, CallbackInfo ci) {
		// Renders a new update name texture, and the minecraft logo texture. The edition texture is disabled.
		context.setShaderColor(1.0F, 1.0F, 1.0F, this.ignoreAlpha ? 1.0F : alpha);
		int x = screenWidth / 2 - 128;
		context.drawTexture(new Identifier("mclmaf2024", "textures/gui/title/update.png"), x, y, 0.0F, 0.0F, 256, 128, 256, 128);
		context.drawTexture(this.minceraft ? MINCERAFT_TEXTURE : LOGO_TEXTURE, x, y, 0.0F, 0.0F, 256, 44, 256, 64);
		context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		ci.cancel();
	}
}