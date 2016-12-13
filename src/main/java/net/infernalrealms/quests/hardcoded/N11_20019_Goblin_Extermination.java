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

public class N11_20019_Goblin_Extermination extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Goblin Extermination";
	public static final int QUEST_ID = 20019;
	public static final int REQUIRED_LEVEL = 11;

	public N11_20019_Goblin_Extermination(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Nyx", "Volgare sent you?", "Good.", "You must know now that only good goblin is a dead one.", "Eliminate 10 goblin archers and 5 thick goblins."),
				},
				{ // Part 2
					new ObjectiveKill(30, 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1115, 62, 712);
						}
					},
					new ObjectiveKill(41, 5) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 1115, 62, 712);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Nyx", "Great work!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 760));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 30)));

		// Quest Log Description
		description.add("Volgare sent you? Good. You must know now that only good");
		description.add("goblin is a dead one.");
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
