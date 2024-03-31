package com.mclegoman.mclmaf2024.common.enchantment;

import net.minecraft.enchantment.Enchantment;

public class GravityEnchantment extends Enchantment {
	public float gravityMultiplier;
	public float safeFallDistanceMultiplier;
	public float fallDamageMultiplier;
	public boolean cursed;
	public boolean treasure;
	public GravityEnchantment(Enchantment.Properties properties, float gravity, float fallDistance, float fallDamage, boolean isCursed, boolean isTreasure) {
		super(properties);
		this.gravityMultiplier = gravity;
		this.safeFallDistanceMultiplier = fallDistance;
		this.fallDamageMultiplier = fallDamage;
		this.cursed = isCursed;
		this.treasure = isTreasure;
	}

	public boolean isTreasure() {
		return true;
	}

	public boolean isAvailableForEnchantedBookOffer() {
		return false;
	}

	public boolean isAvailableForRandomSelection() {
		return false;
	}

	public boolean isCursed() {
		return this.cursed;
	}
}
