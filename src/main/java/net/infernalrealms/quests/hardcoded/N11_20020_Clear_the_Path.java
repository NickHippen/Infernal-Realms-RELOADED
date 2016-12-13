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

public class N11_20020_Clear_the_Path extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Clear the Path";
	public static final int QUEST_ID = 20020;
	public static final int REQUIRED_LEVEL = 11;

	public N11_20020_Clear_the_Path(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Vasile", "The goblin have become a big issue here.", "Could you kill 20 goblins in Devdan Cave to help clear the path for other travelers?"),
				},
				{ // Part 2
					new ObjectiveKill(29, 20) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1126, 69, 690);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Vasile", "It's not much, but you deserve a reward."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 760));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 25)));

		// Quest Log Description
		description.add("The goblin have become a big issue here. Could you kill 20");
		description.add("goblins in Devdan Cave to help clear the path for other");
		description.add("travelers?");
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
