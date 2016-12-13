package net.infernalrealms.skills.magician;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.general.Skill;
import net.infernalrealms.util.EffectsUtil;

public class Static extends Skill {

	public static final Random random = new Random();

	public static final String DISPLAY_NAME = "Static";

	private int level;
	private double archReduction;
	private int enemiesHit;
	private int range;

	public Static(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		this.level = playerData.getSkillLevel(DISPLAY_NAME);
		this.archReduction = getArchReductionAtLevel(this.level);
		this.enemiesHit = getEnemiesHitAtLevel(this.level);
		this.range = getRangeAtLevel(this.level);
	}

	/**
	 * Type: Passive
	 * Description: Basic attacks have a passive chance of arcing (reduced) damage to nearby enemies
	 */
	public static void getEffect(Player player, LivingEntity target) {
		getEffect(player, target, 1, new ArrayList<LivingEntity>());
	}

	public static void getEffect(Player player, LivingEntity target, double modifier, ArrayList<LivingEntity> targetedMobs) {
		//TODO Get effect level & base off of that
		Static statik = new Static(player);
		if (statik.getLevel() == 0) {
			return;
		}
		List<Entity> nearbyEntities = target.getNearbyEntities(statik.getRange(), statik.getRange(), statik.getRange());
		for (Entity e : nearbyEntities) {
			if (e instanceof LivingEntity && !(e instanceof ArmorStand) && !(e instanceof Player) && !targetedMobs.contains(e)) {
				LivingEntity le = (LivingEntity) e;
				modifier *= statik.getArchReduction();
				MobEffects.forceDamage(player, le, modifier);

				// Floating damage indicator
				targetedMobs.add(le);
				//				player.getWorld().playEffect(le.getLocation(), Effect.STEP_SOUND, 57);
				for (int i = 0; i < 10; i++) {
					Location sourceLoc = le.getLocation().add(0, 0.5, 0);
					Vector v = new Vector(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
					for (int z = 0; z < 5; z++) {
						sourceLoc.add(v);
						EffectsUtil.sendColoredParticleToLocation(ParticleEffect.REDSTONE, sourceLoc, Color.CYAN, 3);
						//						EffectsUtil.sendParticleToLocation(ParticleEffect.VILLAGER_HAPPY, sourceLoc, 0F, 0F, 0F, 0.01F, 1);
					}
				}
				if (targetedMobs.size() < statik.getEnemiesHit())
					getEffect(player, le, modifier, targetedMobs);
				break;
			}
		}
	}

	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.PRISMARINE_SHARD, 1, (byte) 0);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Your attacks have a 15% chance");
		lore.add("§7of arching arcane energy to nearby");
		lore.add("§7enemies dealing reduced damage");
		lore.add("§7for every enemy that is hit.");
		lore.add(" ");
		if (getLevel() == 0) {
			lore.add("§c§l[ §r§cLOCKED §l]");
		} else {
			lore.add("§a§l[ §aCurrent Level: " + getLevel() + " §l]");
			lore.add("§7§lREDUCTION PER ARC: §7" + (int) (getArchReduction() * 100) + "%");
			lore.add("§7§lENEMIES HIT: §7" + getEnemiesHit());
			lore.add("§7§lARCH RANGE: §7" + getRange() + " blocks");
		}
		iconMeta.setLore(lore);
		icon.setItemMeta(iconMeta);
		return icon;
	}

	public ItemStack getIconAtLevel(int level) {
		boolean unlocked = level <= this.getLevel();
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (short) (unlocked ? 9 : 8));
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName("§5§l" + DISPLAY_NAME + " §7§l[ §7PASSIVE§7§l ]");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l[ §r§aLevel " + level + " Stats §l]");
		lore.add("§7§lREDUCTION: §7" + (int) Math.round(getArchReductionAtLevel(level) * 100) + "%");
		lore.add("§7§lENEMIES HIT: §7" + getEnemiesHitAtLevel(level));
		lore.add("§7§lARCH RANGE: §7" + getRangeAtLevel(level));
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

	public double getArchReduction() {
		return this.archReduction;
	}

	public static double getArchReductionAtLevel(int level) {
		return 0.65 - level * 0.05;
	}

	public double getEnemiesHit() {
		return this.enemiesHit;
	}

	public static int getEnemiesHitAtLevel(int level) {
		return level < 4 ? 3 : (level < 7 ? 4 : 5);
	}

	public double getRange() {
		return this.range;
	}

	public static int getRangeAtLevel(int level) {
		return level < 3 ? 4 : (level < 5 ? 5 : (level < 7 ? 6 : 7));
	}

}
