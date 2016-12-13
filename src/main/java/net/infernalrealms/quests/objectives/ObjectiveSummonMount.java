package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;

public class ObjectiveSummonMount extends Objective {

	public ObjectiveSummonMount() {
		super(1, 0);
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Summon your mount";
	}
}
