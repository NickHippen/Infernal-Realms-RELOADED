package net.infernalrealms.mobs.spells;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.util.EffectsUtil;

public class StarStrike extends AreaSkyStrike {

	public static final String DISPLAY_NAME = "Star Strike";

	public StarStrike(InfernalMob caster) {
		super(caster);
		this.distance = 15;
		this.height = 7F;
		this.targeted = true;
		this.castCap = 5;
		this.spellDuration = 40;
	}

	@Override
	public void sendParticleEffect(Location location) {
		EffectsUtil.sendParticleToLocation(ParticleEffect.CRIT, location, 0.03F, 0.3F, 0.03F, 0F, 5);
	}

	@Override
	public void hitTarget(LivingEntity target) {
		EffectsUtil.sendParticleToLocation(ParticleEffect.CRIT, target.getLocation().add(0, 0.5, 0), 0.5F, 0.5F, 0.5F, 0.5F, 10);
		target.damage(getDamage(), this.getCaster().getBukkitLivingEntity());
	}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
