package net.infernalrealms.items;

import java.util.Random;

import org.bukkit.ChatColor;

public enum Rarity {

	// @formatter:off
	TRASH(ChatColor.GRAY, new String[] { 
		"Trash",
		"Garbage",
		"Bad",
		"Worn",
		"Rubbish",
		"Scrap",
		"Dirty",
		"Junk",
	}, 0.85, 0.9, 0, 0),
	COMMON(ChatColor.WHITE, new String[] { 
		"Common",
		"Decent",
		"Used",
		"Simple",
		"Ordinary",
		"Regular",
		"Mediocre",
		"Trivial",
	}, 1, 1.05, 0, 1),
	UNCOMMON(ChatColor.GREEN, new String[] {
		"Uncommon",
		"Better than decent",
		"New",
		"Odd",
		"Abnormal",
		"Noteworthy",
	}, 1.1, 1.15, 1, 2),
	RARE(ChatColor.BLUE, new String[] { 
		"Rare",
		"Amazing",
		"Pristine",
		"Scarce",
		"Strange",
		"Extraordinary",
		"Unusual",
		"Seldom",
	}, 1.2, 1.25, 1, 3),
	EPIC(ChatColor.DARK_PURPLE, new String[] {
		"Epic",
		"Incredible",
		"Perfect",
		"Heroic",
		"Ancient",
		"Masterwork",
		"Antique",
	}, 1.3, 1.4, 2, 4),
	;
	// @formatter:on

	private static final Random random = new Random();

	private ChatColor chatColor;
	private String[] displayNames;
	private double minMultiplier;
	private double maxMultiplier;
	private int minSockets;
	private int maxSockets;

	private Rarity(ChatColor chatColor, String[] displayNames, double minMultiplier, double maxMultiplier, int minSockets, int maxSockets) {
		this.chatColor = chatColor;
		this.displayNames = displayNames;
		this.minMultiplier = minMultiplier;
		this.maxMultiplier = maxMultiplier;
		this.minSockets = minSockets;
		this.maxSockets = maxSockets;
	}

	public double getMinMultiplier() {
		return minMultiplier;
	}

	public double getMaxMultiplier() {
		return maxMultiplier;
	}

	public double getRandomMultiplier() {
		return getMinMultiplier() + (getMaxMultiplier() - getMinMultiplier()) * random.nextDouble();
	}

	public int getMinSockets() {
		return minSockets;
	}

	public int getMaxSockets() {
		return maxSockets;
	}

	public int getRandomSockets() {
		return getMinSockets() + random.nextInt((getMaxSockets() - getMinSockets()) + 1);
	}

	public ChatColor getColor() {
		return chatColor;
	}

	public String getRandomDisplayName() {
		return displayNames[random.nextInt(displayNames.length)];
	}

	public String getBaseDisplayName() {
		return displayNames[0];
	}

	public static Rarity fromString(String name) {
		for (Rarity rarity : values()) {
			if (rarity.toString().equalsIgnoreCase(name)) {
				return rarity;
			}
		}
		return null;
	}

	public static Rarity fromChatColor(ChatColor color) {
		for (Rarity rarity : values()) {
			if (rarity.getColor() == color) {
				return rarity;
			}
		}
		return null;
	}

	public int getRarityIndex() {
		switch (this) {
		case TRASH:
			return 0;
		case COMMON:
			return 1;
		case UNCOMMON:
			return 2;
		case RARE:
			return 3;
		case EPIC:
			return 4;
		}
		return 0;
	}

	public static Rarity fromRarityIndex(int index) {
		switch (index) {
		case 0:
			return TRASH;
		case 1:
			return COMMON;
		case 2:
			return UNCOMMON;
		case 3:
			return RARE;
		case 4:
			return EPIC;
		}
		return null;
	}

}
