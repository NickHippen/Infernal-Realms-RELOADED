package net.infernalrealms.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.gui.ItemMessageHandler;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.skills.archer.Concentration;
import net.infernalrealms.skills.general.ArrowSkill;
import net.infernalrealms.skills.warrior.ConcussiveBlows;
import net.infernalrealms.util.EffectsUtil;
import net.minecraft.server.v1_9_R1.EntityLiving;

public class MobEffects {

	public static void damage(Player source, Damageable target, double damageModifer) {
		if (target instanceof ArmorStand) {
			return;
		}
		target.setMetadata("SkillDamage", new FixedMetadataValue(InfernalRealms.getPlugin(), damageModifer));
		target.damage(-5D, source);
	}

	public static void forceDamage(Player source, LivingEntity target, double damageModifier) {
		int before = target.getNoDamageTicks();
		target.setNoDamageTicks(0);
		damage(source, target, damageModifier);
		target.setNoDamageTicks(before);
	}

	public static void applyArrowInformation(Arrow arrow, ArrowSkill arrowSkill, double damageModifier) {
		arrow.setMetadata("SkillEffect", new FixedMetadataValue(InfernalRealms.getPlugin(), arrowSkill));
		arrow.setMetadata("SkillDamage", new FixedMetadataValue(InfernalRealms.getPlugin(), damageModifier));
	}

	public static void stun(final LivingEntity target, long duration) {
		EntityLiving nmsTarget = ((CraftLivingEntity) target).getHandle();
		if (nmsTarget instanceof InfernalMob) {
			InfernalMob customTarget = (InfernalMob) nmsTarget;
			customTarget.setSpeed(0);
			customTarget.setFollowRangeMult(0);

			//			 Stun Indicator
			Hologram holo = HologramsAPI.createHologram(InfernalRealms.getPlugin(), customTarget.getNameTagLocation().add(0, 2.1, 0));
			holo.appendTextLine(ChatColor.GOLD + "Stunned");
			BukkitTask stunIcon = new BukkitRunnable() {
				Location previousUpdateLocation = customTarget.getNameTagLocation().add(0, 2.1, 0);

				public void run() {

					if (!target.isDead() && !holo.isDeleted()) {
						Location currentUpdateLocation = customTarget.getNameTagLocation().add(0, 2.1, 0);
						if (!previousUpdateLocation.equals(currentUpdateLocation)) {
							holo.teleport(currentUpdateLocation);
							previousUpdateLocation = currentUpdateLocation;
						}
					} else {
						holo.delete();
						cancel();
					}
				}

			}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);

			// Reset stats
			new BukkitRunnable() {

				public void run() {
					if (!target.isDead()) {
						customTarget.setSpeed(customTarget.getData().getSpeed());
						customTarget.setFollowRangeMult(customTarget.getData().getFollowRangeMult());
					}
					stunIcon.cancel();
					holo.delete();
				}

			}.runTaskLater(InfernalRealms.getPlugin(), duration);

		}
	}

	public static void levitate(LivingEntity target, long duration) {
		Location l = target.getLocation();
		new BukkitRunnable() {
			int count = 10;

			public void run() {
				target.setVelocity(new Vector(0, 0.5, 0));
				count--;
				if (count <= 0 || target.getLocation().getY() - l.getY() >= 4) {
					new BukkitRunnable() {
						long count = duration;

						public void run() {
							target.setVelocity(new Vector(0, -0.1, 0));
							count--;
							if (count <= 0 || !target.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
								cancel();
							}
						}

					}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
					cancel();
				}
			}

		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public static void jump(LivingEntity target) {
		target.setVelocity(target.getVelocity().setY(0.4));
	}

	public static void applyConcussiveBlowStack(final LivingEntity target, Player player) {
		EntityLiving nmsTarget = ((CraftLivingEntity) target).getHandle();
		if (nmsTarget instanceof InfernalMob) {
			if (!target.hasMetadata("ConcussiveBlowStacks")) {
				target.setMetadata("ConcussiveBlowStacks", new FixedMetadataValue(InfernalRealms.getPlugin(), 0));
			}
			int newStacks = getConcussiveBlowStacks(target) + 1;
			if (newStacks >= 5) {
				stun(target, (long) (20L * new ConcussiveBlows(player).getStunDuration()));
				newStacks = 0;
				ConcussiveBlows.setCooldown(target);
			}
			target.setMetadata("ConcussiveBlowStacks", new FixedMetadataValue(InfernalRealms.getPlugin(), newStacks));
			if (target.hasMetadata("ConcussiveBlowStacksIcon")) {
				Hologram h = ((Hologram) (target.getMetadata("ConcussiveBlowStacksIcon").get(0).value()));
				if (!h.isDeleted())
					h.delete();
			}
			if (newStacks == 0)
				return;
			String stacks = "O O O O O";
			stacks = ChatColor.AQUA + stacks.substring(0, newStacks * 2) + ChatColor.GRAY + stacks.substring(newStacks * 2);
			Hologram holo = HologramsAPI.createHologram(InfernalRealms.getPlugin(), target.getEyeLocation().add(0, 2, 0));
			holo.appendTextLine(stacks);
			target.setMetadata("ConcussiveBlowStacksIcon", new FixedMetadataValue(InfernalRealms.getPlugin(), holo));
			final BukkitTask concussiveBlowIcon = new BukkitRunnable() {
				Location previousUpdateLocation = target.getEyeLocation().add(0, 2, 0);

				public void run() {

					if (!target.isDead() && !holo.isDeleted()) {
						Location currentUpdateLocation = target.getEyeLocation().add(0, 2, 0);
						if (!previousUpdateLocation.equals(currentUpdateLocation)) {
							holo.teleport(currentUpdateLocation);
							previousUpdateLocation = currentUpdateLocation;
						}
					} else {
						holo.delete();
						cancel();
					}
				}

			}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
			if (target.hasMetadata("ConcussiveBlowStacksIconRemover")) {
				((BukkitTask) target.getMetadata("ConcussiveBlowStacksIconRemover").get(0).value()).cancel();
			}
			target.setMetadata("ConcussiveBlowStacksIconRemover", new FixedMetadataValue(InfernalRealms.getPlugin(), new BukkitRunnable() {

				public void run() {
					target.removeMetadata("ConcussiveBlowStacks", InfernalRealms.getPlugin());
					concussiveBlowIcon.cancel();
					holo.delete();
				}

			}.runTaskLater(InfernalRealms.getPlugin(), 60L)));
		}
	}

	private static int getConcussiveBlowStacks(LivingEntity target) {
		if (!target.hasMetadata("ConcussiveBlowStacks"))
			return 0;
		return (int) target.getMetadata("ConcussiveBlowStacks").get(0).value();
	}

	public static void applyConcentrationStack(final LivingEntity target, Player player) {
		if (!target.hasMetadata("ConcentrationStacks")) {
			target.setMetadata("ConcentrationStacks", new FixedMetadataValue(InfernalRealms.getPlugin(), 0));
		}
		int newStacks = getConcentrationStacks(target) + 1;
		if (newStacks >= 4) {
			new BukkitRunnable() {
				@Override
				public void run() {
					if (target.isValid()) {
						forceDamage(player, target, new Concentration(player).getDamageModifier());
						EffectsUtil.sendParticleToLocation(ParticleEffect.SNOW_SHOVEL, target.getLocation().add(0, 0.5, 0), 0.1F, 0.1F,
								0.1F, 0.2F, 15);
						player.playSound(target.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 0.5F, 2F);
					}
				}
			}.runTaskLater(InfernalRealms.getPlugin(), 5L);
			newStacks = 0;
			if (target.hasMetadata("ConcentrationStacksParticles")) {
				((BukkitTask) target.getMetadata("ConcentrationStacksParticles").get(0).value()).cancel();
				target.removeMetadata("ConcentrationStacksParticles", InfernalRealms.getPlugin());
			}
			player.updateInventory(); // Removes item text
			target.removeMetadata("ConcentrationStacks", InfernalRealms.getPlugin());
			return;
		}
		String bars = "| | | |";
		bars = ChatColor.BLUE + bars.substring(0, newStacks * 2) + ChatColor.GRAY + bars.substring(newStacks * 2);
		ItemMessageHandler.displayMessage(player,
				ChatColor.GRAY + "" + ChatColor.BOLD + "[ " + bars + ChatColor.GRAY + ChatColor.BOLD + " ]");
		target.setMetadata("ConcentrationStacks", new FixedMetadataValue(InfernalRealms.getPlugin(), newStacks));
		if (target.hasMetadata("ConcentrationStacksParticles")) {
			((BukkitTask) target.getMetadata("ConcentrationStacksParticles").get(0).value()).cancel();
			target.removeMetadata("ConcentrationStacksParticles", InfernalRealms.getPlugin());
		}
		final double stacks = newStacks;
		final BukkitTask concentrationParticles = new BukkitRunnable() {

			@Override
			public void run() {
				// Particle Effects
				if (target.isDead()) {
					cancel();
					return;
				}
				for (double i = 1; i < stacks + 1; i++) {
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(0.8, i / 1.5 - 0.2, 0), 0F, 0F,
							0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(0, i / 1.5 - 0.2, -0.8), 0F, 0F,
							0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(-0.8, i / 1.5 - 0.2, 0), 0F, 0F,
							0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(0, i / 1.5 - 0.2, 0.8), 0F, 0F,
							0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(0.8, i / 1.5 - 0.2, 0.8), 0F, 0F,
							0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(-0.8, i / 1.5 - 0.2, -0.8), 0F,
							0F, 0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(-0.8, i / 1.5 - 0.2, 0.8), 0F,
							0F, 0F, 0F, 1);
					EffectsUtil.sendParticleToLocation(ParticleEffect.WATER_WAKE, target.getLocation().add(0.8, i / 1.5 - 0.2, -0.8), 0F,
							0F, 0F, 0F, 1);
				}
			}

		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 5L);
		target.setMetadata("ConcentrationStacksParticles", new FixedMetadataValue(InfernalRealms.getPlugin(), concentrationParticles));

		// Icon Removal
		if (target.hasMetadata("ConcentrationStacksParticlesRemover")) {
			((BukkitTask) target.getMetadata("ConcentrationStacksParticlesRemover").get(0).value()).cancel();
		}
		target.setMetadata("ConcentrationStacksParticlesRemover", new FixedMetadataValue(InfernalRealms.getPlugin(), new BukkitRunnable() {

			public void run() {
				target.removeMetadata("ConcentrationStacks", InfernalRealms.getPlugin());
				concentrationParticles.cancel();
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 60L)));
	}

	private static int getConcentrationStacks(LivingEntity target) {
		if (!target.hasMetadata("ConcentrationStacks"))
			return 0;
		return (int) target.getMetadata("ConcentrationStacks").get(0).value();
	}

	/**
	 * Applies poison to the target
	 * @param target The target to be poisoned
	 * @param durationSeconds How many seconds the poison will tick for (damages once every second)
	 * @param damageModifier The damage modifier that will be spread out through the WHOLE poison duration
	 */
	public static void applyPoison(Player player, LivingEntity target, int durationSeconds, double damageModifier) {
		double damageModifierTick = damageModifier / durationSeconds;
		new BukkitRunnable() {
			int count = 1;

			@Override
			public void run() {
				if (!target.isValid() || count >= durationSeconds) {
					cancel();
					return;
				}
				forceDamage(player, target, damageModifierTick);
				count++;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 20L, 20L);
	}

	public static void applyWeakness(LivingEntity target, int durationSeconds, double reductionModifier) {
		target.setMetadata("Weakness", new FixedMetadataValue(InfernalRealms.getPlugin(), reductionModifier));
		new BukkitRunnable() {

			@Override
			public void run() {
				if (target.hasMetadata("Weakness")) {
					target.removeMetadata("Weakness", InfernalRealms.getPlugin());
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 20 * durationSeconds);
	}

}
