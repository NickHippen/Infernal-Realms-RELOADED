package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;

public class N5_20002_KillFalseWorshippers extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();
	public static int questUponCompletionID = 20003;

	// Basic Information
	public static final String QUEST_NAME = "Kill False Worshippers";
	public static final int QUEST_ID = 20002;
	public static final int REQUIRED_LEVEL = -1;

	public N5_20002_KillFalseWorshippers(Player player) {
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
				new ObjectiveKill(13, 6) {
					@Override
					public Location getLocation() {
						return new Location(InfernalRealms.MAIN_WORLD, 1043, 74, 829);
					}
				},
				new ObjectiveDeliver("Kan", "WaterNecklace", "Good, good. You seem like you know your way around a weapon.",
						"I have a friend in the Spider Inn, it is a ways down the path from here.",
						"Once you reach the inn, I recommend you speak to him.")
			},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 400));
		rewards.add(new Reward(Reward.Type.MONEY, 40));

		// Quest Log Description
		description.add("The Nami cultists seem to surround themselves in water.");
		description.add("The only place near that I know of is down the path,");
		description.add("near where the bandits are currently residing.");

		// Quest Upon Completion
		// HardcodedQuest.QuestName.PATH_TO_ENEN.getQuestID()
		questUponCompletionID = HardcodedQuest.QuestName.KANS_RECOMMENDATION.getQuestID();
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
