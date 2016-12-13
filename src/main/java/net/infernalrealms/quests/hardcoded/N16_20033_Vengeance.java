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

public class N16_20033_Vengeance extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Vengeance";
	public static final int QUEST_ID = 20033;
	public static final int REQUIRED_LEVEL = 16;

	public N16_20033_Vengeance(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Vandran", "I am Vandran, the God of Vengeance.", 
												 "These Grounders have been ravaging and disrespecting my shrine.", 
												 "I want you to teach them a lesson by killing them all."),
				},
				{ // Part 2
					new ObjectiveKill(84, 60) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 625, 71, 575);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Vandran", "I reward greatly for those who are loyal to me."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 3, 0)));

		// Quest Log Description
		description.add("These Grounders have been ravaging and disrespecting my");
		description.add("shrine. I want you to teach them a lesson by killing");
		description.add("them all.");
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
