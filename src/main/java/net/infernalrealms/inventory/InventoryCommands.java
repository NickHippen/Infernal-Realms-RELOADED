package net.infernalrealms.inventory;

import net.infernalrealms.player.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("trade")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (PlayerData.getData(player).getCurrentCharacterSlot() != -1) {
					if (args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							if (!target.equals(player)) {
								Trade trade = new Trade(player, target);
								trade.sendInvite();
							} else {
								player.sendMessage(ChatColor.RED + "You cannot invite yourself!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "Player not found!");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Invalid usage! Try /trade [Player].");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are currently unable to perform this action!");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "This command is only usable by players.");
			}
		} else if (commandLabel.equalsIgnoreCase("accepttrade")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					// Accept Trade
					Trade trade = Trade.getTradeFromInvite(player);
					if (trade != null)
						trade.acceptTrade();
					else
						player.sendMessage(ChatColor.RED + "You have no trades to accept.");
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage! Try /accepttrade.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "This command is only usable by players.");
			}
		} else if (commandLabel.equalsIgnoreCase("wipeinv")) {
			Player player = (Player) sender;
			if (player.isOp()) {
				player.sendMessage(ChatColor.RED + "Inventory wiped.");
				InventoryManager.wipeInventory(player);
			}
		}
		return false;
	}
}
