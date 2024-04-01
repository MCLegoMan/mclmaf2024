package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public class TagKeyRegistry {
	public static final TagKey<Item> exchangerCommon;
	public static final TagKey<Item> exchangerUncommon;
	public static final TagKey<Item> exchangerRare;
	public static final TagKey<Item> exchangerEpic;
	public static final TagKey<Item> exchangerPotato;
	public static final TagKey<Item> technobladeCrown;
	public static final TagKey<Item> ancientMoobloomFood;
	public static final TagKey<Item> moobloomFood;
	public static final TagKey<Item> moobloomEonizeFood;
	public static final TagKey<Item> eonizedFlowersItem;
	public static final TagKey<Item> kappaIronAllergy;
	public static final TagKey<Item> kappaDiet;
	public static final TagKey<Block> eonizedFlowersBlock;
	public static final TagKey<Block> moobloomsSpawnableOn;
	public static final TagKey<Block> moobloomPathfindingFavor;
	public static final TagKey<BannerPattern> bannerPatternFrog;
	public static final TagKey<Structure> ancientHouse;
	public static final TagKey<Structure> ancientPortal;
	static {
		exchangerCommon = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "exchanger/common_override"));
		exchangerUncommon = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "exchanger/uncommon_override"));
		exchangerRare = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "exchanger/rare_override"));
		exchangerEpic = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "exchanger/epic_override"));
		exchangerPotato = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "exchanger/potato_override"));
		technobladeCrown = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "technoblade_crown"));
		ancientMoobloomFood = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "ancient_moobloom_food"));
		moobloomFood = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "moobloom_food"));
		moobloomEonizeFood = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "moobloom_eonize_food"));
		eonizedFlowersItem = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "eonized_flowers"));
		kappaIronAllergy = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "kappa_iron_allergy"));
		kappaDiet = TagKey.of(RegistryKeys.ITEM, new Identifier("mclmaf2024", "kappa_diet"));
		eonizedFlowersBlock = TagKey.of(RegistryKeys.BLOCK, new Identifier("mclmaf2024", "eonized_flowers"));
		moobloomsSpawnableOn = TagKey.of(RegistryKeys.BLOCK, new Identifier("mclmaf2024", "moobloom_spawnable_on"));
		moobloomPathfindingFavor = TagKey.of(RegistryKeys.BLOCK, new Identifier("mclmaf2024", "moobloom_pathfinding_favor"));
		bannerPatternFrog = TagKey.of(RegistryKeys.BANNER_PATTERN, new Identifier("mclmaf2024", "pattern_item/frog"));
		ancientHouse = TagKey.of(RegistryKeys.STRUCTURE, new Identifier("mclmaf2024", "ancient_house"));
		ancientPortal = TagKey.of(RegistryKeys.STRUCTURE, new Identifier("mclmaf2024", "ancient_portal"));
	}
}
