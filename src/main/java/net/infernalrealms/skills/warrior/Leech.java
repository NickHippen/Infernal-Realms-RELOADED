package net.infernalrealms.skills.warrior;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.gui.ItemMessageHandler;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.ActiveSkill;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
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

/**
 * @Type Active
 * @Description Damages an enemy in front of you and steals their health.
 */
public class Leech extends ActiveSkill {

	public static final String DISPLAY_NAME = "Leech";

	private Player player;
	private int level;
	private int mpCost;
	private int cooldown;
	private double damageModifier;
	private double leechPercent;

	public Leech(Player player) {
		this.player = player;
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.mpCost = getMpCostAtLevel(level);
		this.damageModifier = getDamageModifierAtLevel(level);
		this.leechPercent = getLeechPercentAtLevel(level);
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
		final Location location = getPlayer().getEyeLocation();
		final Vector v = location.getDirection().multiply(0.7);
		List<Entity> nearbyEntities = getPlayer().getNearbyEntities(10, 10, 10);
		Spell: {
			for (int i = 0; i < 6; i++) {
				location.add(v);
				EffectsUtil.sendParticleToLocation(ParticleEffect.TOWN_AURA, location, 0.03F, 0.03F, 0.03F, 10F, 5);
				for (Entity entity : nearbyEntities) {
					if (entity instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) entity;
						if (!(entity instanceof Player)) {
							if (location.distanceSquared(entity.getLocation()) < 2
									|| location.distanceSquared(((LivingEntity) entity).getEyeLocation()) < 2) {
								MobEffects.damage(getPlayer(), le, getDamageModifier());
								double heal = GeneralUtil.healPlayer(getPlayer(),
										getDamageModifier() * playerData.calculateDamage().getDamage() * getLeechPercent());
								ItemMessageHandler.displayMessage(getPlayer(), String
										.format(ChatColor.DARK_RED + "" + ChatColor.BOLD + "+%.0f" + ChatColor.DARK_RED + " �¤", heal));
								EffectsUtil.sendParticleToLocation(ParticleEffect.ANGRY_VILLAGER, le.getEyeLocation().add(0, 1, 0), 0F, 0F,
										0F, 1F, 1);
								player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.5F, 2F);
								final int m = i;
								new BukkitRunnable() {
									int count = 0;

									@Override
									public void run() {
										count++;
										location.subtract(v);
										EffectsUtil.sendParticleToLocation(ParticleEffect.DRIP_LAVA, location.subtract(0, 0.1, 0), 0F, 0F,
												0F, 10F, 1);

										if (count > m) {
											cancel();
										}
									}
								}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 5L);
								break Spell;
							}
						}
					}
				}
			}
		}

	}

	@Override
	public ItemStack getIcon() {
		// Leech
		ItemStack icon = new ItemStack(Material.MAGMA_CREAM, 1, (short) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Absorbs the health of an enemy in,");
		lore.add("§7your direction, dealing damage and");
		lore.add("§7healing you for some of the damage");
		lore.add("§7dealt.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP Cost: §7" + getMpCost());
			lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifier() * 100) + "%");
			lore.add("§7§lLEECH: §7" + (int) (getLeechPercent() * 100) + "%");
			lore.add("§7§lCOOLDOWN: §7" + getCooldown() + " sec");
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
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l[ §r§aLevel " + level + " Stats §l]");
		lore.add("§7§lMP COST: §7" + getMpCostAtLevel(level));
		lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifierAtLevel(level) * 100) + "%");
		lore.add("§7§lLEECH: §7" + (int) (getLeechPercentAtLevel(level) * 100) + "%");
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

	public int getMpCost() {
		return this.mpCost;
	}

	public double getDamageModifier() {
		return this.damageModifier;
	}

	public double getLeechPercent() {
		return this.leechPercent;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getMpCostAtLevel(int level) {
		return 10 + level * 6;
	}

	public static double getDamageModifierAtLevel(int level) {
		return 1.5D + (double) level / 10D;
	}

	public static double getLeechPercentAtLevel(int level) {
		return level < 7 ? (0.1D + 0.01D * (double) level) : (level == 7 ? 0.18 : 0.2);
	}

	public static int getCooldownAtLevel(int level) {
		switch (level) {
		case 1:
			return 14;
		case 2:
		case 3:
			return 13;
		case 4:
		case 5:
			return 12;
		case 6:
		case 7:
			return 11;
		case 8:
			return 10;
		}
		return 10;
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
					player.getInventory().setItem(3, icon);
				} else {
					player.removeMetadata("S:" + DISPLAY_NAME, InfernalRealms.getPlugin());
					icon.setType(originalMaterial);
					player.getInventory().setItem(3, icon);
					cancel();
				}
				cooldown--;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 20L);
	}

	public Player getPlayer() {
		return player;
	}

}
