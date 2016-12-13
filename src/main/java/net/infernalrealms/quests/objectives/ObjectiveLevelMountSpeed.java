package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.infernalrealms.player.PlayerData;

public class ObjectiveLevelMountSpeed extends Objective implements PassiveCompletion {

	public ObjectiveLevelMountSpeed() {
		super(1, 0);
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Level mount speed";
	}

	@Override
	public boolean check(Player player) {
		if (PlayerData.getData(player).getMountSpeed() > 1) {
			setProgress(1);
			return true;
		}
		return false;
	}

}
