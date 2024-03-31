package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ArmorTrimPatternRegistry {
	public static RegistryKey<ArmorTrimPattern> flower;
	static {
		flower = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("mclmaf2024", "flower"));
	}
}
