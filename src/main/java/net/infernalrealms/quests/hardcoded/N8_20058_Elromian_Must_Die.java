package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N8_20058_Elromian_Must_Die extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Elromian Must Die";
	public static final int QUEST_ID = 20058;
	public static final int REQUIRED_LEVEL = 8;

	public N8_20058_Elromian_Must_Die(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Fnerm", "They are a threat to us and our inn.", 
											   "We need as many as possible dead."),
				},
				{ // Part 2
					new ObjectiveKill(14, 15) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 837, 77, 1232);
						}
					},
					new ObjectiveKill(15, 10) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 837, 77, 1232);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Fnerm", "Thank you, the inn is now a safer place.", 
											   "As for a permanent solution to the problem..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 550));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 0, 90)));

		// Quest Log Description
		description.add("They are a threat to us and our inn. We need as many");
		description.add("as possible dead.");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), QuestName.ELROM);
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
