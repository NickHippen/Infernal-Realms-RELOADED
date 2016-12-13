package net.infernalrealms.mount;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum MountArmor {

	// @formatter:off
	IRON_HORSE_ARMOR(Material.IRON_BARDING, ChatColor.GREEN + "" + ChatColor.BOLD + "Iron Horse Armor", 10, 5, 5),
	GOLD_HORSE_ARMOR(Material.GOLD_BARDING, ChatColor.BLUE + "" + ChatColor.BOLD + "Gold Horse Armor", 15, 10, 10),
	DIAMOND_HORSE_ARMOR(Material.DIAMOND_BARDING, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Diamond Horse Armor", 20, 15, 15),
	;
	// @formatter:on

	private Material armor;
	private String displayName;
	private int speedPerc;
	private int jumpPerc;
	private int expPerc;

	private MountArmor(Material armor, String displayName, int speedPerc, int jumpPerc, int expPerc) {
		this.armor = armor;
		this.displayName = displayName;
		this.speedPerc = speedPerc;
		this.jumpPerc = jumpPerc;
		this.expPerc = expPerc;
	}

	public ItemStack generateItem() {
		ItemStack item = new ItemStack(armor, 1, (short) 0);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		List<String> itemLore = new ArrayList<>();
		itemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+ " + speedPerc + "% Speed");
		itemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+ " + jumpPerc + "% Jump");
		itemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+ " + expPerc + "% EXP Bonus");
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}
}
