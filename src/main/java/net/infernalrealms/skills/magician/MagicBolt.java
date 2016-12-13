package net.infernalrealms.skills.magician;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.MobEffects;
import net.infernalrealms.util.EffectsUtil;

/**
 * This is not a skill (in the same sense as the others). It is the magician basic attack.
 */
public class MagicBolt {

	private static final Random random = new Random();

	public static void cast(Player player) {
		if (hasRecentlyCasted(player)) {
			return;
		}
		setRecentlyCasted(player);
		boolean statik = random.nextInt(20) < 3; // 3/20 = 15%
		Location location = player.getEyeLocation().subtract(0, 0.2, 0);
		spell: {
			List<Entity> nearbyEntities = player.getNearbyEntities(30, 30, 30);
			for (int i = 0; i < 10; i++) {
				Vector v = player.getEyeLocation().getDirection();
				location.add(v);
				if (location.getBlock().getType().isSolid()) {
					break spell;
				}
				EffectsUtil.sendParticleToLocation(ParticleEffect.VILLAGER_HAPPY, location, 0F, 0F, 0F, 0.01F, 1);
				if (i % 3 == 0)
					if (statik) {
						EffectsUtil.sendParticleToLocation(ParticleEffect.CRIT_MAGIC, location, 0F, 0F, 0F, 0.01F, 3);
					} else {
						EffectsUtil.sendParticleToLocation(ParticleEffect.CRIT, location, 0F, 0F, 0F, 0.1F, 1);
					}
				for (Entity entity : nearbyEntities) {
					if (entity instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) entity;
						if (!(entity instanceof Player) && !(entity instanceof ArmorStand) && !entity.isDead()) {
							if (location.distanceSquared(entity.getLocation()) < 1
									|| location.distanceSquared(((LivingEntity) entity).getEyeLocation()) < 1) {
								if (statik) {
									Static.getEffect(player, le);
								}
								MobEffects.forceDamage(player, le, 1);
								i = 10;
								break;
							}
						}
					}
				}
			}
		}
	}

	public static boolean hasRecentlyCasted(Player player) {
		return player.hasMetadata("MagicBoltCooldown");
	}

	public static void setRecentlyCasted(final Player player) {
		if (hasRecentlyCasted(player)) {
			return;
		}
		player.setMetadata("MagicBoltCooldown", new FixedMetadataValue(InfernalRealms.getPlugin(), true));
		new BukkitRunnable() {

			@Override
			public void run() {
				if (player.isOnline()) {
					player.removeMetadata("MagicBoltCooldown", InfernalRealms.getPlugin());
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 10L);
	}
}
