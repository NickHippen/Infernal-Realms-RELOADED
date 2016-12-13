package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N19_20043_The_Greater_Lich extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "The Greater Lich";
	public static final int QUEST_ID = 20043;
	public static final int REQUIRED_LEVEL = 19;

	public N19_20043_The_Greater_Lich(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Sir Richard", "In an effort to keep the Lich population down, we sent a party to defeat the Greater Lich.", 
													 "Unfortunately, it overwhelmed our party and we had to back off. If you ever get a chance, could take a shot at it?", 
													 "It’s located back down the corridor in a separate area."),
				},
				{ // Part 2
					new ObjectiveKill(105, 1),
				},
				{ // Part 3
					new ObjectiveTalk("Sir Richard", "Wow, impressive. Here, take this as a thanks."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 4, 0)));

		// Quest Log Description
		description.add(" In an effort to keep the Lich population down, we");
		description.add("sent a party to defeat the Greater Lich.");
		description.add("Unfortunately, it overwhelmed our party and we had to");
		description.add("back off. If you ever get a chance, could take a shot at");
		description.add("it? It’s located back down the corridor in a separate area.");
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
