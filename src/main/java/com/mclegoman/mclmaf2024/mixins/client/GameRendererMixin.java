package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.client.ClientMain;
import com.mclegoman.mclmaf2024.client.registry.ShaderRegistry;
import com.mclegoman.mclmaf2024.common.Main;
import com.mclegoman.mclmaf2024.common.entity.data.Shareware;
import com.mclegoman.mclmaf2024.common.registry.EasterEggsRegistry;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	// Renders custom shaders.
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;beginWrite(Z)V"))
	private void mclmaf2024$renderShaders(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
		try {
			if (ClientMain.client.player != null) {
				ItemStack headArmor = ClientMain.client.player.getInventory().getArmorStack(3);
				if (((Shareware)ClientMain.client.player).mclmaf2024$isShareware()) {
					ShaderRegistry.setSharewareShader(new Identifier("shaders/post/shareware.json"));
					if (ShaderRegistry.sharewareShader != null) ShaderRegistry.sharewareShader.render(tickDelta);
				}
				if (!headArmor.isEmpty()) {
					// Renders helmet shader if wearing helmet and mclmaf2024:disable_helmet_shader component is missing/false.
					if (ClientMain.client.options.getPerspective().isFirstPerson()) {
						if (ShaderRegistry.shouldRenderHelmetShader(headArmor)) {
							ShaderRegistry.setHelmetShader(new Identifier("shaders/post/helmet.json"));
							if (ShaderRegistry.helmetShader != null) ShaderRegistry.helmetShader.render(tickDelta);
						}
					}
					// Renders flipped shader if wearing an item with a custom name that is in the EasterEggRegistry.flippedEasterEggNames list or mclmaf2024:flipped component is true.
					if (EasterEggsRegistry.isFlippedEasterEgg(headArmor)) {
						ShaderRegistry.setFlippedShader(new Identifier("shaders/post/flipped.json"));
						if (ShaderRegistry.flippedShader != null) ShaderRegistry.flippedShader.render(tickDelta);
					}
					// Renders flipped shader if wearing a wither skeleton skull with custom name that is in the EasterEggRegistry.loveAndHugsEasterEgg list or mclmaf2024:love_and_hugs component is true.
					if (EasterEggsRegistry.isLoveAndHugsEasterEgg(headArmor)) {
						ShaderRegistry.setLoveAndHugsShader(new Identifier("shaders/post/love.json"));
						if (ShaderRegistry.loveAndHugsShader != null) ShaderRegistry.loveAndHugsShader.render(tickDelta);
					}
				}
			}
		} catch (Exception error) {
			Main.logger.error("An error occurred whilst trying to render shaders: " + error);
		}
	}
	// When the game window is resized, the all shader dimensions are updated.
	@Inject(method = "onResized", at = @At(value = "TAIL"))
	private void mclmaf2024$updateShadersOnResized(int width, int height, CallbackInfo ci) {
		if (ShaderRegistry.helmetShader != null) {
			ShaderRegistry.helmetShader.setupDimensions(width, height);
		}
		if (ShaderRegistry.flippedShader != null) {
			ShaderRegistry.flippedShader.setupDimensions(width, height);
		}
		if (ShaderRegistry.loveAndHugsShader != null) {
			ShaderRegistry.loveAndHugsShader.setupDimensions(width, height);
		}
		if (ShaderRegistry.sharewareShader != null) {
			ShaderRegistry.sharewareShader.setupDimensions(width, height);
		}
	}
}
