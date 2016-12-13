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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;

public class Levitate extends ActiveSkill {

	public static final String DISPLAY_NAME = "Levitate";

	/**
	 * Type: Active
	 * Description: Fires a bolt that, upon impact with an enemy, levitates all nearby enimies for x seconds and deals y damage
	 */

	private Player player;
	private int level;
	private double damageModifier;
	private int mpCost;
	private int cooldown;
	private int airtime;

	public Levitate(Player player) {
		this.setPlayer(player);
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.damageModifier = getDamageModifierAtLevel(this.level);
		this.mpCost = getMpCostAtLevel(this.level);
		this.cooldown = getCooldownAtLevel(this.level);
		this.airtime = getAirtimeAtLevel(this.level);
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
		Location location = this.player.getEyeLocation().subtract(0, 0.2, 0);
		ArrayList<LivingEntity> damagedMobs = new ArrayList<>();
		spell: {
			for (int i = 0; i < 15; i++) {
				Vector v = player.getEyeLocation().getDirection();
				location.add(v);
				if (location.getBlock().getType().isSolid()) {
					break spell;
				}
				//				ParticleEffect.MAGIC_CRIT.display(location, 0, 0, 0, 0.1f, 10);
				EffectsUtil.sendParticleToLocation(ParticleEffect.CRIT_MAGIC, location, 0F, 0F, 0F, 0.1F, 10);
				List<Entity> nearbyEntities = player.getNearbyEntities(50, 50, 50);
				for (Entity entity : nearbyEntities) {
					if (entity instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) entity;
						if (!(entity instanceof Player) && !(entity instanceof ArmorStand) && !damagedMobs.contains(le)) {
							if (location.distanceSquared(entity.getLocation()) < 2
									|| location.distanceSquared(((LivingEntity) entity).getEyeLocation()) < 2) {
								MobEffects.forceDamage(getPlayer(), le, getDamageModifier());
								MobEffects.levitate(le, 80L);
								damagedMobs.add(le);
							}
						}
					}
				}
			}
		}
	}

	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (byte) 12);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Shoot a beam of energy at an enemy");
		lore.add("§7dealing damage and raising them");
		lore.add("§7into the air for a short amount of time.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP COST: §7" + getMpCost());
			lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifier() * 100) + "%");
			lore.add("§7§lAIRTIME: §7" + getAirtime() + " sec");
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
		lore.add("§7§lAIRTIME: §7" + getAirtimeAtLevel(level) + " sec");
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

	public int getLevel() {
		return this.level;
	}

	public double getDamageModifier() {
		return this.damageModifier;
	}

	public static double getDamageModifierAtLevel(int level) {
		return 1 + level * 0.05;
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public static int getMpCostAtLevel(int level) {
		switch (level) {
		case 1:
			return 15;
		case 2:
			return 20;
		case 3:
			return 25;
		case 4:
			return 30;
		case 5:
			return 40;
		case 6:
			return 50;
		case 7:
			return 65;
		case 8:
			return 90;
		}
		return 90;
	}

	public int getAirtime() {
		return this.airtime;
	}

	public static int getAirtimeAtLevel(int level) {
		switch (level) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
			return 2;
		case 6:
		case 7:
			return 3;
		case 8:
			return 4;
		}
		return 4;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getCooldownAtLevel(int level) {
		switch (level) {
		case 1:
		case 2:
			return 15;
		case 3:
		case 4:
			return 14;
		case 5:
		case 6:
			return 13;
		case 7:
		case 8:
			return 12;
		}
		return 12;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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

}
