package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N22_20064_An_Honorable_Test extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "An Honorable Test";
	public static final int QUEST_ID = 20064;
	public static final int REQUIRED_LEVEL = 22;

	public N22_20064_An_Honorable_Test(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Lewkenor", "The Path to Fendor is guarded by Gate Defenders and Archers.",
													  "They will test to see how strong you are.",
													  "You will need to defeat many of them to prove yourself."),
					},
					{ // Part 2
						new ObjectiveKill(60, 65),
						new ObjectiveKill(61, 45),
					},
					{ // Part 3
						new ObjectiveTalk("Lewkenor", "The gate defenders still won't let you pass easily but you have proven yourself to them."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 4000));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 6, 0)));

		// Quest Log Description
		description.add("The Path to Fendor is guarded by Gate Defenders and");
		description.add("Archers. They will test to see how strong you are. You");
		description.add("will need to defeat many of them to prove yourself.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.AN_HONORABLE_TEST_2);
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
