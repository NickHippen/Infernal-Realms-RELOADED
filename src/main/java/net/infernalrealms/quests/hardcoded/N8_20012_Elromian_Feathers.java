package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N8_20012_Elromian_Feathers extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Elromian Feathers";
	public static final int QUEST_ID = 20012;
	public static final int REQUIRED_LEVEL = 8;

	public N8_20012_Elromian_Feathers(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Noro", "This may sound strange, but I need you to collect special feathers from the Elromian.", "They say they can be great for bringing luck to an entrepreneur as myself."),
				},
				{ // Part 2
					new ObjectiveDeliver("Noro", "ElromianFeather", 15, "Thank you!", "Maybe I won't be so unlucky now..."),
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
		description.add("This may sound strange, but I need you to collect");
		description.add("special feathers from the Elromian. They say they");
		description.add("can be great for bringing luck to an entrepreneur");
		description.add("as myself.");
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
