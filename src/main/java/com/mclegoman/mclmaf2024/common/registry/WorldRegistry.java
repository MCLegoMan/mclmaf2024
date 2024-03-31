package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WorldRegistry {
	public static RegistryKey<World> ancientWorld;
	static {
		ancientWorld = RegistryKey.of(RegistryKeys.WORLD, new Identifier("mclmaf2024", "ancientworld"));
	}
}
