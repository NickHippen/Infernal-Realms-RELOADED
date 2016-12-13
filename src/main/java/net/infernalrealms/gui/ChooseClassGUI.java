package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.Arrays;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChooseClassGUI {

	public static void open(Player player) {
		if (!Quest.checkQuestCompletion(player, HardcodedQuest.QuestName.PATH_TO_ENEN)) {
			return;
		}

		Inventory inv = player.getServer().createInventory(player, 18, "Please select your class.");

		ItemStack blankGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta blankGlassPaneMeta = blankGlassPane.getItemMeta();
		blankGlassPaneMeta.setDisplayName(" ");
		blankGlassPane.setItemMeta(blankGlassPaneMeta);
		for (int l : new int[] { 0, 9, 2, 11, 3, 12, 5, 14, 6, 15, 8, 17 })
			inv.setItem(l, blankGlassPane);

		ItemStack selectButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta selectButtonMeta = selectButton.getItemMeta();
		selectButtonMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + ChatColor.UNDERLINE
				+ "Click here to choose this class.");
		selectButtonMeta.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Please note that your class cannot",
				ChatColor.GRAY + "" + ChatColor.ITALIC + "be changed after you decide."));
		selectButton.setItemMeta(selectButtonMeta);
		for (int l : new int[] { 10, 13, 16 })
			inv.setItem(l, selectButton);

		// Warrior Icon
		ItemStack warriorIcon = new ItemStack(Material.IRON_SWORD, 1, (short) 0);
		ItemMeta warriorIconMetaL = warriorIcon.getItemMeta();
		warriorIconMetaL.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Path of the Warrior");
		// Animated Frames
		String arrowL = "           ->  ";
		String arrowR = "            -> ";
		ArrayList<String> warriorLoreL = new ArrayList<String>();
		warriorLoreL.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Combat Type: " + ChatColor.GRAY + "Close Combat");
		warriorLoreL.add(" ");
		warriorLoreL.add(ChatColor.RESET + "" + ChatColor.GRAY + arrowL + "Crusader (Damage)");
		warriorLoreL.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Paths: " + ChatColor.GRAY + "-");
		warriorLoreL.add(ChatColor.RESET + "" + ChatColor.GRAY + arrowL + "Paladin (Tank)");
		warriorIconMetaL.setLore(warriorLoreL);
		ArrayList<String> warriorLoreR = new ArrayList<>(warriorLoreL);
		warriorLoreR.set(2, ChatColor.RESET + "" + ChatColor.GRAY + arrowR + "Crusader (Damage)");
		warriorLoreR.set(4, ChatColor.RESET + "" + ChatColor.GRAY + arrowR + "Paladin (Tank)");
		ItemMeta warriorIconMetaR = warriorIcon.getItemMeta();
		warriorIconMetaR.setDisplayName(warriorIconMetaL.getDisplayName());
		warriorIconMetaR.setLore(warriorLoreR);
		// Set location/animation
		inv.setItem(1, warriorIcon);
		GeneralUtil.animateFrames(inv, 1, 15L, warriorIconMetaL, warriorIconMetaR);

		// Archer Icon
		ItemStack archerIcon = new ItemStack(Material.BOW, 1, (short) 0);
		ItemMeta archerIconMetaL = archerIcon.getItemMeta();
		archerIconMetaL.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Path of the Archer");
		// Animated Frames
		ArrayList<String> archerLoreL = new ArrayList<String>();
		archerLoreL.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Combat Type: " + ChatColor.GRAY + "Ranged Combat");
		archerLoreL.add(" ");
		archerLoreL.add(ChatColor.RESET + "" + ChatColor.GRAY + arrowL + "Marksman (Damage)");
		archerLoreL.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Paths: " + ChatColor.GRAY + "-");
		archerLoreL.add(ChatColor.RESET + "" + ChatColor.GRAY + arrowL + "Rogue (Speed)");
		archerIconMetaL.setLore(archerLoreL);
		ArrayList<String> archerLoreR = new ArrayList<>(archerLoreL);
		archerLoreR.set(2, ChatColor.RESET + "" + ChatColor.GRAY + arrowR + "Marksman (Damage)");
		archerLoreR.set(4, ChatColor.RESET + "" + ChatColor.GRAY + arrowR + "Rogue (Speed)");
		ItemMeta archerIconMetaR = archerIcon.getItemMeta();
		archerIconMetaR.setDisplayName(archerIconMetaL.getDisplayName());
		archerIconMetaR.setLore(archerLoreR);
		// Set location/animation
		inv.setItem(4, archerIcon);
		GeneralUtil.animateFrames(inv, 4, 15L, archerIconMetaL, archerIconMetaR);

		// Archer Icon
		ItemStack magicianIcon = new ItemStack(Material.BLAZE_ROD, 1, (short) 0);
		ItemMeta magicianIconMetaL = magicianIcon.getItemMeta();
		magicianIconMetaL.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Path of the Magician");
		// Animated Frames
		ArrayList<String> magicianLoreL = new ArrayList<String>();
		magicianLoreL.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Combat Type: " + ChatColor.GRAY
				+ "Magical Combat");
		magicianLoreL.add(" ");
		magicianLoreL.add(ChatColor.RESET + "" + ChatColor.GRAY + arrowL + "Sorcerer (Damage)");
		magicianLoreL.add(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Paths: " + ChatColor.GRAY + "-");
		magicianLoreL.add(ChatColor.RESET + "" + ChatColor.GRAY + arrowL + "Cleric (Support)");
		magicianIconMetaL.setLore(magicianLoreL);
		ArrayList<String> magicianLoreR = new ArrayList<>(magicianLoreL);
		magicianLoreR.set(2, ChatColor.RESET + "" + ChatColor.GRAY + arrowR + "Sorcerer (Damage)");
		magicianLoreR.set(4, ChatColor.RESET + "" + ChatColor.GRAY + arrowR + "Cleric (Support)");
		ItemMeta magicianIconMetaR = magicianIcon.getItemMeta();
		magicianIconMetaR.setDisplayName(magicianIconMetaL.getDisplayName());
		magicianIconMetaR.setLore(magicianLoreR);
		// Set location/animation
		inv.setItem(7, magicianIcon);
		GeneralUtil.animateFrames(inv, 7, 15L, magicianIconMetaL, magicianIconMetaR);

		player.openInventory(inv);
	}
}
