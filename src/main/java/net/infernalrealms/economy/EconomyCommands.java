package net.infernalrealms.economy;

import net.infernalrealms.player.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!sender.isOp()) {
			return false;
		}
		if (commandLabel.equalsIgnoreCase("ie")) {
			if (args[0].equalsIgnoreCase("balance")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found.");
					return false;
				}
				PlayerData targetData = PlayerData.getData(target);
				sender.sendMessage(ChatColor.GREEN + target.getName() + "'s Balance: " + targetData.getMoneyAsString());
			} else if (args[0].equalsIgnoreCase("setbalance")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found.");
					return false;
				}
				long money = 0;
				try {
					money = Long.parseLong(args[2]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "An error occurred while executing this command.");
					return false;
				}
				PlayerData targetData = PlayerData.getData(target);
				targetData.setMoney(money);
				sender.sendMessage(ChatColor.GREEN + target.getName() + "'s balance set to " + money);
			}
		} else if (commandLabel.equalsIgnoreCase("ip")) {
			if (args[0].equalsIgnoreCase("balance")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found.");
					return false;
				}
				PlayerData targetData = PlayerData.getData(target);
				sender.sendMessage(ChatColor.GREEN + target.getName() + "'s IP Balance: " + targetData.getPremiumMoney());
			} else if (args[0].equalsIgnoreCase("setbalance")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found.");
					return false;
				}
				long money = 0;
				try {
					money = Long.parseLong(args[2]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "An error occurred while executing this command.");
					return false;
				}
				PlayerData targetData = PlayerData.getData(target);
				targetData.setPremiumMoney(money);
				sender.sendMessage(ChatColor.GREEN + target.getName() + "'s IP balance set to " + money);
			} else if (args[0].equalsIgnoreCase("add")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found.");
					return false;
				}
				long money = 0;
				try {
					money = Long.parseLong(args[2]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "An error occurred while executing this command.");
					return false;
				}
				PlayerData targetData = PlayerData.getData(target);
				targetData.modifyPremiumMoney(money);
				sender.sendMessage(ChatColor.GREEN + target.getName() + "'s balance set to " + targetData.getPremiumMoney());
			}
		}
		return false;
	}
}
