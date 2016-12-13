package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HardcodedQuest10010TheZen extends HardcodedQuest {

	public static final String QUEST_NAME = "The Zen";
	public static final int QUEST_ID = 10010;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 400));
		rewards.add(new Reward(Reward.Type.MONEY, 15));

		// Description
		description.add("Speak to the Zen of Irion located");
		description.add("at the center of the castle.");
	}

	public HardcodedQuest10010TheZen(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Zen of Irion", ". . . PLAYER_NAME. I understand your intention to proceed to Enen. I can help you achieve this goal, but I ask something of you in return.", 
						"You are a PLAYER_CLASS, therefore I'm sure you must know at least the basics of fighting.", 
						"There are three items I need that are located throughout this fortress; a staff, a robe and a book.", 
						"They will return my power and allow me to lower the field of energy preventing humans from leaving this place.", 
						"Once you have found these, return to me and I will give you a set of instructions. Good luck."),
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.ZENS_TASK.getQuestID());
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
