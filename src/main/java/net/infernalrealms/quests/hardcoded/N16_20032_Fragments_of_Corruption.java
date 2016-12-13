package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N16_20032_Fragments_of_Corruption extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Fragments of Corruption";
	public static final int QUEST_ID = 20032;
	public static final int REQUIRED_LEVEL = 16;

	public N16_20032_Fragments_of_Corruption(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Kane", "The undead have placed 18 fragments of corruption above the ancient city of Wrayburn.", 
											  "If you do not destroy them this land will be tainted with the corruption for an eternity."),
				},
				{ // Part 2
					new ObjectiveBreakBlock("FragmentofCorruption", 45) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 655, 127, 717);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Kane", "Thank you traveler, the lands have been saved because of you."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 2, 30)));

		// Quest Log Description
		description.add("The undead have placed fragments of corruption above");
		description.add("the ancient city of Wrayburn. If you do not destroy");
		description.add("them this land will be tainted with the corruption");
		description.add("for an eternity.");
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
