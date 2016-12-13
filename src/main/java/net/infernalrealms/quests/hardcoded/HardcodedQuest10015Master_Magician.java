package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10015Master_Magician extends HardcodedQuest {

	public static final String QUEST_NAME = "Master Magician";
	public static final int QUEST_ID = 10015;
	public static final int REQUIRED_LEVEL = 9;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 160));
		rewards.add(new Reward(Reward.Type.MONEY, 20));

		// Description
		description.add("Yertle, the master magician of Enen, wants to speak to you.");
		description.add("You can find him in the mage tower in the back of the town.");
	}

	public HardcodedQuest10015Master_Magician(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Yertle", "Ah, PLAYER_NAME. You are progressing rapidly.", 
											"It is time you move on to one of the more dangerous places of these plains; the tree of Yggdrasil.", 
											"It is a great tree of worship, but was taken over by Ler Maim and his use of sheer force.", 
											"You have obviously show yourself as a capable warrior, the whole plains of Nym need you."),
			},
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		getQuest(getPlayer(), HardcodedQuest.QuestName.DUNGEONEERING.getQuestID());
		super.giveRewards();
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public boolean isStory() {
		return true;
	}

}
