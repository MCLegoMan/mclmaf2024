package com.mclegoman.mclmaf2024.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ComponentTypeRegistry {
	public static final DataComponentType<Boolean> flippedEasterEgg;
	public static final DataComponentType<Boolean> kappaEasterEgg;
	public static final DataComponentType<Boolean> loveAndHugsEasterEgg;
	public static final DataComponentType<Boolean> disableHelmetShader;
	public static void init() {}
	private static <T> DataComponentType<T> register(Identifier id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(DataComponentType.builder()).build());
	}
	static {
		flippedEasterEgg = register(new Identifier("mclmaf2024", "flipped"), (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
		kappaEasterEgg = register(new Identifier("mclmaf2024", "kappa"), (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
		loveAndHugsEasterEgg = register(new Identifier("mclmaf2024", "love_and_hugs"), (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
		disableHelmetShader = register(new Identifier("mclmaf2024", "disable_helmet_shader"), (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
	}
}
