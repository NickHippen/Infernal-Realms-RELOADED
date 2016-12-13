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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;

public class Frostbite extends ActiveSkill {

	public static final String DISPLAY_NAME = "Frostbite";

	/**
	 * Type: Active
	 * Description: Creates a snow storm at the target location for x seconds
	 */

	private Player player;
	private int level;
	private double damageModifier;
	private int mpCost;
	private int cooldown;

	public Frostbite(Player player) {
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
		Location location = getPlayer().getEyeLocation();
		for (int i = 0; i < 12; i++) {
			Vector v = getPlayer().getEyeLocation().getDirection();
			location.add(v);
			Material blockType = location.getBlock().getType();
			if (blockType != Material.AIR && blockType != Material.LONG_GRASS) {
				// Found block
				break;
			}
			// No block found
		}
		location.add(-3, 1, -3);
		for (int z = 0; z < 7; z++) {
			for (int x = 0; x < 7; x++) {
				final Location l = new Location(location.getWorld(), location.getBlockX() + x, location.getBlockY(),
						location.getBlockZ() + z);
				while (l.getY() > 0 && l.getBlock().getType() == Material.AIR) {
					l.subtract(0, 1, 0);
				}
				l.add(0, 1, 0);
				Material blockType = l.getBlock().getType();
				if (blockType == Material.AIR) {

					new BukkitRunnable() {
						private int count = 0;

						@Override
						public void run() {
							count++;
							//							ParticleEffect.SNOWBALL_POOF.display(l, 0, 0, 0, 0.1f, 5);
							EffectsUtil.sendParticleToLocation(ParticleEffect.SNOWBALL, l, 0F, 0F, 0F, 0.1F, 5);
							//							ParticleEffect.SNOW_SHOVEL.display(l, 0, 2, 0, 0.1f, 5);
							EffectsUtil.sendParticleToLocation(ParticleEffect.SNOW_SHOVEL, l, 2F, 2F, 2F, 0.1F, 2);
							List<Entity> nearbyEntities = player.getNearbyEntities(20, 20, 20);
							for (Entity entity : nearbyEntities) {
								if (entity instanceof LivingEntity) {
									LivingEntity le = (LivingEntity) entity;
									if (!(entity instanceof Player)) {
										if (l.distanceSquared(entity.getLocation()) <= 1) {
											MobEffects.forceDamage(getPlayer(), le, getDamageModifier());
											le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 1, false, false));
										}
									}
								}
							}

							if (count >= 8)
								cancel();
						}
					}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 10L);
				}
			}
		}

	}

	public ItemStack getIcon() {
		// Orange Dye
		ItemStack icon = new ItemStack(Material.SNOW_BALL, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Summons a powerful blizzard for 4 seconds");
		lore.add("§7in the direction in which you are");
		lore.add("§7looking dealing damage and slowing");
		lore.add("§7all enemies within its area.");
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
		lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifierAtLevel(level) * 100) + "% per half-second");
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

	public int getLevel() {
		return this.level;
	}

	public double getDamageModifier() {
		return this.damageModifier;
	}

	public static double getDamageModifierAtLevel(int level) {
		return 0.11 + 0.02 * level;
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public static int getMpCostAtLevel(int level) {
		return level < 4 ? level * 5 + 20 : level * 10;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getCooldownAtLevel(int level) {
		return level < 3 ? 16 : (level < 5 ? 15 : (level < 7 ? 14 : (level < 8 ? 13 : 12)));
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

}
