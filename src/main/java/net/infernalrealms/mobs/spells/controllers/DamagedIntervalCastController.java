package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.MobSpell;

/**
 * Represents a CastController that is triggered upon the mob taking damage x times within a given interval
 */
public class DamagedIntervalCastController extends DamagedCastController {

	@AllowFlagValue
	private int tickDelay;
	@AllowFlagValue
	private int hitsNeeded;

	private int currentHits;
	private int lastHitTick;

	public DamagedIntervalCastController(MobSpell spell) {
		super(spell);
		this.lastHitTick = -1;
	}

	@Override
	public boolean check() {
		return false;
	}

	@Override
	public boolean trigger() {
		if (getSpell().getCaster().getSelf().ticksLived - lastHitTick > tickDelay) {
			// Took too long, reset hits
			currentHits = 0;
		}
		currentHits++;
		lastHitTick = getSpell().getCaster().getSelf().ticksLived;
		if (currentHits >= hitsNeeded) {
			return super.trigger();
		}
		return false;
	}

}
