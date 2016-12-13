package net.infernalrealms.gui;

import java.util.ArrayList;

import net.infernalrealms.player.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class StatsTabList {

	public static void sendTab(Player player) {
		ArrayList<String> lines = new ArrayList<>();
		String blankLine = "                                                              ";
		PlayerData playerData = PlayerData.getData(player);
		lines.add("");
		lines.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + ChatColor.UNDERLINE + player.getName());
		lines.add(ChatColor.DARK_PURPLE + "Level " + playerData.getLevel());

		lines.add("");

		String bar = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
		StringBuilder barBuilder = new StringBuilder(bar);
		StringBuilder line = new StringBuilder(blankLine);
		int index = (int) (60 * ((double) playerData.getExp() / (double) playerData.expToNextLevel()));
		if (index > barBuilder.length()) {
			index = barBuilder.length();
		}
		barBuilder.insert(index, ChatColor.GRAY);
		String expBar = ChatColor.DARK_PURPLE + barBuilder.toString(); // ChatColor.DARK_PURPLE + bar.substring(0, index) + ChatColor.GRAY + bar.substring(index, bar.length());
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "Stamina: " + ChatColor.RESET + ChatColor.GRAY + playerData.getStamina()
				+ ChatColor.RESET); // 60 long
		line.insert(33, expBar);
		lines.add(line.toString().substring(0, 100));
		line = new StringBuilder(blankLine);
		String expAmount = ChatColor.GRAY + "" + playerData.getExp() + "/" + playerData.expToNextLevel() + ChatColor.BOLD + " XP";
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "Agility: " + ChatColor.RESET + ChatColor.GRAY + playerData.getAgility()
				+ ChatColor.RESET);
		line.insert(39, expAmount);
		lines.add(line.toString().substring(0, 65));

		lines.add("");

		line = new StringBuilder(blankLine);
		barBuilder = new StringBuilder(bar);
		index = (int) (60 * (((Damageable) player).getHealth() / (double) playerData.getTotalHealth()));
		if (index > barBuilder.length()) {
			index = barBuilder.length();
		}
		barBuilder.insert(index, ChatColor.GRAY);
		String healthBar = ChatColor.RED + barBuilder.toString(); //ChatColor.GREEN + bar.substring(0, index) + ChatColor.GRAY + bar.substring(0, bar.length());
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "Strength: " + ChatColor.RESET + ChatColor.GRAY + playerData.getStrength()
				+ ChatColor.RESET);
		line.insert(32, healthBar);
		lines.add(line.toString().substring(0, 98));
		line = new StringBuilder(blankLine);
		String healthAmount = ChatColor.GRAY + "" + ((int) ((Damageable) player).getHealth()) + "/" + playerData.getTotalHealth()
				+ ChatColor.BOLD + " HP";
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "Spirit: " + ChatColor.RESET + ChatColor.GRAY + playerData.getSpirit()
				+ ChatColor.RESET);
		line.insert(39, healthAmount);
		lines.add(line.toString().substring(0, 64));

		lines.add("");

		line = new StringBuilder(blankLine);
		barBuilder = new StringBuilder(bar);
		index = (int) (60 * ((double) playerData.getMana() / (double) playerData.getTotalMaxMana()));
		if (index > barBuilder.length()) {
			index = barBuilder.length();
		}
		barBuilder.insert(index, ChatColor.GRAY);
		String manaBar = ChatColor.BLUE + barBuilder.toString(); // ChatColor.BLUE + bar.substring(0, index) + ChatColor.GRAY + bar.substring(0, bar.length());
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "Intelligence: " + ChatColor.RESET + ChatColor.GRAY
				+ playerData.getIntelligence() + ChatColor.RESET);
		line.insert(32, manaBar);
		lines.add(line.toString().substring(0, 96));
		line = new StringBuilder(blankLine);
		String manaAmount = ChatColor.GRAY + "" + playerData.getMana() + "/" + playerData.getTotalMaxMana() + ChatColor.BOLD + " MP";
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "AP: " + ChatColor.RESET + ChatColor.GRAY + playerData.getAP()
				+ ChatColor.BOLD + " SP: " + ChatColor.RESET + ChatColor.GRAY + playerData.getSP() + ChatColor.RESET);
		line.insert(43, manaAmount);
		lines.add(line.toString().substring(0, 68));

		lines.add("");

		line = new StringBuilder(blankLine);
		line.insert(0, ChatColor.GRAY + "" + ChatColor.BOLD + "Horse LVL: " + ChatColor.RESET + ChatColor.GRAY + playerData.getMountLevel()
				+ ChatColor.RESET);
		line.insert(35, playerData.getMoneyAsString());
		lines.add(line.toString().substring(0, 76));

		TabList.sendTab(player, lines);
	}
}
