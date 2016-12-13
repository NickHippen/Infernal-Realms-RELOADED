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

public class N9_20016_Vile_Ghouls extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Vile Ghouls";
	public static final int QUEST_ID = 20016;
	public static final int REQUIRED_LEVEL = 9;

	public N9_20016_Vile_Ghouls(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Volgare", "These ghouls are known for reaping the souls of those", "who pass through the corruption...", "Please retrieve them so we can put them to peace."),
				},
				{ // Part 2
					new ObjectiveKill(22, 30) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 844, 77, 802);
						}
					},
					new ObjectiveDeliver("Volgare", "StolenSoulEssence", 10, "The souls in the great beyond thank you..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 880));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 35)));

		// Quest Log Description
		description.add("These ghouls are known for reaping the souls of those");
		description.add("who pass through the corruption, please retrieve them");
		description.add("so we can put them to peace.");
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
