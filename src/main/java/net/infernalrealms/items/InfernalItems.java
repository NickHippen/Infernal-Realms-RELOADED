package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalStrings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InfernalItems {

	@SuppressWarnings("deprecation")
	public static ItemStack generateCustomItem(String itemName) {
		itemName = itemName.replace("_", " ");
		int itemId = YAMLFile.ITEMS.getConfig().getInt(itemName + ".ItemId");
		ItemStack spawnedItem = new ItemStack(itemId, 1);
		ItemMeta spawnedItemMeta = spawnedItem.getItemMeta();
		StringBuilder itemDisplayName = new StringBuilder(itemName);
		String itemColors = YAMLFile.ITEMS.getConfig().getString(itemName + ".Colors");
		itemColors = ChatColor.translateAlternateColorCodes('$', itemColors);
		itemDisplayName.insert(0, itemColors);
		spawnedItemMeta.setDisplayName(itemDisplayName.toString());
		ArrayList<String> spawnedItemLore = new ArrayList<String>();
		String itemType = YAMLFile.ITEMS.getConfig().getString(itemName + ".Type");
		String itemCategory = null;
		if (itemType.equalsIgnoreCase("helmet") || itemType.equalsIgnoreCase("chestplate") || itemType.equalsIgnoreCase("leggings")
				|| itemType.equalsIgnoreCase("boots")) {
			itemCategory = "armor";
		} else {
			itemCategory = "weapon";
		}
		if (!YAMLFile.ITEMS.getConfig().getBoolean(itemName + ".Tradeable")) {
			spawnedItemLore.add(InfernalStrings.UNTRADEABLE);
		}
		spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + itemType);
		if (itemCategory.equals("weapon")) {
			if (YAMLFile.ITEMS.getConfig().contains(itemName + ".Damage")) {
				String[] minMaxDamageSplit = YAMLFile.ITEMS.getConfig().getString(itemName + ".Damage").split("-");
				String minDamageRange = minMaxDamageSplit[0];
				String maxDamageRange = minMaxDamageSplit[1];
				String[] minDamageSplit = minDamageRange.split(" to ");
				String[] maxDamageSplit = maxDamageRange.split(" to ");
				int minMinDamage = Integer.parseInt(minDamageSplit[0]);
				int minMaxDamage = Integer.parseInt(minDamageSplit[1]);
				int maxMinDamage = Integer.parseInt(maxDamageSplit[0]);
				int maxMaxDamage = Integer.parseInt(maxDamageSplit[1]);
				int minDamage = minMinDamage + (int) (Math.random() * ((minMaxDamage - minMinDamage) + 1));
				int maxDamage = maxMinDamage + (int) (Math.random() * ((maxMaxDamage - maxMinDamage) + 1));
				double dps = (minDamage + maxDamage) / 2;
				double dpsRounded = (Math.round(dps * 10)) / 10;
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE

				+ Integer.toString(minDamage) + " - " + Integer.toString(maxDamage) + " Damage");
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "(" + Double.toString(dpsRounded) + " damage average)");
			}
		} else {
			if (YAMLFile.ITEMS.getConfig().contains(itemName + ".Armor")) {
				String[] armorSplit = YAMLFile.ITEMS.getConfig().getString(itemName + ".Armor").split("-");
				int minArmor = Integer.parseInt(armorSplit[0]);
				int maxArmor = Integer.parseInt(armorSplit[1]);
				int armor = minArmor + (int) (Math.random() * ((maxArmor - minArmor) + 1));
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + Integer.toString(armor) + " Armor");
			}
		}
		if (YAMLFile.ITEMS.getConfig().contains(itemName + ".Stats")) {
			List<String> stats = YAMLFile.ITEMS.getConfig().getStringList(itemName + ".Stats");
			for (String stat : stats) {
				String[] statSplit = stat.split(" ");
				String range = statSplit[0];
				String abilityType = statSplit[1];
				String[] rangeSplit = range.split("-");
				int minStat = Integer.parseInt(rangeSplit[0]);
				int maxStat = Integer.parseInt(rangeSplit[1]);
				int statValue = minStat + (int) (Math.random() * ((maxStat - minStat) + 1));
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(statValue) + " " + abilityType);
			}
		}
		spawnedItemLore.add("");
		if (YAMLFile.ITEMS.getConfig().contains(itemName + ".Socket")) {
			List<String> sockets = YAMLFile.ITEMS.getConfig().getStringList(itemName + ".Socket");
			for (String socket : sockets) {
				String[] socketSplit = socket.split(" ");
				String socketType = socketSplit[0];
				String amountRange = socketSplit[1];
				String[] amountRangeSplit = amountRange.split("-");
				int minAmount = Integer.parseInt(amountRangeSplit[0]);
				int maxAmount = Integer.parseInt(amountRangeSplit[1]);
				int socketAmount = minAmount + (int) (Math.random() * ((maxAmount - minAmount) + 1));
				for (int x = 0; x < socketAmount; x++) {
					if (socketType.equalsIgnoreCase("blue")) {
						spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.BLUE + " ▣ " + ChatColor.WHITE + "Blue Socket");
					} else if (socketType.equalsIgnoreCase("yellow")) {
						spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + " ▣ " + ChatColor.WHITE + "Yellow Socket");
					} else if (socketType.equalsIgnoreCase("red")) {
						spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + " ▣ " + ChatColor.WHITE + "Red Socket");
					}
				}
			}
		}
		spawnedItemLore.add("");
		spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + "Requires Level "
				+ Integer.toString(YAMLFile.ITEMS.getConfig().getInt(itemName + ".LevelReq")));
		if (YAMLFile.ITEMS.getConfig().contains(itemName + ".Class")) {
			if (YAMLFile.ITEMS.getConfig().getString(itemName + ".Class").equalsIgnoreCase("warrior")) {
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: Warrior");
			} else if (YAMLFile.ITEMS.getConfig().getString(itemName + ".Class").equalsIgnoreCase("magician")) {
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: Magician");
			} else if (YAMLFile.ITEMS.getConfig().getString(itemName + ".Class").equalsIgnoreCase("archer")) {
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: Archer");
			}
		}
		String[] sellPriceSplit = YAMLFile.ITEMS.getConfig().getString(itemName + ".Sell").split(",");
		String gold = sellPriceSplit[0];
		String silver = sellPriceSplit[1];
		String copper = sellPriceSplit[2];
		spawnedItemLore.add(
				ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + ChatColor.RESET + "" + ChatColor.WHITE + gold + ChatColor.YELLOW
						+ "● " + ChatColor.WHITE + silver + ChatColor.GRAY + "● " + ChatColor.WHITE + copper + ChatColor.GOLD + "● ");
		spawnedItemMeta.setLore(spawnedItemLore);
		spawnedItem.setItemMeta(spawnedItemMeta);
		return spawnedItem;
	}

	@SuppressWarnings("deprecation")
	@Deprecated
	public static ItemStack generateTierItem(String item) {
		Random r = new Random();
		List<String> itemList = YAMLFile.TIERS.getConfig().getStringList(item + ".Item");
		int chosenItemType = (int) (Math.random() * (itemList.size()));
		String[] itemTypeSplit = itemList.get(chosenItemType).split(" ");
		int itemId = Integer.parseInt(itemTypeSplit[0]);
		ItemStack spawnedItem = new ItemStack(itemId, 1);
		ItemMeta spawnedItemMeta = spawnedItem.getItemMeta();
		ArrayList<String> spawnedItemLore = new ArrayList<String>();
		String itemCategory = null;
		if (itemTypeSplit[1].equalsIgnoreCase("helmet") || itemTypeSplit[1].equalsIgnoreCase("chestplate")
				|| itemTypeSplit[1].equalsIgnoreCase("leggings") || itemTypeSplit[1].equalsIgnoreCase("boots")) {
			itemCategory = "armor";
		} else {
			itemCategory = "weapon";
		}
		if (!YAMLFile.TIERS.getConfig().getBoolean(".Tradeable")) {
			spawnedItemLore.add(InfernalStrings.UNTRADEABLE);
		}
		spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + itemTypeSplit[1]);
		if (itemCategory.equals("weapon")) {
			if (YAMLFile.TIERS.getConfig().contains(item + ".Damage")) {
				String[] minMaxDamageSplit = YAMLFile.TIERS.getConfig().getString(item + ".Damage").split("-");
				String minDamageRange = minMaxDamageSplit[0];
				String maxDamageRange = minMaxDamageSplit[1];
				String[] minDamageSplit = minDamageRange.split(" to ");
				String[] maxDamageSplit = maxDamageRange.split(" to ");
				int minMinDamage = Integer.parseInt(minDamageSplit[0]);
				int minMaxDamage = Integer.parseInt(minDamageSplit[1]);
				int maxMinDamage = Integer.parseInt(maxDamageSplit[0]);
				int maxMaxDamage = Integer.parseInt(maxDamageSplit[1]);
				int minDamage = minMinDamage + (int) (Math.random() * ((minMaxDamage - minMinDamage) + 1));
				int maxDamage = maxMinDamage + (int) (Math.random() * ((maxMaxDamage - maxMinDamage) + 1));
				double dps = (minDamage + maxDamage) / 2;
				double dpsRounded = (Math.round(dps * 10)) / 10;
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE

				+ Integer.toString(minDamage) + " - " + Integer.toString(maxDamage) + " Damage");
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "(" + Double.toString(dpsRounded) + " damage average)");
			}
		} else {
			if (YAMLFile.TIERS.getConfig().contains(item + ".Armor")) {
				String[] armorSplit = YAMLFile.TIERS.getConfig().getString(item + ".Armor").split("-");
				int minArmor = Integer.parseInt(armorSplit[0]);
				int maxArmor = Integer.parseInt(armorSplit[1]);
				int armor = minArmor + (int) (Math.random() * ((maxArmor - minArmor) + 1));
				spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + Integer.toString(armor) + " Armor");
			}
		}
		if (YAMLFile.TIERS.getConfig().contains(item + ".Stats")) {
			ArrayList<String> statsList = (ArrayList<String>) YAMLFile.TIERS.getConfig().getStringList(item + ".Stats");
			int statRoll = (int) (Math.random() * (statsList.size()));
			String chosenStat = statsList.get(statRoll);
			String[] chosenStatSplit = chosenStat.split(" ");
			String[] statValueSplit = chosenStatSplit[0].split("-");
			int minStatValue = Integer.parseInt(statValueSplit[0]);
			int maxStatValue = Integer.parseInt(statValueSplit[1]);
			int statValue = minStatValue + (int) (Math.random() * ((maxStatValue - minStatValue) + 1));
			spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(statValue) + " " + chosenStatSplit[1]);
			HashMap<String, Integer> statsOnItem = new HashMap<String, Integer>();
			statsOnItem.put(chosenStat, statValue);
			statsList.remove(statRoll);
			if (statsList.size() > 0) {
				float statChance = r.nextFloat();
				if (statChance <= 0.8f) {
					statRoll = (int) (Math.random() * (statsList.size()));
					chosenStat = statsList.get(statRoll);
					chosenStatSplit = chosenStat.split(" ");
					statValueSplit = chosenStatSplit[0].split("-");
					minStatValue = Integer.parseInt(statValueSplit[0]);
					maxStatValue = Integer.parseInt(statValueSplit[1]);
					statValue = minStatValue + (int) (Math.random() * ((maxStatValue - minStatValue) + 1));
					spawnedItemLore
							.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(statValue) + " " + chosenStatSplit[1]);
					statsOnItem.put(chosenStat, statValue);
					statsList.remove(statRoll);
					if (statsList.size() > 0) {
						statChance = r.nextFloat();
						if (statChance <= 0.7f) {
							statRoll = (int) (Math.random() * (statsList.size()));
							chosenStat = statsList.get(statRoll);
							chosenStatSplit = chosenStat.split(" ");
							statValueSplit = chosenStatSplit[0].split("-");
							minStatValue = Integer.parseInt(statValueSplit[0]);
							maxStatValue = Integer.parseInt(statValueSplit[1]);
							statValue = minStatValue + (int) (Math.random() * ((maxStatValue - minStatValue) + 1));
							spawnedItemLore.add(
									ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(statValue) + " " + chosenStatSplit[1]);
							statsOnItem.put(chosenStat, statValue);
							statsList.remove(statRoll);
							if (statsList.size() > 0) {
								statChance = r.nextFloat();
								if (statChance <= 0.5f) {
									statRoll = (int) (Math.random() * (statsList.size()));
									chosenStat = statsList.get(statRoll);
									chosenStatSplit = chosenStat.split(" ");
									statValueSplit = chosenStatSplit[0].split("-");
									minStatValue = Integer.parseInt(statValueSplit[0]);
									maxStatValue = Integer.parseInt(statValueSplit[1]);
									statValue = minStatValue + (int) (Math.random() * ((maxStatValue - minStatValue) + 1));
									spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(statValue) + " "
											+ chosenStatSplit[1]);
									statsOnItem.put(chosenStat, statValue);
									statsList.remove(statRoll);
									if (statsList.size() > 0) {
										statChance = r.nextFloat();
										if (statChance <= 0.2f) {
											statRoll = (int) (Math.random() * (statsList.size()));
											chosenStat = statsList.get(statRoll);
											chosenStatSplit = chosenStat.split(" ");
											statValueSplit = chosenStatSplit[0].split("-");
											minStatValue = Integer.parseInt(statValueSplit[0]);
											maxStatValue = Integer.parseInt(statValueSplit[1]);
											statValue = minStatValue + (int) (Math.random() * ((maxStatValue - minStatValue) + 1));
											spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(statValue)
													+ " " + chosenStatSplit[1]);
											statsOnItem.put(chosenStat, statValue);
											statsList.remove(statRoll);
											if (statsList.size() > 0) {
												statChance = r.nextFloat();
												if (statChance <= 0.1f) {
													statRoll = (int) (Math.random() * (statsList.size()));
													chosenStat = statsList.get(statRoll);
													chosenStatSplit = chosenStat.split(" ");
													statValueSplit = chosenStatSplit[0].split("-");
													minStatValue = Integer.parseInt(statValueSplit[0]);
													maxStatValue = Integer.parseInt(statValueSplit[1]);
													statValue = minStatValue + (int) (Math.random() * ((maxStatValue - minStatValue) + 1));
													spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+"
															+ Integer.toString(statValue) + " " + chosenStatSplit[1]);
													statsOnItem.put(chosenStat, statValue);
													statsList.remove(statRoll);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			List<String> itemNames = YAMLFile.TIERS.getConfig().getStringList(item + ".Names");
			int nameValue = (int) (Math.random() * (itemNames.size()));
			String preItemName = itemNames.get(nameValue);
			String itemNameNoColor = "";
			int highestValue = 0;
			for (Map.Entry<String, Integer> entry : statsOnItem.entrySet()) {
				if (entry.getValue() > highestValue) {
					highestValue = entry.getValue();
					String[] statSplit = entry.getKey().split(" ");
					itemNameNoColor = preItemName.replaceAll("/stat/", statSplit[1]).replaceAll("/itemtype/", itemTypeSplit[1]);
				}
			}
			StringBuilder itemName = new StringBuilder(itemNameNoColor);
			itemName.insert(0, YAMLFile.TIERS.getConfig().getString(item + ".Colors"));
			spawnedItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('$', itemName.toString()));
		}
		spawnedItemLore.add("");
		if (YAMLFile.TIERS.getConfig().contains(item + ".Sockets") && YAMLFile.TIERS.getConfig().contains(item + ".SocketColors")) {
			String[] sockets = YAMLFile.TIERS.getConfig().getString(item + ".Sockets").split("-");
			int minSockets = Integer.parseInt(sockets[0]);
			int maxSockets = Integer.parseInt(sockets[1]);
			int numberOfSockets = minSockets + (int) (Math.random() * ((maxSockets - minSockets) + 1));
			ArrayList<String> socketTypes = (ArrayList<String>) YAMLFile.TIERS.getConfig().getStringList(item + ".SocketColors");
			for (int x = 0; x < numberOfSockets; x++) {
				int socketChosen = (int) (Math.random() * (socketTypes.size()));
				if (socketTypes.get(socketChosen).equalsIgnoreCase("red")) {
					spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + " ▣ " + ChatColor.WHITE + "Red Socket");
				} else if (socketTypes.get(socketChosen).equalsIgnoreCase("yellow")) {
					spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + " ▣ " + ChatColor.WHITE + "Yellow Socket");
				} else if (socketTypes.get(socketChosen).equalsIgnoreCase("blue")) {
					spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.BLUE + " ▣ " + ChatColor.WHITE + "Blue Socket");
				}
			}
		}
		spawnedItemLore.add("");
		String[] nameSplit = item.split(" ");
		String[] levelSplit = nameSplit[1].split("-");
		int minLevel = Integer.parseInt(levelSplit[0]);
		int maxLevel = Integer.parseInt(levelSplit[1]);
		int level = minLevel + (int) (Math.random() * ((maxLevel - minLevel) + 1));
		spawnedItemLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + "Requires Level " + Integer.toString(level));
		String[] sellPriceSplit = YAMLFile.TIERS.getConfig().getString(item + ".Sell").split(",");
		String gold = sellPriceSplit[0];
		String silver = sellPriceSplit[1];
		String copper = sellPriceSplit[2];
		spawnedItemLore.add(
				ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + ChatColor.RESET + "" + ChatColor.WHITE + gold + ChatColor.YELLOW
						+ "● " + ChatColor.WHITE + silver + ChatColor.GRAY + "● " + ChatColor.WHITE + copper + ChatColor.GOLD + "● ");
		spawnedItemMeta.setLore(spawnedItemLore);
		spawnedItem.setItemMeta(spawnedItemMeta);
		return spawnedItem;
	}

	@Deprecated
	public static ItemStack generateGem(String item) {
		return generateGem(item, 1);
	}

	@Deprecated
	public static ItemStack generateGem(String item, int amount) {
		List<String> gemTypes = YAMLFile.GEMS.getConfig().getStringList(item + ".SocketColor");
		int chosenGem = (int) (Math.random() * (gemTypes.size()));
		ItemStack gem = new ItemStack(Material.INK_SACK, amount, (short) 1);
		String color = "";
		if (gemTypes.get(chosenGem).equalsIgnoreCase("red")) {
			gem = new ItemStack(Material.INK_SACK, 1, (short) 1);
			color = "Red";
		} else if (gemTypes.get(chosenGem).equalsIgnoreCase("yellow")) {
			gem = new ItemStack(Material.INK_SACK, 1, (short) 11);
			color = "Yellow";
		} else if (gemTypes.get(chosenGem).equalsIgnoreCase("blue")) {
			gem = new ItemStack(Material.INK_SACK, 1, (short) 6);
			color = "Blue";
		}
		ItemMeta gemMeta = gem.getItemMeta();
		List<String> stats = YAMLFile.GEMS.getConfig().getStringList(item + ".Stats");
		int chosenStat = (int) (Math.random() * (stats.size()));
		String[] statSplit = stats.get(chosenStat).split(" ");
		String[] statValueSplit = statSplit[0].split("-");
		int minValue = Integer.parseInt(statValueSplit[0]);
		int maxValue = Integer.parseInt(statValueSplit[1]);
		int value = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));
		String[] itemNameSplit = item.split(" ");
		String displayName = YAMLFile.GEMS.getConfig().getString(item + ".Colors") + itemNameSplit[0] + " " + color + " Gem of "
				+ statSplit[1];
		gemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('$', displayName));
		ArrayList<String> gemLore = new ArrayList<String>();
		gemLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + Integer.toString(value) + " " + statSplit[1]);
		gemMeta.setLore(gemLore);
		gem.setItemMeta(gemMeta);
		return gem;
	}

	public static ItemStack generatePotion(String item) {
		return generatePotion(item, 1);
	}

	@SuppressWarnings("deprecation")
	public static ItemStack generatePotion(String item, int amount) {
		String[] material = YAMLFile.POTIONS.getConfig().getString(item + ".Material").split(",");
		int id = 0;
		try {
			id = Integer.parseInt(material[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		short damageValue = 0;
		if (material.length > 1) {
			try {
				damageValue = Short.parseShort(material[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		ItemStack potion = new ItemStack(Material.getMaterial(id), amount, damageValue);
		ItemMeta potionMeta = potion.getItemMeta();
		potionMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + item);
		ArrayList<String> potionLore = new ArrayList<String>();
		if (YAMLFile.POTIONS.getConfig().contains(item + ".Hp")) {
			String durationAddition = YAMLFile.POTIONS.getConfig().contains(item + ".HpDuration")
					? " over " + YAMLFile.POTIONS.getConfig().getInt(item + ".HpDuration") + " seconds" : "";
			String hpLine = ChatColor.GREEN + "" + ChatColor.BOLD + "RESTORES: " + ChatColor.GREEN
					+ YAMLFile.POTIONS.getConfig().getInt(item + ".Hp") + " health points" + durationAddition;
			potionLore.add(hpLine);
		}
		if (YAMLFile.POTIONS.getConfig().contains(item + ".Mp")) {
			String durationAddition = YAMLFile.POTIONS.getConfig().contains(item + ".MpDuration")
					? " over " + YAMLFile.POTIONS.getConfig().getInt(item + ".MpDuration") + " seconds" : "";
			String mpLine = ChatColor.GREEN + "" + ChatColor.BOLD + "RESTORES: " + ChatColor.GREEN
					+ YAMLFile.POTIONS.getConfig().getInt(item + ".Mp") + " mana points" + durationAddition;
			potionLore.add(mpLine);
		}
		potionLore.add("");
		potionLore.add(ChatColor.YELLOW + "Requires Level " + YAMLFile.POTIONS.getConfig().getInt(item + ".Level"));
		String[] sellPriceSplit = YAMLFile.POTIONS.getConfig().getString(item + ".Sell").split(",");
		String gold = sellPriceSplit[0];
		String silver = sellPriceSplit[1];
		String copper = sellPriceSplit[2];
		potionLore.add(
				ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + ChatColor.RESET + "" + ChatColor.WHITE + gold + ChatColor.YELLOW
						+ "● " + ChatColor.WHITE + silver + ChatColor.GRAY + "● " + ChatColor.WHITE + copper + ChatColor.GOLD + "● ");
		potionMeta.setLore(potionLore);
		potion.setItemMeta(potionMeta);
		return potion;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack generateMiscItem(String item, int amount) {
		String name = YAMLFile.MISC_ITEMS.getConfig().getString(item + ".Name");
		String[] material = YAMLFile.MISC_ITEMS.getConfig().getString(item + ".Material").split(",");
		int id;
		try {
			id = Integer.parseInt(material[0]);
		} catch (NumberFormatException e) {
			return null;
		}
		short damageValue = 0;
		if (material.length > 1) {
			try {
				damageValue = Short.parseShort(material[1]);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		ItemStack miscItem = new ItemStack(id, amount, damageValue);
		ItemMeta miscItemMeta = miscItem.getItemMeta();
		miscItemMeta.setDisplayName(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', name));
		ArrayList<String> lore = new ArrayList<>();
		if (YAMLFile.MISC_ITEMS.getConfig().contains(item + ".Lore")) {
			for (String line : YAMLFile.MISC_ITEMS.getConfig().getStringList(item + ".Lore")) {
				lore.add(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', line));
			}
		}
		if (!YAMLFile.MISC_ITEMS.getConfig().getBoolean(item + ".Tradeable")) {
			lore.add(" ");
			if (!YAMLFile.MISC_ITEMS.getConfig().contains(item + ".Removeable")
					|| YAMLFile.MISC_ITEMS.getConfig().getBoolean(item + ".Removeable")) {
				lore.add(InfernalStrings.UNTRADEABLE);
			} else {
				lore.add(InfernalStrings.UNREMOVEABLE);
			}
		}
		if (YAMLFile.MISC_ITEMS.getConfig().getBoolean(item + ".Enchanted")) {
			miscItem = GeneralUtil.addGlow(miscItem);
		}

		// Sell price
		if (YAMLFile.MISC_ITEMS.getConfig().contains(item + ".Sell")) {
			String[] sellPriceSplit = YAMLFile.MISC_ITEMS.getConfig().getString(item + ".Sell").split(",");
			String gold = sellPriceSplit[0];
			String silver = sellPriceSplit[1];
			String copper = sellPriceSplit[2];
			lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + ChatColor.RESET + "" + ChatColor.WHITE + gold
					+ ChatColor.YELLOW + "● " + ChatColor.WHITE + silver + ChatColor.GRAY + "● " + ChatColor.WHITE + copper + ChatColor.GOLD
					+ "● ");
		}

		if (!lore.isEmpty()) {
			miscItemMeta.setLore(lore);
		}
		miscItem.setItemMeta(miscItemMeta);
		return miscItem;
	}

}
