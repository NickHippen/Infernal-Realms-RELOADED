package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N20_20047_The_Valley extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "The Valley";
	public static final int QUEST_ID = 20047;
	public static final int REQUIRED_LEVEL = -1;

	public N20_20047_The_Valley(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Titus", "The lashers have taken over the holy ground outside the mountain.", 
											   "They have upset the gods. Stop them before the Gods wreak Vengeance."),
				},
				{ // Part 2
					new ObjectiveKill(102, 85),
				},
				{ // Part 3
					new ObjectiveTalk("Titus", "The Gods have been appeased."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3000));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 5, 0)));

		// Quest Log Description
		description.add("The lashers have taken over the holy ground outside the mountain.");
		description.add("They have upset the gods. Stop them before the Gods wreak Vengeance.");
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
