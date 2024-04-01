package com.mclegoman.mclmaf2024.client.entity.renderer.feature;

import com.mclegoman.mclmaf2024.common.entity.AncientMoobloomEntity;
import com.mclegoman.mclmaf2024.common.entity.PotatoMoobloomEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MoobloomOverlayFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private final M model;

	public MoobloomOverlayFeatureRenderer(FeatureRendererContext<T, M> context, M model) {
		super(context);
		this.model = model;
	}

	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		this.getContextModel().copyStateTo(this.model);
		this.model.animateModel(entity, limbAngle, limbDistance, tickDelta);
		this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		Identifier texture = entity instanceof AncientMoobloomEntity ? new Identifier("mclmaf2024", "textures/entity/ancient_moobloom/ancient_moobloom_overlay.png") : (entity instanceof PotatoMoobloomEntity ? new Identifier("mclmaf2024", "textures/entity/potato_moobloom/potato_moobloom_overlay.png") : new Identifier("mclmaf2024", "textures/entity/moobloom/moobloom_overlay.png"));
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(texture));
		this.model.render(matrices, vertexConsumer, light, LivingEntityRenderer.getOverlay(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
	}
}