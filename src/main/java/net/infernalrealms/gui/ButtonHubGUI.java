package net.infernalrealms.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.homesteads.HomesteadInstance;
import net.infernalrealms.homesteads.HomesteadUtils;
import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

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

public class ButtonHubGUI implements Listener {

	public static final String TITLE = "Button Hub";

	public static void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 9, TITLE);

		ItemStack stats = new ItemStack(Material.SIGN, 1, (short) 0);
		ItemMeta statsMeta = stats.getItemMeta();
		statsMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Stats");
		List<String> statsLore = new ArrayList<>();
		statsLore.add(ChatColor.GRAY + "Manage and view");
		statsLore.add(ChatColor.GRAY + "ability points here.");
		statsMeta.setLore(statsLore);
		stats.setItemMeta(statsMeta);
		inv.setItem(0, stats);

		ItemStack skills = new ItemStack(Material.FIREBALL, 1, (short) 0);
		ItemMeta skillsMeta = skills.getItemMeta();
		skillsMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Skills");
		List<String> skillsLore = new ArrayList<>();
		skillsLore.add(ChatColor.GRAY + "Manage and add");
		skillsLore.add(ChatColor.GRAY + "points to skills.");
		skillsMeta.setLore(skillsLore);
		skills.setItemMeta(skillsMeta);
		inv.setItem(1, skills);

		ItemStack quests = new ItemStack(Material.BOOK, 1, (short) 0);
		ItemMeta questsMeta = quests.getItemMeta();
		questsMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Quest Log");
		List<String> questsLore = new ArrayList<>();
		questsLore.add(ChatColor.GRAY + "Look at quest");
		questsLore.add(ChatColor.GRAY + "information here.");
		questsMeta.setLore(questsLore);
		quests.setItemMeta(questsMeta);
		inv.setItem(2, quests);

		ItemStack homestead = new ItemStack(Material.DARK_OAK_DOOR, 1, (short) 0);
		ItemMeta homesteadMeta = homestead.getItemMeta();
		homesteadMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Enter Homestead");
		List<String> homesteadLore = new ArrayList<>();
		homesteadLore.add(ChatColor.GRAY + "Enter your homestead");
		homesteadLore.add(ChatColor.GRAY + "world.");
		homesteadMeta.setLore(homesteadLore);
		homestead.setItemMeta(homesteadMeta);
		inv.setItem(3, homestead);

		ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE, 0, (short) 0);
		ItemMeta pickaxeMeta = pickaxe.getItemMeta();
		pickaxeMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Pickaxe Menu");
		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(ChatColor.GRAY + "Coming soon!");
		pickaxeMeta.setLore(pickaxeLore);
		pickaxe.setItemMeta(pickaxeMeta);
		inv.setItem(4, pickaxe);

		ItemStack mount = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta mountMeta = mount.getItemMeta();
		mountMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Mount Management");
		List<String> mountLore = new ArrayList<>();
		mountLore.add(ChatColor.GRAY + "Manage your mount's");
		mountLore.add(ChatColor.GRAY + "skills and looks.");
		mountMeta.setLore(mountLore);
		mount.setItemMeta(mountMeta);
		inv.setItem(5, mount);

		ItemStack donations = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 0);
		ItemMeta donationsMeta = donations.getItemMeta();
		donationsMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Donation Menu");
		List<String> donationsLore = new ArrayList<>();
		donationsLore.add(ChatColor.GRAY + "Support the server");
		donationsLore.add(ChatColor.GRAY + "by using I.P. to buy");
		donationsLore.add(ChatColor.GRAY + "cool items!");
		donationsMeta.setLore(donationsLore);
		donations.setItemMeta(donationsMeta);
		donations = GeneralUtil.addGlow(donations);
		inv.setItem(6, donations);

		inv.setItem(7, GeneralItems.BLANK_BLACK_GLASS_PANE);

		ItemStack logout = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta logoutMeta = logout.getItemMeta();
		logoutMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Logout");
		List<String> logoutLore = new ArrayList<>();
		logoutLore.add(ChatColor.GRAY + "Return to the lobby and");
		logoutLore.add(ChatColor.GRAY + "character selection.");
		logoutMeta.setLore(logoutLore);
		logout.setItemMeta(logoutMeta);
		inv.setItem(8, logout);

		player.openInventory(inv);
	}

	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		if (!event.getInventory().getTitle().equals(TITLE)) {
			return;
		}
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		switch (event.getRawSlot()) {
		case 0:
			StatGUI.open(player);
			break;
		case 1:
			SkillsGUI.open(player);
			break;
		case 2:
			QuestLogGUI.openMenu(player, 1);
			break;
		case 3:
			if (HomesteadUtils.isInOwnHomestead(player)) {
				player.teleport(InfernalRealms.MAIN_WORLD.getSpawnLocation());
			} else {
				try {
					HomesteadInstance.loadAndVisit(player);
				} catch (IOException e) {
					player.sendMessage(ChatColor.RED + "Error visiting homestead.");
					e.printStackTrace();
				}
			}
			break;
		case 4:
			player.sendMessage(ChatColor.RED + "Coming soon!");
			break;
		case 5:
			MountGUI.open(player);
			break;
		case 6:
			DonationShopGUI.open(player);
			break;
		case 8:
			PlayerData.getData(player).logout();
			break;
		}
	}
}
