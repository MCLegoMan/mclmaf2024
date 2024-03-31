package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.entity.data.Shareware;
import com.mclegoman.mclmaf2024.common.registry.EasterEggsRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
	@Shadow public abstract long getSeed();
	@Shadow public abstract List<ServerPlayerEntity> getPlayers();
	@Inject(method = "tick", at = @At("HEAD"))
	private void mclmaf2024$tick(CallbackInfo ci) {
		// We make the player have a "Shareware" attribute for multiplayer support.
		this.getPlayers().forEach((player -> ((Shareware)player).mclmaf2024$setShareware(EasterEggsRegistry.isSharewareEasterEgg(this.getSeed()))));
	}
}
