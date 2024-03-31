package com.mclegoman.mclmaf2024.common.registry;

import com.mclegoman.mclmaf2024.common.screen.ExchangerScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenHandlerRegistry {
	public static final ScreenHandlerType<ExchangerScreenHandler> exchanger;
	public static void init() {
	}
	static {
		exchanger = Registry.register(Registries.SCREEN_HANDLER, new Identifier("mclmaf2024", "exchanger"), new ScreenHandlerType<>(ExchangerScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
	}
}
