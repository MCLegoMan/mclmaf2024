package com.mclegoman.mclmaf2024.client;

import com.mclegoman.mclmaf2024.client.registry.BlockModelRegistry;
import com.mclegoman.mclmaf2024.client.registry.EntityModelRegistry;
import com.mclegoman.mclmaf2024.client.registry.ScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ClientMain implements ClientModInitializer {
	public static MinecraftClient client;
	@Override
	public void onInitializeClient() {
		EntityModelRegistry.init();
		BlockModelRegistry.init();
		ScreenRegistry.init();

		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			if (client1.options.loadToolbarActivatorKey.wasPressed()) {
				client1.takePanorama(client1.runDirectory, 1024, 1024);
			}
		});
	}
	static {
		client = MinecraftClient.getInstance();
	}
}