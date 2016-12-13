package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N18_20038_Forsaken_Extermination extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Forsaken Extermination";
	public static final int QUEST_ID = 20038;
	public static final int REQUIRED_LEVEL = 18;

	public N18_20038_Forsaken_Extermination(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Khusag", "Ah! Are you reinforcements? Well, regardless, could you help me real quick?", 
												"Forsaken have been getting closer and closer to our camp.", 
												"I’d be in your debt if you could help us by eliminating some to help push them back."),
				},
				{ // Part 2
					new ObjectiveKill(92, 70),
				},
				{
					new ObjectiveTalk("Khusag", "Well, hopefully that will keep them away from us for now.", 
												"Here, take this as a thanks."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2050));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 3, 5)));

		// Quest Log Description
		description.add("Ah! Are you reinforcements? Well, regardless, could");
		description.add("you help me real quick? Forsaken have been getting");
		description.add("closer and closer to our camp. I’d be in your debt");
		description.add("if you could help us by eliminating some to help push");
		description.add("them back.");
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
