package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N19_20044_Lich_King extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Lich King";
	public static final int QUEST_ID = 20044;
	public static final int REQUIRED_LEVEL = 19;

	public N19_20044_Lich_King(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Theophilus", "Sorry, but you can't enter the door because the key is upstairs. You will need to kill the Lich King to obtain the key."),
				},
				{ // Part 2
					new ObjectiveKill(96, 1),
					new ObjectiveDeliver("Theophilus", "CastleKey", 1, "Great, you can now access the door.",
																	   "Talk to me if you ever want to get through."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2750));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 4, 25)));

		// Quest Log Description
		description.add("Sorry, but you can't enter the door because the key is");
		description.add("upstairs. You will need to kill the Lich King to obtain");
		description.add("the key.");
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
