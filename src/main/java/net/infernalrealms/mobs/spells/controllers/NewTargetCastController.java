package net.infernalrealms.mobs.spells.controllers;

import net.infernalrealms.mobs.spells.MobSpell;
import net.minecraft.server.v1_9_R1.EntityLiving;

public class NewTargetCastController extends CastController {

	private EntityLiving previousTarget;

	public NewTargetCastController(MobSpell spell) {
		super(spell);
		this.previousTarget = getSpell().getCaster().getSelf().getGoalTarget();
	}

	@Override
	public boolean check() {
		if (!this.previousTarget.equals(getSpell().getCaster().getSelf().getGoalTarget())) {
			this.previousTarget = getSpell().getCaster().getSelf().getGoalTarget();
			return true;
		}
		return false;
	}

}
