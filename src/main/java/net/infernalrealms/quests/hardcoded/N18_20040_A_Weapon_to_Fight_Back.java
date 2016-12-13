package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N18_20040_A_Weapon_to_Fight_Back extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "A Weapon to Fight Back";
	public static final int QUEST_ID = 20040;
	public static final int REQUIRED_LEVEL = 18;

	public N18_20040_A_Weapon_to_Fight_Back(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Alok", "I’ve made it far enough into the castle to see some ghosts that might hold the key to eliminating all of these monsters.", 
											  "It’s just a guess, but I think if you retrieve some Ghost Fluid from the Wraiths further in the castle...",
											  "...we might be able to recover the castle to its glory."),
				},
				{ // Part 2
					new ObjectiveKill(94, 80),
					new ObjectiveDeliver("Alok", "GhostFluid", 12, "All right… let’s see here..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 3, 50)));

		// Quest Log Description
		description.add("I’ve made it far enough into the castle to see some ghosts");
		description.add("that might hold the key to eliminating all of these monsters.");
		description.add("It’s just a guess, but I think if you retrieve some Ghost");
		description.add("Fluid from the Wraiths further in the castle we might be");
		description.add("able to recover the castle to its glory.");
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
