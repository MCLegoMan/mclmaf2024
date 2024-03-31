package com.mclegoman.mclmaf2024.mixins.client;

import com.mclegoman.mclmaf2024.client.ClientMain;
import com.mclegoman.mclmaf2024.common.registry.EasterEggsRegistry;
import net.minecraft.client.Mouse;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public abstract class MouseMixin {
	// Inverts getInvertYMouse() if wearing an item with a with custom name that is in the EasterEggRegistry.flippedEasterEggNames list or mclmaf2024:flipped component is true.
	@Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Ljava/lang/Boolean;booleanValue()Z", ordinal = 0))
	private boolean mclmaf2024$invertMouseWhenFlipped(Boolean enabled) {
		return (ClientMain.client.player != null) ? (EasterEggsRegistry.isFlippedEasterEgg(ClientMain.client.player.getEquippedStack(EquipmentSlot.HEAD)) || enabled) : enabled;
	}
}
