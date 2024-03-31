package com.mclegoman.mclmaf2024.client.registry;

import com.mclegoman.mclmaf2024.common.registry.BlockRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class BlockModelRegistry {
	public static void init() {
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.eonizedDandelion, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.pottedEonizedDandelion, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.eonizedPoppy, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.pottedEonizedPoppy, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.eonizedShortGrass, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ancientPortal, RenderLayer.getTranslucent());
	}
}
