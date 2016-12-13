package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.util.InfernalStrings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Pouch {

	// @formatter:off
	SMALL_POUCH(ChatColor.WHITE, "Small Pouch", 9, true),
	TORN_BAG(ChatColor.WHITE, "Torn Bag", 9, true),
	PENNY_POUCH(ChatColor.WHITE, "Penny Pouch", 9, true),
	KASHMIR_SUPPLY_BAG(ChatColor.BLUE, "Kashmir Supply Bag", 27, true),
	SILK_BAG(ChatColor.GREEN, "Silk Bag", 18, true),
	BLACK_LEATHER_BAG(ChatColor.GREEN, "Black Leather Bag", 18, true),
	FENORS_POUCH(ChatColor.BLUE, "Fenor's Pouch", 18, false),
	PORTABLE_HOLE(ChatColor.GOLD, "Portable Hole", 45, false),
	DIRE_WOLF_STOMACH(ChatColor.GREEN, "Dire Wolf Stomach", 9, true),
	LEAKY_SPIDER_SAC(ChatColor.GREEN, "Leaky Spider Sac", 9, true),
	ELROMS_PERSONAL_POUCH(ChatColor.BLUE, "Elrom’s Personal Pouch", 18, true),
	SOUL_BAG(ChatColor.BLUE, "Soul Bag", 27, true),
	OAK_WOOD_BOX(ChatColor.DARK_PURPLE, "Oak Wood Box", 36, true),
	GOBLIN_COIN_POUCH(ChatColor.GREEN, "Goblin Coin Pouch", 9, true),
	GOBLIN_INGOT_CASE(ChatColor.BLUE, "Goblin Ingot Case", 18, true),
	SELGAUTHIAN_BAG(ChatColor.BLUE, "Selgauthian Bag", 18, true),
	EMPTY_HEART(ChatColor.BLUE, "Empty Heart", 27, true),
	LOST_POUCH(ChatColor.GOLD, "Lost Pouch", 45, true),
	FORSAKEN_SLING(ChatColor.GREEN, "Forsaken Sling", 9, true),
	BLACK_LINEN_POUCH(ChatColor.WHITE, "Black Linen Pouch", 9, true),
	BLACK_CLOTH_POUCH(ChatColor.WHITE, "Black Cloth Pouch", 9, true),
	;
	// @formatter:on

	private ChatColor rarityColor;
	private String displayName;
	private int size;
	private boolean tradeable;

	private Pouch(ChatColor rarityColor, String displayName, int size, boolean tradeable) {
		this.rarityColor = rarityColor;
		this.displayName = displayName;
		this.size = size;
		this.tradeable = tradeable;
	}

	public static Pouch fromDisplayName(String displayName) {
		for (Pouch pouch : values()) {
			if (pouch.getDisplayName().equalsIgnoreCase(displayName)) {
				return pouch;
			}
		}
		return null;
	}

	public static Pouch fromString(String string) {
		for (Pouch pouch : values()) {
			if (pouch.toString().equalsIgnoreCase(string)) {
				return pouch;
			}
		}
		return null;
	}

	public ItemStack generateItemStack() {
		ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 0);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(getRarityColor() + "" + ChatColor.BOLD + getDisplayName());
		List<String> itemLore = new ArrayList<>();
		if (!tradeable) {
			itemLore.add(InfernalStrings.UNTRADEABLE);
		}
		itemLore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "Shift+click to unequip.");
		itemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + getSize() + " Slot Bag");
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}

	public ChatColor getRarityColor() {
		return this.rarityColor;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public int getSize() {
		return this.size;
	}

}