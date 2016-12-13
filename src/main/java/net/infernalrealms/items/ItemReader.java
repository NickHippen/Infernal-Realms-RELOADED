package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.player.Stat;
import net.infernalrealms.util.IndividualMap;
import net.infernalrealms.util.InfernalStrings;

public class ItemReader {

	private ItemReader() {}

	public static long getMoneyValue(ItemStack item) {
		if (item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			if (itemMeta.hasLore()) {
				List<String> itemLore = itemMeta.getLore();
				return getMoneyValue(itemLore, item.getAmount());
			}
		}
		return 0;
	}

	public static long getMoneyValue(List<String> itemLore, int itemAmount) {
		for (String s : itemLore) {
			s = ChatColor.stripColor(s);
			String[] itemLoreSplit = s.split(" ");
			for (String s2 : itemLoreSplit) {
				if (s2.equals("Sell")) {
					long gold;
					long silver;
					long copper;
					try {
						gold = Long.parseLong(itemLoreSplit[2].replace("●", ""));
						silver = Long.parseLong(itemLoreSplit[3].replace("●", ""));
						copper = Long.parseLong(itemLoreSplit[4].replace("●", ""));
					} catch (NumberFormatException e) {
						return 0;
					}
					return (copper + (silver * 100) + (gold * 10000)) * itemAmount;
				}
			}
		}
		String sellButtonLore = ChatColor.stripColor(itemLore.get(0));
		if (sellButtonLore.contains("amount and receive ")) {
			String[] itemLoreSplit = sellButtonLore.split(" ");
			long gold;
			long silver;
			long copper;
			try {
				gold = Long.parseLong(itemLoreSplit[3].replace("●", ""));
				silver = Long.parseLong(itemLoreSplit[4].replace("●", ""));
				copper = Long.parseLong(itemLoreSplit[5].replace("●", ""));
			} catch (NumberFormatException e) {
				return 0;
			}
			return copper + (silver * 100) + (gold * 10000);
		}
		return 0;
	}

	public static String getMoneyAsString(long money) {
		long copper = money;
		long silver = copper / 100L;
		copper -= silver * 100L;
		long gold = silver / 100L;
		silver -= gold * 100L;
		return ChatColor.RESET + "" + ChatColor.WHITE + gold + ChatColor.YELLOW + "● " + ChatColor.WHITE + silver + ChatColor.GRAY + "● "
				+ ChatColor.WHITE + copper + ChatColor.GOLD + "●";
	}

	public static boolean isPotion(ItemStack item) {
		if (item != null) {
			if (item.hasItemMeta()) {
				ItemMeta itemMeta = item.getItemMeta();
				if (itemMeta.hasLore()) {
					for (String line : itemMeta.getLore()) {
						if (line.contains(ChatColor.GREEN + "" + ChatColor.BOLD + "RESTORES: ")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static int[] getPotionValue(ItemStack item, String value) {
		ItemMeta itemMeta = item.getItemMeta();
		for (String line : itemMeta.getLore()) {
			if (line.contains(ChatColor.GREEN + "" + ChatColor.BOLD + "RESTORES: ")) {
				if (line.contains(value)) {
					line = ChatColor.stripColor(line);
					String[] lineSplit = line.split(" ");
					int valueAmount = 0;
					try {
						valueAmount = Integer.parseInt(lineSplit[1]);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return null;
					}
					if (line.contains("over")) {
						int durationAmount = 0;
						try {
							durationAmount = Integer.parseInt(lineSplit[5]);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							return null;
						}
						return new int[] { valueAmount, durationAmount };
					}
					return new int[] { valueAmount };
				}
			}
		}
		return null;
	}

	public static boolean isTradeable(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return true;
		}
		return !item.getItemMeta().getLore().contains(InfernalStrings.UNTRADEABLE)
				&& !item.getItemMeta().getLore().contains(InfernalStrings.UNREMOVEABLE);
	}

	public static boolean isUnremovable(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return false;
		}
		return item.getItemMeta().getLore().contains(InfernalStrings.UNREMOVEABLE);
	}

	public static int getRequiredLevel(ItemStack item) {
		if (item != null && item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			if (itemMeta.hasLore()) {
				return getRequiredLevel(itemMeta.getLore());
			}
		}
		return 1;
	}

	public static int getRequiredLevel(List<String> itemLore) {
		for (String line : itemLore) {
			if (line.contains("Requires Level ")) {
				try {
					return Integer.parseInt(line.split(" ")[2]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					System.out.println("Error parsing required level.");
					break;
				}
			}
		}
		return 1;
	}

	public static String getRequiredClass(ItemStack item) {
		if (item != null && item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			if (itemMeta.hasLore()) {
				return getRequiredClass(itemMeta.getLore());
			}
		}
		return null;
	}

	public static String getRequiredClass(List<String> itemLore) {
		for (String line : itemLore) {
			if (line.contains("Class: ")) {
				return line.split(" ")[1];
			}
		}
		return null;
	}

	public static boolean isEquipable(Player player, ItemStack item) {
		PlayerData playerData = PlayerData.getData(player);
		String requiredClass = getRequiredClass(item);
		return (requiredClass != null ? playerData.getPlayerClass().equals(requiredClass) : true)
				&& (playerData.getLevel() >= getRequiredLevel(item));
	}

	public static boolean isMountArmor(ItemStack item) {
		return item.getType() == Material.IRON_BARDING || item.getType() == Material.GOLD_BARDING
				|| item.getType() == Material.DIAMOND_BARDING;
	}

	public static int getCraftingValue(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return 0;
		}

		return getCraftingValue(item.getItemMeta().getLore());
	}

	public static int getCraftingValue(List<String> itemLore) {
		for (String line : itemLore) {
			if (!line.startsWith(ChatColor.GRAY + "Crafting Value: " + ChatColor.UNDERLINE)) {
				continue;
			}
			try {
				return Integer.parseInt(line.replaceFirst(ChatColor.GRAY + "Crafting Value: " + ChatColor.UNDERLINE, ""));
			} catch (NumberFormatException e) {
				continue;
			}
		}
		return 0;
	}

	public static int getScrapValueIgnoreAmount(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return 0;
		}

		return getScrapValue(item.getItemMeta().getLore());
	}

	public static int getScrapValue(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return 0;
		}

		return item.getAmount() * getScrapValue(item.getItemMeta().getLore());
	}

	public static int getScrapValue(List<String> itemLore) {
		for (String line : itemLore) {
			if (!line.startsWith(ChatColor.GRAY + "Scrap Value: " + ChatColor.UNDERLINE)) {
				continue;
			}
			try {
				return Integer.parseInt(line.replaceFirst(ChatColor.GRAY + "Scrap Value: " + ChatColor.UNDERLINE, ""));
			} catch (NumberFormatException e) {
				continue;
			}
		}
		return 0;
	}

	/**
	 * Determines whether or not the item is eligible for ignoring the force onto holding item slot 0
	 * @param item
	 * @return whether or not the item is holdable
	 */
	public static boolean isHoldable(ItemStack item) {
		return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().contains(InfernalStrings.HOLDABLE);
	}

	public static int getStatFromItem(ItemStack item, Stat stat) {
		if (item == null || item.getType() == Material.AIR || !item.getItemMeta().hasLore()) {
			return 0;
		}

		return getStatFromItem(item.getItemMeta().getLore(), stat);
	}

	public static int getStatFromItem(List<String> equipLore, Stat stat) {
		int statAmount = 0;
		for (String line : equipLore) {
			if (!line.contains(stat.getName())) {
				continue;
			}
			String[] lineSplit = line.split(" ");
			statAmount += Integer.parseInt(ChatColor.stripColor(lineSplit[!line.contains("▣") ? 0 : 2]).replace("+", ""));
		}
		return statAmount;
	}

	public static IndividualMap<Integer, Integer> getMinMaxFromItem(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getType() != Material.AIR) {
			if (item.getItemMeta().hasLore()) {
				return getMinMaxFromItem(item.getItemMeta().getLore());
			}
		}
		return new IndividualMap<>();
	}

	public static IndividualMap<Integer, Integer> getMinMaxFromItem(List<String> weaponLore) {
		IndividualMap<Integer, Integer> values = new IndividualMap<>();
		for (String s : weaponLore) {
			String[] weaponLoreSplit = s.split(" ");
			for (String s2 : weaponLoreSplit) {
				if (s2.equals("Damage")) {
					int minDamage = Integer.parseInt(ChatColor.stripColor(weaponLoreSplit[0]));
					int maxDamage = Integer.parseInt(weaponLoreSplit[2]);
					values.setFirstValue(minDamage);
					values.setSecondValue(maxDamage);
					return values;
				}
			}
		}
		return values;
	}

	public static List<GemColor> getSocketsSlots(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getType() != Material.AIR) {
			if (!item.getItemMeta().hasLore()) {
				return new ArrayList<>(0);
			}
			return getSocketsSlots(item.getItemMeta().getLore());
		}
		return new ArrayList<>(0);
	}

	public static List<GemColor> getSocketsSlots(List<String> itemLore) {
		List<GemColor> sockets = new ArrayList<>(4);
		for (String line : itemLore) {
			if (!line.contains("▣")) {
				continue; // Not a socket line
			}
			Matcher matcher = GemColor.getGemColorPattern().matcher(line);
			if (matcher.find()) {
				ChatColor color = ChatColor.getByChar(matcher.group().charAt(1));
				if (color != null) {
					sockets.add(GemColor.fromChatColor(color));
				}
			}
		}
		return sockets;
	}

	public static int getItemLevel(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
			return 0;
		}

		return getItemLevel(item.getItemMeta().getLore());
	}

	public static int getItemLevel(List<String> itemLore) {
		for (String line : itemLore) {
			if (!line.startsWith(ChatColor.RESET + "" + ChatColor.GRAY + "Item Level ")) {
				continue;
			}
			try {
				return Integer.parseInt(line.replaceFirst(ChatColor.RESET + "" + ChatColor.GRAY + "Item Level ", ""));
			} catch (NumberFormatException e) {
				continue;
			}
		}
		return 0;
	}

	public static Rarity getRarity(ItemStack item) {
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
			return null;
		}
		String name = item.getItemMeta().getDisplayName();
		if (name.length() < 2) {
			return null;
		}
		ChatColor color = ChatColor.getByChar(name.substring(1, 2));
		return Rarity.fromChatColor(color);
	}

}
