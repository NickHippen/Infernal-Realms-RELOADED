package net.infernalrealms.gui;

import java.util.Arrays;
import java.util.List;

import net.infernalrealms.mount.Feed;
import net.infernalrealms.mount.Size;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MountShopGUI {

	private static final int[] PREMIUM_SLOTS = new int[] { 9, 27, 28, 29 };
	private static final Object[] PREMIUM_OPTIONS = new Object[] { Size.TINY, Variant.UNDEAD_HORSE, Variant.SKELETON_HORSE,
			Variant.DONKEY };
	private static final long[] PREMIUM_PRICES = new long[] { 200L, 250L, 300L, 400L };

	private static final int[] NORMAL_SLOTS = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 30, 31, 32, 33, 34, 35 };
	private static final Object[] NORMAL_OPTIONS = new Object[] { Style.BLACK_DOTS, Style.WHITE, Style.WHITE_DOTS, Style.WHITEFIELD,
			Feed.NORMAL_SMALL, Feed.NORMAL_MEDIUM, Feed.NORMAL_LARGE, Feed.NORMAL_BUNDLE, Color.CHESTNUT, Color.CREAMY, Color.DARK_BROWN,
			Color.GRAY, Color.BLACK, Color.WHITE };
	private static final long[] NORMAL_PRICES = new long[] { GeneralUtil.convertCoinsToMoney(5, 0, 0),
			GeneralUtil.convertCoinsToMoney(5, 0, 0), GeneralUtil.convertCoinsToMoney(5, 0, 0), GeneralUtil.convertCoinsToMoney(5, 0, 0),
			GeneralUtil.convertCoinsToMoney(0, 0, 50), GeneralUtil.convertCoinsToMoney(0, 2, 50), GeneralUtil.convertCoinsToMoney(0, 5, 0),
			GeneralUtil.convertCoinsToMoney(0, 10, 0), GeneralUtil.convertCoinsToMoney(1, 0, 0), GeneralUtil.convertCoinsToMoney(5, 0, 0),
			GeneralUtil.convertCoinsToMoney(10, 0, 0), GeneralUtil.convertCoinsToMoney(20, 0, 0),
			GeneralUtil.convertCoinsToMoney(100, 0, 0), GeneralUtil.convertCoinsToMoney(100, 0, 0) };

	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		Inventory inv = player.getServer().createInventory(player, 36, "Mount Shop");

		ItemStack purchaseButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta purchaseButtonMeta = purchaseButton.getItemMeta();
		purchaseButtonMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Purchase");
		purchaseButton.setItemMeta(purchaseButtonMeta);

		ItemStack purchaseIPButton = new ItemStack(Material.INK_SACK, 1, (short) 12);
		ItemMeta purchaseIPButtonMeta = purchaseIPButton.getItemMeta();
		purchaseIPButtonMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + ChatColor.BOLD + "Purchase with I.P");
		purchaseIPButton.setItemMeta(purchaseIPButtonMeta);

		ItemStack purchasedButton = new ItemStack(Material.IRON_FENCE, 1, (short) 0);
		ItemMeta purchasedButtonMeta = purchasedButton.getItemMeta();
		purchasedButtonMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + ChatColor.BOLD + "Purchased");
		purchasedButton.setItemMeta(purchasedButtonMeta);

		ItemStack tinySizeShop = new ItemStack(Material.LEATHER, 1, (short) 0);
		ItemMeta tinySizeShopMeta = tinySizeShop.getItemMeta();
		tinySizeShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Tiny Size");
		tinySizeShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Make your horse tiny and",
				ChatColor.RESET + "" + ChatColor.GRAY + "cute! (cosmetic change)", ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: "
						+ ChatColor.RESET + "" + ChatColor.AQUA + "200 " + ChatColor.BOLD + "I.P"));
		tinySizeShop.setItemMeta(tinySizeShopMeta);
		inv.setItem(0, tinySizeShop);

		ItemStack blackDotsShop = new ItemStack(Material.MONSTER_EGG, 1, (short) 51);
		ItemMeta blackDotsShopMeta = blackDotsShop.getItemMeta();
		blackDotsShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Black Dots");
		blackDotsShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Give your mount a ",
				ChatColor.RESET + "" + ChatColor.GRAY + "black dotted style.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "5 ●"));
		blackDotsShop.setItemMeta(blackDotsShopMeta);
		inv.setItem(1, blackDotsShop);

		ItemStack whiteStyleShop = new ItemStack(Material.MONSTER_EGG, 1, (short) 56);
		ItemMeta whiteStyleShopMeta = whiteStyleShop.getItemMeta();
		whiteStyleShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "White Style");
		whiteStyleShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Give your mount white ",
				ChatColor.RESET + "" + ChatColor.GRAY + "socks and stripes.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "5 ●"));
		whiteStyleShop.setItemMeta(whiteStyleShopMeta);
		inv.setItem(2, whiteStyleShop);

		ItemStack whiteDotsShop = new ItemStack(Material.MONSTER_EGG, 1, (short) 95);
		ItemMeta whiteDotsShopMeta = whiteDotsShop.getItemMeta();
		whiteDotsShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "White Dots");
		whiteDotsShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Give your mount a",
				ChatColor.RESET + "" + ChatColor.GRAY + "white dotted style.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "5 ●"));
		whiteDotsShop.setItemMeta(whiteDotsShopMeta);
		inv.setItem(3, whiteDotsShop);

		ItemStack whitefieldShop = new ItemStack(Material.MONSTER_EGG, 1, (short) 91);
		ItemMeta whitefieldShopMeta = whitefieldShop.getItemMeta();
		whitefieldShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Whitefield");
		whitefieldShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Give your mount",
				ChatColor.RESET + "" + ChatColor.GRAY + "milky splotches.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "5 ●"));
		whitefieldShop.setItemMeta(whitefieldShopMeta);
		inv.setItem(4, whitefieldShop);

		ItemStack smallHorseFeed = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta smallHorseFeedMeta = smallHorseFeed.getItemMeta();
		smallHorseFeedMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Small Horse Feed");
		smallHorseFeedMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Contains 1 unit of feed.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.GOLD + "50 ●"));
		smallHorseFeed.setItemMeta(smallHorseFeedMeta);
		inv.setItem(5, smallHorseFeed);

		ItemStack mediumHorseFeed = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta mediumHorseFeedMeta = mediumHorseFeed.getItemMeta();
		mediumHorseFeedMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Medium Horse Feed");
		mediumHorseFeedMeta.setLore(
				Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Contains 5 unit of feed.", ChatColor.GRAY + "" + ChatColor.BOLD
						+ "PRICE: " + ChatColor.RESET + "" + ChatColor.GRAY + "2 ●" + ChatColor.RESET + "" + ChatColor.GOLD + " 50 ●"));
		mediumHorseFeed.setItemMeta(mediumHorseFeedMeta);
		inv.setItem(6, mediumHorseFeed);

		ItemStack largeHorseFeed = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta largeHorseFeedMeta = largeHorseFeed.getItemMeta();
		largeHorseFeedMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Large Horse Feed");
		largeHorseFeedMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Contains 10 unit of feed.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 ●"));
		largeHorseFeed.setItemMeta(largeHorseFeedMeta);
		inv.setItem(7, largeHorseFeed);

		ItemStack bundleHorseFeed = new ItemStack(Material.HAY_BLOCK, 1, (short) 0);
		ItemMeta bundleHorseFeedMeta = bundleHorseFeed.getItemMeta();
		bundleHorseFeedMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Bundle of Horse Feed");
		bundleHorseFeedMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Contains 20 unit of feed.",
				ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.GRAY + "10 ●"));
		bundleHorseFeed.setItemMeta(bundleHorseFeedMeta);
		inv.setItem(8, bundleHorseFeed);

		ItemStack zombieSkinShop = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
		ItemMeta zombieSkinShopMeta = zombieSkinShop.getItemMeta();
		zombieSkinShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Zombie Horse");
		zombieSkinShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A zombie skin for your",
				ChatColor.RESET + "" + ChatColor.GRAY + "horse (cosmetic only).", ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: "
						+ ChatColor.RESET + "" + ChatColor.AQUA + "250 " + ChatColor.BOLD + "I.P"));
		zombieSkinShop.setItemMeta(zombieSkinShopMeta);
		inv.setItem(18, zombieSkinShop);

		ItemStack skeletonSkinShop = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
		ItemMeta skeletonSkinShopMeta = skeletonSkinShop.getItemMeta();
		skeletonSkinShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Skeleton Horse");
		skeletonSkinShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A skeleton skin for your",
				ChatColor.RESET + "" + ChatColor.GRAY + "horse (cosmetic only).", ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: "
						+ ChatColor.RESET + "" + ChatColor.AQUA + "300 " + ChatColor.BOLD + "I.P"));
		skeletonSkinShop.setItemMeta(skeletonSkinShopMeta);
		inv.setItem(19, skeletonSkinShop);

		ItemStack muleSkinShop = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
		ItemMeta muleSkinShopMeta = muleSkinShop.getItemMeta();
		muleSkinShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mule/Donkey");
		muleSkinShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A mule/donkey skin for your",
				ChatColor.RESET + "" + ChatColor.GRAY + "horse (cosmetic only).", ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: "
						+ ChatColor.RESET + "" + ChatColor.AQUA + "400 " + ChatColor.BOLD + "I.P"));
		muleSkinShop.setItemMeta(muleSkinShopMeta);
		inv.setItem(20, muleSkinShop);

		ItemStack chestnutShop = new ItemStack(Material.LOG, 1, (short) 0);
		ItemMeta chestnutShopMeta = chestnutShop.getItemMeta();
		chestnutShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.BOLD + "Chestnut");
		chestnutShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.WHITE + "Common Color",
				ChatColor.WHITE + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "1 ●"));
		chestnutShop.setItemMeta(chestnutShopMeta);
		inv.setItem(21, chestnutShop);

		ItemStack creamyShop = new ItemStack(Material.WOOD, 1, (short) 2);
		ItemMeta creamyShopMeta = creamyShop.getItemMeta();
		creamyShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Creamy");
		creamyShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GREEN + "Uncommon Color",
				ChatColor.GREEN + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "5 ●"));
		creamyShop.setItemMeta(creamyShopMeta);
		inv.setItem(22, creamyShop);

		ItemStack darkBrownShop = new ItemStack(Material.WOOL, 1, (short) 12);
		ItemMeta darkBrownShopMeta = darkBrownShop.getItemMeta();
		darkBrownShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + ChatColor.BOLD + "Dark Brown");
		darkBrownShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.BLUE + "Rare Color",
				ChatColor.BLUE + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "10 ●"));
		darkBrownShop.setItemMeta(darkBrownShopMeta);
		inv.setItem(23, darkBrownShop);

		ItemStack grayShop = new ItemStack(Material.WOOL, 1, (short) 7);
		ItemMeta grayShopMeta = grayShop.getItemMeta();
		grayShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Gray");
		grayShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "Epic Color",
				ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "20 ●"));
		grayShop.setItemMeta(grayShopMeta);
		inv.setItem(24, grayShop);

		ItemStack blackShop = new ItemStack(Material.WOOL, 1, (short) 15);
		ItemMeta blackShopMeta = blackShop.getItemMeta();
		blackShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "Black");
		blackShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GOLD + "Legendary Color",
				ChatColor.GOLD + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "100 ●"));
		blackShop.setItemMeta(blackShopMeta);
		inv.setItem(25, blackShop);

		ItemStack whiteShop = new ItemStack(Material.WOOL, 1, (short) 0);
		ItemMeta whiteShopMeta = whiteShop.getItemMeta();
		whiteShopMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "White");
		whiteShopMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GOLD + "Legendary Color",
				ChatColor.GOLD + "" + ChatColor.BOLD + "PRICE: " + ChatColor.RESET + "" + ChatColor.YELLOW + "100 ●"));
		whiteShop.setItemMeta(whiteShopMeta);
		inv.setItem(26, whiteShop);

		List<String> unlockedStyles = playerData.getUnlockedMountStyles();
		List<String> unlockedColors = playerData.getUnlockedMountColors();
		List<String> unlockedSkins = playerData.getUnlockedMountVariants();
		List<String> unlockedSizes = playerData.getUnlockedMountSizes();

		for (int i = 0; i < PREMIUM_SLOTS.length; i++) {
			boolean purchaseable = false;
			if (i == 0 && !unlockedSizes.contains(PREMIUM_OPTIONS[i].toString())) {
				purchaseable = true;
			} else if (!unlockedSkins.contains(PREMIUM_OPTIONS[i].toString())) {
				purchaseable = true;
			}
			if (purchaseable) {
				inv.setItem(PREMIUM_SLOTS[i], purchaseButton);
			} else {
				inv.setItem(PREMIUM_SLOTS[i], purchasedButton);
			}
		}
		for (int i = 0; i < NORMAL_SLOTS.length; i++) {
			boolean purchaseable = false;
			if (i < 4) {
				if (!unlockedStyles.contains(NORMAL_OPTIONS[i].toString())) {
					purchaseable = true;
				}
			} else if (i < 8) { // Feed
				purchaseable = true;
			} else if (!unlockedColors.contains(NORMAL_OPTIONS[i].toString())) {
				purchaseable = true;
			}
			if (purchaseable) {
				inv.setItem(NORMAL_SLOTS[i], purchaseButton);
			} else {
				inv.setItem(NORMAL_SLOTS[i], purchasedButton);
			}
		}

		player.openInventory(inv);
	}

	public static void handleClick(InventoryClickEvent event) {
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		PlayerData playerData = PlayerData.getData(player);
		List<String> unlockedStyles = playerData.getUnlockedMountStyles();
		List<String> unlockedColors = playerData.getUnlockedMountColors();
		List<String> unlockedSkins = playerData.getUnlockedMountVariants();
		List<String> unlockedSizes = playerData.getUnlockedMountSizes();

		int index = Arrays.binarySearch(NORMAL_SLOTS, event.getRawSlot());
		if (index >= 0) { // Clicked on "Normal Slot" Item
			Object mountOption = NORMAL_OPTIONS[index];
			if (index < 4) {
				if (!unlockedStyles.contains(mountOption.toString())) {
					if (playerData.getMoney() < NORMAL_PRICES[index]) {
						player.sendMessage(ChatColor.RED + "No money");
						return;
					}
					// Purchase
					playerData.addUnlockedMountStyle(((Style) mountOption));
					playerData.modifyMoney(-NORMAL_PRICES[index]);
					player.sendMessage(ChatColor.GREEN + "Purchase successful.");
				} else {
					player.sendMessage(ChatColor.RED + "Already owned.");
				}
			} else if (index < 8) { // Feed
				if (playerData.getMoney() < NORMAL_PRICES[index]) {
					player.sendMessage(ChatColor.RED + "No money");
					return;
				}
				// Purchase
				ItemStack feed = ((Feed) mountOption).generateItem();
				GeneralUtil.addItemToInventoryOrDrop(player, feed);
				playerData.modifyMoney(-NORMAL_PRICES[index]);
			} else if (!unlockedColors.contains(mountOption.toString())) {
				if (playerData.getMoney() < NORMAL_PRICES[index]) {
					player.sendMessage(ChatColor.RED + "No money");
					return;
				}
				// Purchase
				playerData.addUnlockedMountColor(((Color) mountOption));
				playerData.modifyMoney(-NORMAL_PRICES[index]);
				player.sendMessage(ChatColor.GREEN + "Purchase successful.");
			} else {
				player.sendMessage(ChatColor.RED + "Already owned.");
			}
		} else {
			index = Arrays.binarySearch(PREMIUM_SLOTS, event.getRawSlot());
			if (index < 0) { // Error?
				return;
			}
			Object mountOption = PREMIUM_OPTIONS[index];
			if (index == 0) { // Size
				if (!unlockedSizes.contains(mountOption.toString())) {
					if (playerData.getPremiumMoney() < PREMIUM_PRICES[index]) {
						player.sendMessage(ChatColor.RED + "No money");
						return;
					}
					// Purchase
					playerData.addUnlockedMountSize(((Size) mountOption).toString());
					playerData.modifyPremiumMoney(-PREMIUM_PRICES[index]);
					player.sendMessage(ChatColor.GREEN + "Purchase successful.");
				} else {
					player.sendMessage(ChatColor.RED + "Already owned.");
				}
			} else if (index < 4) { // Skin
				if (!unlockedSkins.contains(mountOption.toString())) {
					if (playerData.getPremiumMoney() < PREMIUM_PRICES[index]) {
						player.sendMessage(ChatColor.RED + "No money");
						return;
					}
					// Purchase
					playerData.addUnlockedMountVariant(((Variant) mountOption));
					playerData.modifyPremiumMoney(-PREMIUM_PRICES[index]);
					player.sendMessage(ChatColor.GREEN + "Purchase successful.");
				} else {
					player.sendMessage(ChatColor.RED + "Already owned.");
				}
			}
		}
		open(player);
	}
}
