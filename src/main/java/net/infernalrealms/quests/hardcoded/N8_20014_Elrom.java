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

public class N8_20014_Elrom extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Elrom";
	public static final int QUEST_ID = 20014;
	public static final int REQUIRED_LEVEL = -1;

	public N8_20014_Elrom(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Fnerm", "Elrom has been controlling his minions and causing havoc near the inn for awhile now...", "He needs to be taken care of.", "Could you defeat him for us?"),
				},
				{ // Part 2
					new ObjectiveKill(21, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 837, 77, 1232);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Fnerm", "Thank you so much!", "I know it mustn't have been easy slaying him.", "Here, take this as thanks."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 700));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 0)));

		// Quest Log Description
		description.add("Elrom has been controlling his minions and causing");
		description.add("havoc near the inn for awhile now... he needs to be");
		description.add("taken care of.");
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
