package net.infernalrealms.inventory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.items.Pouch;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.archer.Haste;
import net.infernalrealms.skills.archer.MagnumShot;
import net.infernalrealms.skills.archer.VenomousArrow;
import net.infernalrealms.skills.magician.Frostbite;
import net.infernalrealms.skills.magician.Levitate;
import net.infernalrealms.skills.magician.Pyroblast;
import net.infernalrealms.skills.warrior.Charge;
import net.infernalrealms.skills.warrior.Leech;
import net.infernalrealms.skills.warrior.Rumble;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryManager {

	private static List<Integer> noInteractSlots = Arrays.asList(1, 2, 3);
	private static List<Integer> pouchSlots = Arrays.asList(9, 10, 18, 19, 27, 28);

	public static List<Integer> getNoInteractSlots() {
		return noInteractSlots;
	}

	public static List<Integer> getPouchSlots() {
		return pouchSlots;
	}

	public static int getPouchSize(ItemStack pouch) {
		int bagSlots = 0;
		search: {
			for (String line : pouch.getItemMeta().getLore()) {
				if (line.contains(" Slot Bag")) {
					String[] lineSplit = ChatColor.stripColor(line).split(" ");
					bagSlots = Integer.parseInt(lineSplit[0]);
					break search;
				}
			}
		}
		return bagSlots;
	}

	public static void activatePouch(Player player, int slot, ItemStack pouch) {
		PlayerData playerData = PlayerData.getData(player);
		if (pouch.hasItemMeta()) {
			if (pouch.getItemMeta().hasLore()) {
				String pouchName = ChatColor.stripColor(pouch.getItemMeta().getDisplayName());
				int bagSlots = getPouchSize(pouch);
				if (!hasPouch(player, pouchName)) {
					player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + pouchName + ChatColor.GRAY + " equipped!");
					for (int i = 0; i <= bagSlots; i++) {
						playerData.getConfig().set(
								playerData.getCurrentCharacterSlot() + ".Inventory." + slot + "." + pouchName + "." + Integer.toString(i),
								new ItemStack(Material.AIR));
					}
				} else {
					return;
				}
			}
		}
		playerData.saveConfig();
	}

	public static boolean hasPouch(Player player, String pouchName) {
		PlayerData playerData = PlayerData.getData(player);
		Set<String> set = playerData.getConfig().getKeys(true); // 9.Small Pouch.1
		for (String path : set) {
			if (path.contains(pouchName) && path.substring(0, 1).equals(Integer.toString(playerData.getCurrentCharacterSlot()))) {
				return true;
			}
		}
		return false;
	}

	// public boolean pouchIsEmpty(Player player, String pouchName) {
	//
	// }

	public static void removePouch(Player player, int slot) {
		PlayerData playerData = PlayerData.getData(player);
		if (pouchSlots.contains(slot)) {
			playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory." + slot, null);
			playerData.saveConfig();
		}
	}

	public static void setDefaultInventory(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getCurrentCharacterSlot() != -1) {
			// Pouches
			for (int i : getPouchSlots()) {
				String iS = playerData.getCurrentCharacterSlot() + ".Inventory." + i;
				if (playerData.getConfig().contains(iS)) {
					for (Pouch pouch : Pouch.values()) {
						if (playerData.getConfig().contains(iS + "." + pouch.getDisplayName())) {
							player.getInventory().setItem(i, pouch.generateItemStack());
						}
					}
					//					if (playerData.getConfig().contains(iS + ".Small Pouch")) {
					//						player.getInventory().setItem(i, GeneralItems.Pouches.smallPouch());
					//					} else if (playerData.getConfig().contains(iS + ".Torn Bag")) {
					//						player.getInventory().setItem(i, GeneralItems.Pouches.tornBag());
					//					} else if (playerData.getConfig().contains(iS + ".Penny Pouch")) {
					//						player.getInventory().setItem(i, GeneralItems.Pouches.pennyPouch());
					//					} else if (playerData.getConfig().contains(iS + ".Kashmir Supply Bag")) {
					//						player.getInventory().setItem(i, GeneralItems.Pouches.kashmirSupplyBag());
					//					} else if (playerData.getConfig().contains(iS + ".Silk Bag")) {
					//						player.getInventory().setItem(i, GeneralItems.Pouches.silkBag());
					//					} else if (playerData.getConfig().contains(iS + ".Black Leather Bag")) {
					//						player.getInventory().setItem(i, GeneralItems.Pouches.blackLeatherBag());
					//					} else {
					//						player.getInventory().setItem(i, GeneralItems.EMPTY_POUCH_SLOT);
					//					}
				} else {
					player.getInventory().setItem(i, GeneralItems.EMPTY_POUCH_SLOT);
				}
			}
		} else {
			ItemStack charSelect = new ItemStack(Material.BOOK, 1, (short) 0);
			ItemMeta charSelectMeta = charSelect.getItemMeta();
			charSelectMeta.setDisplayName(
					ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Right Click to View Character Selection");
			charSelect.setItemMeta(charSelectMeta);
			player.getInventory().setItem(0, charSelect);
		}
	}

	public static int getPouchSlot(Player player, String pouchName) {
		PlayerData playerData = PlayerData.getData(player);
		Set<String> set = playerData.getConfig().getKeys(true); // 1.Inventory.9.Small Pouch.1
		for (String path : set) {
			if (path.contains(pouchName) && path.substring(0, 1).equals(Integer.toString(playerData.getCurrentCharacterSlot()))) {
				String slotString = path.substring(2).replaceAll("[^\\d]", "");
				return Integer.parseInt(slotString);
			}
		}
		return (Integer) null;
	}

	public static void updatePouchContents(Player player, final Inventory pouchInventory) {
		final String pouchName = pouchInventory.getTitle().replace("Pouch: ", "");
		final int pouchSlot = getPouchSlot(player, pouchName);
		final PlayerData playerData = PlayerData.getData(player);
		final PlayerInventory playerInventory = player.getInventory();
		new BukkitRunnable() {
			public void run() {
				// Pouches
				for (int slot = 0; slot < pouchInventory.getSize(); slot++) {
					ItemStack item = pouchInventory.getItem(slot);
					if (item == null) {
						item = new ItemStack(Material.AIR);
					}
					playerData.getConfig()
							.set(playerData.getCurrentCharacterSlot() + ".Inventory." + pouchSlot + "." + pouchName + "." + slot, item);
				}
				// Player Inventory
				for (int slot = 0; slot < playerInventory.getSize(); slot++) {
					ItemStack item = playerInventory.getItem(slot);
					if (item == null) {
						item = new ItemStack(Material.AIR);
					}
					playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + slot, item);
				}
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Helmet",
						playerInventory.getHelmet());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Chestplate",
						playerInventory.getChestplate());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Leggings",
						playerInventory.getLeggings());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Boots", playerInventory.getBoots());
				playerData.saveConfig();
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 3L);

	}

	public static void updateBankContents(Player player, final Inventory bankInventory) {
		final PlayerData playerData = PlayerData.getData(player);
		final PlayerInventory playerInventory = player.getInventory();
		new BukkitRunnable() {
			public void run() {
				// Pouches
				for (int slot = 0; slot < bankInventory.getSize(); slot++) {
					ItemStack item = bankInventory.getItem(slot);
					if (item == null) {
						item = new ItemStack(Material.AIR);
					}
					playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Bank." + slot, item);
				}
				// Player Inventory
				for (int slot = 0; slot < playerInventory.getSize(); slot++) {
					ItemStack item = playerInventory.getItem(slot);
					if (item == null) {
						item = new ItemStack(Material.AIR);
					}
					playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + slot, item);
				}
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Helmet",
						playerInventory.getHelmet());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Chestplate",
						playerInventory.getChestplate());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Leggings",
						playerInventory.getLeggings());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Boots", playerInventory.getBoots());
				playerData.saveConfig();
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 3L);

	}

	//	public static void updatePouchContents(Player player, final Inventory inventory) {
	//		final String pouchName = inventory.getTitle().replace("Pouch: ", "");
	//		final int pouchSlot = getPouchSlot(player, pouchName);
	//		final PlayerData playerData = PlayerData.getData(player);
	//		new BukkitRunnable() {
	//			public void run() {
	//				for (int slot = 0; slot < inventory.getSize(); slot++) {
	//					ItemStack item = inventory.getItem(slot);
	//					if (item == null) {
	//						item = new ItemStack(Material.AIR);
	//					}
	//					System.out.println(slot + ": " + item.getType().toString());
	//					playerData.getConfig().set(
	//							playerData.getCurrentCharacterSlot() + ".Inventory." + pouchSlot + "." + pouchName + "." + slot, item);
	//					System.out.println(playerData.getConfig()
	//							.getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory." + pouchSlot + "." + pouchName + "." + slot)
	//							.getType().toString());
	//				}
	//				//				new BukkitRunnable() {
	//				//
	//				//					int count = 0;
	//				//
	//				//					@Override
	//				//					public void run() {
	//				//						if (count > 3) {
	//				//							cancel();
	//				//							return;
	//				//						}
	//				//						playerData.saveConfig();
	//				//						count++;
	//				//					}
	//				//				}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	//				playerData.saveConfig();
	//			}
	//		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	//
	//	}

	public static boolean pouchHasContents(Player player, ItemStack pouch) {
		String pouchName = ChatColor.stripColor(pouch.getItemMeta().getDisplayName());
		if (hasPouch(player, pouchName)) {
			String pouchSlot = Integer.toString(getPouchSlot(player, pouchName));
			int pouchSize = getPouchSize(pouch);
			PlayerData playerData = PlayerData.getData(player);
			for (int i = 0; i < pouchSize; i++) {
				String iS = Integer.toString(i);
				ItemStack item = playerData.getConfig()
						.getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory." + pouchSlot + "." + pouchName + "." + iS);
				if (!item.getType().equals(Material.AIR)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void updateMainInventoryContent(final Player player) {
		final PlayerData playerData = PlayerData.getData(player);
		if (playerData.getCurrentCharacterSlot() < 0)
			return;
		final PlayerInventory inventory = player.getInventory();

		InfernalRealms.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				for (int slot = 0; slot < inventory.getSize(); slot++) {
					ItemStack item = inventory.getItem(slot);
					if (item == null) {
						item = new ItemStack(Material.AIR);
					}
					playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + slot, item);
				}
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Helmet", inventory.getHelmet());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Chestplate",
						inventory.getChestplate());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Leggings", inventory.getLeggings());
				playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + "Boots", inventory.getBoots());
				playerData.saveConfig();
				//				new BukkitRunnable() {
				//
				//					int count = 0;
				//
				//					@Override
				//					public void run() {
				//						if (count > 3) {
				//							cancel();
				//							return;
				//						}
				//						playerData.saveConfig();
				//						count++;
				//					}
				//				}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
			}
		}, 3L);

	}

	public static void setPlayerInventory(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			ItemStack item = playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + i);
			if (i < 4 && i > 0) {
				updateSkillIcons(player);
				i = 3;
				continue;
			}
			player.getInventory().setItem(i, item);
		}
		player.getInventory()
				.setHelmet(playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Main.Helmet"));
		player.getInventory()
				.setChestplate(playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Main.Chestplate"));
		player.getInventory()
				.setLeggings(playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Main.Leggings"));
		player.getInventory().setBoots(playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Main.Boots"));
		setDefaultInventory(player);
	}

	public static void updateSkillIcons(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		for (int i = 1; i < 4; i++) {
			ItemStack item = GeneralItems.BLANK_BLACK_GLASS_PANE;
			switch (playerData.getPlayerClass()) {
			case "Warrior":
				switch (i) {
				case 1:
					item = new Charge(player).getIcon();
					break;
				case 2:
					item = new Rumble(player).getIcon();
					break;
				case 3:
					item = new Leech(player).getIcon();
					break;
				}
				break;
			case "Archer":
				switch (i) {
				case 1:
					item = new MagnumShot(player).getIcon();
					break;
				case 2:
					item = new VenomousArrow(player).getIcon();
					break;
				case 3:
					item = new Haste(player).getIcon();
					break;
				}
				break;
			case "Magician":
				switch (i) {
				case 1:
					item = new Levitate(player).getIcon();
					break;
				case 2:
					item = new Pyroblast(player).getIcon();
					break;
				case 3:
					item = new Frostbite(player).getIcon();
					break;
				}
				break;
			}
			player.getInventory().setItem(i, item);
		}
	}

}
