package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N12_20021_Lost_Halls extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Lost Halls";
	public static final int QUEST_ID = 20021;
	public static final int REQUIRED_LEVEL = 12;

	public N12_20021_Lost_Halls(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Gryffen", "Acheron has awoken….  that's impossible!", "He has been dead for over 2000 years. Mordoc must be planning something big..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2200));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 5, 0)));

		// Quest Log Description
		description.add("Somewhere in Devdan is an entrance to the Lost Halls, an");
		description.add("ancient city that holds many treasures. The main entrance");
		description.add("was buried thousands of years ago during the Thracian war.");
		description.add("Find the secret entrance to the lost halls and clear it out.");
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
