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

public class N13_20023_Gorraks_Dagger extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Gorrak's Dagger";
	public static final int QUEST_ID = 20023;
	public static final int REQUIRED_LEVEL = 13;

	public N13_20023_Gorraks_Dagger(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Naloth", "Gorrak murdered innocent civilians in pursuit of his own agenda.", 
												"I need him dead, and I will make it worth your time.",
												"Oh, and could you bring me his dagger as proof?"),
				},
				{
					new ObjectiveKill(43, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1136, 92, 549);
						}
					},
					new ObjectiveDeliver("Naloth", "GorraksDagger", "Thank you, the innocents' deaths weren't in vain."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 15)));

		// Quest Log Description
		description.add("Gorrak murdered innocent civilians in pursuit of his own");
		description.add("agenda. I need him dead, and I will make it worth your");
		description.add("time. Oh, and could you bring me his dagger as proof?");
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.MINDLESS_GORKS.getQuestID());
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
