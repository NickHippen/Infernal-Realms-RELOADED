package net.infernalrealms.skills.general;

public abstract class ActiveSkill extends Skill {

	public abstract void activate();

	public abstract boolean hasCooldown();

	public abstract void setCooldown();

}
