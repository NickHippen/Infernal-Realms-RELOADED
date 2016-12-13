package net.infernalrealms.mobs.spells.controllers;

import java.lang.reflect.Constructor;

import net.infernalrealms.mobs.spells.MobSpell;

public enum CastControllerType {

	// @formatter:off
	DAMAGED(DamagedCastController.class),
	DAMAGED_INTERVAL(DamagedIntervalCastController.class),
	DEATH(DeathCastController.class),
	HEALTH(HealthCastController.class),
	INTERVAL(IntervalCastController.class),
	NEW_TARGET(NewTargetCastController.class),
	;
	// @formatter:on

	private Class<? extends CastController> controllerClass;
	private Constructor<? extends CastController> controllerConstructor;

	private CastControllerType(Class<? extends CastController> controllerClass) {
		this.controllerClass = controllerClass;
		try {
			this.controllerConstructor = this.controllerClass.getConstructor(MobSpell.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public Class<? extends CastController> getControllerClass() {
		return controllerClass;
	}

	public Constructor<? extends CastController> getControllerConstructor() {
		return controllerConstructor;
	}

	public static CastControllerType fromString(String string) {
		for (CastControllerType controllerType : values()) {
			if (controllerType.toString().equalsIgnoreCase(string)) {
				return controllerType;
			}
		}
		return null;
	}

}
