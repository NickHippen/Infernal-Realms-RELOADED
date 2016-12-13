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
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N14_20025_Spider_Sacs extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Spider Sacs";
	public static final int QUEST_ID = 20025;
	public static final int REQUIRED_LEVEL = 14;

	public N14_20025_Spider_Sacs(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Renourart", "These pests are causing me a lot of trouble.", 
												   "Kill some of em’ and bring me back their sacs as proof, will ya?"),
				},
				{ // Part 2
					new ObjectiveKill(33, 35) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 976, 70, 573);
						}
					},
					new ObjectiveDeliver("Renourart", "BlackSpiderSac", 6 ,"Great job! This should make my life a little easier."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 40)));

		// Quest Log Description
		description.add("These pests are causing me a lot of trouble. Kill");
		description.add("some of em’ and bring me back their sacs as proof,");
		description.add("will ya?");
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.POTION_MAGIC.getQuestID());
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
