package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;

import net.infernalrealms.general.YAMLFile;

public class ObjectiveKill extends Objective {

	private int targetID;

	public ObjectiveKill(int targetID, int required) {
		super(required, 0);
		this.targetID = targetID;
	}

	public String getTargetName() {
		String name = YAMLFile.MOBS.getConfig().getString(targetID + ".Name");
		return name.substring(0, name.indexOf("["));
	}

	public String getTargetFullName() {
		return YAMLFile.MOBS.getConfig().getString(targetID + ".Name");
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Kill [" + this.getProgress() + "/" + this.getRequired() + "] "
				+ this.getTargetName();
	}
}
