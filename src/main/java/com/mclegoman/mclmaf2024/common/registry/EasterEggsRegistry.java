package com.mclegoman.mclmaf2024.common.registry;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EasterEggsRegistry {
	private final static List<String> flippedEasterEggNames = new ArrayList<>();
	private final static List<String> kappaEasterEggNames = new ArrayList<>();
	private final static List<String> loveAndHugsEasterEggNames = new ArrayList<>();
	private final static List<Long> sharewareEasterEggSeeds = new ArrayList<>();
	public static void addFlippedName(String name) {
		if (!flippedEasterEggNames.contains(name)) flippedEasterEggNames.add(name);
	}
	public static void addKappaName(String name) {
		if (!kappaEasterEggNames.contains(name)) kappaEasterEggNames.add(name);
	}
	public static void addLoveAndHugsName(String name) {
		if (!loveAndHugsEasterEggNames.contains(name)) loveAndHugsEasterEggNames.add(name);
	}
	public static void addSharewareSeed(long seed) {
		if (!sharewareEasterEggSeeds.contains(seed)) sharewareEasterEggSeeds.add(seed);
	}
	public static void init() {
		addFlippedName("MCLegoMan");
		addFlippedName("Dinnerbone");
		addFlippedName("Grumm");
		addKappaName("JudgeAlexander");
		addLoveAndHugsName("Pink Wither");
		addSharewareSeed(1649);
		addSharewareSeed(1681);
	}
	// When an entity is wearing an item in their head slot with a custom name that is in the flippedEasterEggNames List - or has the mclmaf2024:flipped component -, they will be flipped upside-down.
	public static boolean isFlippedEasterEgg(ItemStack itemStack) {
		Text customName = itemStack.getComponents().get(DataComponentTypes.CUSTOM_NAME);
		return (customName != null && flippedEasterEggNames.contains(customName.getString())) || Boolean.TRUE.equals(itemStack.getComponents().get(ComponentTypeRegistry.flippedEasterEgg));
	}
	// When an entity is wearing a Turtle Helmet with a custom name that is in the kappaEasterEggNames List - or has the mclmaf2024:kappa component -, they will shrink and be able to breathe in water.
	public static boolean isKappaEasterEgg(ItemStack itemStack) {
		Text customName = itemStack.getComponents().get(DataComponentTypes.CUSTOM_NAME);
		boolean isJudge = customName != null && kappaEasterEggNames.contains(customName.getString());
		return (itemStack.isOf(Items.TURTLE_HELMET) && isJudge) || Boolean.TRUE.equals(itemStack.getComponents().get(ComponentTypeRegistry.kappaEasterEgg));
	}
	// When an entity is wearing an item in their head slot with a custom name that is in the flippedEasterEggNames List - or has the mclmaf2024:flipped component -, they will be flipped upside-down.
	public static boolean isLoveAndHugsEasterEgg(ItemStack itemStack) {
		Text customName = itemStack.getComponents().get(DataComponentTypes.CUSTOM_NAME);
		boolean isLoveAndHugs = customName != null && loveAndHugsEasterEggNames.contains(customName.getString());
		return (itemStack.isOf(Items.WITHER_SKELETON_SKULL) && isLoveAndHugs) || Boolean.TRUE.equals(itemStack.getComponents().get(ComponentTypeRegistry.loveAndHugsEasterEgg));
	}
	// When the world seed is "1649" or "1681" which the player can also set by inputting "3D" or "3d".
	public static boolean isSharewareEasterEgg(long seed) {
		return sharewareEasterEggSeeds.contains(seed);
	}
}
