package net.infernalrealms.party;

import static net.infernalrealms.general.InfernalRealms.RANDOM;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.infernalrealms.player.PlayerData;

public class PartyCommands implements CommandExecutor {

	/**
	 * <strong>Commands:</strong>
	 * <p>- /party invite [player]</p> <em>Invites the player to the party</em>
	 * <p>- /party leave</p> <em>Leaves your current party</em>
	 * <p>- /party kick</p> <em>Kicks a the player from the party</em>
	 * <p>- /party promote [player]</p> <em>Gives invite/kick powers to the player</em>
	 * <p>- /party info</p> <em>Displays the current party information</em>
	 * <p>- /party help</p> <em>Displays the party commands</em>
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("party")) {
				if (args.length == 0) {
					sendHelpMessage(player);
				} else if (args[0].equalsIgnoreCase("invite")) {
					if (args.length != 2) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party invite [player]");
					} else {
						Party party;
						if (!player.hasMetadata("Party")) {
							party = new Party(player);
						} else {
							party = Party.getParty(player);
							if (!party.canInviteKick(player)) {
								player.sendMessage(ChatColor.RED + "You do not have " + ChatColor.BOLD + "permission" + ChatColor.RESET
										+ ChatColor.RED + " to invite to " + ChatColor.ITALIC + party.getOwner() + "'s" + ChatColor.RESET
										+ ChatColor.RED + " party.");
								return false;
							}
						}
						Player target = Bukkit.getPlayer(args[1]);

						if (target == null) {
							player.sendMessage(ChatColor.RED + args[1] + " is " + ChatColor.BOLD + "offline.");
						} else {
							if (target.getName().equalsIgnoreCase(player.getName())) { // TODO Move this further up (the party still gets created)
								player.sendMessage(ChatColor.RED + "You cannot invite yourself.");
								return false;
							}
							player.sendMessage(ChatColor.GREEN + "" + "Invite sent to " + target.getName() + ".");
							party.invite(target, player);
						}
					}
				} else if (args[0].equalsIgnoreCase("leave")) {
					if (args.length != 1) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party leave");
					} else {
						if (!player.hasMetadata("Party")) {
							player.sendMessage(ChatColor.RED + "You are not currently in a " + ChatColor.BOLD + "party.");
						} else {
							Party party = Party.getParty(player);
							party.leave(player);
						}
					}
				} else if (args[0].equalsIgnoreCase("kick")) {
					if (args.length != 2) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party kick [player]");
					} else {
						if (!player.hasMetadata("Party")) {
							player.sendMessage(ChatColor.RED + "You are not currently in a " + ChatColor.BOLD + "party.");
						} else {
							Party party = Party.getParty(player);
							if (!party.canInviteKick(player)) {
								player.sendMessage(ChatColor.RED + "You do not have " + ChatColor.BOLD + "permission" + ChatColor.RESET
										+ ChatColor.RED + " to kick others from " + ChatColor.ITALIC + party.getOwner() + "'s"
										+ ChatColor.RESET + ChatColor.RED + " party.");
								return false;
							}
							Player target = Bukkit.getPlayer(args[1]);
							if (target == null) {
								player.sendMessage(ChatColor.RED + args[1] + " is " + ChatColor.BOLD + "offline.");
								return false;
							}
							if (target.equals(player)) {
								player.sendMessage(ChatColor.RED + "You cannot kick yourself.");
								return false;
							}
							if (!party.isMember(target)) {
								player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + target.getName() + ChatColor.RESET
										+ ChatColor.RED + " is not in your " + ChatColor.BOLD + "party.");
								return false;
							}
							if (target.getName().equals(party.getOwner())) {
								player.sendMessage(ChatColor.RED + "You cannot kick the party owner!");
								return false;
							}
							PlayerData targetData = PlayerData.getData(target);
							if (targetData.hasDungeon()) {
								player.sendMessage(ChatColor.RED + "You cannot kick someone while they are in the dungeon.");
								return false;
							}
							party.kick(target);
						}
					}
				} else if (args[0].equalsIgnoreCase("promote")) {
					if (args.length != 2) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party promote [player]");
					} else {
						if (!player.hasMetadata("Party")) {
							player.sendMessage(ChatColor.RED + "You are not currently in a " + ChatColor.BOLD + "party.");
						} else {
							Party party = Party.getParty(player);
							if (!party.getOwner().equals(player.getName())) {
								player.sendMessage(ChatColor.RED + "You do not have " + ChatColor.BOLD + "permission" + ChatColor.RESET
										+ ChatColor.RED + " to promote others in " + ChatColor.ITALIC + party.getOwner() + "'s"
										+ ChatColor.RESET + ChatColor.RED + " party.");
								return false;
							}
							Player target = Bukkit.getPlayer(args[1]);
							if (target == null) {
								sender.sendMessage(ChatColor.RED + args[1] + " is " + ChatColor.BOLD + "offline.");
								return false;
							}
							if (!party.isMember(target)) {
								sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + target.getName() + ChatColor.RESET
										+ ChatColor.RED + " is not in your " + ChatColor.BOLD + "party.");
								return false;
							}
							if (party.getPromoted().contains(target.getName()) || party.getOwner().equals(target.getName())) {
								sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + target.getName() + ChatColor.RESET
										+ ChatColor.RED + " is already " + ChatColor.BOLD + "promoted.");
							}
							party.promote(target);
							player.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + target.getName() + ChatColor.RESET
									+ ChatColor.GREEN + " has been " + ChatColor.BOLD + "promoted.");
						}
					}
				} else if (args[0].equalsIgnoreCase("info")) {
					if (args.length != 1) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party info");
					} else {
						if (!player.hasMetadata("Party")) {
							player.sendMessage(ChatColor.RED + "You are not currently in a " + ChatColor.BOLD + "party.");
						} else {
							Party party = Party.getParty(player);
							for (String line : party.getPartyInfoAsStringList()) {
								player.sendMessage(line);
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("accept")) {
					if (args.length != 1) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party accept");
					} else {
						Party party = Party.getPartyFromInvite(player);
						if (party != null)
							party.addPlayer(player);
						else
							player.sendMessage(ChatColor.RED + "You have no pending party invites.");
					}
				} else if (args[0].equalsIgnoreCase("roll")) {
					if (args.length != 1) {
						sendHelpMessage(player);
						return false;
					}
					Party party = Party.getParty(player);
					if (party == null) {
						player.sendMessage(ChatColor.RED + "You are not currently in a " + ChatColor.BOLD + "party.");
						return false;
					}
					party.broadcastToParty(ChatColor.GREEN + "" + ChatColor.UNDERLINE + player.getName() + ChatColor.GREEN + " has rolled "
							+ (RANDOM.nextInt(100) + 1));
				} else if (args[0].equalsIgnoreCase("help")) {
					if (args.length != 1) {
						player.sendMessage(ChatColor.RED + "Invalid Usage! Try /party help");
					} else {
						sendHelpMessage(player);
					}
				}
			}
		}
		return false;
	}

	public void sendHelpMessage(Player player) {
		// @formatter:off
		player.sendMessage(new String[] { 
				ChatColor.AQUA + "" + ChatColor.BOLD + "Party Commands",
				ChatColor.AQUA + "/party invite [playername] - " + ChatColor.GRAY + "Invite a player to the party.",
				ChatColor.AQUA + "/party kick [playername] - " + ChatColor.GRAY + "Kick a player from your party.",
				ChatColor.AQUA + "/party promote [playername] - " + ChatColor.GRAY + "Give a player permission to invite/kick.",
				ChatColor.AQUA + "/party leave - " + ChatColor.GRAY + "Leaves your current party.",
				ChatColor.AQUA + "/party info - " + ChatColor.GRAY + "Display more information such as level, health and class.",
				ChatColor.AQUA + "/party roll - " + ChatColor.GRAY + "Rolls a random number from 1 to 100 and displays it to the entire party." 
				});
		// @formatter:on
	}
}
