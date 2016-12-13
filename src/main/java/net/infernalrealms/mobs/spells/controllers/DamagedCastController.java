package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.MobSpell;

/**
 * Represents a CastController that is triggered upon the mob taking damage
 */
public class DamagedCastController extends CastController {

	public DamagedCastController(MobSpell spell) {
		super(spell);
	}

	/**
	 * Not needed--triggered upon taking damage
	 */
	@Override
	public boolean check() {
		return false;
	}

}
