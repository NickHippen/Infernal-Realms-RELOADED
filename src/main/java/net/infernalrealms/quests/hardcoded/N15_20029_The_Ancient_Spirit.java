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

public class N15_20029_The_Ancient_Spirit extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "The Ancient Spirit";
	public static final int QUEST_ID = 20029;
	public static final int REQUIRED_LEVEL = 15;

	public N15_20029_The_Ancient_Spirit(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Lester", "I’ll just cut to the chase, the ancient spirit Cern’ Kek No’no has been causing all sorts of issues in this area.", 
												"It may be tough, but could you eliminate it for us?"),
				},
				{ // Part 2
					new ObjectiveKill(38, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 905, 102, 618);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Lester", "Nice job, hopefully some of the effects of these spirits will begin to fade with them..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2000));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 5, 0)));

		// Quest Log Description
		description.add("Cern’ Kek No’no has been causing all sorts of issues");
		description.add("in this area. It may be tough, but could you eliminate");
		description.add("it for us?");
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
