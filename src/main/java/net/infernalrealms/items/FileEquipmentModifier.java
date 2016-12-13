package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;

import net.infernalrealms.general.YAMLFile;
import net.md_5.bungee.api.ChatColor;

public class FileEquipmentModifier implements EquipmentModifier {

	public static final List<FileEquipmentModifier> WEAPON_MODIFIERS = new ArrayList<>();
	public static final List<FileEquipmentModifier> ARMOR_MODIFIERS = new ArrayList<>();
	public static final Map<String, List<String>> MODIFIER_GROUPS = new HashMap<>();

	private static final Random random = new Random();

	private String name;
	private boolean prefix;
	private double minArmor;
	private double maxArmor;
	private double minDamageStat;
	private double maxDamageStat;
	private double minStamina;
	private double maxStamina;
	private double minAgility;
	private double maxAgility;
	private double minSpirit;
	private double maxSpirit;
	private double minHP;
	private double maxHP;
	private double minMP;
	private double maxMP;

	public FileEquipmentModifier(String name, boolean prefix, String armorRange, String damageStatsRange, String staminaRange,
			String agilityRange, String spiritRange, String hpRange, String mpRange) {
		this.name = name;
		this.prefix = prefix;

		String[] armorSplit = armorRange.split("-");
		String[] damageStatsSplit = damageStatsRange.split("-");
		String[] staminaSplit = staminaRange.split("-");
		String[] agilitySplit = agilityRange.split("-");
		String[] spiritSplit = spiritRange.split("-");
		String[] hpSplit = hpRange.split("-");
		String[] mpSplit = mpRange.split("-");

		try {
			this.minArmor = Double.parseDouble(armorSplit[0]);
		} catch (Exception e) {
			this.minArmor = -1D;
		}
		try {
			this.maxArmor = Double.parseDouble(armorSplit[1]);
		} catch (Exception e) {
			this.maxArmor = -1D;
		}

		try {
			this.minDamageStat = Double.parseDouble(damageStatsSplit[0]);
		} catch (Exception e) {
			this.minDamageStat = -1D;
		}
		try {
			this.maxDamageStat = Double.parseDouble(damageStatsSplit[1]);
		} catch (Exception e) {
			this.maxDamageStat = -1D;
		}

		try {
			this.minStamina = Double.parseDouble(staminaSplit[0]);
		} catch (Exception e) {
			this.minStamina = -1D;
		}
		try {
			this.maxStamina = Double.parseDouble(staminaSplit[1]);
		} catch (Exception e) {
			this.maxStamina = -1D;
		}

		try {
			this.minAgility = Double.parseDouble(agilitySplit[0]);
		} catch (Exception e) {
			this.minAgility = -1D;
		}
		try {
			this.maxAgility = Double.parseDouble(agilitySplit[1]);
		} catch (Exception e) {
			this.maxAgility = -1D;
		}

		try {
			this.minSpirit = Double.parseDouble(spiritSplit[0]);
		} catch (Exception e) {
			this.minSpirit = -1D;
		}
		try {
			this.maxSpirit = Double.parseDouble(spiritSplit[1]);
		} catch (Exception e) {
			this.maxSpirit = -1D;
		}

		try {
			this.minHP = Double.parseDouble(hpSplit[0]);
		} catch (Exception e) {
			this.minHP = -1D;
		}
		try {
			this.maxHP = Double.parseDouble(hpSplit[1]);
		} catch (Exception e) {
			this.maxHP = -1D;
		}

		try {
			this.minMP = Double.parseDouble(mpSplit[0]);
		} catch (Exception e) {
			this.minMP = -1D;
		}
		try {
			this.maxMP = Double.parseDouble(mpSplit[1]);
		} catch (Exception e) {
			this.maxMP = -1D;
		}
	}

	public String getName() {
		return name;
	}

	public boolean isPrefix() {
		return prefix;
	}

	public double getMinArmor() {
		return minArmor;
	}

	public double getMaxArmor() {
		return maxArmor;
	}

	public boolean hasArmor() {
		return getMinArmor() != -1;
	}

	public double getRandomArmor() {
		if (hasArmor()) {
			return randomRange(getMinArmor(), getMaxArmor());
		}
		return 0;
	}

	public double getMinDamageStat() {
		return minDamageStat;
	}

	public double getMaxDamageStat() {
		return maxDamageStat;
	}

	public boolean hasDamageStat() {
		return getMinDamageStat() != -1;
	}

	public double getRandomDamageStat() {
		if (hasDamageStat()) {
			return randomRange(getMinDamageStat(), getMaxDamageStat());
		}
		return 0;
	}

	public double getMinStamina() {
		return minStamina;
	}

	public double getMaxStamina() {
		return maxStamina;
	}

	public boolean hasStamina() {
		return getMinStamina() != -1;
	}

	public double getRandomStamina() {
		if (hasStamina()) {
			return randomRange(getMinStamina(), getMaxStamina());
		}
		return 0;
	}

	public double getMinAgility() {
		return minAgility;
	}

	public double getMaxAgility() {
		return maxAgility;
	}

	public boolean hasAgility() {
		return getMinAgility() != -1;
	}

	public double getRandomAgility() {
		if (hasAgility()) {
			return randomRange(getMinAgility(), getMaxAgility());
		}
		return 0;
	}

	public double getMinSpirit() {
		return minSpirit;
	}

	public double getMaxSpirit() {
		return maxSpirit;
	}

	public boolean hasSpirit() {
		return getMinSpirit() != -1;
	}

	public double getRandomSpirit() {
		if (hasSpirit()) {
			return randomRange(getMinSpirit(), getMaxSpirit());
		}
		return 0;
	}

	public double getMinHP() {
		return minHP;
	}

	public double getMaxHP() {
		return maxHP;
	}

	public boolean hasHP() {
		return getMinHP() != -1;
	}

	public double getRandomHP() {
		if (hasHP()) {
			return randomRange(getMinHP(), getMaxHP());
		}
		return 0;
	}

	public double getMinMP() {
		return minMP;
	}

	public double getMaxMP() {
		return maxMP;
	}

	public boolean hasMP() {
		return getMinMP() != -1;
	}

	public double getRandomMP() {
		if (hasMP()) {
			return randomRange(getMinMP(), getMaxMP());
		}
		return 0;
	}

	private double randomRange(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}

	public static FileEquipmentModifier getWeaponModifier(String name) {
		for (FileEquipmentModifier em : WEAPON_MODIFIERS) {
			if (em.getName().equalsIgnoreCase(name)) {
				return em;
			}
		}
		return null;
	}

	public static FileEquipmentModifier getArmorModifier(String name) {
		for (FileEquipmentModifier em : ARMOR_MODIFIERS) {
			if (em.getName().equalsIgnoreCase(name)) {
				return em;
			}
		}
		return null;
	}

	public static List<String> getModifierGroup(String name) {
		return MODIFIER_GROUPS.get(name);
	}

	public static void registerModifiers() {
		for (String name : YAMLFile.WEAPON_MODIFIERS.getConfig().getKeys(false)) {
			boolean prefix = YAMLFile.WEAPON_MODIFIERS.getConfig().getBoolean(name + ".Prefix");
			String damageStatsRange = YAMLFile.WEAPON_MODIFIERS.getConfig().getString(name + ".Damage Stats");
			String staminaRange = YAMLFile.WEAPON_MODIFIERS.getConfig().getString(name + ".Stamina");
			String agilityRange = YAMLFile.WEAPON_MODIFIERS.getConfig().getString(name + ".Agility");
			String spiritRange = YAMLFile.WEAPON_MODIFIERS.getConfig().getString(name + ".Spirit");
			String hpRange = YAMLFile.WEAPON_MODIFIERS.getConfig().getString(name + ".HP");
			String mpRange = YAMLFile.WEAPON_MODIFIERS.getConfig().getString(name + ".MP");
			WEAPON_MODIFIERS.add(new FileEquipmentModifier(name, prefix, "1-1", damageStatsRange, staminaRange, agilityRange, spiritRange,
					hpRange, mpRange));
		}
		for (String name : YAMLFile.ARMOR_MODIFIERS.getConfig().getKeys(false)) {
			boolean prefix = YAMLFile.ARMOR_MODIFIERS.getConfig().getBoolean(name + ".Prefix");
			String armorRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".Armor");
			String damageStatsRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".Damage Stats");
			String staminaRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".Stamina");
			String agilityRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".Agility");
			String spiritRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".Spirit");
			String hpRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".HP");
			String mpRange = YAMLFile.ARMOR_MODIFIERS.getConfig().getString(name + ".MP");
			ARMOR_MODIFIERS.add(new FileEquipmentModifier(name, prefix, armorRange, damageStatsRange, staminaRange, agilityRange, spiritRange,
					hpRange, mpRange));
		}
		for (String name : YAMLFile.MODIFIER_GROUPS.getConfig().getKeys(false)) {
			List<String> ems = new ArrayList<>();
			for (String modifierName : YAMLFile.MODIFIER_GROUPS.getConfig().getStringList(name)) {
				if (getWeaponModifier(modifierName) == null && getArmorModifier(modifierName) == null) {
					System.out.println(getWeaponModifier(modifierName) + ", " + getArmorModifier(modifierName));
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid modifier name \"" + modifierName
							+ "\" in group \"" + name + "\".");
					continue;
				}
				ems.add(modifierName);
			}
			MODIFIER_GROUPS.put(name, ems);
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Item registration complete: ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "Armor Modifiers:");
		for (FileEquipmentModifier em : ARMOR_MODIFIERS) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + em.getName());
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "" + ChatColor.UNDERLINE + "Weapon Modifiers:");
		for (FileEquipmentModifier em : WEAPON_MODIFIERS) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + em.getName());
		}
	}
}
