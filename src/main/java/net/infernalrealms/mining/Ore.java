package net.infernalrealms.mining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.blacksmithing.ScrapItem;
import net.infernalrealms.items.Rarity;
import net.infernalrealms.player.PlayerData;

public enum Ore {

	//@formatter:off
	COAL(Material.COAL_ORE, "Coal Ore", 0, 3.0, ChatColor.GRAY, 15, 20, new ScrapItem[] {ScrapItem.WOODLEATHER}, "Coarse", "Normal", "Solid", "Onyx"),
	LAPIS(Material.LAPIS_ORE, "Lapis Ore", 20, 4.0, ChatColor.WHITE, 90, 110, new ScrapItem[] {ScrapItem.CHAINSTONE}, "Splinted", "Normal", "Lustrous", "Amethystine"),
	EMERALD(Material.EMERALD_ORE, "Emerald Ore", 20, 5.0, ChatColor.DARK_GREEN, 160, 180, new ScrapItem[] {ScrapItem.CHAINSTONE, ScrapItem.IRON}, "Cracked", "Normal", "Gleaming", "Jadeite"),
	IRON(Material.EMERALD_ORE, "Iron Ore",40, 6.0, ChatColor.GREEN, 300, 350, new ScrapItem[] {ScrapItem.IRON}, "Crude", "Normal", "Hulk", "Titanium Nugget"),
	GOLD(Material.GOLD_ORE, "Gold Ore", 60, 7.0, ChatColor.BLUE, 650, 750, new ScrapItem[] {ScrapItem.GOLD}, "Formless", "Normal", "Lavish", "Tellurium Nugget"),
	REDSTONE(Material.REDSTONE_ORE, "Ruby Ore", 60, 8.0, ChatColor.RED, 800, 900, new ScrapItem[] {ScrapItem.GOLD, ScrapItem.DIAMOND}, "Small", "Normal", "Sparkling", "Painite"),
	DIAMOND(Material.DIAMOND_ORE, "Diamond Ore",80, 9.0, ChatColor.DARK_PURPLE, 1200, 1350, new ScrapItem[] {ScrapItem.DIAMOND}, "Cracked", "Normal", "Agleam", "Pink Star")
	;
	//@formatter:on

	private static final Random RANDOM = new Random();
	private static final float TRASH_CHANCE = 0.5F;
	private static final float COMMON_CHANCE = TRASH_CHANCE + 0.38F;
	private static final float RARE_CHANCE = COMMON_CHANCE + 0.10F;

	private Material material;
	private String displayName;
	private int minLevel;
	private int maxLevel; // Not max level to be mined; just for determining gem tier requirements
	private double durability;
	private ChatColor color;
	private int minExp;
	private int maxExp;
	private ScrapItem[] scrap;
	private String[] prefixes;

	private Ore(Material material, String displayName, int minLevel, /*int maxLevel, FIXME*/ double durability, ChatColor color, int minExp,
			int maxExp, ScrapItem[] scrap, String... prefixes) {
		this.material = material;
		this.displayName = displayName;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.durability = durability;
		this.color = color;
		this.minExp = minExp;
		this.maxExp = maxExp;
		this.scrap = scrap;
		this.prefixes = prefixes;
	}

	public Material getMaterial() {
		return material;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public double getDurability() {
		return durability;
	}

	public ChatColor getColor() {
		return color;
	}

	public int getRandomExp(int index) {
		return (int) ((RANDOM.nextInt((maxExp - minExp) + 1) + minExp) * getExpMultipler(index));
	}

	public ScrapItem[] getScrap() {
		return scrap;
	}

	public String[] getPrefixes() {
		return prefixes;
	}

	public boolean isMineable(PlayerData playerData) {
		return playerData.getMiningLevel() >= minLevel
				&& PickaxeFactory.isPickaxeMaterial(playerData.getPlayer().getItemInHand().getType());
	}

	public OreItem getRandomRarityDrop() {
		ItemStack ore = new ItemStack(getMaterial(), 1, (short) 0);
		ItemMeta oreMeta = ore.getItemMeta();
		float roll = RANDOM.nextFloat();
		roll = 1;
		int index;
		String quality;
		if (roll < TRASH_CHANCE) {
			index = 0;
			quality = "Trash";
		} else if (roll < COMMON_CHANCE) {
			index = 1;
			quality = "Common";
		} else if (roll < RARE_CHANCE) {
			index = 2;
			quality = "Rare";
		} else { // LEGENDARY_CHANCE
			index = 3;
			quality = "Legendary";

			oreMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + getPrefixes()[index]);
		}
		if (index < 3) {
			oreMeta.setDisplayName(getColor() + "" + ChatColor.BOLD + getPrefixes()[index] + " " + getDisplayName());
		}
		oreMeta.setLore(Arrays.asList(ChatColor.GRAY + quality + " Ore"));
		ore.setItemMeta(oreMeta);

		return new OreItem(ore, index);
	}

	public ItemStack getGemForm() {
		Material material;
		byte dur = 0;
		String tier;
		switch (this) {
		case COAL:
			material = Material.COAL;
			dur = 1;
			tier = "1";
			break;
		case LAPIS:
			material = Material.INK_SACK;
			dur = 4;
			tier = "2";
			break;
		case EMERALD:
			material = Material.EMERALD;
			tier = "2-3";
			break;
		case IRON:
			material = Material.CLAY_BALL;
			tier = "3";
			break;
		case GOLD:
			material = Material.GOLD_NUGGET;
			tier = "4";
			break;
		case REDSTONE:
			material = Material.INK_SACK;
			dur = 1;
			tier = "4-5";
			break;
		case DIAMOND:
			material = Material.INK_SACK;
			dur = 9;
			tier = "5";
			break;
		default:
			return null;
		}
		ItemStack gem = new ItemStack(material, 1, dur);
		ItemMeta gemMeta = gem.getItemMeta();
		gemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + getPrefixes()[3]);
		List<String> gemLore = new ArrayList<>();
		gemLore.add(ChatColor.WHITE + "Tier " + tier + " Legendary Material");
		gemLore.add(ChatColor.GRAY + "Used in smithing rare");
		gemLore.add(ChatColor.GRAY + "and epic level items.");
		gemMeta.setLore(gemLore);
		gem.setItemMeta(gemMeta);
		return gem;
	}

	public static Ore getOreFromMaterial(Material material) {
		if (material == Material.GLOWING_REDSTONE_ORE) {
			return REDSTONE;
		}
		for (Ore ore : values()) {
			if (ore.getMaterial() == material) {
				return ore;
			}
		}
		return null;
	}

	private static double getExpMultipler(int index) {
		switch (index) {
		case 0:
			return 0.6;
		case 1:
			return 1;
		case 2:
			return 1.4;
		case 3:
			return 5;
		}
		return 0;
	}

	public static boolean isOre(ItemStack item) {
		if (item == null) {
			return false;
		}
		for (Ore ore : values()) {
			if (ore.getMaterial() != item.getType()) {
				continue;
			}
			return true;
		}
		return false;
	}

	/**
	 * qualityIndex:
	 * <ul>
	 * <li>0 = trash</li>
	 * <li>1 = common</li>
	 * <li>2 = rare</li>
	 * <li>3 = legendary</li>
	 * </ul>
	 * 
	 * 3 trash ore = 1 trash scrap<br />
	 * 5 common ore = 1 common scrap, 1 uncommon scrap<br /> 
	 * 5 rare ore = 1 rare scrap, 1 epic scrap<br />
	 * 1 legendary ore = equivalent gem
	 */
	public static List<ItemStack> refineOre(ItemStack item) {
		List<ItemStack> refined = new ArrayList<>();
		Ore ore = getOreFromMaterial(item.getType());
		String loreLine = item.getItemMeta().getLore().get(0);
		int multiplier = ore == Ore.EMERALD || ore == Ore.REDSTONE ? 2 : 1;
		if (loreLine.contains("Trash")) {
			int amount = item.getAmount() / 3;
			amount *= multiplier;
			if (amount > 0) {
				refined.add(ore.getScrap()[0].getItemStack(Rarity.TRASH, amount));
			}
		} else if (loreLine.contains("Common")) {
			int amount = item.getAmount() / 5;
			amount *= multiplier;
			if (amount > 0) {
				refined.add(ore.getScrap()[0].getItemStack(Rarity.COMMON, amount));
				refined.add(ore.getScrap()[0].getItemStack(Rarity.UNCOMMON, amount));
			}
		} else if (loreLine.contains("Rare")) {
			int amount = item.getAmount() / 5;
			amount *= multiplier;
			if (amount > 0) {
				refined.add(ore.getScrap()[0].getItemStack(Rarity.RARE, amount));
				refined.add(ore.getScrap()[0].getItemStack(Rarity.EPIC, amount));
			}
		} else if (loreLine.contains("Legendary")) {
			ItemStack gem = ore.getGemForm();
			gem.setAmount(item.getAmount());
			refined.add(gem);
		}
		return refined;
	}

	public static class OreItem {

		private ItemStack bukkitItem;
		private int rarityIndex;

		public OreItem(ItemStack bukkitItem, int rarityIndex) {
			this.bukkitItem = bukkitItem;
			this.rarityIndex = rarityIndex;
		}

		public ItemStack getBukkitItem() {
			return bukkitItem;
		}

		public int getRarityIndex() {
			return rarityIndex;
		}

	}

}
