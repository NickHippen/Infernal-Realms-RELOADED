package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N22_20065_An_Honorable_Test_2 extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "An Honorable Test 2";
	public static final int QUEST_ID = 20065;
	public static final int REQUIRED_LEVEL = -1;

	public N22_20065_An_Honorable_Test_2(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Lewkenor", "Norman is the leader of the gate defenders and he wishes to test you to see how strong you are.", 
													  "He is in the gates wall down the hallway.", 
													  "I suggest you go to him and give all you have to defeat him."),
					},
					{ // Part 2
						new ObjectiveKill(59, 1),
					},
					{ // Part 3
						new ObjectiveTalk("Lewkenor", "Wow you actually defeated him."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 4500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));

		// Quest Log Description
		description.add("Norman is the leader of the gate defenders and he wishes");
		description.add("to test you to see how strong you are. He is in the gates");
		description.add("wall down the hallway. I suggest you go to him and give");
		description.add("all you have to defeat him.");
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
