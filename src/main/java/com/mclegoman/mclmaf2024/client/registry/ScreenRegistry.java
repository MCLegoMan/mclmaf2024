package com.mclegoman.mclmaf2024.client.registry;

import com.mclegoman.mclmaf2024.client.screen.ExchangerScreen;
import com.mclegoman.mclmaf2024.common.registry.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ScreenRegistry {
	public static void init() {
		HandledScreens.register(ScreenHandlerRegistry.exchanger, ExchangerScreen::new);
	}
}
