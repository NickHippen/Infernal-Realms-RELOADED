package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;

public class N5_20007_Nora_Plants extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "No'ra Plants";
	public static final int QUEST_ID = 20007;
	public static final int REQUIRED_LEVEL = 5;

	public N5_20007_Nora_Plants(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveBreakBlock("Nora Plant", 40) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1016, 70, 1031);
						}
					},
				},
				{ // Part 2
					new ObjectiveDeliver("Farah", "NoraLeaves", 30, "Thank you! These will do great good for my research."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 360));
		rewards.add(new Reward(Reward.Type.MONEY, 65));

		// Quest Log Description
		description.add("You might have seen the No'ra leaves around the");
		description.add("plains by now... do you think you could collect");
		description.add("me some?");
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
