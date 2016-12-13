package net.infernalrealms.skills.magician;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.ActiveSkill;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.InfernalEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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

import de.inventivegames.particle.ParticleEffect;

public class Pyroblast extends ActiveSkill {

	public static final String DISPLAY_NAME = "Pyroblast";

	/**
	 * Type: Active
	 * Description: Fires a streak of fire that follows the players cursor
	 */

	private Player player;
	private int level;
	private double damageModifier;
	private int mpCost;
	private int cooldown;

	public Pyroblast(Player player) {
		// TODO Get skill stats based on player
		this.setPlayer(player);
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.damageModifier = getDamageModifierAtLevel(this.level);
		this.mpCost = getMpCostAtLevel(this.level);
		this.cooldown = getCooldownAtLevel(this.level);
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
		final Location location = getPlayer().getEyeLocation();
		Vector v = player.getEyeLocation().getDirection();
		final List<Entity> nearbyEntities = player.getNearbyEntities(32, 32, 32);
		location.add(v);
		location.getWorld().playSound(location, Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 1);
		new BukkitRunnable() {
			int count = 0;

			@Override
			public void run() {
				Vector v = player.getEyeLocation().getDirection().multiply(0.7);
				location.add(v);
				if (location.getBlock().getType().isSolid()) {
					location.getWorld().playSound(location, Sound.BLOCK_CLOTH_BREAK, 10f, 1);
					cancel();
				}
				EffectsUtil.sendParticleToLocation(ParticleEffect.DRIP_LAVA, location, 0.1F, 0.1F, 0.1F, 1F, 10);
				if (count % 3 == 0) { // Do damage
					EffectsUtil.sendParticleToLocation(ParticleEffect.CRIT, location, 0F, 0F, 0F, 0.03F, 1);
					for (Entity e : nearbyEntities) {
						if (e instanceof LivingEntity) {
							LivingEntity le = (LivingEntity) e;
							if (le.getLocation().distanceSquared(location) <= 2 || le.getEyeLocation().distanceSquared(location) <= 2) {
								MobEffects.forceDamage(player, le, getDamageModifier());
							}
						}
					}
				}
				if (++count >= 60 || location.distanceSquared(getPlayer().getLocation()) > 1024) {
					location.getWorld().playSound(location, Sound.BLOCK_CLOTH_BREAK, 10f, 1);
					cancel();
				}
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public ItemStack getIcon() {
		// Orange Dye
		ItemStack icon = new ItemStack(Material.MAGMA_CREAM, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Shoots a fireball that follows your");
		lore.add("§7cursor and pierces through enemies");
		lore.add("§7dealing damage to each.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP COST: §7" + getMpCost());
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
		lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifierAtLevel(level) * 100) + "%");
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getLevel() {
		return this.level;
	}

	public double getDamageModifier() {
		return this.damageModifier;
	}

	public static double getDamageModifierAtLevel(int level) {
		switch (level) {
		case 1:
			return 1.3;
		case 2:
			return 1.35;
		case 3:
			return 1.4;
		case 4:
			return 1.45;
		case 5:
			return 1.5;
		case 6:
			return 1.57;
		case 7:
			return 1.68;
		case 8:
			return 1.8;
		}
		return 1.8;
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public static int getMpCostAtLevel(int level) {
		return 5 + level * 5;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getCooldownAtLevel(int level) {
		switch (level) {
		case 1:
		case 2:
			return 6;
		case 3:
		case 4:
			return 5;
		case 5:
		case 6:
		case 7:
			return 4;
		case 8:
			return 3;
		}
		return 3;
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
					player.getInventory().setItem(2, icon);
				} else {
					player.removeMetadata("S:" + DISPLAY_NAME, InfernalRealms.getPlugin());
					icon.setType(originalMaterial);
					player.getInventory().setItem(2, icon);
					cancel();
				}
				cooldown--;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 20L);
	}

}
