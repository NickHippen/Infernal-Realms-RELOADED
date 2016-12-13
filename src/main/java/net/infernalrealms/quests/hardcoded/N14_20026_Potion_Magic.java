package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N14_20026_Potion_Magic extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Potion Magic";
	public static final int QUEST_ID = 20026;
	public static final int REQUIRED_LEVEL = -1;

	public N14_20026_Potion_Magic(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Renourart", "There are alchemists lurking in an upper region of the cave with nothing but bad intentions.", 
												   "I need you to kill them and bring me back some of their supplies."),
				},
				{ // Part 2
					new ObjectiveKill(35, 15) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 962, 80, 635);
						}
					},
					new ObjectiveDeliver("Renourart", "MagmaCream", 5 ,"Great! These will help me greatly in my study of alchemy."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 80)));
		rewards.add(new Reward(Reward.Type.POTION, "Great Health Potion of Renourart", 10));
		rewards.add(new Reward(Reward.Type.POTION, "Great Mana Potion of Renourart", 10));

		// Quest Log Description
		description.add("There are alchemists lurking in an upper region of");
		description.add("the cave with nothing but bad intentions. I need");
		description.add("you to kill them and bring me back some of their");
		description.add("supplies.");
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
