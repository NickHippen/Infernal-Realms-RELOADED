package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N18_20041_Sending_a_Message extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Sending a Message";
	public static final int QUEST_ID = 20041;
	public static final int REQUIRED_LEVEL = 18;

	public N18_20041_Sending_a_Message(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Ferne", "Agh! These Forsaken are so frustrating!", 
											  "The worst part is, their leader seems to have set up camp just down the passageway from here as though to taunt us!", 
											  "Hmm… You look strong… Do you think you could do something about it?"),
				},
				{ // Part 2
					new ObjectiveKill(104, 1),
				},
				{ // Part 3
					new ObjectiveTalk("Ferne", "That should teach the Forsaken not to mess with us!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 4, 0)));

		// Quest Log Description
		description.add("Agh! These Forsaken are so frustrating! The worst part is,");
		description.add("their leader seems to have set up camp just down the");
		description.add("passageway from here as though to taunt us! Do you think you");
		description.add("could do something about it?");
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
