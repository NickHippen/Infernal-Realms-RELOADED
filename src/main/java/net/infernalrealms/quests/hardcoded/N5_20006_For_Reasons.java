package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;

public class N5_20006_For_Reasons extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "For... reasons.";
	public static final int QUEST_ID = 20006;
	public static final int REQUIRED_LEVEL = 5;

	public N5_20006_For_Reasons(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveDeliver("Gudrid", "NymDireWolfPaw", 8, "Thank you so much!", "I've been waiting a long time to get my hands on these.")
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 360));
		rewards.add(new Reward(Reward.Type.MONEY, 40));

		// Quest Log Description
		description.add("I'm scared to go outside of Enen... and I really");
		description.add("need some Dire Worlf paws. Don't ask why, I just");
		description.add("need them.");
	}

	@Override
	public void giveRewards() {
		getQuest(getPlayer(), QuestName.ELIMINATE_THE_THIEVING_SCUM);
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
