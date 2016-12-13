package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.InfernalEffects;

public abstract class ObjectiveInteractWithBlock extends Objective {

	private Location location;
	private String[] messages;

	public ObjectiveInteractWithBlock(Location location, String... messages) {
		super(1, 0);
		this.location = location;
		this.messages = messages;
	}

	public boolean checkLocation(Location testLoc) {
		return testLoc.getWorld().equals(getLocation().getWorld()) && testLoc.getBlockX() == getLocation().getBlockX()
				&& testLoc.getBlockY() == getLocation().getBlockY() && testLoc.getBlockZ() == getLocation().getBlockZ();
	}

	public Location getLocation() {
		return this.location;
	}

	public void sendMessages(Player player) {
		if (this.messages == null || this.messages.length == 0) {
			PlayerData.setConversation(player, false);
			return;
		}
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
				player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "(" + (i + 1) + ") " + ChatColor.GRAY
						+ messages[i].replaceAll("PLAYER_NAME", player.getName()).replaceAll("PLAYER_CLASS",
								PlayerData.getData(player).getPlayerClass()));
				i++;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, ObjectiveTalk.MESSAGE_DELAY);
	}

}
