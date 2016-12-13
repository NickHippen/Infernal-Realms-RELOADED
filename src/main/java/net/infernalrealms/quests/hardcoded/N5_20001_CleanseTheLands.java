package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N5_20001_CleanseTheLands extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();
	public static int questUponCompletionID = 20002;

	// Basic Information
	public static final String QUEST_NAME = "Cleanse the Lands";
	public static final int QUEST_ID = 20001;
	public static final int REQUIRED_LEVEL = 5;

	public N5_20001_CleanseTheLands(Player player) {
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
				new ObjectiveTalk("Kan", "Hello, traveler.", "As you might have seen, there are Dire Wolves plaguing our lands.", "I need you to help cleanse this problem and eliminate 20 Dire Wolves."),
			},
			{ // Part 2
				new ObjectiveKill(8, 20) {
					@Override
					public Location getLocation() {
						return new Location(InfernalRealms.MAIN_WORLD, 1158, 80, 990);
					}
				},
			},
			new Objective[] 
			{ // Part 3
 				new ObjectiveTalk("Kan", "Thank you! You have made Enen a safer town! Lok'lul blesses you.",
 						"Now that I know you are capable of carrying out a task, I must ask you to do something else.",
 						"There are those who praise false gods, such as the water god Nami.",
 						" I ask that you eliminate these fanatics, and there will be a reward for you."),
			}
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 550));
		rewards.add(new Reward(Reward.Type.MONEY, 58));

		// Quest Log Description
		description.add("Kan the Priest wants you to");
		description.add("cleans the lands of foul Dire Wolves.");

		// Quest Upon Completion
		questUponCompletionID = HardcodedQuest.QuestName.KILL_FALSE_WORSHIPPERS.getQuestID();
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		if (questUponCompletionID != 0) {
			getQuest(getPlayer(), questUponCompletionID);
		}
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
