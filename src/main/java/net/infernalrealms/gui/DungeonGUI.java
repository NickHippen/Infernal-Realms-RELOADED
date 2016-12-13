package net.infernalrealms.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.leaderboard.DungeonLeaderboard;
import net.infernalrealms.leaderboard.Record;
import net.infernalrealms.leaderboard.TimeScore;
import net.infernalrealms.party.Party;
import net.infernalrealms.quests.Reward;

public class DungeonGUI {

	public static String BASE_MENU_NAME = "Dungeon: ";

	public static Inventory openDungeonBaseMenu(Player player, DungeonType dungeon) {
		Inventory inv = Bukkit.createInventory(player, 9, BASE_MENU_NAME + dungeon.getDisplayName());
		for (int slot : new int[] { 0, 1, 3, 5, 7, 8 }) {
			inv.setItem(slot, GeneralItems.BLANK_BLACK_GLASS_PANE);
		}
		ItemStack soloLeaderboard = new ItemStack(Material.SIGN, 1, (short) 0);
		ItemMeta soloLeaderboardMeta = soloLeaderboard.getItemMeta();
		soloLeaderboardMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Solo Run Leaderboard");
		List<String> soloLeaderboardLore = new ArrayList<>();
		soloLeaderboardLore.add(ChatColor.GRAY + "Click here to view the");
		soloLeaderboardLore.add(ChatColor.GRAY + "solo run leaderboard.");
		soloLeaderboardMeta.setLore(soloLeaderboardLore);
		soloLeaderboard.setItemMeta(soloLeaderboardMeta);
		inv.setItem(2, soloLeaderboard);

		ItemStack dungeonQueue = new ItemStack(Material.IRON_SWORD, 1, (short) 0);
		ItemMeta dungeonQeueMeta = dungeonQueue.getItemMeta();
		dungeonQeueMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Dungeon Queue");
		List<String> dungeonQueueLore = new ArrayList<>();
		dungeonQueueLore.add(ChatColor.GRAY + "Click here to queue");
		dungeonQueueLore.add(ChatColor.GRAY + "for the dungeon.");
		dungeonQeueMeta.setLore(dungeonQueueLore);
		dungeonQueue.setItemMeta(dungeonQeueMeta);
		inv.setItem(4, dungeonQueue);

		ItemStack partyLeaderboard = new ItemStack(Material.SIGN, 1, (short) 0);
		ItemMeta partyLeaderboardMeta = partyLeaderboard.getItemMeta();
		partyLeaderboardMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Party Run Leaderboard");
		List<String> partyLeaderboardLore = new ArrayList<>();
		partyLeaderboardLore.add(ChatColor.GRAY + "Click here to view the");
		partyLeaderboardLore.add(ChatColor.GRAY + "party run leaderboard.");
		partyLeaderboardMeta.setLore(partyLeaderboardLore);
		partyLeaderboard.setItemMeta(partyLeaderboardMeta);
		inv.setItem(6, partyLeaderboard);

		player.openInventory(inv);
		return inv;
	}

	public static Inventory promptDungeonQueue(DungeonType dungeon, Player clicker) {
		Party party = Party.getParty(clicker);
		if (party == null || !party.getOwner().equals(clicker.getName())) {
			clicker.sendMessage("You must be the leader of a party to queue for a dungeon.");
			return null;
		}
		if (party.hasDungeon()) {
			return null;
		}
		party.setQueuedDungeonType(dungeon);
		Player owner = Bukkit.getPlayer(party.getOwner());
		Inventory inv = Bukkit.createInventory(owner, 9, "Dungeon Confirmation");
		List<Player> players = party.getAllOnlineMembers();
		for (int i = 0; i < 8; i++) {
			if (i < players.size()) {
				Player player = players.get(i);
				if (player != null) {
					inv.setItem(i, generatePlayerIcon(player));
				}
			} else {
				inv.setItem(i, GeneralItems.BLANK_BLACK_GLASS_PANE);
			}
		}
		ItemStack confirmationButton = new ItemStack(Material.INK_SACK, 1, (short) 13);
		ItemMeta cbMeta = confirmationButton.getItemMeta();
		cbMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Toggle Ready");
		try {
			List<Reward> rewards;
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Rewards:");
			Field f = dungeon.getQuestClass().getDeclaredField("rewards");
			f.setAccessible(true);
			rewards = (List<Reward>) f.get(null);
			for (Reward reward : rewards) {
				lore.add(reward.getQuestLogString());
			}
			cbMeta.setLore(lore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		confirmationButton.setItemMeta(cbMeta);
		inv.setItem(8, confirmationButton);
		for (Player player : players) {
			player.openInventory(inv);
		}
		return inv;
	}

	private static ItemStack generatePlayerIcon(Player player) {
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + player.getName());
		icon.setItemMeta(iconMeta);
		return icon;
	}

	public static boolean checkAllReady(Inventory inventory) {
		if (inventory.getSize() != 9) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			ItemStack item = inventory.getItem(i);
			if (item == null || !item.hasItemMeta()) {
				return false;
			}
			if (item.getType() != Material.INK_SACK) {
				break;
			}
			if (item.getDurability() == 8) {
				return false;
			}
		}
		return true;
	}

	public static class GUIListener implements Listener {

		@EventHandler
		public void onInventoryClick(InventoryClickEvent event) {
			if (!event.getInventory().getTitle().startsWith(BASE_MENU_NAME)) {
				return;
			}
			if (!(event.getWhoClicked() instanceof Player)) {
				return;
			}
			event.setCancelled(true);

			Player player = (Player) event.getWhoClicked();
			DungeonType dungeonType = DungeonType.fromDisplayName(event.getInventory().getTitle().replaceFirst(BASE_MENU_NAME, ""));
			switch (event.getRawSlot()) {
			case 2:
				// Open solo leaderboard
				DungeonLeaderboard<Record<TimeScore>> soloLeaderboard = DungeonLeaderboard.loadDungeonLeaderboard(dungeonType,
						Record.RecordType.SOLO);
				soloLeaderboard.openLeaderboardMenu(player);
				break;
			case 4:
				// Open dungeon queue
				promptDungeonQueue(dungeonType, player);
				break;
			case 6:
				// Open party leaderboard
				DungeonLeaderboard<Record<TimeScore>> partyLeaderboard = DungeonLeaderboard.loadDungeonLeaderboard(dungeonType,
						Record.RecordType.PARTY);
				partyLeaderboard.openLeaderboardMenu(player);
				break;
			}
		}

	}

}
