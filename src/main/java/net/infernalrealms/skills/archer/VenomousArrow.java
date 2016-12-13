package net.infernalrealms.skills.archer;

import java.awt.Color;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

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

public class VenomousArrow extends ActiveSkill implements ArrowSkill {

	public static final String DISPLAY_NAME = "Venomous Arrow";

	/**
	 * Type: Active
	 * Description: Fires a poisonous arrow that deals damage over time
	 */

	private Player player;
	private Arrow arrow;
	private int level;
	private double initialDamageModifier;
	private double dotDamageModifier;
	private double reductionModifier;
	private int mpCost;
	private int cooldown;

	public VenomousArrow(Player player) {
		this.setPlayer(player);
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.initialDamageModifier = getInitialDamageModifierAtLevel(this.level);
		this.dotDamageModifier = getDotDamageModifierAtLevel(this.level);
		this.reductionModifier = getReductionModifierAtLevel(this.level);
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
		setArrow(GeneralUtil.fireArrow(getPlayer()));
		MobEffects.applyArrowInformation(getArrow(), this, getInitialDamageModifier());
		new BukkitRunnable() {
			private int count = 0;

			@Override
			public void run() {
				count++;
				EffectsUtil.sendParticleToLocation(ParticleEffect.HAPPY_VILLAGER, getArrow().getLocation(), 0F, 0F, 0F, 0F, 1);
				if (count >= 20) {
					cancel();
				}
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public void getEffect(LivingEntity target) {
		EffectsUtil.addPotionGraphicalEffect(target, 0x00FF00, 100L);
		EffectsUtil.sendColoredParticleToLocation(ParticleEffect.SPELL_MOB, target.getLocation(), Color.GREEN, 100);
		MobEffects.applyPoison(getPlayer(), target, 5, getDotDamageModifier());
		MobEffects.applyWeakness(target, 5, getReductionModifier());
	}

	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (byte) 2);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Shoot a venomous arrow dealing");
		lore.add("§7initial damage, damage over time and");
		lore.add("§7reducing your targets damage");
		lore.add("§7for the next five seconds.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP COST: §7" + getMpCost());
			lore.add("§7§lDAMAGE: §7" + (int) (getInitialDamageModifier() * 100) + "%");
			lore.add("§7§lDMG OVER TIME: §7" + (int) (getDotDamageModifier() * 100) + "%");
			lore.add("§7§lDMG REDUCTION: §7" + (int) (getReductionModifier() * 100) + "%");
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
		lore.add("§7§lDAMAGE: §7" + (int) (getInitialDamageModifierAtLevel(level) * 100) + "%");
		lore.add("§7§lDMG OVER TIME: §7" + (int) (getDotDamageModifierAtLevel(level) * 100) + "%");
		lore.add("§7§lDMG REDUCTION: §7" + (int) (getReductionModifierAtLevel(level) * 100) + "%");
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

	public double getInitialDamageModifier() {
		return this.initialDamageModifier;
	}

	public static double getInitialDamageModifierAtLevel(int level) {
		return 0.96 + level * 0.04;
	}

	public double getDotDamageModifier() {
		return this.dotDamageModifier;
	}

	public static double getDotDamageModifierAtLevel(int level) {
		return level < 7 ? 0.35 + level * 0.05 : (level == 7 ? 0.75 : 0.85);
	}

	public double getReductionModifier() {
		return this.reductionModifier;
	}

	public static double getReductionModifierAtLevel(int level) {
		return level < 3 ? 0.1 : (level < 6 ? 0.2 : 0.3);
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public static int getMpCostAtLevel(int level) {
		return 10 + 5 * level;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getCooldownAtLevel(int level) {
		return level < 3 ? 14 : (level < 5 ? 13 : (level < 7 ? 12 : 11));
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

	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
