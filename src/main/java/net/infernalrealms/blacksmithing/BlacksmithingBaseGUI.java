package net.infernalrealms.blacksmithing;

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

import net.infernalrealms.items.GeneralItems;

public class BlacksmithingBaseGUI {

	public static final String BASE_NAME = "Blacksmithing";
	public static final int[] BASE_EMPTY_SLOTS = new int[] { 0, 1, 2, 3, 4, 6, 8, 13, 15, 17, 18, 19, 20, 21, 22, 24, 26 };

	public static void openBaseMenu(Player player) {
		Inventory inv = Bukkit.createInventory(player, 27, BASE_NAME);

		// Place empty slots
		for (int slot : BASE_EMPTY_SLOTS) {
			inv.setItem(slot, GeneralItems.BLANK_BLACK_GLASS_PANE);
		}

		// Armor slots
		inv.setItem(9, getCraftButton(Material.IRON_HELMET, "Helmets", "helmet"));
		inv.setItem(10, getCraftButton(Material.IRON_CHESTPLATE, "Chestplates", "chestplate"));
		inv.setItem(11, getCraftButton(Material.IRON_LEGGINGS, "Leggings", "legging"));
		inv.setItem(12, getCraftButton(Material.IRON_BOOTS, "Boots", "pair of boots"));

		// Weapon slots
		inv.setItem(5, getCraftButton(Material.BOW, "Bows", "bow"));
		inv.setItem(14, getCraftButton(Material.IRON_SWORD, "Swords", "sword"));
		inv.setItem(23, getCraftButton(Material.STICK, "Wands", "wand"));

		// Pickaxe equips
		inv.setItem(25, getCraftButton(Material.STICK, "Hilts", "hilt"));
		inv.setItem(16, getCraftButton(Material.FIREWORK_CHARGE, "Bindings", "binding"));
		inv.setItem(7, getCraftButton(Material.IRON_INGOT, "Reinforcements", "reinforcement"));

		player.openInventory(inv);
	}

	public static ItemStack getCraftButton(Material material, String displayName, String loreName) {
		ItemStack item = new ItemStack(material, 1, (short) 0);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Craft " + displayName);
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click here to");
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "craft a " + loreName + ".");
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static class GUIListener implements Listener {

		@EventHandler
		public void onGUIClick(InventoryClickEvent event) {
			if (!event.getInventory().getTitle().equals(BASE_NAME) || !(event.getWhoClicked() instanceof Player)) {
				return;
			}
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();

			switch (event.getRawSlot()) {
			// Armor
			case 9: // Helmet
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.HELMET);
				break;
			case 10: // Chestplate
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.CHESTPLATE);
				break;
			case 11: // Leggings
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.LEGGINGS);
				break;
			case 12: // Boots
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.BOOTS);
				break;
			// Weapons
			case 5: // Bow
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.BOW);
				break;
			case 14: // Sword
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.SWORD);
				break;
			case 23: // Wand
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.WAND);
				break;
			// Pickaxe
			case 6: // Hilt
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.HILT);
				break;
			case 15: // Bindings
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.BINDING);
				break;
			case 24: // Reinforcements
				BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.REINFORCEMENT);
				break;
			}
		}

	}

}
