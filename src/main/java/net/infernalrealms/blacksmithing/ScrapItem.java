package net.infernalrealms.blacksmithing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.items.Rarity;

public enum ScrapItem {

	// @formatter:off
	WOODLEATHER("Woodleather Scraps", Material.LEATHER, (byte) 0, 1, new int[] {9, 13, 17, 21, 32}),
	CHAINSTONE("Chainstone Scraps", Material.IRON_FENCE, (byte) 0, 2, new int[] {28, 42, 55, 70, 105}),
	IRON("Iron Scraps", Material.INK_SACK, (byte) 7, 3, new int[] {56, 82, 109, 139, 208}),
	GOLD("Gold Scraps", Material.INK_SACK, (byte) 11, 4, new int[] {90, 131, 173, 221, 331}),
	DIAMOND("Diamond Scraps", Material.PRISMARINE_SHARD, (byte) 0, 5, new int[] {128, 187, 247, 316, 473});
	// @formatter:on

	private String displayName;
	private Material material;
	private byte damage;
	private int tier;
	private int[] values;

	private ScrapItem(String displayName, Material material, byte damage, int tier, int[] values) {
		this.displayName = displayName;
		this.material = material;
		this.damage = damage;
		this.tier = tier;
		this.values = values;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Material getMaterial() {
		return material;
	}

	public byte getDamage() {
		return damage;
	}

	public int getTier() {
		return tier;
	}

	public int[] getValues() {
		return values;
	}

	public int getValueForRarity(Rarity rarity) {
		return getValues()[rarity.getRarityIndex()];
	}

	public ItemStack getItemStack(Rarity rarity, int amount) {
		ItemStack item = new ItemStack(getMaterial(), amount, getDamage());
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(rarity.getColor() + "" + ChatColor.BOLD + getDisplayName());
		List<String> itemLore = new ArrayList<>(2);
		itemLore.add(ChatColor.WHITE + "Tier " + getTier() + " " + rarity.getBaseDisplayName() + " Material");
		itemLore.add(ChatColor.GRAY + "Scrap Value: " + ChatColor.UNDERLINE + getValueForRarity(rarity));
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);

		return item;
	}

	public static ScrapItem fromItem(ItemStack item) {
		Material type = item.getType();
		short dura = item.getDurability();
		for (ScrapItem scrap : values()) {
			if (scrap.getMaterial() == type && scrap.getDamage() == dura) {
				return scrap;
			}
		}
		return null;
	}

	public static ScrapItem fromLevel(int level) {
		if (level < 20) {
			return WOODLEATHER;
		} else if (level < 40) {
			return CHAINSTONE;
		} else if (level < 60) {
			return IRON;
		} else if (level < 80) {
			return GOLD;
		} else {
			return ScrapItem.DIAMOND;
		}
	}

	public static boolean isScrap(ItemStack item) {
		if (item == null) {
			return false;
		}
		for (ScrapItem scrap : values()) {
			ItemStack scrapItem = scrap.getItemStack(Rarity.COMMON, 1);
			if (scrapItem.getType() != item.getType()) {
				continue;
			} else if (!item.getItemMeta().getDisplayName().endsWith(scrap.getDisplayName())) {
				continue;
			}
			return true;
		}
		return false;
	}

}
