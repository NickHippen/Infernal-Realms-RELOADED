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

public class N7_20011_Zelk_and_her_Minions extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Zelk and her Minions";
	public static final int QUEST_ID = 20011;
	public static final int REQUIRED_LEVEL = 7;

	public N7_20011_Zelk_and_her_Minions(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Halldor", "Zelk has been spawning spiders across the plains for months now, we need her dead.", 
												 "Rumour also has it her body parts have healing powers...", 
												 "Bring me one of her legs!"),
				},
				{ // Part 2
					new ObjectiveKill(12, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 964, 76, 957);
						}
					},
					new ObjectiveKill(11, 12) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 964, 76, 957);
						}
					},
					new ObjectiveKill(10, 12) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 964, 76, 957);
						}
					},
					new ObjectiveDeliver("Halldor", "ZelksLeg", 1, "Good, good.", "Let's see if this works..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 440));
		rewards.add(new Reward(Reward.Type.MONEY, 75));

		// Quest Log Description
		description.add("Zelk has been spawning spiders across the plains");
		description.add("for months now, we need her dead. Rumour also has");
		description.add("it her body parts have healing powers.... Bring me");
		description.add("one of her legs!");
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
