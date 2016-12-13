package net.infernalrealms.quests.objectives;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;

public abstract class ObjectiveCompleteQuest extends Objective implements PassiveCompletion {

	private HardcodedQuest.QuestName goalQuest;

	public ObjectiveCompleteQuest(HardcodedQuest.QuestName goalQuest) {
		super(1, 0);
		this.goalQuest = goalQuest;
	}

	public HardcodedQuest.QuestName getGoalQuest() {
		return this.goalQuest;
	}

	@Override
	public boolean check(Player player) {
		if (Quest.checkQuestCompletion(player, getGoalQuest())) {
			addProgress(1);
			return true;
		}
		return false;
	}

}
