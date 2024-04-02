package com.mclegoman.mclmaf2024.client.registry;

import com.mclegoman.mclmaf2024.common.registry.EnchantmentRegistry;
import com.mclegoman.mclmaf2024.common.registry.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupRegistry {
	private static final ItemGroup aprilFools;
	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
			content.addAfter(Items.LAPIS_BLOCK, ItemRegistry.mysteriousBlock);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
			content.addAfter(Items.DEEPSLATE_LAPIS_ORE, ItemRegistry.mysteriousOre);
			content.addAfter(ItemRegistry.mysteriousOre, ItemRegistry.deepslateMysteriousOre);
			content.addAfter(Items.DANDELION, ItemRegistry.eonizedDandelion);
			content.addAfter(Items.POPPY, ItemRegistry.eonizedPoppy);
			content.addAfter(Items.SHORT_GRASS, ItemRegistry.eonizedShortGrass);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
			content.addAfter(Items.DROPPER, ItemRegistry.exchanger);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
			content.addAfter(Items.LAPIS_LAZULI, ItemRegistry.mysteriousGem);
			content.addAfter(Items.SKULL_BANNER_PATTERN, ItemRegistry.frogBannerPattern);
			content.addAfter(Items.WHEAT, ItemRegistry.mysteriousWheat);
			content.addAfter(Items.PINK_DYE, ItemRegistry.eonizeEssence);
			content.addAfter(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, ItemRegistry.flowerArmorTrimSmithingTemplate);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
			content.addAfter(Items.MOOSHROOM_SPAWN_EGG, ItemRegistry.moobloomSpawnEgg);
			content.addAfter(ItemRegistry.moobloomSpawnEgg, ItemRegistry.ancientMoobloomSpawnEgg);
			content.addAfter(ItemRegistry.ancientMoobloomSpawnEgg, ItemRegistry.potatoMoobloomSpawnEgg);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.addAfter(Items.END_PORTAL_FRAME, ItemRegistry.ancientPortalFrame);
			content.addAfter(Items.ENDER_EYE, ItemRegistry.eonizedPearl);
		});
	}
	static {
		aprilFools = Registry.register(Registries.ITEM_GROUP, new Identifier("mclmaf2024", "mclmaf2024"), FabricItemGroup.builder()
				.icon(() -> new ItemStack(ItemRegistry.mysteriousGem))
				.displayName(Text.translatable("itemGroup.mclmaf2024.mclmaf2024"))
				.entries((context, entries) -> {
					entries.add(ItemRegistry.mysteriousBlock);
					entries.add(ItemRegistry.mysteriousOre);
					entries.add(ItemRegistry.deepslateMysteriousOre);
					entries.add(ItemRegistry.eonizedShortGrass);
					entries.add(ItemRegistry.eonizedDandelion);
					entries.add(ItemRegistry.eonizedPoppy);
					entries.add(ItemRegistry.ancientPortalFrame);
					entries.add(ItemRegistry.eonizedPearl);
					entries.add(ItemRegistry.exchanger);
					entries.add(ItemRegistry.mysteriousGem);
					entries.add(ItemRegistry.mysteriousWheat);
					entries.add(ItemRegistry.eonizeEssence);
					entries.add(ItemRegistry.frogBannerPattern);
					entries.add(ItemRegistry.flowerArmorTrimSmithingTemplate);
					entries.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(EnchantmentRegistry.antiGravity, 1)));
					entries.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(EnchantmentRegistry.antiAntiGravity, 1)));
					entries.add(ItemRegistry.moobloomSpawnEgg);
					entries.add(ItemRegistry.ancientMoobloomSpawnEgg);
					entries.add(ItemRegistry.potatoMoobloomSpawnEgg);
				})
				.build());
	}
}
