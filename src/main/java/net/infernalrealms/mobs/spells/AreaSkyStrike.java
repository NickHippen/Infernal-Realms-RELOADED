package net.infernalrealms.mobs.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.spells.controllers.CastController;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.util.GeneralUtil;
import static net.infernalrealms.general.InfernalRealms.RANDOM;

import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.mobs.spells.MobSpell;
import net.infernalrealms.mobs.types.InfernalMob;

public abstract class AreaSkyStrike extends DamagingMobSpell implements AdjustableParticles {

	@AllowFlagValue
	protected int distance;
	@AllowFlagValue
	protected float height;
	@AllowFlagValue
	protected boolean targeted;
	@AllowFlagValue
	protected short castCap;
	@AllowFlagValue
	protected int spellDuration;

	private int ticks = -1;
	private int tickDelay = -1;

	protected AreaSkyStrike(InfernalMob caster) {
		super(caster);
	}

	@Override
	protected void castAfterDelay() {
		if (this.getCaster().getSelf().getGoalTarget() == null) {
			return;
		}
		// Set these values if they have't been set yet
		if (this.ticks == -1) {
			this.ticks = Math.round(height);
		}
		if (this.tickDelay == -1) {
			this.tickDelay = Math.round((float) spellDuration / (float) this.ticks);
		}

		// Spell Effect
		for (Location location : getTargetLocations()) {
			location.add(0, height, 0);
			new BukkitRunnable() {

				private int count = 0;

				@Override
				public void run() {
					if (count >= ticks) {
						cancel();
						return;
					}
					sendParticleEffect(location);
					location.subtract(0, 0.5, 0);
					List<Entity> nearbyEntities = GeneralUtil.getNearbyEntities(location, 0.5, 0.5, 0.5);
					for (Entity e : nearbyEntities) {
						if (!(e instanceof Player)) {
							continue;
						}
						Player player = (Player) e;
						hitTarget(player);
						cancel();
						return;
					}
					count++;
				}
			}.runTaskTimer(InfernalRealms.getPlugin(), 0L, tickDelay);
		}
	}

	public List<Location> getTargetLocations() {
		List<Location> targetLocations;
		if (targeted) {
			List<Entity> nearbyEntities = this.getCaster().getBukkitLivingEntity().getNearbyEntities(distance, distance, distance);
			targetLocations = new ArrayList<>(nearbyEntities.size());
			for (Entity e : nearbyEntities) {
				if (!(e instanceof Player)) {
					continue;
				}
				targetLocations.add(e.getLocation());
			}
		} else {
			targetLocations = new ArrayList<>(this.castCap);
			for (int i = 0; i < this.castCap; i++) {
				Location baseLocation = this.getCaster().getBukkitLivingEntity().getLocation();
				targetLocations.add(baseLocation.add((double) -this.distance + (2D * (double) this.distance) * RANDOM.nextDouble(), 0,
						(double) -this.distance + (2D * (double) this.distance) * RANDOM.nextDouble()));
			}
		}
		return targetLocations;
	}

}
