package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N21_20053_The_Dwellers extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "The Dwellers";
	public static final int QUEST_ID = 20053;
	public static final int REQUIRED_LEVEL = 21;

	public N21_20053_The_Dwellers(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Rhazien", "There is a great excavation site over by the bandit camps but it is overrun by a bunch of Cave Dwellers and Nymphs.", 
												 "If you kill them for me I will let you mine there!"),
				},
				{ // Part 2
					new ObjectiveKill(54, 85),
					new ObjectiveKill(55, 65),
				},
				{ // Part 3
					new ObjectiveTalk("Rhazien", "Thanks you are free to mine there whenever you wish."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3900));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 10, 0)));

		// Quest Log Description
		description.add("There is a great excavation site over by the bandit camps");
		description.add("but it is overrun by a bunch of Cave Dwellers and Nymphs.");
		description.add("If you kill them for me I will let you mine there!");
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
