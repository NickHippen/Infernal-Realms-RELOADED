package net.infernalrealms.skills.archer;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.ActiveSkill;
import net.infernalrealms.skills.general.ArrowSkill;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalEffects;

public class MagnumShot extends ActiveSkill implements ArrowSkill {

	public static final String DISPLAY_NAME = "Magnum Shot";

	/**
	 * Type: Active Description: Fires a powerful arrow that explodes upon
	 * impact of a target
	 */

	private Player player;
	private Arrow arrow;

	private int level;
	private double damageModifier;
	private int mpCost;
	private float knockback;
	private int cooldown;

	public MagnumShot(Player player) {
		this.setPlayer(player);
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.damageModifier = getDamageModifierAtLevel(this.level);
		this.mpCost = getMpCostAtLevel(this.level);
		this.knockback = getKnockbackAtLevel(this.level);
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
		Vector v = player.getEyeLocation().getDirection();
		Location shootLocation = player.getEyeLocation().add(v.multiply(1.5));
		setArrow(GeneralUtil.fireArrow(getPlayer()));
		getArrow().setShooter(getPlayer());
		EffectsUtil.sendParticleToLocation(ParticleEffect.FLAME, shootLocation, 0F, 0F, 0F, 0.05F, 15);
		MobEffects.applyArrowInformation(getArrow(), this, getDamageModifier());
		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 2F);
		new BukkitRunnable() {
			private int count = 0;

			@Override
			public void run() {
				count++;

				EffectsUtil.sendParticleToLocation(ParticleEffect.FLAME, getArrow().getLocation(), 0F, 0F, 0F, 0.01F, 2);
				EffectsUtil.sendParticleToLocation(ParticleEffect.SMOKE_NORMAL, getArrow().getLocation(), 0.01F, 0.01F, 0.01F, 0F, 1);

				if (count >= 20) {
					EffectsUtil.sendParticleToLocation(ParticleEffect.SMOKE_NORMAL, getArrow().getLocation(), 0.1F, 0.1F, 0.1F, 0.1F, 10);
					getArrow().getLocation().getWorld().playSound(getArrow().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1f, 1);
					cancel();
				}
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public void getEffect(LivingEntity target) {
		EffectsUtil.sendParticleToLocation(ParticleEffect.EXPLOSION_LARGE, getArrow().getLocation(), 0.1F, 0.1F, 0.1F, 1F, 5);
		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1F, 0F);
		target.setVelocity(getArrow().getVelocity().multiply(getKnockback()).setY(0.3));
		for (Entity e : target.getNearbyEntities(3, 3, 3)) {
			if (e instanceof LivingEntity && !(e instanceof Player)) {
				LivingEntity le = (LivingEntity) e;
				MobEffects.damage(getPlayer(), le, getDamageModifier() * 0.5);
			}
		}

	}

	public ItemStack getIcon() {
		// Orange Dye
		ItemStack icon = new ItemStack(Material.FIREWORK_CHARGE, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Fires a charged arrow, creating");
		lore.add("§7a powerful explosion on contact");
		lore.add("§7dealing damage to nearby enemies");
		lore.add("§7and knocking back the shot target.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP COST: §7" + getMpCost());
			lore.add("§7§lDAMAGE: §7" + (int) (getDamageModifier() * 100) + "%");
			lore.add("§7§lKNOCKBACK FORCE: §7" + (int) (getKnockback() * 100) + "%");
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
		lore.add("§7§lDAMAGE: §7" + (int) Math.round(getDamageModifierAtLevel(level) * 100) + "%");
		lore.add("§7§lKNOCKBACK FORCE: §7" + (int) Math.round(getKnockbackAtLevel(level) * 100) + "%");
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
		return level < 7 ? 1.2 + level * 0.2 : (level == 7 ? 2.7 : 3.0);
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public static int getMpCostAtLevel(int level) {
		return level < 6 ? 10 + 5 * level : -20 + 10 * level;
	}

	public float getKnockback() {
		return this.knockback;
	}

	public static float getKnockbackAtLevel(int level) {
		return 0.2F + 0.04F * level;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getCooldownAtLevel(int level) {
		return level < 4 ? 12 : (level < 7 ? 11 : 10);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Arrow getArrow() {
		return arrow;
	}

	public void setArrow(Arrow arrow) {
		this.arrow = arrow;
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