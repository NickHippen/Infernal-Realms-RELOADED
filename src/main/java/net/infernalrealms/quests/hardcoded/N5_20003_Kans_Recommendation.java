package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N5_20003_Kans_Recommendation extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Kan's Recommendation";
	public static final int QUEST_ID = 20003;
	public static final int REQUIRED_LEVEL = -1;

	public N5_20003_Kans_Recommendation(Player player) {
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
				new ObjectiveTalk("Zenumbra", " Hey there! You say Kan sent ya? Well, he’s a good friend of mine, so I’ll trust his judgement.",
						"By the looks of it, it seems like he sent you to me cause’ you’re a capable person. How bout I give you a task?",
						"Do you think you could collect 5 Ler’nin Herbs for me? Those Elromian folk seem to have an abundance of them."),
			},
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
		rewards.add(new Reward(Reward.Type.EXP, 180));
		rewards.add(new Reward(Reward.Type.MONEY, 21));

		// Quest Log Description
		description.add("Kan the Priest wants you to speak");
		description.add("to Zenumbra at the Spider Inn.");
	}

	@Override
	public void giveRewards() {
		getQuest(getPlayer(), QuestName.LERNIN_HERBS);
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
