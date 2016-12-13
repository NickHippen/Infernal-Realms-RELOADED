package net.infernalrealms.gui;

import java.util.Arrays;

import net.infernalrealms.items.ItemEtherealBankerCoupon;
import net.infernalrealms.items.ItemEtherealMerchantCoupon;
import net.infernalrealms.items.Pouch;
import net.infernalrealms.mount.Feed;
import net.infernalrealms.mount.MountArmor;
import net.infernalrealms.mount.Size;
import net.infernalrealms.player.PlayerData;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DonationShopGUI implements Listener {

	private static String TITLE = ChatColor.AQUA + "" + ChatColor.BOLD + "Current I.P: " + ChatColor.DARK_GRAY + ChatColor.BOLD;

	private static int[] COSTS = new int[] { 439, 999, 349, 899, 25, 25, 349, 299, 49, 149, 249, 19, 89, 169, 499, 599, 699, 299, 399, 299,
			499, 499 };

	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		Inventory inv = player.getServer().createInventory(player, 36, TITLE + playerData.getPremiumMoney());

		ItemStack item0 = new ItemStack(Material.GOLD_SWORD, 1, (short) 0);
		ItemMeta item0Meta = item0.getItemMeta();
		item0Meta.setDisplayName("§b§l24 Hour Drop Increase");
		item0Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7Increase the chance of an item",
				"§7dropping as well as money by 100%", "§7for 24 hours!", "§7§lPRICE: §b439 §lI.P"));
		item0.setItemMeta(item0Meta);
		inv.setItem(0, item0);
		ItemStack item1 = new ItemStack(Material.GOLD_SWORD, 1, (short) 0);
		ItemMeta item1Meta = item1.getItemMeta();
		item1Meta.setDisplayName("§b§l3 Day Drop Increase");
		item1Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7Increase the chance of an item",
				"§7dropping as well as money by 100%", "§7for 24 hours!", "§7§lPRICE: §b999 §lI.P"));
		item1.setItemMeta(item1Meta);
		inv.setItem(1, item1);
		ItemStack item2 = new ItemStack(Material.BOOK, 1, (short) 0);
		ItemMeta item2Meta = item2.getItemMeta();
		item2Meta.setDisplayName("§b§l24 Hour Profession EXP Increase");
		item2Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7Increase the amount of EXP gained by all",
				"§7professions by 25% for 24 hours!", "§7§o(includes mounts and pets)", "§7§lPRICE: §b349 §lI.P"));
		item2.setItemMeta(item2Meta);
		inv.setItem(2, item2);
		ItemStack item3 = new ItemStack(Material.BOOK, 1, (short) 0);
		ItemMeta item3Meta = item3.getItemMeta();
		item3Meta.setDisplayName("§b§l3 Day Profession EXP Increase");
		item3Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7Increase the amount of EXP gained by all",
				"§7professions by 25% for 3 days!", "§7§o(includes mounts and pets)", "§7§lPRICE: §b899 §lI.P"));
		item3.setItemMeta(item3Meta);
		inv.setItem(3, item3);
		ItemStack item4 = new ItemStack(Material.PAPER, 1, (short) 0);
		ItemMeta item4Meta = item4.getItemMeta();
		item4Meta.setDisplayName("§b§lEthereal Merchant Coupon");
		item4Meta.setLore(Arrays.asList("§7§oTradeable", "§7Summons the Shopkeeper menu any-", "§7where in the world. (consumed on use)",
				"§7§lPRICE: §b25 §lI.P"));
		item4.setItemMeta(item4Meta);
		inv.setItem(4, item4);
		ItemStack item5 = new ItemStack(Material.PAPER, 1, (short) 0);
		ItemMeta item5Meta = item5.getItemMeta();
		item5Meta.setDisplayName("§b§lEthereal Banker Coupon");
		item5Meta.setLore(Arrays.asList("§7§oTradeable", "§7Summons your bank anywhere", "§7in the world. (consumed on use)",
				"§7§lPRICE: §b25 §lI.P"));
		item5.setItemMeta(item5Meta);
		inv.setItem(5, item5);
		ItemStack item6 = new ItemStack(Material.INK_SACK, 1, (short) 0);
		ItemMeta item6Meta = item6.getItemMeta();
		item6Meta.setDisplayName("§6§lPortable Hole");
		item6Meta.setLore(Arrays.asList("§7Untradeable", "§745 Slot Bag", "§7§lPRICE: §b349 §lI.P"));
		item6.setItemMeta(item6Meta);
		inv.setItem(6, item6);
		ItemStack item7 = new ItemStack(Material.MAP, 1, (short) 0);
		ItemMeta item7Meta = item7.getItemMeta();
		item7Meta.setDisplayName("§b§lBank Expansion Coupon");
		item7Meta.setLore(Arrays.asList("§7§oApplied upon purchase", "§7Increases the amount of slots", "§7in your bank by nine.",
				"§7§lPRICE: §b299 §lI.P"));
		item7.setItemMeta(item7Meta);
		inv.setItem(7, item7);
		ItemStack item8 = new ItemStack(Material.TRIPWIRE_HOOK, 1, (short) 0);
		ItemMeta item8Meta = item8.getItemMeta();
		item8Meta.setDisplayName("§a§lUncommon Chest Key");
		item8Meta.setLore(
				Arrays.asList("§7§oTradeable", "§7Used to open an Uncommon", "§7Mystery chest of any level.", "§7§lPRICE: §b49 §lI.P"));
		item8.setItemMeta(item8Meta);
		inv.setItem(8, item8);
		ItemStack item9 = new ItemStack(Material.TRIPWIRE_HOOK, 1, (short) 0);
		ItemMeta item9Meta = item9.getItemMeta();
		item9Meta.setDisplayName("§9§lRare Chest Key");
		item9Meta.setLore(
				Arrays.asList("§7§oTradeable", "§7Used to open a Rare", "§7Mystery chest of any level.", "§7§lPRICE: §b149 §lI.P"));
		item9.setItemMeta(item9Meta);
		inv.setItem(9, item9);
		ItemStack item10 = new ItemStack(Material.TRIPWIRE_HOOK, 1, (short) 0);
		ItemMeta item10Meta = item10.getItemMeta();
		item10Meta.setDisplayName("§5§lEpic Chest Key");
		item10Meta.setLore(
				Arrays.asList("§7§oTradeable", "§7Used to open a Epic", "§7Mystery chest of any level.", "§7§lPRICE: §b249 §lI.P"));
		item10.setItemMeta(item10Meta);
		inv.setItem(10, item10);
		ItemStack item11 = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta item11Meta = item11.getItemMeta();
		item11Meta.setDisplayName("§a§lSmall Steroids");
		item11Meta.setLore(Arrays.asList("§7§oTradeable", "§7Instantly feeds your horse 1", "§7unit of feed (no hunger required).",
				"§7§lPRICE: §b19 §lI.P"));
		item11.setItemMeta(item11Meta);
		inv.setItem(11, item11);
		ItemStack item12 = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta item12Meta = item12.getItemMeta();
		item12Meta.setDisplayName("§9§lMedium Steroids");
		item12Meta.setLore(Arrays.asList("§7§oTradeable", "§7Instantly feeds your horse 5", "§7unit of feed (no hunger required).",
				"§7§lPRICE: §b89§l I.P"));
		item12.setItemMeta(item12Meta);
		inv.setItem(12, item12);
		ItemStack item13 = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta item13Meta = item13.getItemMeta();
		item13Meta.setDisplayName("§5§lLarge Steroids");
		item13Meta.setLore(Arrays.asList("§7§oTradeable", "§7Instantly feeds your horse 10", "§7unit of feed (no hunger required).",
				"§7§lPRICE: §b169§l I.P"));
		item13.setItemMeta(item13Meta);
		inv.setItem(13, item13);
		ItemStack item14 = new ItemStack(Material.IRON_BARDING, 1, (short) 0);
		ItemMeta item14Meta = item14.getItemMeta();
		item14Meta.setDisplayName("§a§lIron Horse Armor");
		item14Meta.setLore(Arrays.asList("§7§oTradeable", "§f+ 10% Speed", "§f+ 5% Jump", "§f+ 5% EXP Bonus", "§7§lPRICE: §b499 §lI.P"));
		item14.setItemMeta(item14Meta);
		inv.setItem(14, item14);
		ItemStack item15 = new ItemStack(Material.GOLD_BARDING, 1, (short) 0);
		ItemMeta item15Meta = item15.getItemMeta();
		item15Meta.setDisplayName("§9§lGold Horse Armor");
		item15Meta.setLore(Arrays.asList("§7§oTradeable", "§f+ 15% Speed", "§f+ 10% Jump", "§f+ 10% EXP Bonus", "§7§lPRICE: §b599 §lI.P"));
		item15.setItemMeta(item15Meta);
		inv.setItem(15, item15);
		ItemStack item16 = new ItemStack(Material.DIAMOND_BARDING, 1, (short) 0);
		ItemMeta item16Meta = item16.getItemMeta();
		item16Meta.setDisplayName("§5§lDiamond Horse Armor");
		item16Meta.setLore(Arrays.asList("§7§oTradeable", "§f+ 20% Speed", "§f+ 15% Jump", "§f+ 15% EXP Bonus", "§7§lPRICE: §b699 §lI.P"));
		item16.setItemMeta(item16Meta);
		inv.setItem(16, item16);
		ItemStack item17 = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
		ItemMeta item17Meta = item17.getItemMeta();
		item17Meta.setDisplayName("§5§lZombie Horse");
		item17Meta.setLore(
				Arrays.asList("§7§oApplied on purchase", "§7A zombie skin for your", "§7horse (cosmetic only).", "§7§lPRICE: §b299 §lI.P"));
		item17.setItemMeta(item17Meta);
		inv.setItem(17, item17);
		ItemStack item18 = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
		ItemMeta item18Meta = item18.getItemMeta();
		item18Meta.setDisplayName("§5§lSkeleton Horse");
		item18Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7A skeleton skin for your", "§7horse (cosmetic only).",
				"§7§lPRICE: §b399 §lI.P"));
		item18.setItemMeta(item18Meta);
		inv.setItem(18, item18);
		ItemStack item19 = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
		ItemMeta item19Meta = item19.getItemMeta();
		item19Meta.setDisplayName("§5§lMule");
		item19Meta.setLore(
				Arrays.asList("§7§oApplied on purchase", "§7A mule skin for your", "§7mount (cosmetic only).", "§7§lPRICE: §b299 §lI.P"));
		item19.setItemMeta(item19Meta);
		inv.setItem(19, item19);
		ItemStack item20 = new ItemStack(Material.LEATHER, 1, (short) 0);
		ItemMeta item20Meta = item20.getItemMeta();
		item20Meta.setDisplayName("§5§lTiny Size");
		item20Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7Make your horse tiny and", "§7cute! (cosmetic change)",
				"§7§lPRICE: §b499 §lI.P"));
		item20.setItemMeta(item20Meta);
		inv.setItem(20, item20);
		ItemStack item21 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta item21Meta = item21.getItemMeta();
		item21Meta.setDisplayName("§b§lNew Character Slot");
		item21Meta.setLore(Arrays.asList("§7§oApplied on purchase", "§7Unlocks a new character slot.", "§7§lPRICE: §b499 §lI.P"));
		item21.setItemMeta(item21Meta);
		inv.setItem(21, item21);
		ItemStack item34 = new ItemStack(Material.EXP_BOTTLE, 1, (short) 0);
		ItemMeta item34Meta = item34.getItemMeta();
		item34Meta.setDisplayName("§b§lParticle Effect Manager");
		item34Meta.setLore(Arrays.asList("§7Manage and equip particles here!"));
		item34.setItemMeta(item34Meta);
		inv.setItem(34, item34);
		ItemStack item35 = new ItemStack(Material.EXP_BOTTLE, 1, (short) 0);
		ItemMeta item35Meta = item35.getItemMeta();
		item35Meta.setDisplayName("§b§lParticle Effect Shop");
		item35Meta.setLore(Arrays.asList("§7Browse and buy particle effects here!"));
		item35.setItemMeta(item35Meta);
		inv.setItem(35, item35);

		player.openInventory(inv);
	}

	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		if (!inv.getTitle().contains(TITLE) || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if (event.getRawSlot() >= 0 && event.getRawSlot() < COSTS.length) { // Purchasing something
			PlayerData playerData = PlayerData.getData(player);
			int cost = COSTS[event.getRawSlot()];
			if (playerData.getPremiumMoney() < cost) {
				player.sendMessage(ChatColor.RED + "You do not have enough I.P. to purchase this.");
				return;
			}
			purchase: {
				switch (event.getRawSlot()) {
				case 0:
					playerData.addDoubleDrop(1440);
					break;
				case 1:
					playerData.addDoubleDrop(1440 * 3);
					break;
				case 2:
					playerData.addDoubleProfessionExp(1440);
					break;
				case 3:
					playerData.addDoubleProfessionExp(1440 * 3);
					break;
				case 4:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(ItemEtherealMerchantCoupon.generateItem());
					break;
				case 5:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(ItemEtherealBankerCoupon.generateItem());
					break;
				case 6:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(Pouch.PORTABLE_HOLE.generateItemStack());
					break;
				case 7:
					// Coming soon
					player.sendMessage(ChatColor.RED + "Currently unavailable.");
					break purchase;
				case 8:
					// Coming soon
					player.sendMessage(ChatColor.RED + "Currently unavailable.");
					break purchase;
				case 9:
					// Coming soon
					player.sendMessage(ChatColor.RED + "Currently unavailable.");
					break purchase;
				case 10:
					// Coming soon
					player.sendMessage(ChatColor.RED + "Currently unavailable.");
					break purchase;
				case 11:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(Feed.STEROID_SMALL.generateItem());
					break;
				case 12:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(Feed.STEROID_MEDIUM.generateItem());
					break;
				case 13:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(Feed.STEROID_LARGE.generateItem());
					break;
				case 14:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(MountArmor.IRON_HORSE_ARMOR.generateItem());
					break;
				case 15:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(MountArmor.GOLD_HORSE_ARMOR.generateItem());
					break;
				case 16:
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
						break purchase;
					}
					player.getInventory().addItem(MountArmor.DIAMOND_HORSE_ARMOR.generateItem());
					break;
				case 17:
					playerData.addUnlockedMountVariant(Variant.UNDEAD_HORSE);
					break;
				case 18:
					playerData.addUnlockedMountVariant(Variant.SKELETON_HORSE);
					break;
				case 19:
					playerData.addUnlockedMountVariant(Variant.DONKEY);
					break;
				case 20:
					playerData.addUnlockedMountSize(Size.TINY.toString());
					break;
				case 21:
					if (playerData.getCharacterSlots() >= 9) {
						player.sendMessage(ChatColor.RED + "You can not unlock any more character slots.");
						break purchase;
					}
					playerData.modifyCharacterSlots(1);
					break;
				}
				playerData.modifyPremiumMoney(-cost);
				player.sendMessage(ChatColor.GREEN + "Purchase successful!");
				open(player);
			}
		} else if (event.getRawSlot() == 34) {
			ParticleManagerGUI.open(player);
		} else if (event.getRawSlot() == 35) {
			ParticleShopGUI.open(player);
		}
	}
}
