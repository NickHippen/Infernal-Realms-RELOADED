package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N23_20068_Tamed_Spiders extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Tamed Spiders";
	public static final int QUEST_ID = 20068;
	public static final int REQUIRED_LEVEL = 23;

	public N23_20068_Tamed_Spiders(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Thax", "The Jungle Creeps have tamed spiders and are using them to attack travelers.", 
												  "Kill the spiders so we can move on to the next step."),
					},
					{ // Part 2
						new ObjectiveKill(63, 55),
					},
					{ // Part 3
						new ObjectiveTalk("Thax", "Those spiders won't be bothering anybody again."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 4000));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 5, 0)));

		// Quest Log Description
		description.add("The Jungle Creeps have tamed spiders and are using them to");
		description.add("attack travelers. Kill the spiders so we can move on to the");
		description.add("next step.");
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
