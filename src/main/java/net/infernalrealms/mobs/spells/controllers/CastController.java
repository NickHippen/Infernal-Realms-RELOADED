package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.Flagable;
import net.infernalrealms.mobs.spells.MobSpell;

public abstract class CastController implements Flagable {

	private MobSpell spell;

	public CastController(MobSpell spell) {
		this.setSpell(spell);
	}

	/**
	 * Determines whether or not this controller should be triggered
	 * @return whether or not the controller should be triggered
	 */
	public abstract boolean check();

	/**
	 * Called when the controller is activated
	 * @return whether or not the spell call was successful
	 */
	public boolean trigger() {
		if (!getSpell().getCaster().getSelf().isAlive() || !getSpell().getCaster().getSelf().valid) {
			return false;
		}
		getSpell().cast();
		return true;
	}

	public MobSpell getSpell() {
		return spell;
	}

	public void setSpell(MobSpell spell) {
		this.spell = spell;
	}

}
