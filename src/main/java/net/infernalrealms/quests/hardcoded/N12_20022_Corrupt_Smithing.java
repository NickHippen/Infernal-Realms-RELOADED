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

public class N12_20022_Corrupt_Smithing extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Corrupt Smithing";
	public static final int QUEST_ID = 20022;
	public static final int REQUIRED_LEVEL = 12;

	public N12_20022_Corrupt_Smithing(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Morden", "There are some Goblin smiths near the entrance of the cave who I need dead.", 
							"They have valuable iron ingots that they stole from the Guild and I’ve been assigned to get them back.", 
							"Eliminate 3 Goblin Blacksmiths and retrieve 8 Tempered Iron Ingots."),
				},
				{
					new ObjectiveKill(34, 3) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 969, 70, 762);
						}
					},
					new ObjectiveDeliver("Morden", "TemperedIronIngot", 8, "Thank you, this is a great relief."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1250));

		// Quest Log Description
		description.add("There are some Goblin smiths near the entrance of the cave");
		description.add("who I need dead. They have valuable iron ingots that they");
		description.add("stole from the Guild and I’ve been assigned to get them back.");
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
