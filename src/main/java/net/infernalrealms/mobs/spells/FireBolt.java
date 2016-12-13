package net.infernalrealms.mobs.spells;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.util.EffectsUtil;

public class FireBolt extends TrackingBolt {

	public static final String DISPLAY_NAME = "Fire Bolt";

	public FireBolt(InfernalMob caster) {
		super(caster);
	}

	@Override
	public void sendParticleEffect(Location location) {
		EffectsUtil.sendParticleToLocation(ParticleEffect.FLAME, location, 0F, 0F, 0F, 0F, 1);
	}

	@Override
	public void hitTarget(LivingEntity target) {
		target.damage(getDamage(), this.getCaster().getBukkitLivingEntity());
	}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
