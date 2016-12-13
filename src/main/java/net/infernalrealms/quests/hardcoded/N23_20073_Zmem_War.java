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
import net.infernalrealms.util.GeneralUtil;

public class N23_20073_Zmem_War extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Z'mem War";
	public static final int QUEST_ID = 20073;
	public static final int REQUIRED_LEVEL = -1;

	public N23_20073_Zmem_War(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Durer", "It's time to take this war to the Z知em.", 
												   "Go to their base and kill the Z知em Warrior and Z知em Archers."),
					},
					{ // Part 2
						new ObjectiveKill(111, 100) {
							@Override
							public Location getLocation() {
								return new Location(InfernalRealms.MAIN_WORLD, 23, 18, 339);
							}
						},
						new ObjectiveKill(112, 65) {
							@Override
							public Location getLocation() {
								return new Location(InfernalRealms.MAIN_WORLD, 23, 18, 339);
							}
						},
					},
					{ // Part 3
						new ObjectiveTalk("Durer", "Good job! Only a few more things left until we take down the Z知em."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 5500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 10, 50)));

		// Quest Log Description
		description.add("It's time to take this war to the Z知em. Go to their");
		description.add("base and kill the Z知em Warrior and Z知em Archers.");
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
