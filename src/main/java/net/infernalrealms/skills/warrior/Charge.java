package net.infernalrealms.skills.warrior;

import java.util.ArrayList;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.ActiveSkill;
import net.infernalrealms.util.InfernalEffects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * @Type Active
 * @Description Charges 10 blocks in the direction you are aiming and empowers next basic attack to stun enemy for 1 second.
 */
public class Charge extends ActiveSkill {

	public static final String DISPLAY_NAME = "Charge";

	private Player player;
	private int level;
	private int mpCost;
	private double damageModifier;
	private int cooldown;

	public Charge(Player player) {
		this.setPlayer(player);
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.mpCost = getMpCostAtLevel(level);
		this.damageModifier = getDamageModifierAtLevel(level);
		this.cooldown = getCooldownAtLevel(level);
	}

	public void activate() {
		if (getLevel() == 0) {
			return;
		}
		if (hasCooldown()) {
			player.playSound(player.getLocation(), Sound.ENTITY_HORSE_LAND, 1F, 0F);
			player.sendMessage(ChatColor.RED + DISPLAY_NAME + " is currently on cooldown!");
			return;
		}
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getMana() < this.mpCost) {
			InfernalEffects.playInsufficientManaSound(getPlayer());
			player.sendMessage(ChatColor.RED + "You do not have enough mana to use " + DISPLAY_NAME + ".");
			return;
		}
		playerData.modifyMana(-this.mpCost);
		setCooldown();
		final Vector v = player.getEyeLocation().getDirection();
		v.setY(0);
		new BukkitRunnable() {
			int count = 10;

			public void run() {
				player.setVelocity(v);
				for (Entity e : player.getNearbyEntities(0.5, 1, 0.5)) {
					if (e instanceof LivingEntity && !(e instanceof Player)) {
						LivingEntity le = (LivingEntity) e;
						MobEffects.damage(getPlayer(), le, getDamageModifier());
						cancel();
						player.setVelocity(v.zero());
						player.setNoDamageTicks(30);
						MobEffects.stun(le, 80L);
					}
				}
				if (--count <= 0) {
					cancel();
				}
			}

		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ItemStack getIcon() {
		// Light Gray Dye
		ItemStack icon = new ItemStack(Material.RABBIT_FOOT, 1, (short) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Charges in the targeted direction,");
		lore.add("§7dealing damage and stunning the");
		lore.add("§7first target for 4 seconds.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP Cost: §7" + getMpCost());
			lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifier() * 100) + "%");
			lore.add("§7§lCOOLDOWN: §7" + getCooldown() + " sec");
		}
		iconMeta.setLore(lore);
		icon.setItemMeta(iconMeta);
		return icon;
	}

	public ItemStack getIconAtLevel(int level) {
		boolean unlocked = level <= this.getLevel();
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (short) (unlocked ? 9 : 8));
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l[ §r§aLevel " + level + " Stats §l]");
		lore.add("§7§lMP COST: §7" + getMpCostAtLevel(level));
		lore.add("§7§lDAMAGE: §7" + String.format("%.0f", getDamageModifierAtLevel(level) * 100) + "%");
		lore.add("§7§lCOOLDOWN: §7" + getCooldownAtLevel(level) + " sec");
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

	public boolean hasCooldown() {
		return player.hasMetadata("S:" + DISPLAY_NAME);
	}

	public void setCooldown() {
		player.setMetadata("S:" + DISPLAY_NAME, new FixedMetadataValue(InfernalRealms.getPlugin(), DISPLAY_NAME));
		new BukkitRunnable() {
			int cooldown = getCooldown();
			ItemStack icon = getIcon();
			Material originalMaterial = icon.getType();

			@Override
			public void run() {
				if (cooldown > 0) {
					icon.setAmount(cooldown);
					icon.setType(Material.BARRIER);
					player.getInventory().setItem(1, icon);
				} else {
					player.removeMetadata("S:" + DISPLAY_NAME, InfernalRealms.getPlugin());
					icon.setType(originalMaterial);
					player.getInventory().setItem(1, icon);
					cancel();
				}
				cooldown--;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 20L);
	}

	public int getLevel() {
		return this.level;
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public double getDamageModifier() {
		return this.damageModifier;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getMpCostAtLevel(int level) {
		return 12 + level * 5;
	}

	public static double getDamageModifierAtLevel(int level) {
		return 1.5D + (double) level / 10D;
	}

	public static int getCooldownAtLevel(int level) {
		return level < 3 ? 11 : (level < 7 ? 10 : 9);
	}

}
