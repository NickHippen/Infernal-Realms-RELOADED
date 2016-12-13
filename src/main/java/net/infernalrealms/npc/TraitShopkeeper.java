package net.infernalrealms.npc;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.items.InfernalItems;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalEffects;

public class TraitShopkeeper extends Trait {

	public static final String CHOICES_SHOP_TITLE = "Shopkeeper";
	public static final String SELL_SHOP_TITLE = ChatColor.MAGIC + "" + ChatColor.RESET + "  ";
	public static final String BUY_SHOP_TITLE = ChatColor.MAGIC + "" + ChatColor.RESET + " ";

	private String shopName;

	public TraitShopkeeper() {
		super("shopkeeper");
	}

	//Here you should load up any values you have previously saved. 
	//This does NOT get called when applying the trait for the first time, only loading onto an existing npc at server start.
	//This is called AFTER onAttach so you can load defaults in onAttach and they will be overridden here.
	//This is called BEFORE onSpawn so do not try to access npc.getBukkitEntity(). It will be null.
	public void load(DataKey key) {
		this.shopName = key.getString("shopName");
	}

	//Save settings for this NPC. These values will be added to the citizens saves.yml under this NPC.
	public void save(DataKey key) {
		key.setRaw("shopName", this.shopName);
	}

	@EventHandler
	public void onRightClick(NPCRightClickEvent event) {
		if (event.getNPC() == this.getNPC()) {
			Player player = event.getClicker();
			openChoices(player, shopName);
		}
	}

	//Run code when your trait is attached to a NPC. 
	//This is called BEFORE onSpawn so do not try to access npc.getBukkitEntity(). It will be null.
	@Override
	public void onAttach() {}

	// Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getBukkitEntity() is still valid.
	@Override
	public void onDespawn() {}

	//Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be null until this method is called.
	//This is called AFTER onAttach and AFTER Load when the server is started.
	@Override
	public void onSpawn() {}

	//run code when the NPC is removed. Use this to tear down any repeating tasks.
	@Override
	public void onRemove() {}

	public void setShop(String shopName) {
		this.shopName = shopName;
	}

	public static void openChoices(Player player, String shopName) {
		Inventory inv = player.getServer().createInventory(player, 9, CHOICES_SHOP_TITLE);

		ItemStack sellIcon = new ItemStack(Material.HOPPER, 1, (short) 0);
		ItemMeta sellIconMeta = sellIcon.getItemMeta();
		sellIconMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Sell Items");
		sellIconMeta.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click here to open",
				ChatColor.GRAY + "" + ChatColor.ITALIC + "the selling menu."));
		sellIcon.setItemMeta(sellIconMeta);
		inv.setItem(3, sellIcon);

		ItemStack buyIcon = new ItemStack(Material.POTION, 1, (short) 0);
		ItemMeta buyIconMeta = buyIcon.getItemMeta();
		buyIconMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Buy Items");
		buyIconMeta.setLore(Arrays.asList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click here to open",
				ChatColor.GRAY + "" + ChatColor.ITALIC + "the buying menu."));
		buyIcon.setItemMeta(buyIconMeta);
		inv.setItem(5, buyIcon);

		// Use different thread to set metadata to avoid glitches.
		new BukkitRunnable() {

			@Override
			public void run() {
				player.setMetadata("CurrentShop", new FixedMetadataValue(InfernalRealms.getPlugin(), shopName));
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 0L);
		player.openInventory(inv);
	}

	public static void openSellShop(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		Inventory inv = player.getServer().createInventory(player, 9, SELL_SHOP_TITLE + playerData.getMoneyAsString());

		ItemStack button = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta buttonMeta = button.getItemMeta();
		buttonMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Left-click" + ChatColor.RESET
				+ ChatColor.GRAY + " to confirm the");
		buttonMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "amount and receive " + ItemReader.getMoneyAsString(0)));
		button.setItemMeta(buttonMeta);
		inv.setItem(8, button);

		player.openInventory(inv);
	}

	public static void openBuyShop(Player player, String shopName) {
		List<String> contentList = YAMLFile.SHOPS.getConfig().getStringList(shopName);
		if (contentList == null || contentList.isEmpty()) {
			new InvalidShopException("Shop \"" + shopName + "\" is empty / null.").printStackTrace();
			return;
		}

		PlayerData playerData = PlayerData.getData(player);
		String title = shopName + BUY_SHOP_TITLE + playerData.getMoneyAsString();
		if (title.length() > 32) {
			title = BUY_SHOP_TITLE + playerData.getMoneyAsString();
			System.out.println("WARNING: Shop Name is too long: " + shopName);
		}
		Inventory inv = player.getServer().createInventory(player,
				contentList.size() % 9 == 0 ? contentList.size() : ((contentList.size() / 9) + 1) * 9, title);

		for (String item : contentList) {
			String[] itemInfo = item.split("#");
			if (itemInfo.length != 4) {
				new InvalidShopException("Shop \"" + shopName + "\" has invalid item \"" + item + "\".").printStackTrace();
				return;
			}

			// Identifier (This points us to the item itself)
			String identifier = itemInfo[1];

			// Sell price
			String[] sellPriceSplit = itemInfo[2].split(",");
			if (sellPriceSplit.length != 3) {
				new InvalidShopException("Shop \"" + shopName + "\" has invalid item \"" + item + "\" @ Sell Price: " + itemInfo[2] + ".")
						.printStackTrace();
				return;
			}
			int gold;
			int silver;
			int copper;
			try {
				gold = Integer.parseInt(sellPriceSplit[0]);
				silver = Integer.parseInt(sellPriceSplit[1]);
				copper = Integer.parseInt(sellPriceSplit[2]);
			} catch (NumberFormatException e) {
				new InvalidShopException("Shop \"" + shopName + "\" has invalid item \"" + item + "\" @ Sell Price: " + itemInfo[2] + ".")
						.printStackTrace();
				return;
			}

			// Quantity
			int quantity;
			try {
				quantity = Integer.parseInt(itemInfo[3]);
			} catch (NumberFormatException e) {
				new InvalidShopException("Shop \"" + shopName + "\" has invalid item \"" + item + "\" @ Quantity: " + itemInfo[3] + ".")
						.printStackTrace();
				return;
			}

			// Create ItemStack to be placed in chest
			ItemStack itemStack;
			switch (itemInfo[0].toLowerCase()) { // Type
			case "potion":
				itemStack = InfernalItems.generatePotion(identifier, quantity);
				break;
			case "item":
				itemStack = InfernalItems.generateCustomItem(identifier);
				break;
			case "misc":
				itemStack = InfernalItems.generateMiscItem(identifier, quantity);
				break;
			default:
				new InvalidShopException("Shop \"" + shopName + "\" has invalid item \"" + item + "\" @ Quantity: " + itemInfo[3] + ".")
						.printStackTrace();
				return;
			}
			ItemMeta itemMeta = itemStack.getItemMeta();
			List<String> lore = itemMeta.getLore();

			// Remove sell price line if possible
			for (int i = lore.size() - 1; i >= 0; i--) {
				String line = lore.get(i);
				if (line.contains("Sell Price: ")) {
					lore.remove(i);
					break;
				}
			}

			// Set buy price
			lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Buy Price: "
					+ GeneralUtil.getMoneyAsString(GeneralUtil.convertCoinsToMoney(gold, silver, copper)));

			itemMeta.setLore(lore);
			itemStack.setItemMeta(itemMeta);
			inv.addItem(itemStack);
		}

		// Use different thread to set metadata to avoid glitches.
		new BukkitRunnable() {

			@Override
			public void run() {
				player.setMetadata("CurrentShop", new FixedMetadataValue(InfernalRealms.getPlugin(), shopName));
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 0L);
		player.openInventory(inv);
	}

	public static void processChoicesClick(InventoryClickEvent event, Player player) {
		event.setCancelled(true);
		if (!player.hasMetadata("CurrentShop")) {
			player.sendMessage(ChatColor.RED + "An error has occurred.");
			return;
		}
		String shopName = (String) player.getMetadata("CurrentShop").get(0).value();
		switch (event.getRawSlot()) {
		case 3:
			openSellShop(player);
			break;
		case 5:
			openBuyShop(player, shopName);
			break;
		}
	}

	public static void processBuyShopClick(InventoryClickEvent event, Player player, String shopName) {
		List<String> contentList = YAMLFile.SHOPS.getConfig().getStringList(shopName);
		if (event.getRawSlot() >= contentList.size()) {
			// Didn't click on an item
			return;
		}
		String item = contentList.get(event.getRawSlot());
		String[] itemInfo = item.split("#");

		// Sell price
		String[] sellPriceSplit = itemInfo[2].split(",");
		int gold;
		int silver;
		int copper;
		try {
			gold = Integer.parseInt(sellPriceSplit[0]);
			silver = Integer.parseInt(sellPriceSplit[1]);
			copper = Integer.parseInt(sellPriceSplit[2]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		}
		PlayerData playerData = PlayerData.getData(player);

		long cost = GeneralUtil.convertCoinsToMoney(gold, silver, copper);
		if (playerData.getMoney() < cost) {
			player.sendMessage(ChatColor.RED + "You do not have enough money to purchase this.");
			return;
		}

		String identifier = itemInfo[1];

		// Quantity
		int quantity;
		try {
			quantity = Integer.parseInt(itemInfo[3]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		}

		ItemStack itemStack;
		switch (itemInfo[0].toLowerCase()) { // Type
		case "potion":
			itemStack = InfernalItems.generatePotion(identifier, quantity);
			break;
		case "item":
			itemStack = InfernalItems.generateCustomItem(identifier);
			break;
		case "misc":
			itemStack = InfernalItems.generateMiscItem(identifier, quantity);
			break;
		default:
			return;
		}
		if (player.getInventory().firstEmpty() == -1) {
			player.sendMessage(ChatColor.RED + "You do not have enough inventory space.");
			return;
		}
		playerData.modifyMoney(-cost);
		player.getInventory().addItem(itemStack);
		player.sendMessage(ChatColor.GREEN + "Purchase successful!");
		InfernalEffects.playBuySound(player);
		openBuyShop(player, shopName);
	}

}
