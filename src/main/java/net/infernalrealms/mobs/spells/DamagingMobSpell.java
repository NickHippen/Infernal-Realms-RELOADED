package net.infernalrealms.mobs.spells;

import org.bukkit.entity.LivingEntity;

import net.infernalrealms.mobs.spells.controllers.CastController;
import net.infernalrealms.mobs.types.InfernalMob;

public abstract class DamagingMobSpell extends MobSpell {

	public DamagingMobSpell(InfernalMob caster) {
		super(caster);
	}

	public double getDamage() {
		return this.getCaster().getData().getDamage();
	}

	public abstract void hitTarget(LivingEntity target);
}
