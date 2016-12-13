package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.npc.NPCManager;
import net.infernalrealms.party.Party;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.InfernalEffects;

public class ObjectiveTalk extends Objective {

	public static final long MESSAGE_DELAY = 80L;

	private String npcName;
	private String[] messages;
	private Location location;
	boolean dungeon;

	public ObjectiveTalk(String npcName, String... messages) {
		this(false, npcName, messages);
	}

	public ObjectiveTalk(boolean dungeon, String npcName, String... messages) {
		super(1, 0);
		this.dungeon = dungeon;
		this.npcName = npcName;
		this.messages = messages;
		this.location = NPCManager.getNPCLocationByName(getNpcName());
	}

	public String getNpcName() {
		return this.npcName;
	}

	public String[] getMessages() {
		return this.messages;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	public void sendMessages(Player player) {
		if (this.messages == null || this.messages.length == 0) {
			PlayerData.setConversation(player, false);
			return;
		}
		Party party = null;
		if (dungeon) {
			party = Party.getParty(player);
		}
		if (party == null) {
			player.sendMessage("");
			new BukkitRunnable() {
				int i = 0;

				@Override
				public void run() {
					if (!player.isOnline() || i >= messages.length) {
						PlayerData.setConversation(player, false);
						cancel();
						return;
					}
					InfernalEffects.playConversationContinueSound(player);
					player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "(" + (i + 1) + ") " + ChatColor.DARK_GREEN
							+ ChatColor.BOLD + npcName + ": " + ChatColor.GRAY + messages[i].replaceAll("PLAYER_NAME", player.getName())
									.replaceAll("PLAYER_CLASS", PlayerData.getData(player).getPlayerClass()));
					i++;
				}
			}.runTaskTimer(InfernalRealms.getPlugin(), 0L, MESSAGE_DELAY);
		} else {
			final Party finalParty = party;
			new BukkitRunnable() {
				int i = 0;

				@Override
				public void run() {
					if (!player.isOnline() || i >= messages.length) {
						PlayerData.setConversation(player, false);
						cancel();
						return;
					}
					finalParty.getDungeonMembers().forEach(InfernalEffects::playConversationContinueSound);
					finalParty.broadcastToDungeonParty(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "(" + (i + 1) + ") "
							+ ChatColor.DARK_GREEN + ChatColor.BOLD + npcName + ": " + ChatColor.GRAY + messages[i]);
					i++;
				}
			}.runTaskTimer(InfernalRealms.getPlugin(), 0L, MESSAGE_DELAY);
		}
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Talk to " + this.getNpcName();
	}
}
