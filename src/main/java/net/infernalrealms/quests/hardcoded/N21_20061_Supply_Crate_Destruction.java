package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N21_20061_Supply_Crate_Destruction extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Supply Create Destruction";
	public static final int QUEST_ID = 20061;
	public static final int REQUIRED_LEVEL = 21;

	public N21_20061_Supply_Crate_Destruction(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("La Croix", "The bandits have been storing their stolen goods in supply crates at their camp site.", 
												  "It would be impossible for you to steal them back but you can get in and destroy the crates.", 
												  "It will cause a lot of problems for the bandits."),
				},
				{ // Part 2
					new ObjectiveBreakBlock("Bandit Supply Crate", 35),
				},
				{ // Part 3
					new ObjectiveTalk("La Croix", "That should slow down the bandits."),
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
		description.add("The bandits have been storing their stolen goods in supply");
		description.add("crates at their camp site. It would be impossible for you");
		description.add("to steal them back but you can get in and destroy the crates.");
		description.add("It will cause a lot of problems for the bandits.");
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
