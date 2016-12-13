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
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N23_20070_Spider_Eggs extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Spider Eggs";
	public static final int QUEST_ID = 20070;
	public static final int REQUIRED_LEVEL = 23;

	public N23_20070_Spider_Eggs(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Thax", "The Jungle Creeps have been getting their spiders from the spider nests around the jungle.", 
												  "If we destroy the spider eggs before they hatch we won't have to deal with them."),
					},
					{ // Part 2
						new ObjectiveBreakBlock("Spider Egg", 30) {
							@Override
							public Location getLocation() {
								return new Location(InfernalRealms.MAIN_WORLD, 296, 65, 427);
							}
						},
					},
					{ // Part 3
						new ObjectiveTalk("Thax", "That should slow down the attacks on travelers.", 
												  "Thank you kind wander."),
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
		description.add("The Jungle Creeps have been getting their spiders from the");
		description.add("spider nests around the jungle. If we destroy the spider");
		description.add("eggs before they hatch we won't have to deal with them.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.SHELOB_THE_DEVOURER);
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
