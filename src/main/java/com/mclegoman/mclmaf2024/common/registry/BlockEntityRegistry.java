package com.mclegoman.mclmaf2024.common.registry;

import com.mclegoman.mclmaf2024.common.block.ExchangerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityRegistry {
	public static final BlockEntityType<ExchangerBlockEntity> exchangerEntity;
	public static void init() {
	}
	static {
		exchangerEntity = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier("mclmaf2024", "exchanger"), FabricBlockEntityTypeBuilder.create(ExchangerBlockEntity::new, BlockRegistry.exchanger).build());
	}
}
