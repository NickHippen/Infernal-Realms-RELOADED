package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10013Unknown_Places extends HardcodedQuest {

	public static final String QUEST_NAME = "Unknown Places";
	public static final int QUEST_ID = 10013;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 45));
		rewards.add(new Reward(Reward.Type.MONEY, 20));

		// Description
		description.add("Hmmm... Where am I? Maybe I should go find");
		description.add("someone and find out...");
	}

	public HardcodedQuest10013Unknown_Places(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Bella", "Hey, you! You’ve been asleep for the past few days. Not really sure what happened to you. Sorta just showed up on the outskirts of town, out of nowhere!", 
										   "The guards told me to keep you here, incase you were hurt.", 
										   "Where are we? Oh, we’re in Enen! Next thing you should do is probably talk to Kassen, he’s next to the tower in the town square. Good luck!"),
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		getQuest(getPlayer(), HardcodedQuest.QuestName.MEET_THE_TOWN.getQuestID());
		super.giveRewards();
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public boolean isStory() {
		return true;
	}

}
