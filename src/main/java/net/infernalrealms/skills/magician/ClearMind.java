package net.infernalrealms.skills.magician;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.Skill;

/**
 * Type: Passive
 * Description: Increases the player's mana regeneration by x%
 */
public class ClearMind extends Skill {

	public static final String DISPLAY_NAME = "Clear Mind";

	private int level;
	private double manaModifier;

	public ClearMind(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.manaModifier = getManaModifierAtLevel(this.level);
	}

	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.PRISMARINE_CRYSTALS, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Allows you to focus on your abilities");
		lore.add("§7to increase your mana regeneration.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lREGENERATION: §7" + (int) (getManaModifier() * 100) + "%");
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
		lore.add("§7§lREGENERATION: §7" + (int) Math.round(getManaModifierAtLevel(level) * 100) + "%");
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

	public double getManaModifier() {
		return this.manaModifier;
	}

	public static double getManaModifierAtLevel(int level) {
		return level < 7 ? 1 + 0.05 * level : (level == 7 ? 1.4 : 1.5);
	}

}
