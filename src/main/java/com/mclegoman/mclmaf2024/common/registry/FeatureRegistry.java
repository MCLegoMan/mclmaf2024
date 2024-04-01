package com.mclegoman.mclmaf2024.common.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class FeatureRegistry {
	public static final RegistryKey<PlacedFeature> oreMysterious;
	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreMysterious);
		BiomeModifications.addSpawn(context -> context.canGenerateIn(DimensionOptions.field_50994), SpawnGroup.CREATURE, EntityRegistry.potatoMoobloom, 8, 4, 8);
	}
	static {
		oreMysterious = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("mclmaf2024","ore_mysterious"));
	}
}
