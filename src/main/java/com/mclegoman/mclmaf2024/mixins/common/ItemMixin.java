package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.registry.EasterEggsRegistry;
import com.mclegoman.mclmaf2024.common.registry.TagKeyRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void mclmaf2024$kappaDiet(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (user.isAlive()) {
			// If the entity is wearing something in EquipmentSlot.HEAD that makes isKappaEasterEgg true, they will only be able to eat vegetables and fish.
			ItemStack headArmor = this.getHelmet(user);
			boolean isKappa = (headArmor != null && EasterEggsRegistry.isKappaEasterEgg(headArmor));
			ItemStack itemStack = user.getStackInHand(hand);
			if (itemStack.contains(DataComponentTypes.FOOD) && isKappa) {
				if (!this.kappaDiet(itemStack)) {
					cir.setReturnValue(TypedActionResult.fail(itemStack));
					user.sendMessage(Text.translatable("chat.mclmaf2024.kappa.pescatarian").formatted(Formatting.BOLD, Formatting.RED), true);
				}
			}
		}
	}
	@Unique
	private boolean kappaDiet(ItemStack itemStack) {
		return !itemStack.isIn(TagKeyRegistry.kappaDiet);
	}
	@Unique
	private ItemStack getHelmet(LivingEntity entity) {
		return entity != null && entity.isAlive() && entity.isMobOrPlayer() ? (entity.getArmorItems() != null ? entity.getEquippedStack(EquipmentSlot.HEAD) : null) : null;
	}
}
