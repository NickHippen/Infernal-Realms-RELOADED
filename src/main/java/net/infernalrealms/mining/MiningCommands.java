package net.infernalrealms.mining;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MiningCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		switch (commandLabel.toLowerCase()) {
		case "pickaxe":
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					return false;
				}
				Player player = (Player) sender;
				if (PickaxeFactory.hasPickaxeInInventory(player)) {
					player.sendMessage(ChatColor.RED + "You already have your pickaxe!");
					return false;
				}
				// Spawn pickaxe
				if (PickaxeFactory.giveNewPickaxe(player)) {
					player.sendMessage(ChatColor.GREEN + "Spawned pickaxe.");
					return true;
				}
				player.sendMessage(ChatColor.RED + "Your inventory is full.");
				return false;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("manage")) {
					if (!(sender instanceof Player)) {
						return false;
					}
					Player player = (Player) sender;
					MiningManagementGUI.open(player);
					return true;
				} else {
					sendHelpMessage(sender);
				}
			} else {
				sendHelpMessage(sender);
				return false;
			}
			break;
		}
		return false;
	}

	private static void sendHelpMessage(CommandSender sender) {
		// @formatter:off
		sender.sendMessage(new String[] { 
				ChatColor.AQUA + "" + ChatColor.BOLD + "Pickaxe Commands",
				ChatColor.AQUA + "/pickaxe - " + ChatColor.GRAY + "Spawns your personal pickaxe.",
				ChatColor.AQUA + "/pickaxe manage - " + ChatColor.GRAY + "Opens the pickaxe management menu.",
				});
		// @formatter:on
	}

}
