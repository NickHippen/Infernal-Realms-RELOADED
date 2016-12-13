package net.infernalrealms.skills.archer;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.ActiveSkill;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.InfernalEffects;

public class Haste extends ActiveSkill {

	public static final String DISPLAY_NAME = "Haste";

	private Player player;
	private int level;
	private double speedModifier;
	private int cooldown;
	private int mpCost;

	public Haste(Player player) {
		this.setPlayer(player);
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.speedModifier = getSpeedModifierAtLevel(this.level);
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
		player.setWalkSpeed(0.2F * (float) getSpeedModifier());

		// Effects
		EffectsUtil.playItemSpray(Material.FEATHER, player.getLocation(), 10, 1.5F);
		EffectsUtil.sendParticleToLocation(ParticleEffect.VILLAGER_HAPPY, player.getLocation(), 1F, 1F, 1F, 1F, 30);
		player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1F, 1);

		new BukkitRunnable() {
			int count = 0;

			@Override
			public void run() {
				if (count < 6) { // Skill in progress
					EffectsUtil.playItemSpray(Material.FEATHER, player.getLocation().subtract(0, 1, 0), 1, 0F);
				} else { // End skill
					player.setWalkSpeed(0.2F); // Default Walking Speed
					// Effects
					player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1F, 1);
					cancel();
					return;
				}
				count++;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 20L, 20L);
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

	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.FEATHER, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Give yourself a powerful boost");
		lore.add("§7of speed for 8 seconds.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lMP COST: §7" + getMpCost());
			lore.add("§7§lSPEED: §7" + (int) (getSpeedModifier() * 100) + "%");
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
		lore.add("§7§lSPEED: §7" + (int) Math.round(getSpeedModifierAtLevel(level) * 100) + "%");
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

	public double getSpeedModifier() {
		return this.speedModifier;
	}

	public static double getSpeedModifierAtLevel(int level) {
		return 1.2 + level * 0.05;
	}

	public int getMpCost() {
		return this.mpCost;
	}

	public static int getMpCostAtLevel(int level) {
		return 20 + level * 7;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getCooldownAtLevel(int level) {
		return level == 1 ? 20 : (level < 4 ? 19 : (level < 6 ? 18 : (level < 8 ? 17 : 16)));
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
