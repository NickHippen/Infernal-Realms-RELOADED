package net.infernalrealms.general;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.infernalrealms.player.PlayerData;

public class MiscCommands implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("leavedungeon")) {
				PlayerData playerData = PlayerData.getData(player);
				if (!playerData.hasDungeon()) {
					player.sendMessage(ChatColor.RED + "You are not currently in a dungeon.");
					return false;
				}
				playerData.getDungeon().exit(player);
			}
		}
		return false;
	}

	// Override commands
	@EventHandler
	public void onCommandPreprocess(AsyncPlayerChatEvent event) {
		if (event.getMessage().toLowerCase().startsWith("/help")) {
			// Override to new help message
			sendHelpMessage(event.getPlayer());
		}
	}

	private static void sendHelpMessage(CommandSender sender) {
		// @formatter:off
		sender.sendMessage(new String[] { 
				ChatColor.AQUA + "" + ChatColor.BOLD + "General Help",
				ChatColor.AQUA + "/stats - " + ChatColor.GRAY + "Opens the stats menu.",
				ChatColor.AQUA + "/skills - " + ChatColor.GRAY + "Opens the skills menu.",
				ChatColor.AQUA + "/quests - " + ChatColor.GRAY + "Opens the quests menu.",
				ChatColor.AQUA + "/particles - " + ChatColor.GRAY + "Opens the particles menu.",
				ChatColor.AQUA + "/trade [Player] - " + ChatColor.GRAY + "Requests a trade with the player.",
				ChatColor.AQUA + "/logout - " + ChatColor.GRAY + "Returns you to the character selection lobby.",
				ChatColor.AQUA + "/pickaxe help - " + ChatColor.GRAY + "View commands related to the mining profession.",
				ChatColor.AQUA + "/mount help - " + ChatColor.GRAY + "View commands related to the mount profession.",
				ChatColor.AQUA + "/party help - " + ChatColor.GRAY + "View commands related to parties.",
				});
		// @formatter:on
	}

}
