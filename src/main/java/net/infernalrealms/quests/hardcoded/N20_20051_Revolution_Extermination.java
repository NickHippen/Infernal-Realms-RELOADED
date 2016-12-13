package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N20_20051_Revolution_Extermination extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Revolution Extermination";
	public static final int QUEST_ID = 20051;
	public static final int REQUIRED_LEVEL = -1;

	public N20_20051_Revolution_Extermination(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Thor Von Stein", "The Vagrant Anarchist are taking orders from the leaders.", 
														"If you kill the leaders the anarchist will be forced to retreat back to the mountains."),
				},
				{ // Part 2
					new ObjectiveKill(108, 55),
				},
				{ // Part 3
					new ObjectiveTalk("Thor Von Stein", "The anarchist should be fleeing now thanks to you. If you see any more just kill them ."),
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
		description.add("The Vagrant Anarchist are taking orders from the leaders.");
		description.add("If you kill the leaders the anarchist will be forced to");
		description.add("retreat back to the mountains.");
	}

	@Override
	public void giveRewards() {
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
