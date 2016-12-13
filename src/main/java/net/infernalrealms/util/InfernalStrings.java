package net.infernalrealms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.player.PlayerData;

public class InfernalStrings {

	public static final String UNTRADEABLE = ChatColor.RESET + "" + ChatColor.WHITE + "Untradeable";
	public static final String UNREMOVEABLE = ChatColor.RESET + "" + ChatColor.RESET + UNTRADEABLE;
	public static final String HOLDABLE = ChatColor.RESET + "" + ChatColor.DARK_AQUA + ChatColor.RESET;
	public static final String UNEQUIPABLE_MESSAGE = ChatColor.RESET + "" + ChatColor.RED + "You are not able to use this item properly.";
	public static final String QUEST_MARKER = ChatColor.RESET + "" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD + "!";
	public static final String QUEST_DELIVER_MARKER = ChatColor.RESET + "" + ChatColor.RESET + ChatColor.DARK_PURPLE + ChatColor.BOLD + "!";
	public static final String UNLOCKED_LINE = "§a§l[ §r§aUNLOCKED §l]";
	public static final String LOCKED_LINE = "§c§l[ §r§cLOCKED §l]";
	public static final String NO_INTERACT_END = ChatColor.RESET + "" + ChatColor.RESET; // End inv. title for no-interact

	private InfernalStrings() {}

	public static void displayPlayerRequiredMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
	}

	public static void displayLoginMessage(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		for (int i = 0; i < 10; i++) {
			player.sendMessage(" ");
		}
		player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Infernal Realms v"
				+ InfernalRealms.getPlugin().getDescription().getVersion());
		player.sendMessage(ChatColor.GRAY + "Welcome, " + ChatColor.ITALIC + player.getName() + ".");
		player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "http://infernalrealms.net");
		long doubleDrop = playerData.getDoubleDropDuration();
		long doubleExp = playerData.getDoubleExpDuration();
		long doubleProfession = playerData.getDoubleProfessionExpDuration();
		if (doubleDrop > 0) {
			player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "You currently have Double Drop.");
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Time Remaining: " + formatSeconds(doubleDrop));
		}
		if (doubleExp > 0) {
			player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "You currently have Double EXP.");
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Time Remaining: " + formatSeconds(doubleExp));
		}
		if (doubleProfession > 0) {
			player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "You currently have Double Profession EXP.");
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Time Remaining: " + formatSeconds(doubleProfession));
		}
		for (int i = 0; i < 3; i++) {
			player.sendMessage(" ");
		}
	}

	public static String formatSeconds(long seconds) {
		long hours = seconds / 3600;
		long t = seconds - (hours * 3600);
		long minutes = t / 60;
		long newSeconds = t - (minutes * 60);
		String format = "";
		if (hours > 0) {
			format += hours + " hour" + (hours != 1 ? "s" : "") + " ";
		}
		if (minutes > 0) {
			format += minutes + " minute" + (minutes != 1 ? "s" : "") + " ";
		}
		if (newSeconds > 0) {
			format += newSeconds + " second" + (newSeconds != 1 ? "s" : "");
		}
		return format;
	}

	/**
	 * Formats the bar to the given specifications
	 * @param cleanBar The bar to be formatted e.g. "|||||||||||||||||||||||||||||||"
	 * @param filledFormat the format for the filled section
	 * @param unfilledFormat the format for the unfilled section
	 * @param percentage the percentage to be filled
	 * @return the formatted bar: <span style="color:#9A41A0">||||||||||||</span><span style="color:#000000">|||||||||||||||||||</span>
	 */
	public static String formatPercentageBar(String cleanBar, String filledFormat, String unfilledFormat, double percentage) {
		StringBuilder barBuilder = new StringBuilder(cleanBar);
		int index = (int) (cleanBar.length() * percentage);
		if (index > barBuilder.length()) {
			index = barBuilder.length();
		}
		barBuilder.insert(index, unfilledFormat);
		return filledFormat + barBuilder.toString();
	}

	/**
	 * Formats the bar to the given specifications
	 * @param cleanBar The bar to be formatted e.g. "|||||||||||||||||||||||||||||||"
	 * @param filledFormat the format for the filled section
	 * @param unfilledFormat the format for the unfilled section
	 * @param percentage the percentage to be filled
	 * @return the formatted bar: <span style="color:#9A41A0">||||||||||||</span><span style="color:#000000">|||||||||||||||||||</span>
	 */
	public static String formatPercentageBar(String cleanBar, ChatColor filledFormat, ChatColor unfilledFormat, double percentage) {
		return formatPercentageBar(cleanBar, filledFormat.toString(), unfilledFormat.toString(), percentage);
	}

	/**
	 * Gets the flags of the given command
	 * @param command the command to check for flags
	 * @return the list containing all of the flags
	 */
	public static List<String> getFlags(String command) {
		Objects.requireNonNull(command);

		List<String> flags = new ArrayList<>();
		int index;
		while ((index = command.indexOf(" -")) != -1) {
			if (index + 1 == command.length()) {
				// Dash at the end, don't count it
				break;
			}
			command = command.substring(index + 2);
			int endIndex = command.indexOf(" -");
			if (endIndex == -1) {
				endIndex = command.length();
			}
			flags.add(command.substring(0, endIndex));
		}
		return flags;
	}

	/**
	 * Gets the valued flags with corresponding values of the given command
	 * @param command the command to check for flags (Example command: /test -targeted=true -distance=5)
	 * @return the map containing all of the flags with their corresponding value if no value is given the value will be null
	 */
	public static Map<String, String> getValuedFlags(String command) {
		List<String> flags = getFlags(command);
		Map<String, String> valuedFlags = new HashMap<>(flags.size());
		for (String baseFlag : flags) {
			String[] flagSplit = baseFlag.split("=");
			String flag = flagSplit[0];
			String value;
			if (flagSplit.length <= 1) {
				value = null;
			} else {
				value = flagSplit[1];
			}
			valuedFlags.put(flag, value);
		}
		return valuedFlags;
	}

	/**
	 * Strips the flags off of the command
	 * @param command the command to be stripped
	 * @return the stripped command
	 */
	public static String stripFlags(String command) {
		int endIndex = command.indexOf(" -");
		if (endIndex == -1) {
			// No flags
			return command;
		}
		return command.substring(0, endIndex);
	}

}
