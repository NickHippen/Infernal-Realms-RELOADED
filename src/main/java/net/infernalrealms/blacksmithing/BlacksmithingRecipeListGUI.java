package net.infernalrealms.blacksmithing;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.infernalrealms.items.EquipmentGroup;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.mining.Ore;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

public class BlacksmithingRecipeListGUI {

	public static final String BASE_NAME = "Recipe List [";
	public static final String CRAFT_NAME = "Recipe Crafting Menu";

	public static void openBaseMenu(Player player, RecipeItemType type) {
		openBaseMenu(player, type, 1);
	}

	public static void openBaseMenu(Player player, RecipeItemType type, int page) {
		if (page < 1) {
			throw new IndexOutOfBoundsException("Page must be > 0");
		}
		PlayerData playerData = PlayerData.getData(player);
		List<List<String>> recipeItems = playerData.getRecipeLog(type);
		int maxPages = ((recipeItems.size() / 45) + 1);
		if (recipeItems.size() % 45 == 0) {
			maxPages--;
		}
		if (recipeItems.size() == 0) {
			// Empty recipe list
			player.sendMessage(ChatColor.RED + "You do not have any recipes of this type available.");
			return;
		} else if (page > maxPages) {
			throw new IndexOutOfBoundsException("Cannot exceed " + maxPages + " pages. Was given " + page + ".");
		}
		boolean allowBack = page > 1;
		boolean allowNext = page < maxPages;
		int size;
		if (allowBack || allowNext) {
			// Make room for next page / previous page icon
			size = 54;
		} else {
			size = 45;
		}

		Inventory inv = Bukkit.createInventory(player, size, BASE_NAME + type + "][" + page + "]");
		for (int i = (page - 1) * 45; i < recipeItems.size() && i < page * 45; i++) {
			ItemStack recipeItem = convertRecipeLoreToGUIItem(recipeItems.get(i));
			if (recipeItem == null || !type.isOfType(recipeItem.getType())) {
				continue;
			}
			inv.addItem(recipeItem);
		}
		if (allowBack) {
			ItemStack backButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
			ItemMeta backButtonMeta = backButton.getItemMeta();
			backButtonMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Previous Page: " + (page - 1));
			backButton.setItemMeta(backButtonMeta);
			inv.setItem(45, backButton);
		}
		if (allowNext) {
			ItemStack nextButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
			ItemMeta nextButtonMeta = nextButton.getItemMeta();
			nextButtonMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Next Page: " + (page + 1));
			nextButton.setItemMeta(nextButtonMeta);
			inv.setItem(53, nextButton);
		}
		player.openInventory(inv);
	}

	public static void openCraftMenu(Player player, List<String> recipeLore) {
		Inventory inv = Bukkit.createInventory(player, 9, CRAFT_NAME);
		ItemStack recipeItem = convertRecipeLoreToGUIItem(recipeLore);
		inv.setItem(8, recipeItem);
		ItemStack craftButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta craftButtonMeta = craftButton.getItemMeta();
		int craftValue = ItemReader.getCraftingValue(recipeLore);
		craftButtonMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Craft Item");
		craftButtonMeta
				.setLore(Arrays.asList(ChatColor.WHITE + "Scrap Value Needed: " + craftValue, ChatColor.WHITE + "Scrap Value Offered: 0"));
		craftButton.setItemMeta(craftButtonMeta);
		inv.setItem(7, craftButton);

		player.openInventory(inv);
	}

	public static class GUIListener implements Listener {

		@EventHandler
		public void onBaseGUIClick(InventoryClickEvent event) {
			if (!event.getInventory().getTitle().startsWith(BASE_NAME) || !(event.getWhoClicked() instanceof Player)) {
				return;
			}
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			String title = event.getInventory().getTitle();
			int currPage = Integer.parseInt(title.replaceAll("[^0-9]", ""));
			RecipeItemType type = RecipeItemType.fromString(title.substring(title.indexOf("[") + 1, title.indexOf("]")));
			if (event.getRawSlot() >= 0 && event.getRawSlot() < 45) {
				PlayerData playerData = PlayerData.getData(player);
				List<List<String>> recipeItems = playerData.getRecipeLog(type);
				int recipeIndex = event.getRawSlot() + ((currPage - 1) * 45);
				if (recipeIndex >= recipeItems.size()) {
					return;
				}
				openCraftMenu(player, recipeItems.get(recipeIndex));
			} else {
				switch (event.getRawSlot()) {
				case 45: // Previous
					openBaseMenu(player, type, currPage - 1);
					break;
				case 53: // Next
					openBaseMenu(player, type, currPage + 1);
					break;
				}
			}
		}

		@EventHandler
		public void onCraftGUIClick(InventoryClickEvent event) {
			if (!event.getInventory().getTitle().equals(CRAFT_NAME) || !(event.getWhoClicked() instanceof Player)) {
				return;
			}
			Player player = (Player) event.getWhoClicked();
			if (event.getRawSlot() == 8) {
				event.setCancelled(true);
			} else if (event.getRawSlot() == 7) {
				event.setCancelled(true);
				// Attempt to craft
				int scrapValue;
				try {
					scrapValue = getTotalScrapValue(event.getInventory());
				} catch (ZeroScrapValueException e) {
					player.sendMessage(ChatColor.RED + "You cannot offer items with no scrap value.");
					return;
				}
				ItemStack itemToCraft = event.getInventory().getItem(8);
				int craftValue = ItemReader.getCraftingValue(itemToCraft);
				if (craftValue > scrapValue) {
					player.sendMessage(ChatColor.RED + "You have not offered enough scrap!");
					player.sendMessage(ChatColor.RED + "Offered: " + scrapValue + ", Needed: " + craftValue);
					return;
				}
				int offeredScrap = 0;
				ItemStack item;
				lowestSearch: while ((item = getLowestValueScrapAndRemove(event.getInventory())) != null) {
					int currScrapValue = ItemReader.getScrapValueIgnoreAmount(item);
					while (item.getAmount() > 1) {
						offeredScrap += currScrapValue;
						item.setAmount(item.getAmount() - 1);
						if (offeredScrap >= craftValue) {
							event.getInventory().addItem(item); // Add back to inventory so the rest will return to player
							break lowestSearch;
						}
					}
					offeredScrap += currScrapValue; // "Take" last one
					if (offeredScrap >= craftValue) {
						break lowestSearch;
					}
				}
				// Don't clear inventory because the remaining items should be returned on close
				player.closeInventory();
				player.getInventory().addItem(BlacksmithingRecipeFactory.convertRecipeLoreToItem(itemToCraft.getItemMeta().getDisplayName(),
						itemToCraft.getItemMeta().getLore()));
				player.sendMessage(ChatColor.GREEN + "The item has been successsfully crafted.");
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 0F);
			} else {
				if (event.getRawSlot() >= 0 && event.getRawSlot() < 7) {
					new BukkitRunnable() {

						@Override
						public void run() {
							ItemStack item = event.getInventory().getItem(7);
							ItemMeta itemMeta = item.getItemMeta();
							List<String> lore = itemMeta.getLore();
							int scrapValue;
							try {
								scrapValue = getTotalScrapValue(event.getInventory());
							} catch (ZeroScrapValueException e) {
								scrapValue = 0;
							}
							lore.set(1, ChatColor.WHITE + "Scrap Value Offered: " + scrapValue);
							itemMeta.setLore(lore);
							item.setItemMeta(itemMeta);
						}
					}.runTaskLater(InfernalRealms.getPlugin(), 1L);
				}
				ItemStack clickedOn = event.getCurrentItem();
				ItemStack clickedWith = event.getCursor();
				ItemStack itemToCraft = event.getInventory().getItem(8);
				if (clickedWith != null && clickedWith.getType() != Material.AIR) {
					return;
				} else if (!ScrapItem.isScrap(clickedOn)) {
					event.setCancelled(true);
					return;
				} else if (ScrapItem.fromItem(clickedOn) != ScrapItem.fromLevel(ItemReader.getRequiredLevel(itemToCraft))) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "You cannot use scrap of a different tier.");
					return;
				}
			}
		}

		@EventHandler
		public void onCraftGUIDrag(InventoryDragEvent event) {
			if (!event.getInventory().getTitle().equals(CRAFT_NAME)) {
				return;
			}
			for (int i : event.getRawSlots()) {
				event.getView().setCursor(event.getOldCursor());
				InventoryClickEvent ice = new InventoryClickEvent(event.getView(), SlotType.CONTAINER, i, ClickType.LEFT,
						InventoryAction.PLACE_SOME);
				onCraftGUIClick(ice);
				if (ice.isCancelled()) {
					event.setCancelled(true);
					return;
				}
			}
		}

		@EventHandler
		public void onCraftGUIClose(InventoryCloseEvent event) {
			if (!event.getInventory().getTitle().equals(CRAFT_NAME) || !(event.getPlayer() instanceof Player)) {
				return;
			}
			Player player = (Player) event.getPlayer();
			for (int i = 0; i < 7; i++) {
				ItemStack item = event.getInventory().getItem(i);
				if (item != null) {
					GeneralUtil.addItemToInventoryOrDrop(player, item);
				}
			}
		}

	}

	private static int getTotalScrapValue(Inventory inv) throws ZeroScrapValueException {
		int totalScrapValue = 0;
		for (int i = 0; i < inv.getSize() - 2; i++) {
			ItemStack item = inv.getItem(i);
			if (item == null || item.getType() == Material.AIR) {
				continue;
			}
			int scrapValue = ItemReader.getScrapValue(item);
			if (scrapValue == 0) {
				throw new ZeroScrapValueException();
			}
			totalScrapValue += scrapValue;
		}
		if (totalScrapValue == 0) {
			throw new ZeroScrapValueException();
		}
		return totalScrapValue;
	}

	private static ItemStack convertRecipeLoreToGUIItem(List<String> recipeLore) {
		List<String> lore = new ArrayList<>(recipeLore.size());
		EquipmentGroup equipmentGroup = EquipmentGroup.fromString(ChatColor.stripColor(recipeLore.get(3)));
		int level = ItemReader.getRequiredLevel(recipeLore);

		ItemStack item = new ItemStack(equipmentGroup.getMaterialForLevel(level));
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(recipeLore.get(0));
		for (int i = 1; i < recipeLore.size(); i++) {
			lore.add(recipeLore.get(i));
		}
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);

		return item;
	}

	private static ItemStack getLowestValueScrapAndRemove(Inventory inv) {
		int lowestIndex = -1;
		int value = Integer.MAX_VALUE;
		for (int i = 0; i < 7; i++) {
			ItemStack item = inv.getItem(i);
			if (item == null) {
				continue;
			}
			int curr = ItemReader.getScrapValueIgnoreAmount(item);
			if (curr <= 0) {
				continue;
			}
			if (curr < value) {
				value = curr;
				lowestIndex = i;
			}
		}
		if (lowestIndex < 0) {
			return null;
		}
		ItemStack item = inv.getItem(lowestIndex);
		inv.setItem(lowestIndex, null);
		return item;
	}

}
