package net.infernalrealms.chat;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;

public class BroadcastRunnable extends BukkitRunnable {

	public static List<String> announcements = YAMLFile.ANNOUNCEMENTS.getConfig().getStringList("Announcements");

	public static BroadcastRunnable currentRunnable = null;

	private int index;

	public BroadcastRunnable() {
		runTaskTimer(InfernalRealms.getPlugin(), 600L, 4800L);
		this.index = 0;
	}

	@Override
	public void run() {
		if (announcements == null) {
			System.out.println("WARNING: Error getting announcements!");
			return;
		}

		if (index >= announcements.size()) {
			// Restart loop
			index = 0;
		}

		String announcement = announcements.get(index);
		if (announcement == null) {
			System.out.println("WARNING: Error getting announcement line " + index + "!");
			return;
		}

		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "TIP: " + ChatColor.GRAY
				+ ChatColor.translateAlternateColorCodes('&', announcement));
		Bukkit.broadcastMessage(" ");

		index++;
	}

	public static void reloadAnnouncements() {
		announcements = YAMLFile.ANNOUNCEMENTS.getConfig().getStringList("Announcements");
	}

}
