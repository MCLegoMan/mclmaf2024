package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

public class GameEventRegistry {
	public static RegistryEntry.Reference<GameEvent> eonized;
	public static void init() {
	}
	static {
		eonized = Registry.registerReference(Registries.GAME_EVENT, new Identifier("mclmaf2024", "eonized"), new GameEvent(16));
	}
}
