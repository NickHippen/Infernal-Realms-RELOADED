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

public class N9_20015_Exterminate_the_Plague extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Exterminate the Plague";
	public static final int QUEST_ID = 20015;
	public static final int REQUIRED_LEVEL = 9;

	public N9_20015_Exterminate_the_Plague(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Warden", "These horrible creatures have been spreading disease like wildfire, not to mention they smell.", "Could you eliminate 15 rotting corpses to help keep their numbers down?"),
				},
				{ // Part 2
					new ObjectiveKill(23, 30) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 859, 75, 765);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Warden", "Whew! Much better!", "I can already smell the difference!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 850));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 5)));

		// Quest Log Description
		description.add("These horrible creatures have been spreading disease");
		description.add("like wildfire, not to mention they smell.");
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
