package net.infernalrealms.blacksmithing;

import org.bukkit.Material;

public enum RecipeItemType {

	// @formatter:off
	SWORD(Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD),
	BOW(Material.BOW),
	WAND(Material.STICK, Material.BLAZE_ROD),
	HELMET(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET),
	CHESTPLATE(Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE),
	LEGGINGS(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS),
	BOOTS(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS),
	HILT,
	BINDING,
	REINFORCEMENT,
	;
	// @formatter:on

	private Material[] materials;

	private RecipeItemType(Material... materials) {
		this.materials = materials;
	}

	public boolean isOfType(Material material) {
		for (Material type : materials) {
			if (type == material) {
				return true;
			}
		}
		return false;
	}

	public static RecipeItemType fromString(String string) {
		for (RecipeItemType recipeItemType : values()) {
			if (recipeItemType.toString().equalsIgnoreCase(string)) {
				return recipeItemType;
			}
		}
		return null;
	}
}
