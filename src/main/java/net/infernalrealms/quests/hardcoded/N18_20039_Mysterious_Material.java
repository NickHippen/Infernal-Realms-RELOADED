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

public class N18_20039_Mysterious_Material extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Mysterious Material";
	public static final int QUEST_ID = 20039;
	public static final int REQUIRED_LEVEL = 18;

	public N18_20039_Mysterious_Material(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Gorwin", "Crypt Fiends have been getting closer and closer to us and everyone is starting to get worried.", 
												"I should be worried about it too, but honestly I’m more fascinated by their weapon’s material.", 
												"Could you eliminate some of them and bring me some of the remains of their weapon."),
				},
				{ // Part 2
					new ObjectiveKill(91, 55),
					new ObjectiveDeliver("Gorwin", "StoneShrapnel", 18, "Yes! Thank you! I can’t wait to study this material..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2250));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 3, 0)));

		// Quest Log Description
		description.add("Crypt Fiends have been getting closer and closer to");
		description.add("us and everyone is starting to get worried. I should");
		description.add("be worried about it too, but honestly I’m more");
		description.add("fascinated by their weapon’s material. Could you");
		description.add("eliminate some of them and bring me some of the remains");
		description.add("of their weapon.");
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
