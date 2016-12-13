package net.infernalrealms.mobs.spells;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.spells.controllers.CastController;
import net.infernalrealms.mobs.types.InfernalMob;

public abstract class MobSpell implements Flagable {

	private InfernalMob caster;
	private CastController controller;

	@AllowFlagValue
	private int castDelay;
	@AllowFlagValue
	private double castSpeed;

	public MobSpell(InfernalMob caster) {
		this.setCaster(caster);
	}

	public void cast() {
		// Slow or stop movement
		this.getCaster().setSpeed(0);

		new BukkitRunnable() {

			@Override
			public void run() {
				castAfterDelay();
				// Return movement
				getCaster().setSpeed(getCaster().getData().getSpeed());
			}
		}.runTaskLater(InfernalRealms.getPlugin(), getCastDelay());
	}

	protected abstract void castAfterDelay();

	public abstract String getDisplayName();

	Vector trimVector(Vector vec) {
		vec.normalize();
		vec.setX(vec.getX() / 2D);
		vec.setY(vec.getY() / 2D);
		vec.setZ(vec.getZ() / 2D);
		return vec;
	}

	public InfernalMob getCaster() {
		return caster;
	}

	public void setCaster(InfernalMob caster) {
		this.caster = caster;
	}

	public int getCastDelay() {
		return castDelay;
	}

	public CastController getController() {
		return controller;
	}

	public void setController(CastController controller) {
		this.controller = controller;
	}

}
