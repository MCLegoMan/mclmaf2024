package com.mclegoman.mclmaf2024.common.registry;

import com.mclegoman.mclmaf2024.common.enchantment.GravityEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class EnchantmentRegistry {
	public static final Enchantment antiGravity;
	public static final Enchantment antiAntiGravity;
	public static void init() {
	}
	static {
		antiGravity = Registry.register(Registries.ENCHANTMENT, new Identifier("mclmaf2024", "anti_gravity"),  new GravityEnchantment(Enchantment.properties(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.leveledCost(10, 10), Enchantment.leveledCost(25, 10), 8, EquipmentSlot.FEET), 0.2F, 8.0F, 0.2F, false, true));
		antiAntiGravity = Registry.register(Registries.ENCHANTMENT, new Identifier("mclmaf2024", "anti_anti_gravity"),  new GravityEnchantment(Enchantment.properties(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.leveledCost(10, 10), Enchantment.leveledCost(25, 10), 8, EquipmentSlot.FEET), 8.0F, 0.2F, 8.0F, true, true));
	}
}
