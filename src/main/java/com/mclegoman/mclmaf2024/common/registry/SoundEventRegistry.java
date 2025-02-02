package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundEventRegistry {
	public static SoundEvent ancientMoobloomDeeonize;
	public static SoundEvent ancientMoobloomShear;
	public static SoundEvent moobloomShear;
	public static SoundEvent moobloomEonize;
	public static SoundEvent ancientPortalAmbience;
	public static SoundEvent ancientPortalOpen;
	public static SoundEvent ancientPortalFrameEonized;
	public static RegistryEntry.Reference<SoundEvent> musicMysterious;
	public static MusicSound musicMenu;
	public static void init() {
	}
	private static SoundEvent registerSoundEvent(Identifier identifier) {
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}
	private static RegistryEntry.Reference<SoundEvent> registerSoundEventReference(Identifier identifier) {
		return Registry.registerReference(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}
	static {
		ancientMoobloomShear = registerSoundEvent(new Identifier("mclmaf2024", "entity.ancient_moobloom.shear"));
		ancientMoobloomDeeonize = registerSoundEvent(new Identifier("mclmaf2024", "entity.ancient_moobloom.deeonize"));
		moobloomShear = registerSoundEvent(new Identifier("mclmaf2024", "entity.moobloom.shear"));
		moobloomEonize = registerSoundEvent(new Identifier("mclmaf2024", "entity.moobloom.eonize"));
		ancientPortalAmbience = registerSoundEvent(new Identifier("mclmaf2024", "block.ancient_portal.ambience"));
		ancientPortalOpen = registerSoundEvent(new Identifier("mclmaf2024", "block.ancient_portal.open"));
		ancientPortalFrameEonized = registerSoundEvent(new Identifier("mclmaf2024", "block.ancient_portal_frame.eonize"));
		musicMysterious = registerSoundEventReference(new Identifier("mclmaf2024", "music.mysterious"));
		musicMenu = new MusicSound(musicMysterious, 20, 600, true);
	}
}
