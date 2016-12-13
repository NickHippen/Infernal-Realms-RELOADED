package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N21_20052_Xoumien extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Xoumien";
	public static final int QUEST_ID = 20052;
	public static final int REQUIRED_LEVEL = 21;

	public N21_20052_Xoumien(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Magnus", "Xoumien has wrote a Creed that contains a ritual for the demise of this world.", 
												"The Fabler’s and Evil Worshippers are trying to perform this ritual.", 
												"Stop them and bring back the materials used in the ritual so I can find out how to stop it."),
				},
				{ // Part 3
					new ObjectiveDeliver("Magnus", "RitualShroom", 30, "With these shrooms I will get started right away figuring out how to stop this ritual."),
					new ObjectiveDeliver("Magnus", "XoumienTheDemiseCreed", 10, "These creeds should help me figure out how to stop the ritual."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3750));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));

		// Quest Log Description
		description.add("Xoumien has wrote a Creed that contains a ritual for the");
		description.add("demise of this world. The Fabler’s and Evil Worshippers");
		description.add("are trying to perform this ritual. Stop them and bring");
		description.add("back the materials used in the ritual so I can find out");
		description.add("how to stop it.");
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
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
