package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.MobSpell;
import net.infernalrealms.util.GeneralUtil;

/**
 * Represents a cast controller that is cast every interval between a "minTickRate" and "maxTickRate".
 * Note that the tick is checked every 10 ticks. 
 */
public class IntervalCastController extends CastController {

	@AllowFlagValue
	private int minTickRate;
	@AllowFlagValue
	private int maxTickRate;

	private int nextTick;

	public IntervalCastController(MobSpell spell) {
		super(spell);
		// Set defaults
		this.minTickRate = 40;
		this.maxTickRate = 40;

		setNextTick();
	}

	@Override
	public boolean check() {
		if (getSpell().getCaster().getSelf().ticksLived >= this.nextTick) {
			setNextTick();
			return true;
		}
		return false;
	}

	private void setNextTick() {
		this.nextTick += GeneralUtil.randomIntRange(minTickRate, maxTickRate);
	}

}
