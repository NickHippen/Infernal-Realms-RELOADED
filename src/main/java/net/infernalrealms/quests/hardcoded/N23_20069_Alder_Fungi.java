package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class N23_20069_Alder_Fungi extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Alder Fungi";
	public static final int QUEST_ID = 20069;
	public static final int REQUIRED_LEVEL = 23;

	public N23_20069_Alder_Fungi(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("CranachTheElder", "Alder Fungi is growing on some of the trees.", 
															 "It will kill the tree and then spread to a new one if you don't stop it.", 
															 "I have the power to stop the fungi but that would require me to raise my arm up which I don't feel like doing.", 
															 "You can take care of them for me right?"),
					},
					{ // Part 2
						new ObjectiveBreakBlock("Alder Fungi", 50),
					},
					{ // Part 3
						new ObjectiveTalk("CranachTheElder", "I was going to give you a reward but I dont feel like reaching into my pouch and grabbing my coins."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 5500));

		// Quest Log Description
		description.add("Alder Fungi is growing on some of the trees. It will kill the");
		description.add("tree and then spread to a new one if you don't stop it. I have");
		description.add("the power to stop the fungi but that would require me to raise");
		description.add("my arm up which I don't feel like doing. You can take care of");
		description.add("them for me right?");
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
