package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;

public class ObjectiveLearnSkill extends Objective {

	public ObjectiveLearnSkill() {
		super(1, 0);
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Learn a new skill";
	}

}
