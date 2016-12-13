package net.infernalrealms.mobs.spells;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.util.EffectsUtil;

public class Gust extends DamagingMobSpell {

	public static final String DISPLAY_NAME = "Gust";

	@AllowFlagValue
	private int distance;
	@AllowFlagValue
	private float force;

	public Gust(InfernalMob caster) {
		super(caster);
	}

	@Override
	public void castAfterDelay() {
		if (this.getCaster().getSelf().getGoalTarget() == null) {
			return;
		}
		LivingEntity self = this.getCaster().getBukkitLivingEntity();
		List<Entity> nearbyEntities = self.getNearbyEntities(distance, distance, distance);
		Vector from = new Vector(self.getLocation().getX(), self.getLocation().getY(), self.getLocation().getZ());
		for (Entity e : nearbyEntities) {
			if (!(e instanceof Player)) {
				continue;
			}
			Player player = (Player) e;
			Vector to = new Vector(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
			Vector vectorTowards = to.subtract(from);
			player.setVelocity(vectorTowards.multiply(force));
		}
		EffectsUtil.sendParticleToLocation(ParticleEffect.CLOUD, this.getCaster().getBukkitLivingEntity().getLocation().add(0, 0.5, 0), 2F,
				0F, 2F, 0.01F, 50);
	}

	@Override
	public void hitTarget(LivingEntity target) {}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
