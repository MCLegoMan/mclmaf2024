package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.client.ClientMain;
import com.mclegoman.mclmaf2024.common.Main;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Shadow private boolean doBackgroundFade;
	@Shadow private long backgroundFadeStart;
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I"), method = "render")
	private void mclmaf2025$render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		// Renders the mod name and version on the title screen above the minecraft version.
		int fadeColor = MathHelper.ceil((this.doBackgroundFade ? MathHelper.clamp(((float)(Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F) - 1.0F, 0.0F, 1.0F) : 1.0F) * 255.0F) << 24;
		if (Main.modContainer != null) context.drawTextWithShadow(ClientMain.client.textRenderer, Text.translatable("mclmaf2024.version_overlay", Text.translatable("mclmaf2024.name"), Main.modContainer.getMetadata().getVersion()), 2, ClientMain.client.getWindow().getScaledHeight() - 20, 16777215 | fadeColor);
	}
}