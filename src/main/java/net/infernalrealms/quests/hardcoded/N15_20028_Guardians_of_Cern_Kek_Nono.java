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

public class N15_20028_Guardians_of_Cern_Kek_Nono extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Guardians of Cern' Kek No'no";
	public static final int QUEST_ID = 20028;
	public static final int REQUIRED_LEVEL = -1;

	public N15_20028_Guardians_of_Cern_Kek_Nono(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Gill", "Oh, I see you’ve come back to help me some more.", 
											  "There have been rumors that Cern’ Kek No’no has appeared at the top of the walkway that scales the mountains.", 
											  "I don’t think it will be necessary to defeat him in order to get the spirits to get away, however.", 
											  "If you defeat some of the Spirit Guardians surrounding him they may begin to fade."),
				},
				{ // Part 2
					new ObjectiveKill(37, 30) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 909, 90, 513);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Gill", "Well, thanks for your help!", 
											  "I guess we’ll just have to see what kind of an impact your help will make."),
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
		description.add("There have been rumors that Cern’ Kek No’no has");
		description.add("appeared at the top of the walkway that scales the");
		description.add("mountains. I don’t think it will be necessary to");
		description.add("defeat him in order to get the spirits to get away,");
		description.add("however. If you defeat some of the Spirit Guardians");
		description.add("surrounding him they may begin to fade.");
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
