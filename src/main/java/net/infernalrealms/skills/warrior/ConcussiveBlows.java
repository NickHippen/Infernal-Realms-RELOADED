package net.infernalrealms.skills.warrior;

import java.util.ArrayList;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.Skill;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @Type Passive
 * @Description Every 5 hits stuns the target for x seconds
 */
public class ConcussiveBlows extends Skill {

	public static final String DISPLAY_NAME = "Concussive Blows";

	private Player player;
	private int level;
	private double stunDuration;

	public ConcussiveBlows(Player player) {
		this.player = player;
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.stunDuration = getStunDurationAtLevel(level);
	}

	public void getEffect(LivingEntity target) {
		if (getLevel() == 0) {
			return;
		}
		if (hasCooldown(target)) {
			return;
		}
		MobEffects.applyConcussiveBlowStack(target, player);
	}

	public static boolean hasCooldown(Entity e) {
		return e.hasMetadata("S:" + DISPLAY_NAME);
	}

	public static void setCooldown(final Entity e) {
		e.setMetadata("S:" + DISPLAY_NAME, new FixedMetadataValue(InfernalRealms.getPlugin(), DISPLAY_NAME));
		new BukkitRunnable() {

			public void run() {
				e.removeMetadata("S:" + DISPLAY_NAME, InfernalRealms.getPlugin());
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 160L);
	}

	@Override
	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (short) 15);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Every 5th hit on a monster applies");
		lore.add("§7a stun. Monsters cannot be stunned");
		lore.add("§7more than once every 8 seconds.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lSTUN TIME: §7" + String.format("%.1f", getStunDuration()) + " sec");
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
		lore.add("§7§lSTUN TIME: §7" + String.format("%.1f", getStunDurationAtLevel(level)) + " sec");
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

	public double getStunDuration() {
		return this.stunDuration;
	}

	public static double getStunDurationAtLevel(int level) {
		return 1.4D + (double) level / 5D;
	}

}
