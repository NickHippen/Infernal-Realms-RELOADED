package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N6_20056_Eliminate_the_Thieving_Scum extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Eliminate the Thieving Scum";
	public static final int QUEST_ID = 20056;
	public static final int REQUIRED_LEVEL = 6;

	public N6_20056_Eliminate_the_Thieving_Scum(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Nizar", "These bandits stole my home and all of my things…",  
											   "I want to be avenged!"),
				},
				{ // Part 2
					new ObjectiveKill(45, 30) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1001, 72, 845);
						}
					},
					new ObjectiveDeliver("Nizar", "StolenGoods", 8, "I ask you a final favor: please, kill Hogni, that bad excuse for a human.", "Talk to me again if you're interested."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 400));
		rewards.add(new Reward(Reward.Type.MONEY, 50));

		// Quest Log Description
		description.add("These bandits stole my home and all of my things…");
		description.add("I want to be avenged! Follow the path to the right");
		description.add("and you should eventually arrive at my house.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), QuestName.KILL_HOGNI);
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
