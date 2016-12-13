package net.infernalrealms.player;

import net.infernalrealms.gui.ChooseClassGUI;
import net.infernalrealms.gui.ParticleManagerGUI;
import net.infernalrealms.gui.QuestLogGUI;
import net.infernalrealms.gui.SkillsGUI;
import net.infernalrealms.gui.StatGUI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("stats")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				StatGUI.open(player);
			} else {
				sender.sendMessage(ChatColor.RED + "You cannot use this command!");
			}
		} else if (commandLabel.equalsIgnoreCase("choose")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				PlayerData playerData = PlayerData.getData(player);
				if (playerData.getPlayerClass().equals("Beginner")) {
					ChooseClassGUI.open(player);
				} else {
					player.sendMessage(ChatColor.RED + "You have already selected a class.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You cannot use this command!");
			}
		} else if (commandLabel.equalsIgnoreCase("skills")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				PlayerData playerData = PlayerData.getData(player);
				if (playerData.getPlayerClass().equals(PlayerClass.BEGINNER.getName())) {
					player.sendMessage("You have no skills.");
				} else {
					SkillsGUI.open(player);
				}
			}
		} else if (commandLabel.equalsIgnoreCase("logout")) {
			if (sender instanceof Player) {
				PlayerData.getData((Player) sender).logout();
			}
		} else if (commandLabel.equalsIgnoreCase("quests")) {
			if (sender instanceof Player) {
				QuestLogGUI.openMenu((Player) sender, 1);
			}
		} else if (commandLabel.equalsIgnoreCase("particles")) {
			if (sender instanceof Player) {
				ParticleManagerGUI.open((Player) sender);
			}
		}
		return false;
	}
}