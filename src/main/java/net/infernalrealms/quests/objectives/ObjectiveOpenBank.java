package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;

public class ObjectiveOpenBank extends Objective {

	public ObjectiveOpenBank() {
		super(1, 0);
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Open your bank";
	}
}
