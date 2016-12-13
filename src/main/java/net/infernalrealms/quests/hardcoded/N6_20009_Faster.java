package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveLevelMountSpeed;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N6_20009_Faster extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Faster!";
	public static final int QUEST_ID = 20009;
	public static final int REQUIRED_LEVEL = 6;

	public N6_20009_Faster(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Neldor", "You should put that horse of yours to use!", "How about you train him a little to speed him up?"),
				},
				{ // Part 2
					new ObjectiveLevelMountSpeed(),
				},
				{ // Part 3
					new ObjectiveTalk("Neldor", "Great job!", "I can see you're becoming an expert rider!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 250));
		rewards.add(new Reward(Reward.Type.MONEY, 60));

		// Quest Log Description
		description.add("You should put that horse of yours to use! How");
		description.add("about you train him a little to speed him up?");
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
