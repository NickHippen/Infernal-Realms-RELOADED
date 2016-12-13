package net.infernalrealms.player;

import org.bukkit.ChatColor;

import net.infernalrealms.skills.archer.Concentration;
import net.infernalrealms.skills.archer.Haste;
import net.infernalrealms.skills.archer.MagnumShot;
import net.infernalrealms.skills.archer.SharpEye;
import net.infernalrealms.skills.archer.VenomousArrow;
import net.infernalrealms.skills.general.Skill;
import net.infernalrealms.skills.magician.ClearMind;
import net.infernalrealms.skills.magician.Frostbite;
import net.infernalrealms.skills.magician.Levitate;
import net.infernalrealms.skills.magician.Pyroblast;
import net.infernalrealms.skills.magician.Static;
import net.infernalrealms.skills.warrior.Charge;
import net.infernalrealms.skills.warrior.ConcussiveBlows;
import net.infernalrealms.skills.warrior.Leech;
import net.infernalrealms.skills.warrior.Recovery;
import net.infernalrealms.skills.warrior.Rumble;

public enum PlayerClass {

	//@formatter:off
	BEGINNER("Beginner", ChatColor.WHITE, "à®ƒ", null), 
	@SuppressWarnings("unchecked") ARCHER("Archer", ChatColor.DARK_GREEN, "âž¶", new Class[] { MagnumShot.class, VenomousArrow.class, Haste.class, SharpEye.class, Concentration.class }), 
	@SuppressWarnings("unchecked") WARRIOR("Warrior", ChatColor.DARK_GRAY, "â�‡", new Class[] { Charge.class, Rumble.class, Leech.class, Recovery.class, ConcussiveBlows.class }), 
	@SuppressWarnings("unchecked") MAGICIAN("Magician", ChatColor.BLUE, "âšš", new Class[] { Levitate.class, Pyroblast.class, Frostbite.class, Static.class, ClearMind.class });
	//@formatter:on

	public static final String SKILL_INDICATOR = "§r§r§r";

	private String name;
	private ChatColor color;
	private String suffix;
	private Class<? extends Skill>[] skills;

	private PlayerClass(String name, ChatColor color, String suffix, Class<? extends Skill>[] skills) {
		this.name = name;
		this.color = color;
		this.suffix = suffix;
		this.skills = skills;
	}

	public static PlayerClass fromString(String s) {
		for (PlayerClass playerClass : values()) {
			if (playerClass.getName().equals(s))
				return playerClass;
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public Class<? extends Skill>[] getSkills() {
		return this.skills;
	}

	public String toString() {
		return this.getName();
	}

	public String getSuffixString() {
		return this.suffix;
	}

	public ChatColor getColor() {
		return this.color;
	}

}
