package net.infernalrealms.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.chat.InteractiveChat;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

public class Trade {

	public static final List<Integer> NO_INTERACT_SLOTS = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42,
			43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);
	public static final List<Integer> PROMPTER_SLOTS = Arrays.asList(9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30);
	public static final List<Integer> TARGET_SLOTS = Arrays.asList(14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35);
	public static final int COPPER_SLOT = 50;
	public static final int SILVER_SLOT = 49;
	public static final int GOLD_SLOT = 48;

	private boolean valid = false;

	public Player prompter;
	private long prompterMoney;
	private boolean prompterReady;
	public Player target;
	private long targetMoney;
	private boolean targetReady;

	private Inventory tradeWindow;

	public Trade(Player prompter, Player target) {
		this.prompter = prompter;
		this.target = target;
		if (prompter.getWorld().equals(target.getWorld()) && prompter.getLocation().distanceSquared(target.getLocation()) <= 400) {
			this.valid = true;
			this.createTradeWindow();
		}
	}

	public void acceptTrade() {
		prompter.setMetadata("Trade", new FixedMetadataValue(InfernalRealms.getPlugin(), this));
		target.setMetadata("Trade", new FixedMetadataValue(InfernalRealms.getPlugin(), this));
		target.removeMetadata("PendingTrade", InfernalRealms.getPlugin());
		prompter.openInventory(tradeWindow);
		target.openInventory(tradeWindow);
	}

	public void sendInvite() {
		final String targetName = target.getName();
		if (!this.isValid()) {
			prompter.sendMessage(ChatColor.RED + targetName + " is too far away to trade.");
			return;
		}
		if (target.hasMetadata("PendingTrade")) {
			prompter.sendMessage(ChatColor.RED + targetName + " has a " + ChatColor.BOLD + "pending invite.");
			return;
		}
		if (target.hasMetadata("Trade")) {
			prompter.sendMessage(ChatColor.RED + targetName + " is " + ChatColor.BOLD + "busy.");
			return;
		}
		setTradeInvite();
		target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.8F);
		InteractiveChat interactiveChat = new InteractiveChat(ChatColor.AQUA + "" + ChatColor.ITALIC + prompter.getName() + ChatColor.RESET
				+ ChatColor.AQUA + " has asked you to " + ChatColor.BOLD + "trade." + ChatColor.RESET + ChatColor.AQUA + " Type "
				+ ChatColor.ITALIC + "/accepttrade" + ChatColor.RESET + ChatColor.AQUA + " or click here if you would like to trade.");
		interactiveChat.applyHoverMessage(ChatColor.AQUA + "Accept Trade");
		interactiveChat.applyClickCommand("/accepttrade");
		interactiveChat.sendToPlayer(target);
		//		target.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + prompter.getName() + ChatColor.RESET + ChatColor.AQUA
		//				+ " has asked you to " + ChatColor.BOLD + "trade." + ChatColor.RESET + ChatColor.AQUA + " Type " + ChatColor.ITALIC
		//				+ "/accepttrade" + ChatColor.RESET + ChatColor.AQUA + " or click " + ChatColor.BOLD + "here" + ChatColor.AQUA
		//				+ " if you would like to trade.");
		prompter.sendMessage(ChatColor.GREEN + "" + "Trade request sent to " + target.getName() + ".");

		// Invite Expiration
		final Trade trade = this;
		new BukkitRunnable() {

			public void run() {
				Trade t = getTradeFromInvite(target);
				if (t != null && t.equals(trade)) {
					prompter.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + targetName + ChatColor.RESET + ChatColor.RED
							+ " failed to accept your " + ChatColor.BOLD + "trade invite.");
					target.sendMessage(ChatColor.RED + " You have failed to accept " + ChatColor.ITALIC + prompter.getName() + "'s "
							+ ChatColor.RESET + ChatColor.RED + ChatColor.BOLD + "trade invite.");
					target.playSound(target.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.25F, 0.5F); // TODO 1.9 CHANGE (was Sound.FIZZ)
					target.removeMetadata("PendingTrade", InfernalRealms.getPlugin());
				}
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 400L);
	}

	private void setTradeInvite() {
		target.setMetadata("PendingTrade", new FixedMetadataValue(InfernalRealms.getPlugin(), this));
	}

	public static Trade getTradeFromInvite(Player player) {
		if (player.hasMetadata("PendingTrade")) {
			return (Trade) player.getMetadata("PendingTrade").get(0).value();
		}
		return null; // No invite found
	}

	private void createTradeWindow() {
		tradeWindow = Bukkit.createInventory(prompter, 54, "Trading...");
		ItemStack blankGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta blankGlassPaneMeta = blankGlassPane.getItemMeta();
		blankGlassPaneMeta.setDisplayName(" ");
		blankGlassPane.setItemMeta(blankGlassPaneMeta);
		for (int i : new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 47, 51 })
			tradeWindow.setItem(i, blankGlassPane);

		ItemStack confirmButton = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
		confirmButtonMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Confirm");
		confirmButton.setItemMeta(confirmButtonMeta);
		for (int i : new int[] { 46, 52 })
			tradeWindow.setItem(i, confirmButton);

		ItemStack addGold = new ItemStack(Material.GOLD_INGOT, 1, (short) 0);
		ItemMeta addGoldMeta = addGold.getItemMeta();
		addGoldMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.YELLOW + "Trade Gold");
		ArrayList<String> goldLore = new ArrayList<String>();
		goldLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LMB: " + ChatColor.GRAY + "Add 1" + ChatColor.YELLOW + "●" + ChatColor.GRAY
				+ " to the trade");
		goldLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "RMB: " + ChatColor.GRAY + "Subtract 1" + ChatColor.YELLOW + "●"
				+ ChatColor.GRAY + " to the trade");
		goldLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "SHIFT+LMB/RMB: " + ChatColor.RESET + ChatColor.GRAY + "Add/Subtract 10"
				+ ChatColor.YELLOW + "●" + ChatColor.GRAY + " to the trade");
		goldLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click this item to add/subtract");
		goldLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "money to your trade.");
		addGoldMeta.setLore(goldLore);
		addGold.setItemMeta(addGoldMeta);
		tradeWindow.setItem(48, addGold);

		ItemStack addSilver = new ItemStack(Material.IRON_INGOT, 1, (short) 0);
		ItemMeta addSilverMeta = addSilver.getItemMeta();
		addSilverMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GRAY + "Trade Silver");
		ArrayList<String> silverLore = new ArrayList<String>();
		silverLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LMB: " + ChatColor.GRAY + "Add 1●" + ChatColor.GRAY + " to the trade");
		silverLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "RMB: " + ChatColor.GRAY + "Subtract 1●" + ChatColor.GRAY + " to the trade");
		silverLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "SHIFT+LMB/RMB: " + ChatColor.RESET + ChatColor.GRAY + "Add/Subtract 10●"
				+ ChatColor.GRAY + " to the trade");
		silverLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click this item to add/subtract");
		silverLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "money to your trade.");
		addSilverMeta.setLore(silverLore);
		addSilver.setItemMeta(addSilverMeta);
		tradeWindow.setItem(49, addSilver);

		ItemStack addCopper = new ItemStack(Material.CLAY_BRICK, 1, (short) 0);
		ItemMeta addCopperMeta = addCopper.getItemMeta();
		addCopperMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GOLD + "Trade Copper");
		ArrayList<String> copperLore = new ArrayList<String>();
		copperLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LMB: " + ChatColor.GRAY + "Add 1" + ChatColor.GOLD + "●" + ChatColor.GRAY
				+ " to the trade");
		copperLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "RMB: " + ChatColor.GRAY + "Subtract 1" + ChatColor.GOLD + "●"
				+ ChatColor.GRAY + " to the trade");
		copperLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "SHIFT+LMB/RMB: " + ChatColor.RESET + ChatColor.GRAY + "Add/Subtract 10"
				+ ChatColor.GOLD + "●" + ChatColor.GRAY + " to the trade");
		copperLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click this item to add/subtract");
		copperLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "money to your trade.");
		addCopperMeta.setLore(copperLore);
		addCopper.setItemMeta(addCopperMeta);
		tradeWindow.setItem(50, addCopper);

		updateHead(Trader.PROMPTER);
		updateHead(Trader.TARGET);
	}

	public void updateHead(Trader tt) {
		boolean isPrompter = tt == Trader.PROMPTER;
		Player player = isPrompter ? prompter : target;
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta headMeta = head.getItemMeta();
		headMeta.setDisplayName(
				ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + player.getName() + ChatColor.RESET + ChatColor.GRAY + " is trading:");
		headMeta.setLore(Arrays.asList(GeneralUtil.getMoneyAsString(this.prompterMoney),
				ChatColor.RESET + "" + ChatColor.GRAY + "And all the items above."));
		head.setItemMeta(headMeta);
		tradeWindow.setItem(isPrompter ? 45 : 53, head);
	}

	//	public void updatePrompterHead() {
	//		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
	//		ItemMeta headMeta = head.getItemMeta();
	//		headMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + prompter.getName() + ChatColor.RESET + ChatColor.GRAY
	//				+ " is trading:");
	//		headMeta.setLore(Arrays.asList(GeneralUtil.getMoneyAsString(this.prompterMoney), ChatColor.RESET + "" + ChatColor.GRAY
	//				+ "And all the items above."));
	//		head.setItemMeta(headMeta);
	//		tradeWindow.setItem(45, head);
	//	}
	//
	//	public void updateTargetHead() {
	//		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
	//		ItemMeta headMeta = head.getItemMeta();
	//		headMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + target.getName() + ChatColor.RESET + ChatColor.GRAY
	//				+ " is trading:");
	//		headMeta.setLore(Arrays.asList(GeneralUtil.getMoneyAsString(this.targetMoney), ChatColor.RESET + "" + ChatColor.GRAY
	//				+ "And all the items above."));
	//		head.setItemMeta(headMeta);
	//		tradeWindow.setItem(53, head);
	//	}

	public void modifyMoney(long amount, Trader tt) {
		boolean isPrompter = tt == Trader.PROMPTER;
		Player modifier = isPrompter ? prompter : target;
		PlayerData playerData = PlayerData.getData(modifier);
		long money = playerData.getMoney();
		if (amount > 0) { // Adding
			if ((isPrompter ? this.prompterMoney : this.targetMoney) + amount > money) {
				modifier.sendMessage(
						ChatColor.RED + "You cannot add any more money. Current money: " + GeneralUtil.getMoneyAsString(money));
				return;
			}
			if (isPrompter)
				this.prompterMoney += amount;
			else
				this.targetMoney += amount;
		} else { // Subtracting
			if (-amount > (isPrompter ? this.prompterMoney : this.targetMoney)) {
				modifier.sendMessage(ChatColor.RED + "You cannot remove any more money.");
				return;
			}
			if (isPrompter)
				this.prompterMoney += amount;
			else
				this.targetMoney += amount;
		}
		this.sendMessage(ChatColor.GREEN + modifier.getName() + " is now offering: "
				+ GeneralUtil.getMoneyAsString(isPrompter ? this.prompterMoney : this.targetMoney));
		updateHead(Trader.PROMPTER);
		cancelReadyTrade();
	}

	//	public void modifyPrompterMoney(long amount) {
	//		PlayerData prompterData = PlayerData.getData(prompter);
	//		long money = prompterData.getMoney();
	//		if (amount > 0) { // Adding
	//			if (this.prompterMoney + amount > money) {
	//				prompter.sendMessage(ChatColor.RED + "You cannot add any more money. Current money: " + GeneralUtil.getMoneyAsString(money));
	//				return;
	//			}
	//			this.prompterMoney += amount;
	//		} else { // Subtracting
	//			if (-amount > this.prompterMoney) {
	//				prompter.sendMessage(ChatColor.RED + "You cannot remove any more money.");
	//				return;
	//			}
	//			this.prompterMoney += amount;
	//		}
	//		updatePrompterHead();
	//		cancelReadyTrade();
	//	}
	//
	//	public void modifyTargetMoney(long amount) {
	//		PlayerData targetData = PlayerData.getData(target);
	//		long money = targetData.getMoney();
	//		if (amount > 0) { // Adding
	//			if (amount > money) {
	//				target.sendMessage(ChatColor.RED + "You cannot add any more money. Current money: " + GeneralUtil.getMoneyAsString(money));
	//				return;
	//			}
	//			this.targetMoney += amount;
	//		} else { // Subtracting
	//			if (-amount > this.targetMoney) {
	//				target.sendMessage(ChatColor.RED + "You cannot remove any more money.");
	//				return;
	//			}
	//			this.targetMoney += amount;
	//		}
	//		updateTargetHead();
	//		cancelReadyTrade();
	//	}

	public void readyPrompterTrade() {
		this.prompterReady = true;
		this.tradeWindow.getItem(46).setDurability((short) 10);
		this.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
		attemptTransfer();
	}

	public void readyTargetTrade() {
		this.targetReady = true;
		this.tradeWindow.getItem(52).setDurability((short) 10);
		this.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
		attemptTransfer();
	}

	public void cancelReadyTrade() {
		this.prompterReady = false;
		this.targetReady = false;
		playSound(Sound.BLOCK_CLOTH_BREAK, 0.5F, 0.5F);
		new BukkitRunnable() {
			public void run() {
				tradeWindow.getItem(46).setDurability((short) 8);
				tradeWindow.getItem(52).setDurability((short) 8);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);

	}

	public ItemStack[] getItemOffers(Trader tt) {
		ItemStack[] offers = new ItemStack[12];
		int count = 0;
		for (int row = 1; row <= 3; row++) {
			for (int col = 0; col <= 3; col++) {
				int slot = row * 9 + col;
				offers[count++] = this.tradeWindow.getItem(slot + (tt == Trader.PROMPTER ? 0 : 5));
			}
		}
		return offers;
	}

	public boolean attemptTransfer() {
		if (this.prompterReady && this.targetReady) {
			// Checking Conditions...
			ItemStack[] prompterSide = getItemOffers(Trader.PROMPTER);
			ItemStack[] targetSide = getItemOffers(Trader.TARGET);
			if (!GeneralUtil.hasInventorySpace(prompter.getInventory(), targetSide)) {
				this.sendMessage(ChatColor.RED + prompter.getName() + " does not have enough inventory space to complete the trade.");
				cancelReadyTrade();
				return false;
			}
			if (!GeneralUtil.hasInventorySpace(target.getInventory(), prompterSide)) {
				this.sendMessage(ChatColor.RED + target.getName() + " does not have enough inventory space to complete the trade.");
				cancelReadyTrade();
				return false;
			}
			PlayerData prompterData = PlayerData.getData(prompter);
			PlayerData targetData = PlayerData.getData(target);
			if (this.targetMoney > targetData.getMoney() || this.prompterMoney > prompterData.getMoney()) {
				this.sendMessage(ChatColor.RED + "Due someone having insufficient funds, the trade was unable to be completed.");
				cancelReadyTrade();
				return false;
			}

			// Good to complete the rest of the trade
			for (int i = 0; i < 12; i++) {
				if (targetSide[i] != null)
					prompter.getInventory().addItem(targetSide[i]);
				if (prompterSide[i] != null)
					target.getInventory().addItem(prompterSide[i]);
			}
			//TODO Check if they actually still have the money they are offering (Should be done?)
			prompterData.modifyMoney(this.targetMoney);
			prompterData.modifyMoney(-this.prompterMoney);
			targetData.modifyMoney(this.prompterMoney);
			targetData.modifyMoney(-this.targetMoney);
			clearTrade(true);
			return true;
		}
		return false;
	}

	public void clearTrade() {
		clearTrade(false);
	}

	public void clearTrade(boolean success) {
		prompter.removeMetadata("Trade", InfernalRealms.getPlugin());
		target.removeMetadata("Trade", InfernalRealms.getPlugin());
		if (!success) {
			ItemStack[] prompterSide = getItemOffers(Trader.PROMPTER);
			ItemStack[] targetSide = getItemOffers(Trader.TARGET);
			for (int i = 0; i < 12; i++) {
				if (prompterSide[i] != null) {
					GeneralUtil.addItemToInventoryOrDrop(prompter, prompterSide[i]);
				}
				if (targetSide[i] != null) {
					GeneralUtil.addItemToInventoryOrDrop(target, targetSide[i]);
				}
			}
			this.playSound(Sound.ENTITY_ITEM_BREAK, 0.25F, 0.5F);
		}
		prompter.closeInventory();
		target.closeInventory();
		prompter.updateInventory();
		target.updateInventory();
		//		for (HumanEntity he : tradeWindow.getViewers()) {
		//			he.closeInventory();
		//		}
	}

	public void sendMessage(String message) {
		prompter.sendMessage(message);
		target.sendMessage(message);
	}

	/**
	 * Plays a sound both members of the trade.
	 * @param sound
	 * @param volume
	 * @param pitch
	 */
	public void playSound(Sound sound, float volume, float pitch) {
		prompter.playSound(prompter.getLocation(), sound, volume, pitch);
		target.playSound(target.getLocation(), sound, volume, pitch);
	}

	public boolean isValid() {
		return this.valid;
	}

	public enum Trader {
		PROMPTER(), TARGET();
		private Trader() {}
	}
}
