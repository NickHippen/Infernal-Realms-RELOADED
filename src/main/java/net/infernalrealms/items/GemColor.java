package net.infernalrealms.items;

import static net.infernalrealms.general.InfernalRealms.RANDOM;

import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public enum GemColor {

	//@formatter:off
	RED(ChatColor.DARK_RED, (short) 1, "Strength", "Dexterity", "Intelligence"),
	YELLOW(ChatColor.YELLOW, (short) 11, "Stamina", "Agility", "Health"),
	BLUE(ChatColor.BLUE, (short) 6,"Spirit", "Mana"),
	;
	//@formatter:on

	private ChatColor chatColor;
	private short damage;
	private String[] stats;

	private GemColor(ChatColor chatColor, short damage, String... stats) {
		this.chatColor = chatColor;
		this.damage = damage;
		this.stats = stats;
	}

	public ChatColor getChatColor() {
		return this.chatColor;
	}

	public short getDamage() {
		return this.damage;
	}

	public String[] getStats() {
		return this.stats;
	}

	public String getRandomStat() {
		return getStats()[RANDOM.nextInt(getStats().length)];
	}

	public String getDisplayName() {
		return toString().substring(0, 1) + toString().substring(1, toString().length()).toLowerCase();
	}

	public String getSocketLoreLine() {
		return ChatColor.RESET + "" + getChatColor() + " ▣ " + ChatColor.WHITE + getDisplayName() + " Socket";
	}

	public static GemColor getRandomGemColor() {
		return values()[RANDOM.nextInt(values().length)];
	}

	public static GemColor fromString(String color) {
		for (GemColor gc : values()) {
			if (gc.toString().equalsIgnoreCase(color)) {
				return gc;
			}
		}
		return null;
	}

	public static Pattern getGemColorPattern() {
		return Pattern.compile((new StringBuilder()).append("(?i)").append(String.valueOf('\247')).append("[49E]").toString());
	}

	public static GemColor fromChatColor(ChatColor color) {
		for (GemColor gc : values()) {
			if (gc.getChatColor() == color) {
				return gc;
			}
		}
		return null;
	}

}
