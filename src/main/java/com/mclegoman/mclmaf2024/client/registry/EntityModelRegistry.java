package com.mclegoman.mclmaf2024.client.registry;

import com.mclegoman.mclmaf2024.client.entity.model.MoobloomEntityModel;
import com.mclegoman.mclmaf2024.client.entity.renderer.MoobloomEntityRenderer;
import com.mclegoman.mclmaf2024.common.registry.EntityRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class EntityModelRegistry {
	public static final EntityModelLayer pigOverlay;
	public static final EntityModelLayer moobloom;
	public static final EntityModelLayer moobloomOverlay;
	public static void init() {
		EntityRendererRegistry.register(EntityRegistry.moobloom, MoobloomEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(moobloom, MoobloomEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(EntityRegistry.ancientMoobloom, MoobloomEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(moobloomOverlay, MoobloomEntityModel::getTexturedOverlayModelData);
		EntityModelLayerRegistry.registerModelLayer(pigOverlay, () -> PigEntityModel.getTexturedModelData(new Dilation(0.498F)));
	}
	static {
		pigOverlay = new EntityModelLayer(new Identifier("minecraft", "pig"), "mclmaf2024_pig_overlay");
		moobloom = new EntityModelLayer(new Identifier("mclmaf2024", "moobloom"), "main");
		moobloomOverlay = new EntityModelLayer(new Identifier("mclmaf2024", "moobloom"), "overlay");
	}
}
