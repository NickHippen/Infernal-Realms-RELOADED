package net.infernalrealms.items;

import java.util.Random;

import org.bukkit.Material;

public enum EquipmentGroup {

	//@formatter:off
	HELMET(new String[] {
			"Helmet",
			"Armet",
			"Sallet",
			"Great Helm",
			"Barbute",
	},
	Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET),
	
	CHESTPLATE(new String[] {
			"Chestplate",
			"Breastplate",
			"Cuirass",
			"Brigandine",
	},
	Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE),
	
	LEGGINGS(new String[] {
			"Leggings",
			"Pants",
			"Greaves",
	},
	Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS),
	
	BOOTS(new String[] {
			"Boots",
			"Sandals",
			"Sabatons",
			"Clogs",
	},
	Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS),
	
	SWORD("Strength", new String[] {
			"Sword",
			"Dagger",
			"Scimitar",
			"Rapier",
			"Longsword",
			"Claymore",
	},
	Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD),
	
	BOW("Dexterity", new String[] {
			"Bow",
			"Shortbow",
			"Longbow",
			"Recurve Bow",
			"Composite Bow",
			"Mongol Bow",
	},
	Material.BOW),
	
	WAND("Intelligence", new String[] {
			"Wand",
			"Focus",
			"Rod",
			"Stave",
	},
	Material.STICK, Material.BLAZE_ROD),
	
	;
	//@formatter:on

	private Material[] materials;
	private String[] displayNames;
	private String statType;

	private EquipmentGroup(String[] displayNames, Material... materials) {
		this(null, displayNames, materials);
	}

	private EquipmentGroup(String statType, String[] displayNames, Material... materials) {
		this.statType = statType;
		this.materials = materials;
		this.displayNames = displayNames;
	}

	public Material getMaterialForLevel(int level) {
		switch (this) {
		case HELMET:
		case CHESTPLATE:
		case LEGGINGS:
		case BOOTS:
		case SWORD:
			try {
				return materials[level / 20];
			} catch (ArrayIndexOutOfBoundsException e) {
				return materials[materials.length - 1];
			}
		case BOW:
			return materials[0];
		case WAND:
			try {
				return materials[level / 50];
			} catch (ArrayIndexOutOfBoundsException e) {
				return materials[materials.length - 1];
			}
		}
		// Should never get here.
		return null;
	}

	public static EquipmentGroup fromString(String string) {
		for (EquipmentGroup eg : values()) {
			if (eg.toString().equalsIgnoreCase(string)) {
				return eg;
			}
		}
		return null;
	}

	public boolean isWeapon() {
		switch (this) {
		case SWORD:
		case BOW:
		case WAND:
			return true;
		default:
			return false;
		}
	}

	public boolean isArmor() {
		return !isWeapon();
	}

	public boolean isOfType(Material material) {
		for (Material type : materials) {
			if (type == material) {
				return true;
			}
		}
		return false;
	}

	public String getRandomDisplayName() {
		return displayNames[new Random().nextInt(displayNames.length)];
	}

	public String getPrimaryDisplayName() {
		return displayNames[0];
	}

	public String getStatType() {
		if (isArmor()) {
			return new String[] { "Strength", "Dexterity", "Intelligence" }[new Random().nextInt(3)];
		}
		return this.statType;
	}
}
