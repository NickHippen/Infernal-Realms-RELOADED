package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;

public class N7_20010_Spider_Eyes extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Spider Eyes";
	public static final int QUEST_ID = 20010;
	public static final int REQUIRED_LEVEL = 7;

	public N7_20010_Spider_Eyes(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveDeliver("Farah", "NymSpiderEye", 10, "Thank you so much!", "I will put these to good use!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 390));
		rewards.add(new Reward(Reward.Type.MONEY, 55));

		// Quest Log Description
		description.add("There are spiders outside of Enen, and I need");
		description.add("their eyes to make an amazing soup for a family");
		description.add("gathering. Please bring me 10 spider eyes!");
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
