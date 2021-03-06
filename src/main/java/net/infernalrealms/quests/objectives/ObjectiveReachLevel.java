package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.infernalrealms.player.PlayerData;

public class ObjectiveReachLevel extends Objective implements PassiveCompletion {

	private int level;

	public ObjectiveReachLevel(int level) {
		super(1, 0);
		this.level = level;
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Reach level " + this.level;
	}

	@Override
	public boolean check(Player player) {
		if (PlayerData.getData(player).getLevel() > this.level) {
			setProgress(1);
			return true;
		}
		return false;
	}

}
