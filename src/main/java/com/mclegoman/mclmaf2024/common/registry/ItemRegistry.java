package com.mclegoman.mclmaf2024.common.registry;

import com.mclegoman.mclmaf2024.client.registry.ItemGroupRegistry;
import com.mclegoman.mclmaf2024.common.item.EonizedPearlItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ItemRegistry {
	public static final Item mysteriousOre;
	public static final Item deepslateMysteriousOre;
	public static final Item mysteriousGem;
	public static final Item mysteriousBlock;
	public static final Item mysteriousWheat;
	public static final Item eonizeEssence;
	public static final Item eonizedPearl;
	public static final Item eonizedDandelion;
	public static final Item eonizedPoppy;
	public static final Item eonizedShortGrass;
	public static final Item exchanger;
	public static final Item frogBannerPattern;
	public static final Item ancientMoobloomSpawnEgg;
	public static final Item moobloomSpawnEgg;
	public static final Item flowerArmorTrimSmithingTemplate;
	public static final Item ancientPortalFrame;
	public static void init() {
		ItemGroupRegistry.init();
	}
	static {
		mysteriousOre = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "mysterious_ore")), new BlockItem(BlockRegistry.mysteriousOre, new Item.Settings()));
		deepslateMysteriousOre = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "deepslate_mysterious_ore")), new BlockItem(BlockRegistry.deepslateMysteriousOre, new Item.Settings()));
		mysteriousGem = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "mysterious_gem")), new Item(new Item.Settings()));
		mysteriousBlock = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "mysterious_block")), new BlockItem(BlockRegistry.mysteriousBlock, new Item.Settings()));
		mysteriousWheat = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "mysterious_wheat")), new Item(new Item.Settings()));
		eonizeEssence = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "eonize_essence")), new Item(new Item.Settings()));
		eonizedPearl = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "eonized_pearl")), new EonizedPearlItem(new Item.Settings()));
		eonizedDandelion = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "eonized_dandelion")), new BlockItem(BlockRegistry.eonizedDandelion, new Item.Settings()));
		eonizedPoppy = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "eonized_poppy")), new BlockItem(BlockRegistry.eonizedPoppy, new Item.Settings()));
		eonizedShortGrass = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "eonized_short_grass")), new BlockItem(BlockRegistry.eonizedShortGrass, new Item.Settings()));
		exchanger = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "exchanger")), new BlockItem(BlockRegistry.exchanger, new Item.Settings()));
		frogBannerPattern = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "frog_banner_pattern")), new BannerPatternItem(TagKeyRegistry.bannerPatternFrog, (new Item.Settings()).maxCount(1).rarity(Rarity.UNCOMMON)));
		ancientMoobloomSpawnEgg = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "ancient_moobloom_spawn_egg")), new SpawnEggItem(EntityRegistry.ancientMoobloom,	16635136, 2250561, new Item.Settings()));
		moobloomSpawnEgg = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "moobloom_spawn_egg")), new SpawnEggItem(EntityRegistry.moobloom,	16635136, 10592673, new Item.Settings()));
		flowerArmorTrimSmithingTemplate = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "flower_armor_trim_smithing_template")), SmithingTemplateItem.of(ArmorTrimPatternRegistry.flower));
		ancientPortalFrame = Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier("mclmaf2024", "ancient_portal_frame")), new BlockItem(BlockRegistry.ancientPortalFrame, new Item.Settings()));
	}
}
