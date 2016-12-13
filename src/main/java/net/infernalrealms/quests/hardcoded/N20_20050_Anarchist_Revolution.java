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

public class N20_20050_Anarchist_Revolution extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Anarchist Revolution";
	public static final int QUEST_ID = 20050;
	public static final int REQUIRED_LEVEL = 20;

	public N20_20050_Anarchist_Revolution(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Thor Von Stein", "The Vagrant Anarchist have been terrorizing the farm and setting fire to the area.", 
														"We would be very appreciative if you could stop them for us."),
				},
				{ // Part 2
					new ObjectiveKill(107, 100),
				},
				{ // Part 3
					new ObjectiveTalk("Thor Von Stein", "Thank you, this is not the end of the battle but it is a good start."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 3500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));

		// Quest Log Description
		description.add("The Vagrant Anarchist have been terrorizing the farm and");
		description.add("setting fire to the area. We would be very appreciative if");
		description.add("you could stop them for us.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.REVOLUTION_EXTERMINATION);
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
