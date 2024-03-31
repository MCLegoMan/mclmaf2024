package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.client.entity.renderer.feature.PigOverlayFeatureRenderer;
import com.mclegoman.mclmaf2024.client.registry.EntityModelRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntityRenderer.class)
public abstract class PigEntityRendererMixin extends MobEntityRenderer<PigEntity, PigEntityModel<PigEntity>> {
	public PigEntityRendererMixin(EntityRendererFactory.Context context, PigEntityModel<PigEntity> entityModel, float f) {
		super(context, entityModel, f);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void mclmaf2024$addPigOverlay(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new PigOverlayFeatureRenderer<>(this, new PigEntityModel<>(context.getPart(EntityModelRegistry.pigOverlay))));
	}
}
