package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.MobSpell;

public class HealthCastController extends CastController {

	private boolean casted = false;

	@AllowFlagValue
	private float percent;

	public HealthCastController(MobSpell spell) {
		super(spell);
	}

	@Override
	public boolean check() {
		return !this.casted && (getSpell().getCaster().getSelf().getHealth() / getSpell().getCaster().getSelf().getMaxHealth()) < percent;
	}

	@Override
	public boolean trigger() {
		this.casted = true;
		return super.trigger();
	}

}
