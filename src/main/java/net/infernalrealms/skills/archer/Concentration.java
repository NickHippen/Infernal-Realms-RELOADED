package net.infernalrealms.skills.archer;

import java.util.ArrayList;

import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.Skill;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Concentration extends Skill {

	public static final String DISPLAY_NAME = "Concentration";

	private Player player;
	private int level;
	private double damageModifier;

	public Concentration(Player player) {
		this.player = player;
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.damageModifier = getDamageModifierAtLevel(this.level);
	}

	public void getEffect(LivingEntity target) {
		if (getLevel() == 0) {
			return;
		}
		MobEffects.applyConcentrationStack(target, player);
	}

	public ItemStack getIcon() {
		// Orange Dye
		ItemStack icon = new ItemStack(Material.DOUBLE_PLANT, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Every 4th attack on an enemy");
		lore.add("§7deals additional damage shortly");
		lore.add("§7after.");
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
		lore.add("§7§lDAMAGE: §7" + (int) Math.round(getDamageModifierAtLevel(level) * 100) + "%");
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
		return level < 8 ? 1 + level * 0.1 : (level == 7 ? 1.8 : 2);
	}

}
