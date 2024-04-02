package com.mclegoman.mclmaf2024.common;

import com.mclegoman.mclmaf2024.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static ModContainer modContainer;
	public static Logger logger = LoggerFactory.getLogger("mclmaf2024");
	@Override
	public void onInitialize() {
		modContainer = FabricLoader.getInstance().getModContainer("mclmaf2024").isPresent() ? FabricLoader.getInstance().getModContainer("mclmaf2024").get() : null;
		ComponentTypeRegistry.init();
		GameEventRegistry.init();
		SoundEventRegistry.init();
		BlockRegistry.init();
		BlockEntityRegistry.init();
		ItemRegistry.init();
		EnchantmentRegistry.init();
		FeatureRegistry.init();
		EntityRegistry.init();
		ScreenHandlerRegistry.init();
		LootTablesRegistry.init();
		EasterEggsRegistry.init();
	}
}