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

public class N20_20049_Treasure_of_the_Doomed extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Treasure of the Doomed";
	public static final int QUEST_ID = 20049;
	public static final int REQUIRED_LEVEL = -1;

	public N20_20049_Treasure_of_the_Doomed(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Granger", "The Vault of the Doomed has been opened and it our our turn to take the treasure.", 
													"Kill whatever lies within and take the Ring of Oppression so we may destroy our enemies."),
				},
				{ // Part 2
					new ObjectiveKill(106, 1),
					new ObjectiveDeliver("Granger", "RingOfOppression", 1, "Ah this should fetch us a nice price to the highest bidder.", 
																		   "Oh you want a reward? Fine, take this.")
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3250));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 10, 0)));

		// Quest Log Description
		description.add("The Vault of the Doomed has been opened and it our our turn");
		description.add("to take the treasure. Kill whatever lies within and take the");
		description.add("Ring of Oppression so we may destroy our enemies.");
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
