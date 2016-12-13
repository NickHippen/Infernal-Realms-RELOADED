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

public class N5_20005_ABountyUnclaimed extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "A Bounty Unclaimed";
	public static final int QUEST_ID = 20005;
	public static final int REQUIRED_LEVEL = 5;

	public N5_20005_ABountyUnclaimed(Player player) {
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
					new ObjectiveTalk("William", "Greyhound... we have a history. Lets just say, I need him dead."),
				},
				{ // Part 2
					new ObjectiveKill(27, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1158, 80, 990);
						}
					},
				},
				new Objective[] 
				{ // Part 3
	 				new ObjectiveTalk("William", "Finally, Greyhound is dead. I’m at peace."),
				}
		};
		return objectives;
		// @formatter:on
	}

	/* 		rewards.add(new Reward(Reward.Type.EXP, 5));
	 * 		rewards.add(new Reward(Reward.Type.MONEY, 5));
	 * 		rewards.add(new Reward(Reward.Type.SET_ITEM, "Flaming Kitten"));
	 *      rewards.add(new Reward(Reward.Type.TIER_ITEM, "Uncommon 1-4));
	 *      rewards.add(new Reward(Reward.Type.GEM, "Uncommon 1-4));
	 *      rewards.add(new Reward(Reward.Type.POTION, "Potion of Health", 1));
	 *      rewards.add(new Reward(Reward.Type.MISC_ITEM, "Item of Doom", 1));
	 */

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 380));
		rewards.add(new Reward(Reward.Type.MONEY, 40));

		// Quest Log Description
		description.add("William has a history with the Dire Wolf named");
		description.add("Greyhound. Kill him and retrieve his head to ");
		description.add("prove the kill. He is in his den closed outside");
		description.add("of Enen.");

		// Quest Upon Completion
		// HardcodedQuest.QuestName.PATH_TO_ENEN.getQuestID()
		//		questUponCompletionID = HardcodedQuest.QuestName.LERNIN_HERBS.getQuestID();
		// Message Upon Completion
		//		questEndMessage = "";
		// Warp Upon Completion
		//		questEndWarp = "";
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
