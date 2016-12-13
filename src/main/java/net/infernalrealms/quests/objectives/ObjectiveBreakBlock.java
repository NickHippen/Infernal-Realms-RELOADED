package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;

public class ObjectiveBreakBlock extends Objective {

	private String targetBlock;

	public ObjectiveBreakBlock(String targetBlock, int required) {
		super(required, 0);
		this.targetBlock = targetBlock;
	}

	public String getTargetBlock() {
		return this.targetBlock;
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Break [" + this.getProgress() + "/" + this.getRequired() + "] "
				+ this.getTargetBlock();
	}
}
