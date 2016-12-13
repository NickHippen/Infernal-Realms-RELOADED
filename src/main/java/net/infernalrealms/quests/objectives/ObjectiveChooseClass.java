package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;

public class ObjectiveChooseClass extends Objective {

	public ObjectiveChooseClass() {
		super(1, 0);
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Select a class";
	}

}
