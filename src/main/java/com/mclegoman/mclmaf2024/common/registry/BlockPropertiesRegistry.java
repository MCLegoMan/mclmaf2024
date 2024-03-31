package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.state.property.BooleanProperty;

public class BlockPropertiesRegistry {
	public static BooleanProperty eonized;
	public static BooleanProperty natural;
	static {
		eonized = BooleanProperty.of("mclmaf2024_eonized");
		natural = BooleanProperty.of("mclmaf2024_natural");
	}
}
