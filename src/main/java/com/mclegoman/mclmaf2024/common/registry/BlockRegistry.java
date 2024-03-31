package com.mclegoman.mclmaf2024.common.registry;

import com.mclegoman.mclmaf2024.common.block.AncientPortalBlock;
import com.mclegoman.mclmaf2024.common.block.AncientPortalFrameBlock;
import com.mclegoman.mclmaf2024.common.block.EonizedShortGrass;
import com.mclegoman.mclmaf2024.common.block.ExchangerBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class BlockRegistry {
	public static final Block mysteriousOre;
	public static final Block deepslateMysteriousOre;
	public static final Block mysteriousBlock;
	public static final Block exchanger;
	public static final Block eonizedDandelion;
	public static final Block pottedEonizedDandelion;
	public static final Block eonizedPoppy;
	public static final Block pottedEonizedPoppy;
	public static final Block eonizedShortGrass;
	public static final Block ancientPortalFrame;
	public static final Block ancientPortal;
	public static void init() {
	}
	static {
		mysteriousOre = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "mysterious_ore")), new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F)));
		deepslateMysteriousOre = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "deepslate_mysterious_ore")), new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)));
		mysteriousBlock = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "mysterious_block")), new Block(AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).requiresTool().strength(3.0F, 3.0F)));
		exchanger = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "exchanger")), new ExchangerBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(Instrument.BASS).strength(2.5F).sounds(BlockSoundGroup.WOOD).burnable()));
		eonizedDandelion = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "eonized_dandelion")), new FlowerBlock(StatusEffects.SATURATION, 0.7F, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY)));
		pottedEonizedDandelion = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "potted_eonized_dandelion")), Blocks.createFlowerPotBlock(eonizedDandelion));
		eonizedPoppy = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "eonized_poppy")), new FlowerBlock(StatusEffects.NIGHT_VISION, 10.0F, AbstractBlock.Settings.create().mapColor(MapColor.RED).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY)));
		pottedEonizedPoppy = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "potted_eonized_poppy")), Blocks.createFlowerPotBlock(eonizedPoppy));
		eonizedShortGrass = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "eonized_short_grass")), new EonizedShortGrass(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).burnable().pistonBehavior(PistonBehavior.DESTROY)));
		ancientPortalFrame = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "ancient_portal_frame")), new AncientPortalFrameBlock((AbstractBlock.Settings.create().mapColor(MapColor.PINK).instrument(Instrument.BASEDRUM).sounds(BlockSoundGroup.GLASS).luminance((state) -> 1).strength(-1.0F, 3600000.0F).dropsNothing())));
		ancientPortal = Registry.register(Registries.BLOCK, RegistryKey.of(Registries.BLOCK.getKey(), new Identifier("mclmaf2024", "ancient_portal")), new AncientPortalBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance((state) -> 11).pistonBehavior(PistonBehavior.BLOCK)));
	}
}
