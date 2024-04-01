package com.mclegoman.mclmaf2024.mixins.common;

import com.mclegoman.mclmaf2024.common.enchantment.GravityEnchantment;
import com.mclegoman.mclmaf2024.common.entity.helper.Knockback;
import com.mclegoman.mclmaf2024.common.registry.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	@Shadow
	public abstract int getArmor();
	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);
	@Shadow @Nullable
	public abstract LivingEntity getAttacker();
	@Shadow
	public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);
	@Shadow public abstract Iterable<ItemStack> getArmorItems();
	@Shadow public abstract boolean isMobOrPlayer();
	@Shadow public abstract boolean isAlive();

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

	@Shadow public abstract Iterable<ItemStack> getHandItems();

	@Inject(method = "getScale", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$modifyScales(CallbackInfoReturnable<Float> cir) {
		// If the entity is wearing something in EquipmentSlot.HEAD, they will be shrunk by 2.5% of EquipmentSlot.HEAD.getProtection(), if that item also makes isKappaEasterEgg true, they will be shrunk to 65% of that value.
		ItemStack headArmor = getHelmet();
		if (headArmor != null) {
			int headArmorProtection = headArmor.getItem() instanceof ArmorItem ? ((ArmorItem) headArmor.getItem()).getProtection() : 0;
			cir.setReturnValue((float) ((cir.getReturnValue() - (headArmorProtection > 1 ? headArmorProtection * (getGravity() * 0.1F) : 0.0F)) * (EasterEggsRegistry.isKappaEasterEgg(headArmor) ? 0.65F : 1.0F)));
		}
	}
	@Inject(method = "canBreatheInWater", at = @At("HEAD"), cancellable = true)
	private void mclmaf2024$kappaEasterEggWaterBreathing(CallbackInfoReturnable<Boolean> cir) {
		// If the entity is wearing something in EquipmentSlot.HEAD that makes isKappaEasterEgg true, they will be able to breathe in water.
		ItemStack headArmor = getHelmet();
		if (headArmor != null && EasterEggsRegistry.isKappaEasterEgg(headArmor)) cir.setReturnValue(true);
	}
	@ModifyArg(method = "takeKnockback", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(D)Lnet/minecraft/util/math/Vec3d;"))
	private double mclmaf2024$kappaEasterEggKnockback(double strength) {
		// If the entity is wearing something in EquipmentSlot.HEAD that makes isKappaEasterEgg true, they will have a higher knockback.
		if (this.getAttacker() != null) {
			ItemStack headArmor = getHelmet(this.getAttacker());
			if (headArmor != null && EasterEggsRegistry.isKappaEasterEgg(headArmor))
				return strength * 5.0F;
		}
		return strength;
	}
	@Inject(method = "tick", at = @At("RETURN"))
	private void mclmaf2024$kappaIronAllergy(CallbackInfo ci) {
		if (this.isAlive()) {
			ItemStack headArmor = getHelmet();
			boolean isKappa = (headArmor != null && EasterEggsRegistry.isKappaEasterEgg(headArmor));
			if (isKappa) {
				// If the player is wearing/holding something made of iron, they will be given the wither effect.
				getArmorItems().forEach(this::kappaIronAllergy);
				getHandItems().forEach(this::kappaIronAllergy);
			}
		}
	}
	@Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;removePowderSnowSlow()V"))
	private void mclmaf2024$kappaFreezeInColdBiomes(CallbackInfo ci) {
		// We disable the setFrozenTicks() function in EntityMixin due to Kappa Easter Egg needing to be frozen in cold biomes.
		// If the entity is wearing something in EquipmentSlot.HEAD that makes isKappaEasterEgg true, they will freeze in cold biomes, OR if the entity is in powder snow and can be frozen.
		if (!this.getWorld().isClient && this.isAlive()) {
			ItemStack headArmor = getHelmet();
			boolean isKappa = (headArmor != null && EasterEggsRegistry.isKappaEasterEgg(headArmor));
			boolean isCold = this.getWorld().getBiome(this.getBlockPos()).value().computeTemperature(this.getBlockPos()) < 0.1;
			int frozenTicks = this.getFrozenTicks();
			if ((this.inPowderSnow && this.canFreeze()) || (isKappa && isCold)) {
				this.dataTracker.set(Entity.FROZEN_TICKS, Math.min(this.getMinFreezeDamageTicks(), frozenTicks + 1));
			} else {
				this.dataTracker.set(Entity.FROZEN_TICKS, Math.max(0, frozenTicks - 2));
			}
		}
	}
	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void mclmaf2024$heavyArmorDisablesSprinting(CallbackInfo ci) {
		// If the entity has an armor protection value above 10, swimming and sprinting will be disabled.
		if ((getArmor() > (20 - ((20 * (getGravity() * 10)) * 0.64F)) && this.isSprinting()) && !this.hasStatusEffect(StatusEffects.STRENGTH))
			this.setSprinting(false);
	}
	@Inject(method = "getGravity", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$gravityEnchantments_gravity(CallbackInfoReturnable<Double> cir) {
		if (this.isAlive()) {
			// Multiply gravity by (anti)antiGravity.gravityMultiplier if wearing (anti-)anti-gravity enchantment.
			// Multiply gravity by dimension gravity multiplier.
			boolean isAntiGravity = EnchantmentHelper.getEquipmentLevel(EnchantmentRegistry.antiGravity, ((LivingEntity) this.getEntityWorld().getEntityById(this.getId()))) > 0;
			boolean isAntiAntiGravity = EnchantmentHelper.getEquipmentLevel(EnchantmentRegistry.antiAntiGravity, ((LivingEntity) this.getEntityWorld().getEntityById(this.getId()))) > 0;
			boolean isAncientWorld = this.getWorld().getRegistryKey() == WorldRegistry.ancientWorld;
			boolean isNether = this.getWorld().getRegistryKey() == World.NETHER;
			boolean isEnd = this.getWorld().getRegistryKey() == World.END;
			boolean isPotato = this.getWorld().getRegistryKey() == World.field_50737;
			float dimensionHeight = this.getWorld().getDimension().height();
			if (isAntiGravity || isAntiAntiGravity || isAncientWorld || isNether || isEnd || isPotato) {
				double gravity = cir.getReturnValue();
				if (isAncientWorld) {
					gravity *= 0.8;
				}
				if (isNether) {
					gravity *= 1.2;
				}
				if (isEnd) {
					gravity *= 0.2;
				}
				if (isPotato) {
					gravity *= 0.5;
				}
				if (isAntiGravity) {
					gravity *= ((GravityEnchantment) EnchantmentRegistry.antiGravity).gravityMultiplier;
				}
				if (isAntiAntiGravity) {
					gravity *= ((GravityEnchantment) EnchantmentRegistry.antiAntiGravity).gravityMultiplier;
				}
				// Multiply gravity by -1 if wearing both Anti-Gravity and Anti-Anti-Gravity.
				if (isAntiGravity && isAntiAntiGravity) {
					// We limit this so the entity doesn't go too high.
					if (!(this.getY() > dimensionHeight * 10)) gravity *= -1;
				}
				cir.setReturnValue(gravity);
			}
		}
	}
	@Inject(method = "getSafeFallDistance()I", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$gravityEnchantments_fallDistance(CallbackInfoReturnable<Integer> cir) {
		if (this.isAlive()) {
			// Multiply safeFallDistance by (anti)antiGravity.safeFallDistanceMultiplier if wearing (anti-)anti-gravity enchantment.
			// Multiply fall distance by dimension fall distance multiplier.
			boolean isAntiGravity = EnchantmentHelper.getEquipmentLevel(EnchantmentRegistry.antiGravity, ((LivingEntity) this.getEntityWorld().getEntityById(this.getId()))) > 0;
			boolean isAntiAntiGravity = EnchantmentHelper.getEquipmentLevel(EnchantmentRegistry.antiAntiGravity, ((LivingEntity) this.getEntityWorld().getEntityById(this.getId()))) > 0;
			boolean isAncientWorld = this.getWorld().getRegistryKey() == WorldRegistry.ancientWorld;
			boolean isNether = this.getWorld().getRegistryKey() == World.NETHER;
			boolean isEnd = this.getWorld().getRegistryKey() == World.END;
			boolean isPotato = this.getWorld().getRegistryKey() == World.field_50737;
			if (isAntiGravity || isAntiAntiGravity || isAncientWorld || isNether || isEnd || isPotato) {
				double gravity = cir.getReturnValue();
				if (isAncientWorld) {
					gravity *= 2.0;
				}
				if (isNether) {
					gravity *= 0.8;
				}
				if (isEnd) {
					gravity *= 8.0;
				}
				if (isPotato) {
					gravity *= 5.0;
				}
				if (isAntiGravity) {
					gravity *= ((GravityEnchantment) EnchantmentRegistry.antiGravity).safeFallDistanceMultiplier;
				}
				if (isAntiAntiGravity) {
					gravity *= ((GravityEnchantment) EnchantmentRegistry.antiAntiGravity).safeFallDistanceMultiplier;
				}
				cir.setReturnValue((int) gravity);
			}
		}
	}
	@Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
	private void mclmaf2024$fallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
		if (this.isAlive()) {
			// Multiply fallDamage by (anti)antiGravity.fallDamageMultiplier if wearing (anti-)anti-gravity enchantment.
			// Multiply fall damage by dimension fall damage multiplier.
			boolean isAntiGravity = EnchantmentHelper.getEquipmentLevel(EnchantmentRegistry.antiGravity, ((LivingEntity) this.getEntityWorld().getEntityById(this.getId()))) > 0;
			boolean isAntiAntiGravity = EnchantmentHelper.getEquipmentLevel(EnchantmentRegistry.antiAntiGravity, ((LivingEntity) this.getEntityWorld().getEntityById(this.getId()))) > 0;
			boolean isAncientWorld = this.getWorld().getRegistryKey() == WorldRegistry.ancientWorld;
			boolean isNether = this.getWorld().getRegistryKey() == World.NETHER;
			boolean isEnd = this.getWorld().getRegistryKey() == World.END;
			boolean isPotato = this.getWorld().getRegistryKey() == World.field_50737;
			if (isAntiGravity || isAntiAntiGravity || isAncientWorld || isNether || isEnd || isPotato) {
				double gravity = cir.getReturnValue();
				if (isAncientWorld) {
					gravity *= 0.8;
				}
				if (isNether) {
					gravity *= 1.2;
				}
				if (isEnd) {
					gravity *= 0.2;
				}
				if (isPotato) {
					gravity *= 0.5;
				}
				if (isAntiGravity) {
					gravity *= ((GravityEnchantment) EnchantmentRegistry.antiGravity).fallDamageMultiplier;
				}
				if (isAntiAntiGravity) {
					gravity *= ((GravityEnchantment) EnchantmentRegistry.antiAntiGravity).fallDamageMultiplier;
				}
				cir.setReturnValue((int) gravity);
			}
		}
	}
	@Inject(method = "damage", at = @At("HEAD"))
	private void mclmaf2024$knockbackOnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.isAlive()) {
			// Knockback attacker entity when attacking.
			if (source.getSource() != null && source.getSource() != this) {
				this.getWorld().addParticle(ParticleTypes.GUST_EMITTER_SMALL, source.getSource().getX(), source.getSource().getY(), source.getSource().getZ(), 1.0, 0.0, 0.0);
				if (source.getSource() instanceof LivingEntity) {
					Knockback.knockbackEntity(this.getWorld(), (LivingEntity) source.getSource(), 5.0F, 1.2F, SoundCategory.PLAYERS);
				} else {
					if (source.getSource() instanceof ProjectileEntity) {
						Knockback.knockbackEntity(this.getWorld(), (LivingEntity) ((ProjectileEntity) source.getSource()).getOwner(), 5.0F, 1.2F, SoundCategory.PLAYERS);
					}
				}
			}
		}
	}
	@Unique
	private ItemStack getHelmet() {
		return this.isAlive() && this.isMobOrPlayer() ? (this.getArmorItems() != null ? this.getEquippedStack(EquipmentSlot.HEAD) : null) : null;
	}
	@Unique
	private ItemStack getHelmet(LivingEntity entity) {
		return entity != null && entity.isAlive() && entity.isMobOrPlayer() ? (entity.getArmorItems() != null ? entity.getEquippedStack(EquipmentSlot.HEAD) : null) : null;
	}
	@Unique
	private void kappaIronAllergy(ItemStack itemStack) {
		if (itemStack.isIn(TagKeyRegistry.kappaIronAllergy)) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80), this);
			if (this.isPlayer()) {
				PlayerEntity player = this.getWorld().getPlayerByUuid(this.getUuid());
				if (player != null) player.sendMessage(Text.translatable("chat.mclmaf2024.kappa.iron_allergy").formatted(Formatting.BOLD, Formatting.RED), true);
			}
		}
	}
}
