package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.player.Stat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StatGUI {

	@SuppressWarnings("deprecation")
	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getPlayerClass().equalsIgnoreCase("Beginner")) {
			player.sendMessage(ChatColor.RED + "You must select a class before putting in ability points.");
			return;
		}
		Inventory inv = player.getServer().createInventory(player, 9,
				ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Current AP: " + ChatColor.GRAY + ChatColor.BOLD + playerData.getAP());
		String addPointLine = ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Click " + ChatColor.GRAY + ChatColor.ITALIC
				+ "to add a point.";

		inv.setItem(0, GeneralItems.BLANK_BLACK_GLASS_PANE);

		ItemStack strengthIcon = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta strengthIconMeta = strengthIcon.getItemMeta();
		strengthIconMeta
				.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Strength: " + ChatColor.GRAY + playerData.getStrength());
		List<String> strengthIconLore = new ArrayList<>();
		strengthIconLore.add(ChatColor.GRAY + "Increases close-combat damage");
		strengthIconLore.add(ChatColor.GRAY + "by 1 per point.");
		strengthIconLore.add(addPointLine);
		strengthIconMeta.setLore(strengthIconLore);
		strengthIcon.setItemMeta(strengthIconMeta);
		inv.setItem(1, strengthIcon);

		ItemStack dexterityIcon = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta dexterityIconMeta = dexterityIcon.getItemMeta();
		dexterityIconMeta
				.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Dexterity: " + ChatColor.GRAY + playerData.getDexterity());
		List<String> dexterityIconLore = new ArrayList<>();
		dexterityIconLore.add(ChatColor.GRAY + "Increases ranged-combat damage");
		dexterityIconLore.add(ChatColor.GRAY + "by 1 per point.");
		dexterityIconLore.add(addPointLine);
		dexterityIconMeta.setLore(dexterityIconLore);
		dexterityIcon.setItemMeta(dexterityIconMeta);
		inv.setItem(2, dexterityIcon);

		ItemStack intelligenceIcon = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta intelligenceIconMeta = intelligenceIcon.getItemMeta();
		intelligenceIconMeta.setDisplayName(
				ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Intelligence: " + ChatColor.GRAY + playerData.getIntelligence());
		List<String> intelligenceIconLore = new ArrayList<>();
		intelligenceIconLore.add(ChatColor.GRAY + "Increases magic-combat damage");
		intelligenceIconLore.add(ChatColor.GRAY + "by 1 per point.");
		intelligenceIconLore.add(addPointLine);
		intelligenceIconMeta.setLore(intelligenceIconLore);
		intelligenceIcon.setItemMeta(intelligenceIconMeta);
		inv.setItem(3, intelligenceIcon);

		ItemStack agilityIcon = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta agilityIconMeta = agilityIcon.getItemMeta();
		agilityIconMeta
				.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Agility: " + ChatColor.GRAY + playerData.getAgility());
		List<String> agilityIconLore = new ArrayList<>();
		double dodgeCrit = playerData.getTotalDodgeCritBonusForNextLevel();
		agilityIconLore.add(ChatColor.GRAY + "Increases critical hit chance");
		agilityIconLore.add(ChatColor.GRAY + "by " + String.format("%.2f", dodgeCrit * 100D) + "% next point");
		agilityIconLore.add(ChatColor.GRAY + "Current chance: " + String.format("%.2f", playerData.getTotalDodgeCrit() * 100D) + "%");
		agilityIconLore.add(addPointLine);
		agilityIconMeta.setLore(agilityIconLore);
		agilityIcon.setItemMeta(agilityIconMeta);
		inv.setItem(4, agilityIcon);

		ItemStack staminaIcon = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta staminaIconMeta = staminaIcon.getItemMeta();
		staminaIconMeta
				.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Stamina: " + ChatColor.GRAY + playerData.getStamina());
		List<String> staminaIconLore = new ArrayList<>();
		staminaIconLore.add(ChatColor.GRAY + "Increases health regeneration");
		staminaIconLore.add(ChatColor.GRAY + "by 0.4 health every four seconds");
		staminaIconLore.add(ChatColor.GRAY + "per point.");
		staminaIconLore.add(addPointLine);
		staminaIconMeta.setLore(staminaIconLore);
		staminaIcon.setItemMeta(staminaIconMeta);
		inv.setItem(5, staminaIcon);

		ItemStack spiritIcon = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta spiritIconMeta = spiritIcon.getItemMeta();
		spiritIconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Spirit: " + ChatColor.GRAY + playerData.getSpirit());
		List<String> spiritIconLore = new ArrayList<>();
		spiritIconLore.add(ChatColor.GRAY + "Increases mana regeneration");
		spiritIconLore.add(ChatColor.GRAY + "by 0.25 mana every three seconds");
		spiritIconLore.add(ChatColor.GRAY + "per point.");
		spiritIconLore.add(addPointLine);
		spiritIconMeta.setLore(spiritIconLore);
		spiritIcon.setItemMeta(spiritIconMeta);
		inv.setItem(6, spiritIcon);

		ItemStack charStats = new ItemStack(175, 1, (short) 0);
		ItemMeta charStatsMeta = charStats.getItemMeta();
		charStatsMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Character Stats:");
		List<String> charStatsLore = new ArrayList<>();
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "HEALTH: " + ChatColor.WHITE + playerData.getTotalHealth()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.HEALTH) + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "MANA: " + ChatColor.WHITE + playerData.getTotalMaxMana() + ChatColor.GREEN
				+ " (+" + playerData.getEquipStat("Mana") + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "STRENGTH: " + ChatColor.WHITE + playerData.getTotalStrength()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.STRENGTH) + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "DEXTERITY: " + ChatColor.WHITE + playerData.getTotalDexterity()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.DEXTERITY) + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "INTELLIGENCE: " + ChatColor.WHITE + playerData.getTotalIntelligence()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.INTELLIGENCE) + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "AGILITY: " + ChatColor.WHITE + playerData.getTotalAgility()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.AGILITY) + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "STAMINA: " + ChatColor.WHITE + playerData.getTotalStamina()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.STAMINA) + ")");
		charStatsLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "SPIRIT: " + ChatColor.WHITE + playerData.getTotalSpirit()
				+ ChatColor.GREEN + " (+" + playerData.getEquipStat(Stat.SPIRIT) + ")");
		charStatsLore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "GEARSCORE: " + ChatColor.WHITE + "" + ChatColor.UNDERLINE + playerData.getGearscore());
		charStatsMeta.setLore(charStatsLore);
		charStats.setItemMeta(charStatsMeta);
		inv.setItem(7, charStats);

		inv.setItem(8, GeneralItems.BLANK_BLACK_GLASS_PANE);

		player.openInventory(inv);
	}
}
