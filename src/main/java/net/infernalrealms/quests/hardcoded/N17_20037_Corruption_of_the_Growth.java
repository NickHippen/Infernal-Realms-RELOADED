package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N17_20037_Corruption_of_the_Growth extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Corruption of the Growth";
	public static final int QUEST_ID = 20037;
	public static final int REQUIRED_LEVEL = -1;

	public N17_20037_Corruption_of_the_Growth(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Turrios", "On the other side of the cave there is an Ancient Corrupted Creature causing growth in this cave to become corrupt.", 
												 "Could you eliminate him to help remedy the corrupted growth in this cave?"),
				},
				{ // Part 2
					new ObjectiveKill(89, 1),
				},
				{
					new ObjectiveTalk("Turrios", "Ah, this is much better. Hopefully we’ll start seeing improvements in growth soon."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2000));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 2, 0)));

		// Quest Log Description
		description.add("Turrios is the origin of all of the growth in this");
		description.add("cave, but we haven’t been able to find him recently.");
		description.add("Could you locate him and make sure he’s safe,");
		description.add("something has been off recently...");
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
