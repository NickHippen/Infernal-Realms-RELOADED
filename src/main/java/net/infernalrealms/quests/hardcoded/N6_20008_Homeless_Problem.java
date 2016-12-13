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

public class N6_20008_Homeless_Problem extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Homeless Problem";
	public static final int QUEST_ID = 20008;
	public static final int REQUIRED_LEVEL = 6;

	public N6_20008_Homeless_Problem(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Tahir", "The homeless are stealing and taking over our safe streets...", "Could you eliminate 5 Weak Vagrants to help settle them down?"),
				},
				{ // Part 2
					new ObjectiveKill(9, 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1014, 72, 979);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Tahir", "Good job.", "The path should be more safe now thanks to you."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 395));
		rewards.add(new Reward(Reward.Type.MONEY, 55));

		// Quest Log Description
		description.add("The homeless are stealing and taking over our safe");
		description.add("streets... we need to show them a lesson!");
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
