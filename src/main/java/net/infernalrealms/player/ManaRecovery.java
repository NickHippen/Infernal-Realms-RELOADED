package net.infernalrealms.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.chat.InteractiveChat;
import net.infernalrealms.gui.ActionBarHandler;
import net.infernalrealms.gui.QuestTabList;
import net.infernalrealms.gui.StatsTabList;
import net.infernalrealms.gui.TabList;
import net.infernalrealms.gui.TabListType;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.skills.magician.ClearMind;
import net.md_5.bungee.api.ChatColor;

public class ManaRecovery extends BukkitRunnable {

	private int count = 1;
	private boolean tabRotateStats = false;

	public void run() {
		try {
			for (World w : Bukkit.getServer().getWorlds()) {
				for (Player player : w.getPlayers()) {
					if (!player.hasMetadata("NPC")) {
						PlayerData playerData = PlayerData.getData(player);
						ActionBarHandler.sendActionBar(player, playerData.getPlayerInfoString());
						if (count % 3 == 0) { // Every 3 seconds
							int manaRegen = (int) (1 + playerData.getTotalSpirit() * 0.25);
							if (playerData.getPlayerSuperClass().equalsIgnoreCase("Magician")) {
								manaRegen *= new ClearMind(player).getManaModifier();
							}
							playerData.modifyMana(playerData.getMana() < playerData.getTotalMaxMana() ? manaRegen : 0);
							if (player.getGameMode() != GameMode.CREATIVE && !ItemReader.isHoldable(player.getItemInHand()))
								player.getInventory().setHeldItemSlot(0);
							if (TabList.getTabListType(player) == TabListType.STATS) {
								StatsTabList.sendTab(player);
							}
						}
						if (count % 6 == 0) {
							if (TabList.getTabListType(player) == TabListType.ROTATE) {
								if (tabRotateStats) {
									StatsTabList.sendTab(player);
								} else {
									QuestTabList.sendTab(player);
								}
							}
							// Save locations
							playerData.setLocation();
						}
						if (count % 30 == 0) {
							if (playerData.getAP() > 0) {
								InteractiveChat message = new InteractiveChat(ChatColor.RED
										+ "You have unspent ability points (AP). Click here to open up the menu to spend them!");
								message.applyClickCommand("/stats");
								message.applyHoverMessage("Open stats menu.");
								message.sendToPlayer(player);
							}
							if (playerData.getLevel() > 7 && playerData.getSP() > 1
									|| playerData.getLevel() <= 7 && playerData.getSP() > 0) {
								if (playerData.getLevel() != 1) {
									InteractiveChat message = new InteractiveChat(ChatColor.RED
											+ "You have unspent skill points (SP). Click here to open up the menu to spend them!");
									message.applyClickCommand("/skills");
									message.applyHoverMessage("Open skills menu.");
									message.sendToPlayer(player);
								}
							}
						}
					}
				}
			}
			if (count % 6 == 0) {
				tabRotateStats = !tabRotateStats;
			}
			if (++count > 30) {
				count = 1;
			}
			// Any misc. test things below:
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
