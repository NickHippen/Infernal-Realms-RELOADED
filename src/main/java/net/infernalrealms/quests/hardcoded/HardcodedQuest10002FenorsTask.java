package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.items.Pouch;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveTalkWithReward;

public class HardcodedQuest10002FenorsTask extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();
	public static int questUponCompletionID = 0;

	// Basic Information
	public static final String QUEST_NAME = "Fenor's Task";
	public static final int QUEST_ID = 10002;
	public static final int REQUIRED_LEVEL = -1;

	public HardcodedQuest10002FenorsTask(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	//	@Override
	//	public void nextPart() {
	//	  if (getPart() == 0) {
	//	    getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Ability points provide combat bonuses for each class. You receive 5 per level and each has a different function. For [yourclass] we recommend a balance between [stat] and [stat], Type /stats or use the inventory button to spend them at any time.");
	//	  }
	//	  super.nextPart();
	//	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalkWithReward("Fenor", new Reward(Reward.Type.MISC_ITEM, "FenorsPouch"), "Hey, you! Would you mind bringing this pouch up to Angar once you get into the inn?"),
				
			},
			new Objective[] 
			{ // Part 2
 				new ObjectiveDeliver("Angar", "FenorsPouch", "Welcome, newcomer. I see that you have brought me the pouch back up! Thanks, Fenor has been down in the caves for awhile now.", 
 						"The lands out of here have been quite dangerous lately. I’m always out to help fresh arrivals, you should probably take this pouch for yourself. Just let me take the coal out real quick!",
 						"I recommend you go talk to Taurnil before heading out of the building. Go down the next corridor and he should be in the reading hall upstairs. He’ll help you on your journey to Enen!") {
 					@Override
 					public String getDescription() {
						return "Take Fenor's Pouch to Angar";
 					}
 				}
			}
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 5));
		rewards.add(new Reward(Reward.Type.MONEY, 15));
		rewards.add(new Reward(Pouch.FENORS_POUCH));

		// Quest Log Description
		description.add("Hey, you! Would you mind bringing");
		description.add("this pouch up to Angar once you");
		description.add("get into the inn? Thanks!");

		// Quest Upon Completion
		// HardcodedQuest.QuestName.PATH_TO_ENEN.getQuestID()
		questUponCompletionID = HardcodedQuest.QuestName.PATH_TO_ENEN.getQuestID();
		// Message Upon Completion
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.PATH_TO_ENEN);
		//		if (questUponCompletionID != 0) {
		//			getQuest(getPlayer(), questUponCompletionID);
		//		}
		//		if (questEndMessage != null) {
		//			getPlayer().sendMessage(questEndMessage);
		//		}
		//		if (questEndWarp != null) {
		//			getPlayer().performCommand("/warp " + questEndWarp);
		//		}
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
