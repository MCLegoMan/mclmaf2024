package com.mclegoman.mclmaf2024.client;

import com.mclegoman.mclmaf2024.client.registry.BlockModelRegistry;
import com.mclegoman.mclmaf2024.client.registry.EntityModelRegistry;
import com.mclegoman.mclmaf2024.client.registry.ScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.random.Random;

public class ClientMain implements ClientModInitializer {
	public static MinecraftClient client;
	public static final boolean isMinceraft;
	@Override
	public void onInitializeClient() {
		EntityModelRegistry.init();
		BlockModelRegistry.init();
		ScreenRegistry.init();
	}
	static {
		client = MinecraftClient.getInstance();
		isMinceraft = Random.create().nextFloat() < 1.0E-4;
	}
}