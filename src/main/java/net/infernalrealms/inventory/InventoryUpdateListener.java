package net.infernalrealms.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.infernalrealms.npc.TraitShopkeeper;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

public class InventoryUpdateListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			PlayerData playerData = PlayerData.getData(player);
			playerData.setDirtyEquips(true);
			/*
			if (playerData.hasClickBuffer()) {
				event.setCancelled(true);
				return;
			}
			*/
			if (event.getInventory().getTitle().contains("Pouch: ")) {
				InventoryManager.updatePouchContents(player, event.getInventory());
			} else if (event.getInventory().getTitle().equals("Bank")) {
				InventoryManager.updateBankContents(player, event.getInventory());
			} else {
				InventoryManager.updateMainInventoryContent(player);
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if (event.getInventory().getTitle().contains("Pouch: ")) {
				InventoryManager.updatePouchContents(player, event.getInventory());
			} else if (event.getInventory().getTitle().equals("Bank")) {
				InventoryManager.updateBankContents(player, event.getInventory());
			} else {
				if (inventory.getTitle().contains(TraitShopkeeper.SELL_SHOP_TITLE)) {
					for (int i = 0; i < 8; i++) {
						ItemStack item = inventory.getItem(i);
						if (item != null) {
							GeneralUtil.addItemToInventoryOrDrop(player, item);
						}
					}
				}
				InventoryManager.updateMainInventoryContent(player);
			}
		}
	}

	@EventHandler
	public void onFurnaceExtract(FurnaceExtractEvent event) {
		Player player = event.getPlayer();
		InventoryManager.updateMainInventoryContent(player);
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			PlayerData playerData = PlayerData.getData(player);
			playerData.setDirtyEquips(true);
			if (event.getInventory().getTitle().contains("Pouch: ")) {
				InventoryManager.updatePouchContents(player, event.getInventory());
			} else if (event.getInventory().getTitle().equals("Bank")) {
				InventoryManager.updateBankContents(player, event.getInventory());
			} else {
				InventoryManager.updateMainInventoryContent(player);
			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if (event.getInventory().getTitle().contains("Pouch: ")) {
				InventoryManager.updatePouchContents(player, event.getInventory());
			} else {
				InventoryManager.updateMainInventoryContent(player);
			}
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		InventoryManager.updateMainInventoryContent(player);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		InventoryManager.updateMainInventoryContent(player);
	}

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		InventoryManager.updateMainInventoryContent(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		InventoryManager.updateMainInventoryContent(player);
	}

}
