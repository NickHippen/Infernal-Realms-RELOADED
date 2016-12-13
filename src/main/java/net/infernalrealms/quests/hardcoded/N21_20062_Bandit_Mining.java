package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N21_20062_Bandit_Mining extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Bandit Mining";
	public static final int QUEST_ID = 20062;
	public static final int REQUIRED_LEVEL = -1;

	public N21_20062_Bandit_Mining(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Maud Maxine", "The bandits have enslaved a group of goblins and are forcing them to work in the mines.", 
													 "The goblins can not be reasoned with so you will have to put them out of their misery."),
				},
				{ // Part 2
					new ObjectiveKill(49, 85),
				},
				{ // Part 3
					new ObjectiveTalk("Maud Maxine", "Those goblins are in better places now thanks to you."),
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
		description.add("The bandits have enslaved a group of goblins and are");
		description.add("forcing them to work in the mines. The goblins can not");
		description.add("be reasoned with so you will have to put  them out of");
		description.add("their misery.");
	}

	@Override
	public void giveRewards() {
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
