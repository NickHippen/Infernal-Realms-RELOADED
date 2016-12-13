package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveInteractWithBlock;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N11_20018_Finding_Hagwin extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Finding Hagwin";
	public static final int QUEST_ID = 20018;
	public static final int REQUIRED_LEVEL = 11;

	public N11_20018_Finding_Hagwin(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Ellyn", "Hagwin went into Devdan with a small group, but they have not come back yet.", "I need to know if he is alright.", "Could you please find him and see if he is okay?"),
				},
				{ // Part 2
					new ObjectiveInteractWithBlock(new Location(InfernalRealms.MAIN_WORLD, 1219, 81, 737), ChatColor.ITALIC + "A note on Hagwin's body reads", ChatColor.ITALIC + "\"If anybody finds me here, tell Ellyn we will be together in the great beyond.\"") {
						@Override
						public String getDescription() {
							return "Find Hagwin";
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Ellyn", "What does this mean?", "He can't be dead...") {
						@Override
						public String getDescription() {
							return "Return to Ellyn";
						}
					},
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 760));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 25)));

		// Quest Log Description
		description.add("Hagwin went into Devdan with a small group, but they have");
		description.add("not come back yet. I need to know if he is alright. Could");
		description.add("you please find him and see if he is okay?");
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
