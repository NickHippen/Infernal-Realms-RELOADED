package net.infernalrealms.skills.warrior;

import java.util.ArrayList;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.Skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @Type Passive
 * @Description Increases the player's health regeneration from all sources by x%
 */
public class Recovery extends Skill {

	public static final String DISPLAY_NAME = "Recovery";

	private Player player;
	private int level;
	private double healing;

	public Recovery(Player player) {
		this.player = player;
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.healing = getHealingAtLevel(level);
	}

	@Override
	public ItemStack getIcon() {
		// Yellow Dye
		ItemStack icon = new ItemStack(Material.NETHER_STAR, 1, (short) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Passively increases healing gained");
		lore.add("§7from all sources.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lBONUS HEALING: §7" + String.format("%.0f", 100 * getHealing()) + "%");
		}
		iconMeta.setLore(lore);
		icon.setItemMeta(iconMeta);
		return icon;
	}

	@Override
	public ItemStack getIconAtLevel(int level) {
		boolean unlocked = level <= this.getLevel();
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (short) (unlocked ? 9 : 8));
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l[ §r§aLevel " + level + " Stats §l]");
		lore.add("§7§lHEALING: §7" + String.format("%.2f", 100 * getHealingAtLevel(level)) + "%");
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

	public Player getPlayer() {
		return this.player;
	}

	public int getLevel() {
		return this.level;
	}

	public double getHealing() {
		return this.healing;
	}

	public static double getHealingAtLevel(int level) {
		return 1.00D + 0.0625D * (double) level;
	}

}
