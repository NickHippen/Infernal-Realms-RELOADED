package net.infernalrealms.skills.archer;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.Skill;

/**
 * Type: Passive
 * Description: Critical strikes deal more damage
 */
public class SharpEye extends Skill {

	public static final String DISPLAY_NAME = "Sharp Eye";

	private int level;
	private double damageModifier;

	public SharpEye(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.damageModifier = getDamageModifierAtLevel(this.level);
	}

	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.FERMENTED_SPIDER_EYE, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Increases the damage multiplier");
		lore.add("§7of your critical strikes.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifier() * 100) + "%");
		}
		iconMeta.setLore(lore);
		icon.setItemMeta(iconMeta);
		return icon;
	}

	public ItemStack getIconAtLevel(int level) {
		boolean unlocked = level <= this.getLevel();
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (short) (unlocked ? 9 : 8));
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l[ §r§aLevel " + level + " Stats §l]");
		lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifierAtLevel(level) * 100) + "%");
		lore.add("§7§lSP COST: §7" + (level < 7 ? "1" : "2"));
		lore.add(unlocked ? "§a§l[ §r§aUNLOCKED §l]" : "§c§l[ §r§cLOCKED §l]");
		if (level < this.getLevel()) { // Add Strike
			iconMeta.setDisplayName("§m" + iconMeta.getDisplayName());
			for (int i = 0; i < lore.size(); i++)
				lore.set(i, "§m" + lore.get(i));
		}
		iconMeta.setLore(lore);
		icon.setItemMeta(iconMeta);
		return icon;
	}

	public int getLevel() {
		return this.level;
	}

	public double getDamageModifier() {
		return this.damageModifier;
	}

	public static double getDamageModifierAtLevel(int level) {
		return level < 7 ? 1.5 + 0.1 * level : (level == 7 ? 2.3 : 2.5);
	}

	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
