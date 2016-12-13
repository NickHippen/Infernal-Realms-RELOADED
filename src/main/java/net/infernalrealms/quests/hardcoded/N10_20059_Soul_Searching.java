package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N10_20059_Soul_Searching extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Soul Searching";
	public static final int QUEST_ID = 20059;
	public static final int REQUIRED_LEVEL = 10;

	public N10_20059_Soul_Searching(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Marden", "The souls of many of our people have been trapped and stolen from us by the horrible beings inside of the Illitran tower.", 
							 					"Please, free our souls and kill the evil soul warden.", 
							 					"Once you have completed this task, speak to Nokealo."),
				},
				{ // Part 2
					new ObjectiveBreakBlock("Soul Containers", 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 838, 81, 809);
						}
					},
					new ObjectiveKill(130, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 827, 91, 821);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Nokealo", "Ah, I can assume you're done with Marden's task, then? Great!", 
												 "Talk to me once more when you're ready to continue."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 600));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 0)));

		// Quest Log Description
		description.add("The souls of many of our people have been trapped and");
		description.add("stolen from us by the horrible beings inside of the");
		description.add("Illitran tower. Please, free our souls and kill the");
		description.add("evil soul warden.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), QuestName.FREEING_OUR_SOULS);
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
