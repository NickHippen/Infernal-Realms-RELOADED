package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N5_20055_While_Your_At_It extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "While your at it...";
	public static final int QUEST_ID = 20055;
	public static final int REQUIRED_LEVEL = -1;

	public N5_20055_While_Your_At_It(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Gudrid", "They are very important to me!"),
				},
				{ // Part 2
					new ObjectiveKill(8, 15) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 829,  78, 1034);
						}
					},
					new ObjectiveKill(24, 5) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 829,  78, 1034);
						}
					},
					new ObjectiveKill(26, 5) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 829,  78, 1034);
						}
					},
					new ObjectiveDeliver("Gudrid", "NymDireWolfPaw", 25, "I can do so much with these, so many things..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 500));
		rewards.add(new Reward(Reward.Type.MONEY, 100));

		// Quest Log Description
		description.add("They are very important to me!");
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
