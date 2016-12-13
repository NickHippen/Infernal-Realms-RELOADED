package net.infernalrealms.gui;

import java.util.ArrayList;

import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectCharacterGUI {

	private static ItemStack getCharacterIcon(Player player, int characterSlot) {
		PlayerData playerData = PlayerData.getData(player);
		ItemStack charIcon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta charIconMeta = charIcon.getItemMeta();
		charIconMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Character Slot " + characterSlot);
		ArrayList<String> charIconLore = new ArrayList<String>();

		// Class
		String playerClass = playerData.getConfig().getString(characterSlot + ".Class");
		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "CLASS: " + ChatColor.RESET + ChatColor.WHITE
				+ (playerClass == null ? PlayerClass.BEGINNER.getName() : playerClass));

		// Level
		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LEVEL: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getConfig().getInt(characterSlot + ".Level"));

		charIconLore.add(" ");

		// Helmet
		ItemStack e = playerData.getConfig().getItemStack(characterSlot + ".Inventory.Main.Helmet");
		String s = e != null && e.hasItemMeta() ? e.getItemMeta().getDisplayName() : ChatColor.WHITE + "None";
		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "HEAD: " + s);

		// Chest
		e = playerData.getConfig().getItemStack(characterSlot + ".Inventory.Main.Chestplate");
		s = e != null && e.hasItemMeta() ? e.getItemMeta().getDisplayName() : ChatColor.WHITE + "None";
		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "CHEST: " + s);

		// Legs
		e = playerData.getConfig().getItemStack(characterSlot + ".Inventory.Main.Leggings");
		s = e != null && e.hasItemMeta() ? e.getItemMeta().getDisplayName() : ChatColor.WHITE + "None";
		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LEGS: " + s);

		// BOOTS
		e = playerData.getConfig().getItemStack(characterSlot + ".Inventory.Main.Boots");
		s = e != null && e.hasItemMeta() ? e.getItemMeta().getDisplayName() : ChatColor.WHITE + "None";
		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "BOOTS: " + s);

		charIconLore.add(" ");

		long money;
		if (!playerData.getConfig().contains(characterSlot + ".Stats.Money"))
			money = 0;
		else
			money = playerData.getConfig().getLong(characterSlot + ".Stats.Money");

		charIconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "MONEY: " + GeneralUtil.getMoneyAsString(money));

		charIconMeta.setLore(charIconLore);
		charIcon.setItemMeta(charIconMeta);
		return charIcon;
	}

	public static void open(Player player) {
		Inventory inv = player.getServer().createInventory(player, 9, "Please select your character.");
		PlayerData playerData = PlayerData.getData(player);

		ItemStack lockedChar = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
		ItemMeta lockedCharMeta = lockedChar.getItemMeta();
		lockedCharMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Character Slot Locked");
		lockedChar.setItemMeta(lockedCharMeta);
		for (int i = 9; i > playerData.getCharacterSlots(); i--)
			inv.setItem(i - 1, lockedChar);

		ItemStack newChar = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta newCharMeta = newChar.getItemMeta();
		newCharMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Empty Character Slot");
		newChar.setItemMeta(newCharMeta);

		for (int i = 0; i < playerData.getCharacterSlots(); i++) {
			if (playerData.getConfig().contains(Integer.toString(i + 1))) {
				inv.setItem(i, getCharacterIcon(player, i + 1));
			} else {
				inv.setItem(i, newChar);
			}
		}

		player.openInventory(inv);
	}
}
