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

public class N23_20071_Shelob_the_Devourer extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Shelob the Devourer";
	public static final int QUEST_ID = 20071;
	public static final int REQUIRED_LEVEL = -1;

	public N23_20071_Shelob_the_Devourer(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
					{ // Part 1
						new ObjectiveTalk("Thax", "Shelob the Devourer has been, you know, devouring people for way too long.", 
												  "I feel you are strong enough now to take her on."),
					},
					{ // Part 2
						new ObjectiveKill(140, 1) {
							@Override
							public Location getLocation() {
								return new Location(InfernalRealms.MAIN_WORLD, 414, 71, 422);
							}
						},
					},
					{ // Part 3
						new ObjectiveTalk("Thax", "Wow, you actually killed her.", 
												  "I thought for sure you would die."),
					},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 5500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 9, 0)));

		// Quest Log Description
		description.add("Shelob the Devourer has been, you know, devouring people");
		description.add("for way too long. I feel you are strong enough now to");
		description.add("take her on.");
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
