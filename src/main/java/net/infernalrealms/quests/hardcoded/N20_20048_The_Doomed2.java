package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N20_20048_The_Doomed2 extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "The Doomed 2";
	public static final int QUEST_ID = 20048;
	public static final int REQUIRED_LEVEL = -1;

	public N20_20048_The_Doomed2(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Granger", "There is still another group of Doom Guards and Doom Brutes.", 
												 "Take them out to clear the final pathway to the vault."),
				},
				{ // Part 2
					new ObjectiveKill(99, 80),
					new ObjectiveKill(103, 40),
				},
				{ // Part 3
					new ObjectiveTalk("Granger", "Good job, the vault is almost ours now."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3250));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 5, 0)));

		// Quest Log Description
		description.add("There is still another group of Doom Guards and Doom Brutes.");
		description.add("Take them out to clear the final pathway to the vault.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.TREASURE_OF_THE_DOOMED);
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
