package net.infernalrealms.mobs.spells;

import net.infernalrealms.mobs.types.InfernalMob;

public class TeleportSpell extends MobSpell {

	public static final String DISPLAY_NAME = "Teleport";

	public TeleportSpell(InfernalMob caster) {
		super(caster);
	}

	@Override
	public void castAfterDelay() {
		if (this.getCaster().getSelf().getGoalTarget() == null) {
			return;
		}
		getCaster().getBukkitLivingEntity().teleport(this.getCaster().getSelf().getGoalTarget().getBukkitEntity().getLocation());
	}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
