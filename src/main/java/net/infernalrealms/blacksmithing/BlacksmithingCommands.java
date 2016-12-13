package net.infernalrealms.blacksmithing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.infernalrealms.general.YAMLFile;

public class BlacksmithingCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("ib")) {
			if (!sender.isOp()) {
				return false;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players can use this command.");
				return false;
			}
			Player player = (Player) sender;
			if (args.length == 0) {
				sendHelpMessage(player);
				return false;
			}
			if (args[0].equalsIgnoreCase("setrefinery")) {
				if (args.length != 4) {
					sendHelpMessage(player);
					return false;
				}
				Location loc = new Location(player.getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						Integer.parseInt(args[3]));
				if (loc.getWorld().getBlockAt(loc).getType() != Material.ANVIL) {
					player.sendMessage(ChatColor.RED + "Error: Furnace not detected at this location. Not set.");
					return false;
				}
				List<Location> refineryLocations = RefineryForgeListener.getRefineryLocations();
				refineryLocations.add(loc);
				YAMLFile.BLACKSMTIHING_LOCATIONS.getConfig().set("Refinery", refineryLocations);
				YAMLFile.BLACKSMTIHING_LOCATIONS.save();
				player.sendMessage(ChatColor.GREEN + "Location set as refinery.");
			} else if (args[0].equalsIgnoreCase("setforge")) {
				if (args.length != 4) {
					sendHelpMessage(player);
					return false;
				}
				Location loc = new Location(player.getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						Integer.parseInt(args[3]));
				if (loc.getWorld().getBlockAt(loc).getType() != Material.FURNACE) {
					player.sendMessage(ChatColor.RED + "Error: Furnace not detected at this location. Not set.");
					return false;
				}
				List<Location> forgeLocations = RefineryForgeListener.getForgeLocations();
				forgeLocations.add(loc);
				YAMLFile.BLACKSMTIHING_LOCATIONS.getConfig().set("Forge", forgeLocations);
				YAMLFile.BLACKSMTIHING_LOCATIONS.save();
				player.sendMessage(ChatColor.GREEN + "Location set as forge.");
			} else {
				sendHelpMessage(sender);
			}

		}
		return false;
	}

	private static void sendHelpMessage(CommandSender sender) {
		sender.sendMessage("/ib setrefinery [x] [y] [z]");
		sender.sendMessage("/ib setforge [x] [y] [z]");
	}
}
