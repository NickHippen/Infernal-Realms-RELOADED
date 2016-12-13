package net.infernalrealms.skills.general;

import net.infernalrealms.gui.SkillsGUI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkillsCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("stats")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				SkillsGUI.open(player);
			} else {
				sender.sendMessage(ChatColor.RED + "You cannot use this command!");
			}
		}
		return false;
	}

}
