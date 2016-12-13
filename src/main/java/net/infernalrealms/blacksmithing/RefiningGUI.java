package net.infernalrealms.blacksmithing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.items.Rarity;
import net.infernalrealms.mining.Ore;
import net.infernalrealms.util.GeneralUtil;

public class RefiningGUI {

	public static final String BASE_NAME = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "INPUTS               RESULTS";

	public static void openGUI(Player player) {
		Inventory inv = Bukkit.createInventory(player, 27, BASE_NAME);

		inv.setItem(3, GeneralItems.BLANK_BLACK_GLASS_PANE);
		inv.setItem(21, GeneralItems.BLANK_BLACK_GLASS_PANE);

		ItemStack confirmButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
		confirmButtonMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Refine Items");
		confirmButton.setItemMeta(confirmButtonMeta);
		inv.setItem(12, confirmButton);

		player.openInventory(inv);
	}

	public static class GUIListener implements Listener {

		@EventHandler
		public void onGUIClick(InventoryClickEvent event) {
			if (!event.getInventory().getTitle().equals(BASE_NAME) || !(event.getWhoClicked() instanceof Player)) {
				return;
			}
			//			if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
			//				event.setCancelled(true);
			//				return;
			//			}
			Player player = (Player) event.getWhoClicked();

			if (event.getRawSlot() == 12) {
				// Confirm transaction
				event.setCancelled(true);
				for (int i = 0; i < 15; i++) {
					int slot = 4 + (i / 5) * 9 + i % 5;
					ItemStack item = event.getInventory().getItem(slot);
					if (item != null && item.getType() != Material.AIR) {
						GeneralUtil.addItemToInventoryOrDrop(player, item);
					}
				}
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Items refined.");
				player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1F, 0F); // TODO 1.9 CHANGE (was Sound.FIZZ)
				clearLeftInventory(event.getInventory());
				player.closeInventory();
			} else if (event.getRawSlot() % 9 < 3) {
				// Left Side
				updateValueContent(event.getInventory());
			} else if (event.getRawSlot() < event.getView().getTopInventory().getSize()) { // Top inv
				event.setCancelled(true);
			} else if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) { // Shift click on bottom inv
				if (getLeftInventory(event.getInventory()).size() >= 3) {
					event.setCancelled(true);
					return;
				}
				updateValueContent(event.getInventory());
			}
		}

		@EventHandler
		public void onGUIDrag(InventoryDragEvent event) {
			for (int i : event.getRawSlots()) {
				event.getView().setCursor(event.getOldCursor());
				InventoryClickEvent ice = new InventoryClickEvent(event.getView(), SlotType.CONTAINER, i, ClickType.LEFT,
						InventoryAction.PLACE_SOME);
				onGUIClick(ice);
				if (ice.isCancelled()) {
					event.setCancelled(true);
					return;
				}
			}
		}

		@EventHandler
		public void onGUIClose(InventoryCloseEvent event) {
			if (!event.getInventory().getTitle().equals(BASE_NAME) || !(event.getPlayer() instanceof Player)) {
				return;
			}
			Player player = (Player) event.getPlayer();
			for (ItemStack item : getLeftInventory(event.getInventory())) {
				if (item == null || item.getType() == Material.AIR) {
					continue;
				}
				GeneralUtil.addItemToInventoryOrDrop(player, item);
			}
		}

	}

	public static void updateValueContent(Inventory inv) {
		new BukkitRunnable() {
			@Override
			public void run() {
				clearRightInventory(inv);
				List<ItemStack> inputs = getLeftInventory(inv);
				Inventory temp = Bukkit.createInventory(null, 18);
				for (ItemStack item : inputs) {
					List<ItemStack> outputs = getScrapItemsForItem(item);
					for (ItemStack outputItem : outputs) {
						temp.addItem(outputItem);
					}
				}
				for (int i = 0; i < 15; i++) {
					ItemStack item = temp.getItem(i);
					if (item == null || item.getType() == Material.AIR) {
						// End of list
						break;
					}
					// 0 1 2 3 4, 5  6  7  8  9 , 10 11 12 13 14
					// 4 5 6 7 8, 13 14 15 16 17, 22 23 24 25 26
					int slot = 4 + (i / 5) * 9 + i % 5;
					inv.setItem(slot, item);
				}
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	}

	public static List<ItemStack> getLeftInventory(Inventory inv) {
		List<ItemStack> list = new ArrayList<>(9);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int slot = 9 * i + j;
				ItemStack item = inv.getItem(slot);
				if (item == null || item.getType() == Material.AIR) {
					continue;
				}
				list.add(item);
			}
		}
		return list;
	}

	public static void clearRightInventory(Inventory inv) {
		for (int i = 0; i < 15; i++) {
			int slot = 4 + (i / 5) * 9 + i % 5;
			inv.setItem(slot, null);
		}
	}

	public static void clearLeftInventory(Inventory inv) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int slot = 9 * i + j;
				inv.setItem(slot, null);
			}
		}
	}

	public static List<ItemStack> getScrapItemsForItem(ItemStack item) {
		List<ItemStack> list;
		System.out.println(Ore.isOre(item));
		if (!Ore.isOre(item)) {
			list = new ArrayList<>();
			int scrapValue = (ItemReader.getItemLevel(item) * 5) / 3;
			Rarity rarity = ItemReader.getRarity(item);
			int level = ItemReader.getRequiredLevel(item);
			ScrapItem scrap = ScrapItem.fromLevel(level);
			int givenScrapValue = 0;
			while (rarity != null) {
				int amount = 0;
				int scrapValueInc = scrap.getValueForRarity(rarity);
				while (givenScrapValue <= scrapValue - scrapValueInc) {
					givenScrapValue += scrapValueInc;
					amount++;
				}
				if (amount > 0) {
					list.add(scrap.getItemStack(rarity, amount));
				}
				rarity = Rarity.fromRarityIndex(rarity.getRarityIndex() - 1);
			}
		} else {
			return Ore.refineOre(item);
		}
		return list;
	}

}
