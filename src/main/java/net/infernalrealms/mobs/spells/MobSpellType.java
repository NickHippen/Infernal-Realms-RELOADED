package net.infernalrealms.mobs.spells;

import java.lang.reflect.Constructor;

import net.infernalrealms.mobs.types.InfernalMob;

public enum MobSpellType {

	// @formatter:off
	FIRE_BOLT(FireBolt.class),
	GUST(Gust.class),
	STAR_STRIKE(StarStrike.class),
	;
	// @formatter:on

	public static Constructor<? extends MobSpell> NEW_SPELL;

	private Class<? extends MobSpell> spellClass;
	private Constructor<? extends MobSpell> spellConstructor;

	private MobSpellType(Class<? extends MobSpell> spellClass) {
		this.spellClass = spellClass;
		try {
			this.spellConstructor = this.spellClass.getConstructor(InfernalMob.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public Class<? extends MobSpell> getSpellClass() {
		return spellClass;
	}

	public Constructor<? extends MobSpell> getSpellConstructor() {
		return spellConstructor;
	}

	public static MobSpellType fromString(String string) {
		for (MobSpellType spellType : values()) {
			if (spellType.toString().equalsIgnoreCase(string)) {
				return spellType;
			}
		}
		return null;
	}

}
