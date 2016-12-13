package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N22_20067_Creeps_in_Alyuin extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Creeps in Alyuin";
	public static final int QUEST_ID = 20067;
	public static final int REQUIRED_LEVEL = 22;

	public N22_20067_Creeps_in_Alyuin(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Thord", "The Alyuin Jungle is very dangerous.", 
												   "The Jungle creeps attack anybody they see so it makes it hard for travelers to get past the jungle to Cortona.", 
												   "You could make this easier by killing any Jungle Creeps you come across."),
					},
					{ // Part 2
						new ObjectiveKill(62, 150),
					},
					{ // Part 3
						new ObjectiveTalk("Thord", "Alyuin is a safer place because of you, kind traveler."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 4500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 8, 0)));

		// Quest Log Description
		description.add("The Alyuin Jungle is very dangerous. The Jungle creeps");
		description.add("attack anybody they see so it makes it hard for travelers");
		description.add("to get past the jungle to Cortona. You could make this");
		description.add("easier by killing any Jungle Creeps you come across.");
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
