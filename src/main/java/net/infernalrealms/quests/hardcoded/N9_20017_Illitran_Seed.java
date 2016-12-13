package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N9_20017_Illitran_Seed extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Illitran Seed";
	public static final int QUEST_ID = 20017;
	public static final int REQUIRED_LEVEL = 9;

	public N9_20017_Illitran_Seed(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Edin", "You might have seen strange brown things growing from the ground.", "Those are what the peasants call the seed of death.", "They grow into poisonous plants that can spread", "across entire plains in a matter of years!", "They are characterized by a purple glow.", "Could you please destroy 20 Illitran Seeds in order to stump their growth?"),
				},
				{ // Part 2
					new ObjectiveBreakBlock("Illitran Seed", 30) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 862, 75, 778);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Edin", "You have helped the realm greatly.", "Take this as a token of my thanks."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 550));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 0)));

		// Quest Log Description
		description.add("You might have seen strange brown things growing from");
		description.add("the ground - those are what the peasants call the seed");
		description.add("of death. They grow into poisonous plants that can spread");
		description.add("across entire plains in a matter of years! They are");
		description.add("characterized by a purple glow.");
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
