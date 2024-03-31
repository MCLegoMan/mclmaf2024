package com.mclegoman.mclmaf2024.client.registry;

import com.mclegoman.mclmaf2024.client.ClientMain;
import com.mclegoman.mclmaf2024.common.Main;
import com.mclegoman.mclmaf2024.common.registry.ComponentTypeRegistry;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ShaderRegistry {
	public static PostEffectProcessor helmetShader;
	public static PostEffectProcessor flippedShader;
	public static PostEffectProcessor loveAndHugsShader;
	public static PostEffectProcessor sharewareShader;
	public static void setHelmetShader(Identifier identifier) {
		try {
			if (helmetShader == null) {
				helmetShader = new PostEffectProcessor(ClientMain.client.getTextureManager(), ClientMain.client.getResourceManager(), ClientMain.client.getFramebuffer(), identifier);
				helmetShader.setupDimensions(ClientMain.client.getWindow().getFramebufferWidth(), ClientMain.client.getWindow().getFramebufferHeight());
			}
		} catch (Exception error) {
			Main.logger.error("An error occurred whilst setting helmet shader: " + error.getLocalizedMessage());
		}
	}
	public static boolean shouldRenderHelmetShader(ItemStack headArmor) {
		return !headArmor.getComponents().contains(ComponentTypeRegistry.disableHelmetShader) || !Boolean.TRUE.equals(headArmor.getComponents().get(ComponentTypeRegistry.disableHelmetShader));
	}
	public static void setFlippedShader(Identifier identifier) {
		try {
			if (flippedShader == null) {
				flippedShader = new PostEffectProcessor(ClientMain.client.getTextureManager(), ClientMain.client.getResourceManager(), ClientMain.client.getFramebuffer(), identifier);
				flippedShader.setupDimensions(ClientMain.client.getWindow().getFramebufferWidth(), ClientMain.client.getWindow().getFramebufferHeight());
			}
		} catch (Exception error) {
			Main.logger.error("An error occurred whilst setting flipped shader: " + error.getLocalizedMessage());
		}
	}
	public static void setLoveAndHugsShader(Identifier identifier) {
		try {
			if (loveAndHugsShader == null) {
				loveAndHugsShader = new PostEffectProcessor(ClientMain.client.getTextureManager(), ClientMain.client.getResourceManager(), ClientMain.client.getFramebuffer(), identifier);
				loveAndHugsShader.setupDimensions(ClientMain.client.getWindow().getFramebufferWidth(), ClientMain.client.getWindow().getFramebufferHeight());
			}
		} catch (Exception error) {
			Main.logger.error("An error occurred whilst setting helmet shader: " + error.getLocalizedMessage());
		}
	}

	public static void setSharewareShader(Identifier identifier) {
		try {
			if (sharewareShader == null) {
				sharewareShader = new PostEffectProcessor(ClientMain.client.getTextureManager(), ClientMain.client.getResourceManager(), ClientMain.client.getFramebuffer(), identifier);
				sharewareShader.setupDimensions(ClientMain.client.getWindow().getFramebufferWidth(), ClientMain.client.getWindow().getFramebufferHeight());
			}
		} catch (Exception error) {
			Main.logger.error("An error occurred whilst setting shareware shader: " + error.getLocalizedMessage());
		}
	}
}
