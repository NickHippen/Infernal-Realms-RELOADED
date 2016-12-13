package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N22_20066_Sleasurs_Task extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Sleasur's Task";
	public static final int QUEST_ID = 20066;
	public static final int REQUIRED_LEVEL = 22;

	public N22_20066_Sleasurs_Task(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Sleasur", "The Crazy Fishermen are invading our fishing spots and stealing our Sailfin Fish.", 
													 "Could you clear out the Crazy Fishermen and bring our fish back?"),
					},
					{ // Part 2
						new ObjectiveKill(75, 100),
						new ObjectiveDeliver("Sleasur", "SailfinFish", 25, "Thanks, that will teach them a lesson for messing with Fendor."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 4500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));

		// Quest Log Description
		description.add("The Crazy Fishermen are invading our fishing spots and");
		description.add("stealing our Sailfin Fish. Could you clear out the Crazy");
		description.add("Fishermen and bring our fish back?");
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
