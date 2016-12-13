package net.infernalrealms.skills.warrior;

import java.util.ArrayList;
import java.util.List;

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

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.ActiveSkill;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalEffects;

/**
 * Type: Active
 * Description: Sends out ground particle in a wave knocking enemies back and dealing damage
 */
public class Rumble extends ActiveSkill {

	public static final String DISPLAY_NAME = "Rumble";

	private Player player;
	private int level;
	private int mpCost;
	private double damageModifier;
	private int cooldown;

	public Rumble(Player player) {
		// TODO Get skill stats based on player
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
		Location rumbleLoc = player.getLocation();
		getPlayer().playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1F, 0F);
		getPlayer().playSound(player.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 1F, 0F);
		getPlayer().playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 1F, 0F);
		for (int circle = 0; circle < 4; circle++) {
			int circle2x = (circle + 1) * 2;
			new BukkitRunnable() {
				@Override
				public void run() {
					rumbleLoc.add(-1, 0, 1);
					for (int i = 0; i < circle2x; i++) {
						rumbleLoc.add(1, 0, 0);
						applyEffect(rumbleLoc);
					}
					for (int i = 0; i < circle2x; i++) {
						rumbleLoc.add(0, 0, -1);
						applyEffect(rumbleLoc);
					}
					for (int i = 0; i < circle2x; i++) {
						rumbleLoc.add(-1, 0, 0);
						applyEffect(rumbleLoc);
					}
					for (int i = 0; i < circle2x; i++) {
						rumbleLoc.add(0, 0, 1);
						applyEffect(rumbleLoc);
					}
					getPlayer().playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 0.5F, 0F);
					getPlayer().playSound(player.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0F);
					getPlayer().playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 0.5F, 0F);
				}
			}.runTaskLater(InfernalRealms.getPlugin(), circle * 7);

		}
	}

	public void applyEffect(Location location) {
		EffectsUtil.sendBlockDustParticleToLocation(Material.DIRT, location, 0.25F, 0F, 0.25F, 0.1F, 2);
		EffectsUtil.sendBlockDustParticleToLocation(Material.GRAVEL, location, 0.25F, 0F, 0.25F, 0.1F, 2);
		List<Entity> targetEntities = GeneralUtil.getNearbyEntities(location, 1, 1, 1);
		for (Entity entity : targetEntities) {
			if (entity instanceof LivingEntity && !(entity instanceof Player)) {
				MobEffects.damage(getPlayer(), (LivingEntity) entity, getDamageModifier());
				MobEffects.jump((LivingEntity) entity);
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ItemStack getIcon() {
		// Green Dye
		ItemStack icon = new ItemStack(Material.FLINT, 1, (short) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(PlayerClass.SKILL_INDICATOR + "§5§l" + DISPLAY_NAME + " §7§l[ §7ACTIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Rumbles the ground, knocking back");
		lore.add("§7surround enemies and dealing");
		lore.add("§7damage.");
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

	public int getCooldown() {
		return this.cooldown;
	}

	public static int getMpCostAtLevel(int level) {
		return level < 7 ? 10 + level * 5 : (level == 7 ? 47 : 54);
	}

	public static double getDamageModifierAtLevel(int level) {
		return 1D + (double) level / 10D;
	}

	public static int getCooldownAtLevel(int level) {
		switch (level) {
		case 1:
		case 2:
			return 12;
		case 3:
		case 4:
		case 5:
		case 6:
			return 11;
		case 7:
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
