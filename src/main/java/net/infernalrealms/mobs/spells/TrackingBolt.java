package net.infernalrealms.mobs.spells;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.types.InfernalMob;

public abstract class TrackingBolt extends DamagingMobSpell implements AdjustableParticles {

	@AllowFlagValue
	private int distance;
	@AllowFlagValue
	private int ticks;
	@AllowFlagValue
	private int tickDelay;

	protected TrackingBolt(InfernalMob caster) {
		super(caster);
	}

	@Override
	protected void castAfterDelay() {
		if (this.getCaster().getSelf().getGoalTarget() == null) {
			return;
		}
		LivingEntity self = this.getCaster().getBukkitLivingEntity();
		Entity e = this.getCaster().getSelf().getGoalTarget().getBukkitEntity();
		if (!(e instanceof LivingEntity)) {
			return;
		}
		LivingEntity target = (LivingEntity) e;
		List<Entity> nearbyEntities = self.getNearbyEntities(this.distance, this.distance, this.distance);
		new BukkitRunnable() {

			private Location attackLocation = self.getEyeLocation();
			private int count = 0;
			Vector from = new Vector(self.getEyeLocation().getX(), self.getEyeLocation().getY(), self.getEyeLocation().getZ());

			@Override
			public void run() {
				if (count >= ticks) {
					cancel();
					return;
				}
				// Calculate direction
				from = new Vector(attackLocation.getX(), attackLocation.getY(), attackLocation.getZ());
				Vector to = new Vector(target.getEyeLocation().getX(), target.getEyeLocation().getY(), target.getEyeLocation().getZ());
				Vector vectorTowards = to.subtract(from);
				vectorTowards = trimVector(vectorTowards);

				// Change location and perform effects
				attackLocation.add(vectorTowards);
				sendParticleEffect(attackLocation);
				for (Entity e : nearbyEntities) {
					if (!(e instanceof Player)) {
						continue;
					}
					Player player = (Player) e;
					if (attackLocation.distanceSquared(player.getEyeLocation()) < 1) {
						// Attackable target found
						hitTarget(player);
						cancel();
						return;
					}
				}

				count++;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, tickDelay);
	}

}
