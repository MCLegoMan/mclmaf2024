package com.mclegoman.mclmaf2024.client.entity.renderer;

import com.mclegoman.mclmaf2024.client.entity.renderer.feature.MoobloomFlowerFeatureRenderer;
import com.mclegoman.mclmaf2024.client.entity.renderer.feature.MoobloomOverlayFeatureRenderer;
import com.mclegoman.mclmaf2024.client.registry.EntityModelRegistry;
import com.mclegoman.mclmaf2024.common.entity.AncientMoobloomEntity;
import com.mclegoman.mclmaf2024.common.entity.MoobloomEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, CowEntityModel<MoobloomEntity>> {
	public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.COW)), 0.7F);
		this.addFeature(new MoobloomFlowerFeatureRenderer<>(this, context.getBlockRenderManager()));
		this.addFeature(new MoobloomOverlayFeatureRenderer<>(this, new CowEntityModel<>(context.getPart(EntityModelRegistry.moobloomOverlay))));
	}

	@Override
	public Identifier getTexture(MoobloomEntity entity) {
		if (entity instanceof AncientMoobloomEntity) return new Identifier("mclmaf2024", "textures/entity/ancient_moobloom/ancient_moobloom.png");
		else return new Identifier("mclmaf2024", "textures/entity/moobloom/moobloom.png");
	}
}

