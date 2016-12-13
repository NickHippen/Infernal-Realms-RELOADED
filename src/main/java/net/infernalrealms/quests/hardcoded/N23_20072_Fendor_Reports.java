package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N23_20072_Fendor_Reports extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Fendor Reports";
	public static final int QUEST_ID = 20072;
	public static final int REQUIRED_LEVEL = 23;

	public N23_20072_Fendor_Reports(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Durer", "I'm glad to finally meet you. Fendor needs your help with a big issue.", 
												   "The Z’mem Scouts have been gathering information about fendor.", 
												   "They will use this information to attack us when we are weakest.", 
												   "You need to stop them before they do this or the trade route to Cortona will be taken over."),
					},
					{ // Part 2
						new ObjectiveDeliver("Durer", "FendorReport", 35, "Thanks to you we have one this battle but the war is not over."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 5500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 9, 0)));

		// Quest Log Description
		description.add("I'm glad to finally meet you. Fendor needs your help");
		description.add("with a big issue. The Z’mem Scouts have been gathering");
		description.add("information about fendor. They will use this information");
		description.add("to attack us when we are weakest. You need to stop them");
		description.add("before they do this or the trade route to cortona will");
		description.add("be taken over.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.ZMEM_WAR);
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
