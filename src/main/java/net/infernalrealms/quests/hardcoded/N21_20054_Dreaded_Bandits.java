package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N21_20054_Dreaded_Bandits extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Dreaded Bandit";
	public static final int QUEST_ID = 20054;
	public static final int REQUIRED_LEVEL = 21;

	public N21_20054_Dreaded_Bandits(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Maud Maxine", "The Bandits have been terrorizing travelers that pass through these lands.", 
													 "It's time for their tyranny to end."),
				},
				{ // Part 2
					new ObjectiveKill(47, 55),
					new ObjectiveKill(48, 35),
				},
				{ // Part 3
					new ObjectiveTalk("Maud Maxine", "Thanks wanderer your work will not be forgotten."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3750));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));

		// Quest Log Description
		description.add("The Bandits have been terrorizing travelers that pass");
		description.add("through these lands. It's time for their tyranny to end.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.BANDIT_MINING);
		super.giveRewards();
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public boolean isStory() {
		return false;
	}

}
