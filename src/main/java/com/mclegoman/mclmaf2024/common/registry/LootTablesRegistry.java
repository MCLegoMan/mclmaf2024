package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class LootTablesRegistry {
	public static final RegistryKey<LootTable> exchangerCommon;
	public static final RegistryKey<LootTable> exchangerUncommon;
	public static final RegistryKey<LootTable> exchangerRare;
	public static final RegistryKey<LootTable> exchangerEpic;
	public static final RegistryKey<LootTable> exchangerLegendary;
	public static final RegistryKey<LootTable> exchangerPotato;
	public static final RegistryKey<LootTable> eonizeMoobloom;
	public static void init() {
	}
	static {
		exchangerCommon = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "exchanger/common")));
		exchangerUncommon = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "exchanger/uncommon")));
		exchangerRare = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "exchanger/rare")));
		exchangerEpic = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "exchanger/epic")));
		exchangerLegendary = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "exchanger/legendary")));
		exchangerPotato = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "exchanger/potato")));
		eonizeMoobloom = LootTables.registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier("mclmaf2024", "entities/eonize_moobloom")));
	}
}
