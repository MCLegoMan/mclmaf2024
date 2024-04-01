package com.mclegoman.mclmaf2024.client;

import com.mclegoman.mclmaf2024.client.registry.BlockModelRegistry;
import com.mclegoman.mclmaf2024.client.registry.EntityModelRegistry;
import com.mclegoman.mclmaf2024.client.registry.ScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class ClientMain implements ClientModInitializer {
	public static MinecraftClient client;
	@Override
	public void onInitializeClient() {
		EntityModelRegistry.init();
		BlockModelRegistry.init();
		ScreenRegistry.init();
	}
	static {
		client = MinecraftClient.getInstance();
	}
}