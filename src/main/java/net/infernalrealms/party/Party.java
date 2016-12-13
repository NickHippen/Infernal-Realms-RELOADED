package net.infernalrealms.party;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.infernalrealms.chat.InteractiveChat;
import net.infernalrealms.dungeons2.DungeonInstance;
import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.player.PlayerData;

public class Party {

	private String owner;
	private List<String> promoted = new ArrayList<String>();
	private List<String> members = new ArrayList<String>();
	private Scoreboard board;
	private Objective objective;
	private Team team;
	private DungeonInstance dungeon;
	private DungeonType queuedDungeonType = null;

	public Party(Player owner) {
		this.setOwner(owner.getName());
		setParty(owner, this);

		// Scoreboard
		board = InfernalRealms.manager.getNewScoreboard();
		team = board.registerNewTeam("Party");
		team.addPlayer(owner);
		team.setCanSeeFriendlyInvisibles(true);
		objective = board.registerNewObjective("Health", "dummy");
		objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Party");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		updatePlayerHealth(owner);
		owner.setScoreboard(board);
	}

	public boolean isMember(Player player) {
		String playerName = player.getName();
		return owner.equals(playerName) || promoted.contains(playerName) || members.contains(playerName);
	}

	public boolean canInviteKick(Player player) {
		String playerName = player.getName();
		return owner.equals(playerName) || promoted.contains(playerName);
	}

	/**
	 * Prompts an invite to the target player, if possible.
	 * @param player
	 */
	public void invite(final Player target, final Player sender) {
		final String targetName = target.getName();
		if (isFull()) {
			sender.sendMessage(ChatColor.RED + "The party is currently " + ChatColor.BOLD + "full.");
			return;
		}
		if (target.hasMetadata("PendingParty")) {
			sender.sendMessage(ChatColor.RED + targetName + " has a " + ChatColor.BOLD + "pending invite.");
			return;
		}
		if (target.hasMetadata("Party")) {
			sender.sendMessage(ChatColor.RED + targetName + " is " + ChatColor.BOLD + "already in a party.");
			return;
		}
		setPartyInvite(target, this);
		InteractiveChat interactiveChat = new InteractiveChat(ChatColor.AQUA + "" + ChatColor.ITALIC + sender.getName() + ChatColor.RESET
				+ ChatColor.AQUA + " has invited you to a " + ChatColor.BOLD + "party." + ChatColor.RESET + ChatColor.AQUA + " Type "
				+ ChatColor.ITALIC + "/party accept" + ChatColor.RESET + ChatColor.AQUA + " or click here to join the party.");
		interactiveChat.applyHoverMessage(ChatColor.AQUA + "Accept party invite.");
		interactiveChat.applyClickCommand("/party accept");
		interactiveChat.sendToPlayer(target);
		//		target.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + sender.getName() + ChatColor.RESET + ChatColor.AQUA
		//				+ " has invited you to a " + ChatColor.BOLD + "party." + ChatColor.RESET + ChatColor.AQUA + " Type " + ChatColor.ITALIC
		//				+ "/party accept" + ChatColor.RESET + ChatColor.AQUA + " to join.");
		final Party thisParty = this;
		new BukkitRunnable() {

			public void run() {
				Party p = getPartyFromInvite(target);
				if (p != null && p.equals(thisParty)) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + targetName + ChatColor.RESET + ChatColor.RED
							+ " failed to accept your " + ChatColor.BOLD + "party invite.");
					target.sendMessage(ChatColor.RED + " You have failed to accept " + ChatColor.ITALIC + sender.getName() + "'s "
							+ ChatColor.RESET + ChatColor.RED + ChatColor.BOLD + "party invite.");
					target.removeMetadata("PendingParty", InfernalRealms.getPlugin());
				}
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 400L);
	}

	public void addPlayer(Player player) {
		player.removeMetadata("PendingParty", InfernalRealms.getPlugin());
		if (isFull()) {
			player.sendMessage(ChatColor.RED + "You were unable to join because the party is " + ChatColor.BOLD + "full.");
			return;
		}
		setParty(player, this);
		members.add(player.getName());

		// Scoreboard
		team.addPlayer(player);
		player.setScoreboard(board);
		updatePlayerHealth(player);

		// Party Message
		broadcastToParty(ChatColor.GREEN + "" + ChatColor.ITALIC + player.getName() + ChatColor.RESET + ChatColor.GREEN + " has "
				+ ChatColor.BOLD + "joined" + ChatColor.RESET + ChatColor.GREEN + " the party.");
	}

	public void removePlayer(Player player) {
		String playerName = player.getName();
		if (isMember(player)) {
			members.remove(playerName);
			promoted.remove(playerName);
			player.removeMetadata("Party", InfernalRealms.getPlugin());

			// Scoreboard
			team.removePlayer(player);
			player.setScoreboard(InfernalRealms.manager.getNewScoreboard());
			board.resetScores(playerName);
		}
	}

	public void leave(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.hasDungeon()) {
			player.sendMessage(ChatColor.RED + "You cannot leave the party while in a dungeon.");
			return;
		}
		removePlayer(player);
		player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You" + ChatColor.RESET + ChatColor.RED + " have " + ChatColor.BOLD
				+ "left" + ChatColor.RESET + ChatColor.RED + " the party.");
		if (player.getName().equals(getOwner())) {
			for (Player p : getAllOnlineMembers()) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + player.getName() + ChatColor.RESET + ChatColor.RED + " has "
						+ ChatColor.BOLD + "disbanded" + ChatColor.RESET + ChatColor.RED + " the party.");
				removePlayer(p);
			}
			return;
		}
		broadcastToParty(ChatColor.RED + "" + ChatColor.ITALIC + player.getName() + ChatColor.RESET + ChatColor.RED + " has "
				+ ChatColor.BOLD + "left" + ChatColor.RESET + ChatColor.RED + " the party.");
	}

	public void kick(Player player) {
		removePlayer(player);
		player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You" + ChatColor.RESET + ChatColor.RED + " have been " + ChatColor.BOLD
				+ "kicked" + ChatColor.RESET + ChatColor.RED + " from the party.");
		broadcastToParty(ChatColor.RED + "" + ChatColor.ITALIC + player.getName() + ChatColor.RESET + ChatColor.RED + " has been "
				+ ChatColor.BOLD + "kicked" + ChatColor.RESET + ChatColor.RED + " from the party.");
	}

	public void promote(Player player) {
		String playerName = player.getName();
		members.remove(playerName);
		promoted.add(playerName);
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You" + ChatColor.RESET + ChatColor.GREEN + " have been "
				+ ChatColor.BOLD + "promoted.");
	}

	public void broadcastToParty(String message) {
		for (Player player : getAllOnlineMembers()) {
			player.sendMessage(message);
		}
	}

	public void broadcastToParty(InteractiveChat message) {
		List<Player> members = getAllOnlineMembers();
		message.broadcastToPlayers(members);
	}

	public void broadcastToDungeonParty(String message) {
		for (Player player : getAllOnlineMembers()) {
			PlayerData playerData = PlayerData.getData(player);
			if (playerData.hasDungeon()) {
				player.sendMessage(message);
			}
		}
	}

	public void broadcastToDungeonParty(InteractiveChat message) {
		message.broadcastToPlayers(getDungeonMembers());
	}

	public List<Player> getDungeonMembers() {
		List<Player> members = new ArrayList<Player>();
		for (Player player : getAllOnlineMembers()) {
			PlayerData playerData = PlayerData.getData(player);
			if (playerData.hasDungeon()) {
				members.add(player);
			}
		}
		return members;
	}

	public List<Player> getAllOnlineMembers() {
		List<Player> allOnlineMembers = new ArrayList<Player>();
		for (String playerName : members) {
			Player player = Bukkit.getPlayer(playerName);
			if (player == null)
				continue;
			allOnlineMembers.add(player);
		}
		for (String playerName : promoted) {
			Player player = Bukkit.getPlayer(playerName);
			if (player == null)
				continue;
			allOnlineMembers.add(player);
		}
		Player player = Bukkit.getPlayer(owner);
		if (player != null)
			allOnlineMembers.add(player);
		return allOnlineMembers;
	}

	public List<String> getPartyInfoAsStringList() {
		ArrayList<String> partyInfo = new ArrayList<String>();
		partyInfo.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Party Information" + ChatColor.RED + ChatColor.BOLD + " [ " + ChatColor.RESET
				+ ChatColor.RED + "created by " + getOwner() + ChatColor.BOLD + " ]");
		for (Player player : getAllOnlineMembers()) {
			String asteriks = !canInviteKick(player) ? "" : player.getName().equals(getOwner()) ? "**" : "*";
			PlayerData playerData = PlayerData.getData(player);
			partyInfo.add(ChatColor.AQUA + player.getName() + ChatColor.GREEN + ChatColor.BOLD + " [" + ChatColor.RESET + ChatColor.GREEN
					+ (int) ((Damageable) player).getHealth() + "/" + (int) ((Damageable) player).getMaxHealth() + ChatColor.BOLD + "] "
					+ ChatColor.GRAY + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.GRAY + playerData.getPlayerClass()
					+ ChatColor.BOLD + "] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.DARK_PURPLE
					+ "LVL " + playerData.getLevel() + ChatColor.BOLD + "] " + ChatColor.RED + ChatColor.BOLD + asteriks);
		}
		return partyInfo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getPromoted() {
		return promoted;
	}

	public void setPromoted(ArrayList<String> promoted) {
		this.promoted = promoted;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<String> members) {
		this.members = members;
	}

	public boolean isFull() {
		return members.size() + promoted.size() >= 7; // Max size is 8 (owner + members + prompted)
	}

	/**
	 * Gets the target player's party
	 * @param player
	 * @return the target player's party, returns null if the player is not in a party
	 */
	public static Party getParty(Player player) {
		if (player.hasMetadata("Party")) {
			return (Party) player.getMetadata("Party").get(0).value();
		}
		return null;
	}

	public static Party getPartyFromInvite(Player player) {
		if (player.hasMetadata("PendingParty")) {
			return (Party) player.getMetadata("PendingParty").get(0).value();
		}
		return null;
	}

	public static void setParty(Player player, Party party) {
		player.setMetadata("Party", new FixedMetadataValue(InfernalRealms.getPlugin(), party));
	}

	public static void setPartyInvite(Player player, Party party) {
		player.setMetadata("PendingParty", new FixedMetadataValue(InfernalRealms.getPlugin(), party));
	}

	public void updatePlayerHealth(Player player) {
		objective.getScore(player.getName()).setScore((int) ((Damageable) player).getHealth());
	}

	//	@SuppressWarnings("unchecked")
	//	public void promptDungeon(DungeonType dungeon) {
	//		setQueuedDungeonType(dungeon);
	//		DungeonGUI.promptDungeonQueue(dungeon, this);
	//	}

	public List<Player> getNearbyPartyMembers(Location location, double distance) {
		double distanceSq = distance * distance;
		ArrayList<Player> nearbyPlayers = new ArrayList<Player>();
		for (Player player : getAllOnlineMembers()) {
			if (player.getWorld().equals(location.getWorld())) {
				if (player.getLocation().distanceSquared(location) <= distanceSq) {
					nearbyPlayers.add(player);
				}
			}
		}
		return nearbyPlayers;
	}

	public boolean hasDungeon() {
		return this.dungeon != null;
	}

	public DungeonInstance getDungeon() {
		return this.dungeon;
	}

	public void setQueuedDungeonType(DungeonType dt) {
		this.queuedDungeonType = dt;
	}

	public DungeonType getQueuedDungeonType() {
		return this.queuedDungeonType;
	}

	public void setDungeon(DungeonInstance dungeon) {
		this.dungeon = dungeon;
	}

}
