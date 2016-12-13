package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N6_20057_Kill_Hogni extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Kill Hogni";
	public static final int QUEST_ID = 20057;
	public static final int REQUIRED_LEVEL = -1;

	public N6_20057_Kill_Hogni(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Nizar", "Ah! Thank you for coming again! Hogni will pay...", 
											   "While you are at my old home, destroy his experimental beacon before the bandits can sell it on the black market."),
				},
				{ // Part 2
					new ObjectiveKill(44, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1002, 73, 828);
						}
					},
					new ObjectiveBreakBlock("Bandit Beacon", 1),
				},
				{ // Part 3
					new ObjectiveTalk("Nizar", "Thank you, now those monsters did it all for nothing."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 400));
		rewards.add(new Reward(Reward.Type.MONEY, 80));

		// Quest Log Description
		description.add("While you are at Nizar’s old home, destroy his");
		description.add("experimental beacon before the bandits can sell");
		description.add("it on the black market.");
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
