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

public class N8_20013_They_Scare_Me extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "They scare me...";
	public static final int QUEST_ID = 20013;
	public static final int REQUIRED_LEVEL = 8;

	public N8_20013_They_Scare_Me(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveKill(16, 15) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 835, 76, 1207);
						}
					},
					new ObjectiveDeliver("Hermel", "ElromianPlate", 6, "Wow, the metal really is as strong as they say!", "Thanks!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 540));
		rewards.add(new Reward(Reward.Type.MONEY, 75));

		// Quest Log Description
		description.add("I really really want some of that amazing Elromian");
		description.add("plate, but only the brutes carry it. I wouldn't even");
		description.add("think of facing one; they are huge! Do you think you");
		description.add("could help me out?");
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
