package net.infernalrealms.mount;

import net.infernalrealms.gui.MountGUI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MountCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("mount")) {
				if (args.length == 0) {
					MountManager.beginSummoning(player);
				} else {
					if (args[0].equalsIgnoreCase("manage") && args.length == 1) {
						MountGUI.open(player);
					} else {
						sendHelpMessage(sender);
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You can not use this command!");
		}
		return false;
	}

	private static void sendHelpMessage(CommandSender sender) {
		// @formatter:off
		sender.sendMessage(new String[] { 
				ChatColor.AQUA + "" + ChatColor.BOLD + "Mount Commands",
				ChatColor.AQUA + "/mount - " + ChatColor.GRAY + "Summons your mount.",
				ChatColor.AQUA + "/mount manage - " + ChatColor.GRAY + "Opens the mount management menu.",
				});
		// @formatter:on
	}

}
