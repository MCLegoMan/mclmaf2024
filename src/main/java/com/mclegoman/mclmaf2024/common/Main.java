package com.mclegoman.mclmaf2024.common;

import com.mclegoman.mclmaf2024.common.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static Logger logger = LoggerFactory.getLogger("mclmaf2024");
	@Override
	public void onInitialize() {
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