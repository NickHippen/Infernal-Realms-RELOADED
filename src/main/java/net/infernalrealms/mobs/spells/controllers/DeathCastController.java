package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.MobSpell;

public class DeathCastController extends CastController {

	public DeathCastController(MobSpell spell) {
		super(spell);
	}

	/**
	 * Not needed--triggered upon death
	 */
	@Override
	public boolean check() {
		return false;
	}

	@Override
	public boolean trigger() {
		getSpell().cast();
		return true;
	}

}
