package net.infernalrealms.leaderboard;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.infernalrealms.items.GeneralItems;

public abstract class Leaderboard<T extends Record<?>> {

	private static final String LEADERBOARD_TITLE = "Leaderboard";

	private List<T> records;

	public Leaderboard(List<T> records) {
		this.records = records;
	}

	public Inventory openLeaderboardMenu(Player player) {
		Inventory inv = player.getServer().createInventory(player, getMaxRecordsTracked(), LEADERBOARD_TITLE);
		for (int i = 0; i < getRecords().size(); i++) {
			T record = getRecords().get(i);
			inv.setItem(i, record.generateIcon(i + 1));
		}
		for (int i = getRecords().size(); i < 18; i++) {
			inv.setItem(i, GeneralItems.BLANK_BLACK_GLASS_PANE);
		}

		player.openInventory(inv);
		return inv;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public void trim() {
		if (getRecords().size() > 18) {
			setRecords(getRecords().subList(0, 18));
		}
	}

	public abstract int getMaxRecordsTracked();

	public abstract void save();

	public static class GUIListener implements Listener {

		@EventHandler
		public void onGUIClick(InventoryClickEvent event) {
			if (!event.getInventory().getTitle().equals(LEADERBOARD_TITLE)) {
				return;
			}
			event.setCancelled(true);
		}

	}

}
