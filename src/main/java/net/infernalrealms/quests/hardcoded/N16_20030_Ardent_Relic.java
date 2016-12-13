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
import net.infernalrealms.util.GeneralUtil;

public class N16_20030_Ardent_Relic extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Ardent Relic";
	public static final int QUEST_ID = 20030;
	public static final int REQUIRED_LEVEL = 16;

	public N16_20030_Ardent_Relic(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Murik", "There is a relic in that ancient city further down the path.", 
											   "The city is overrun by the undead and I dont want to risk any of my soldiers lives to retrieve it.", 
											   "We believe the relic is on a creature called Balthier.", 
											   "Retrieve it for us and you will be rewarded greatly."),
				},
				{ // Part 2
					new ObjectiveKill(82, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 684, 62, 691);
						}
					},
					new ObjectiveDeliver("Murik", "ArdentRelic", "How magnificent the Ardent relic is. The power this relic holds is astonishing.", 
																 "Thank you for retrieving this for us."),
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
		description.add("There is a relic in that ancient city further down the");
		description.add("path. The city is overrun by the undead and I dont want");
		description.add("to risk any of my soldiers lives to retrieve it. We");
		description.add("believe the relic is on a creature called Balthier.");
		description.add("Retrieve it for us and you will be rewarded greatly.");
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
