package net.infernalrealms.mobs.spells;

import org.bukkit.entity.LivingEntity;

import net.infernalrealms.mobs.spells.controllers.CastController;
import net.infernalrealms.mobs.types.InfernalMob;

public class FireSpell extends DamagingMobSpell {

	public static final String DISPLAY_NAME = "Fire";

	public FireSpell(InfernalMob caster) {
		super(caster);
	}

	@Override
	public void castAfterDelay() {
		if (this.getCaster().getSelf().getGoalTarget() == null) {
			return;
		}
		hitTarget((LivingEntity) this.getCaster().getSelf().getGoalTarget().getBukkitEntity());
	}

	@Override
	public void hitTarget(LivingEntity target) {
		target.setFireTicks(100);
	}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
