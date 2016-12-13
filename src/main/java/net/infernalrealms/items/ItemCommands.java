package net.infernalrealms.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.infernalrealms.blacksmithing.BlacksmithingRecipeFactory;
import net.infernalrealms.general.YAMLFile;

public class ItemCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("ii")) {
			if (sender.isOp()) {
				if (args.length == 1) {
					displayHelp(sender);
				}
				if (args[0].equalsIgnoreCase("tier")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
						try {
							targetPlayer.getInventory().addItem(InfernalItemsRELOADED.generateTierItem(args[2].split("#")));
						} catch (InvalidTraitException e) {
							targetPlayer.sendMessage("Invalid traits: " + e.getMessage());
						}
					} else {
						sender.sendMessage("Player not found!");
					}
				} else if (args[0].equalsIgnoreCase("item")) {
					if (args.length == 3) {
						if (Bukkit.getPlayer(args[1]) != null) {
							Player targetPlayer = Bukkit.getPlayer(args[1]);
							String translatedArgs2 = args[2].replace("_", " ");
							if (YAMLFile.ITEMS.getConfig().contains(translatedArgs2)) {
								sender.sendMessage("Item in file!");
								targetPlayer.getInventory().addItem(InfernalItems.generateCustomItem(translatedArgs2));
							} else {
								sender.sendMessage("Item not found!");
							}
						} else {
							sender.sendMessage("Player not found!");
						}
					} else {
						sender.sendMessage("Invalid usage");
					}
				} else if (args[0].equalsIgnoreCase("gem")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						try {
							targetPlayer.getInventory().addItem(InfernalItemsRELOADED.generateGem(args[2].split("#")));
						} catch (InvalidTraitException e) {
							targetPlayer.sendMessage("Invalid traits: " + e.getMessage());
						}
					}
				} else if (args[0].equalsIgnoreCase("pouch")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						targetPlayer.getInventory().addItem(Pouch.KASHMIR_SUPPLY_BAG.generateItemStack());
					}
				} else if (args[0].equalsIgnoreCase("potion")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						String translatedArgs2 = args[2].replace("_", " ");
						if (YAMLFile.POTIONS.getConfig().contains(translatedArgs2)) {
							sender.sendMessage("Potion exists!");
							targetPlayer.getInventory().addItem(InfernalItems.generatePotion(translatedArgs2));
						} else {
							sender.sendMessage("Potion not found.");
							sender.sendMessage(YAMLFile.POTIONS.getConfig().getKeys(true).toString());
							sender.sendMessage(translatedArgs2);
						}
					}
				} else if (args[0].equalsIgnoreCase("misc")) {
					if (args.length == 4) {
						Player targetPlayer = Bukkit.getPlayer(args[1]);
						if (targetPlayer != null) {
							if (YAMLFile.MISC_ITEMS.getConfig().contains(args[2])) {
								sender.sendMessage("Misc. item exists!");
								try {
									targetPlayer.getInventory().addItem(InfernalItems.generateMiscItem(args[2], Integer.parseInt(args[3])));
								} catch (Exception e) {
									e.printStackTrace();
									sender.sendMessage("Error giving item.");
								}
							} else {
								sender.sendMessage("Misc. Item not found.");
							}
						} else {
							sender.sendMessage("Invalid player");
						}
					} else {
						sender.sendMessage("Proper usage: /ii misc [player] [item name] [amount]");
					}
				} else if (args[0].equalsIgnoreCase("recipe")) { // /ii recipe [player] [Values]
					if (args.length != 3) {
						displayHelp(sender);
						return false;
					}
					Player targetPlayer = Bukkit.getPlayer(args[1]);
					if (targetPlayer == null) {
						sender.sendMessage(ChatColor.RED + "Player not found.");
						return false;
					}
					try {
						targetPlayer.getInventory().addItem(BlacksmithingRecipeFactory.generateRecipe(args[2].split("#")));
					} catch (InvalidTraitException e) {
						sender.sendMessage(e.getMessage());
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid Command.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You cannot use this command.");
			}
		}
		return false;
	}

	public static void displayHelp(CommandSender sender) {
		sender.sendMessage("/ii tier <Target Name> <ITEM GROUP#WEAPONTYPES#LEVEL-LEVEL#RARITY>");
		sender.sendMessage("/ii item <Target Name> <Item_Name>");
		sender.sendMessage("/ii potion <Target Name> <Item_Name>");
		sender.sendMessage("/ii misc <Target Name> <ItemName> <Amount>");
		sender.sendMessage("/ii recipe <Target Name> <TYPES#LEVEL-LEVEL#RARITY>");
	}

}
