package net.infernalrealms.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class GeneralItems {

	private GeneralItems() {
	}

	public static final ItemStack EMPTY_POUCH_SLOT;
	public static final ItemStack BLANK_BLACK_GLASS_PANE;

	static {
		// EMPTY_POUCH_SLOT
		ItemStack emptyPouchSlot = new ItemStack(Material.IRON_FENCE, 1, (short) 0);
		ItemMeta emptyPouchSlotMeta = emptyPouchSlot.getItemMeta();
		emptyPouchSlotMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.UNDERLINE + "Empty Pouch Slot");
		List<String> emptyPouchSlotLore = Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drag and drop a pouch", ChatColor.GRAY
				+ "" + ChatColor.ITALIC + "over this slot to equip it.");
		emptyPouchSlotMeta.setLore(emptyPouchSlotLore);
		emptyPouchSlot.setItemMeta(emptyPouchSlotMeta);
		EMPTY_POUCH_SLOT = emptyPouchSlot;

		// BLANK_BLACK_GLASS_PANE
		ItemStack blankGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta blankGlassPaneMeta = blankGlassPane.getItemMeta();
		blankGlassPaneMeta.setDisplayName(" ");
		blankGlassPane.setItemMeta(blankGlassPaneMeta);
		BLANK_BLACK_GLASS_PANE = blankGlassPane;
	}

}
