package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N22_20063_Through_the_Gate extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Through the Gate";
	public static final int QUEST_ID = 20063;
	public static final int REQUIRED_LEVEL = 22;

	public N22_20063_Through_the_Gate(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Bludwan", "I can open the gate but first I need the keys.", 
												 "The Bandits ran off with the 5 keys and are probably storing them on the Bandit Chiefs.", 
												 "You are going to need to recover the 5 keys."),
				},
				{ // Part 2
					new ObjectiveDeliver("Bludwan", "AyluinKey", 5, "I will allow you through the gate.", 
																	"You Should talk to Thord in Fendor, I'm sure he will have a task for you."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3750));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));
		rewards.add(new Reward(Reward.Type.CUSTOM, "Access through the gate"));

		// Quest Log Description
		description.add(" I can open the gate but first I need the keys. The");
		description.add("Bandits ran off with the 5 keys and are probably");
		description.add("storing them on the Bandit Chiefs. You are going to");
		description.add("need to recover the 5 keys.");
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
