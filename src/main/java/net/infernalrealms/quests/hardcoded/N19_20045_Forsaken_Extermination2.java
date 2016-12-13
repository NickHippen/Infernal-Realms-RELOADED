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

public class N19_20045_Forsaken_Extermination2 extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Forsaken Extermination 2";
	public static final int QUEST_ID = 20045;
	public static final int REQUIRED_LEVEL = 19;

	public N19_20045_Forsaken_Extermination2(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Titus", "The Forsaken have made it deeper into the castle.", 
											   "They are quickly spreading so we need you to take them out."),
				},
				{ // Part 2
					new ObjectiveKill(101, 85),
				},
				{ // Part 3
					new ObjectiveTalk("Titus", "Good Job! I knew I could count on you."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2750));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 4, 50)));

		// Quest Log Description
		description.add("The Forsaken have made it deeper into the castle. They");
		description.add("are quickly spreading so we need you to take them out.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.THE_VALLEY);
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
