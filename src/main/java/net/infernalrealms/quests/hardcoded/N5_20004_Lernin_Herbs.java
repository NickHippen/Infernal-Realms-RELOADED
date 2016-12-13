package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;

public class N5_20004_Lernin_Herbs extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Ler'Nin Herbs";
	public static final int QUEST_ID = 20004;
	public static final int REQUIRED_LEVEL = -1;

	public N5_20004_Lernin_Herbs(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveDeliver("Zenumbra", "LerNinHerbs", 5, "Wow, thanks! I'm so glad Kan recommended you to me.")
			},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 180));
		rewards.add(new Reward(Reward.Type.MONEY, 21));

		// Quest Log Description
		description.add("Zenumbra needs 5 Ler'Nin Herbs. The Elromian seem to");
		description.add("carry them; they are located behind the Spider Inn,");
		description.add("behind the trees.");
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
