package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N13_20024_Mindless_Gorks extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Mindless Gorks";
	public static final int QUEST_ID = 20024;
	public static final int REQUIRED_LEVEL = -1;

	public N13_20024_Mindless_Gorks(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Naloth", "I’m sure you have heard of Gorrak and how he has convinced these idiotic goblins to follow him…",
												"how about you thin out his “army” for me?"),
				},
				{ // Part 2
					new ObjectiveKill(31, 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1149, 92, 549);
						}
					},
					new ObjectiveKill(32, 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1149, 92, 549);
						}
					},
					new ObjectiveKill(40, 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1149, 92, 549);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Naloth", "Good work. His plans should start to fall apart."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 20)));

		// Quest Log Description
		description.add("I’m sure you have heard of Gorrak and how he has");
		description.add("convinced these idiotic goblins to follow him… how");
		description.add("about you thin out his “army” for me?");
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
