package com.mclegoman.mclmaf2024.common.registry;

import com.mclegoman.mclmaf2024.common.entity.AncientMoobloomEntity;
import com.mclegoman.mclmaf2024.common.entity.MoobloomEntity;
import com.mclegoman.mclmaf2024.common.entity.PotatoMoobloomEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityRegistry {
	public static final EntityType<MoobloomEntity> moobloom;
	public static final EntityType<AncientMoobloomEntity> ancientMoobloom;
	public static final EntityType<PotatoMoobloomEntity> potatoMoobloom;
	public static void init() {
		FabricDefaultAttributeRegistry.register(moobloom, MoobloomEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ancientMoobloom, AncientMoobloomEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(potatoMoobloom, PotatoMoobloomEntity.createAttributes());
	}
	static {
		moobloom = Registry.register(Registries.ENTITY_TYPE, new Identifier("mclmaf2024", "moobloom"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F).withEyeHeight(1.3F)).build());
		ancientMoobloom = Registry.register(Registries.ENTITY_TYPE, new Identifier("mclmaf2024", "ancient_moobloom"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AncientMoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F).withEyeHeight(1.3F)).build());
		potatoMoobloom = Registry.register(Registries.ENTITY_TYPE, new Identifier("mclmaf2024", "potato_moobloom"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PotatoMoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F).withEyeHeight(1.3F)).build());
	}
}
