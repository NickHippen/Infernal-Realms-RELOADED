package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N17_20034_Clear_the_Path2 extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Clear the Path 2";
	public static final int QUEST_ID = 20034;
	public static final int REQUIRED_LEVEL = 17;

	public N17_20034_Clear_the_Path2(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Consort", "We’ve been trying to make it to the Castle of Gorwin, but these monsters have been blocking our path.", 
												 "Can you help clear the path so we can make it there?"),
				},
				{ // Part 2
					new ObjectiveKill(90, 80) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.SECONDARY_WORLD, 3604, 10, 3727);
						}
						
						@Override
						public String getLocationDescription() {
							return ChatColor.GRAY + "Gorwin: " + super.getLocationDescription();
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Consort", "Thanks, it should be a little easier for us to make it there now."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 2000));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 3, 0)));

		// Quest Log Description
		description.add("We’ve been trying to make it to the Castle of Gorwin,");
		description.add("but these monsters have been blocking our path. Can");
		description.add("you help clear the path so we can make it there?");
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
