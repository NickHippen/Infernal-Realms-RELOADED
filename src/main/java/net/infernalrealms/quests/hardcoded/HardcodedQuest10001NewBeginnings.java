package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10001NewBeginnings extends HardcodedQuest {

	public static final String QUEST_NAME = "New Beginnings";
	public static final int QUEST_ID = 10001;
	public static final int REQUIRED_LEVEL = 1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 5));
		rewards.add(new Reward(Reward.Type.MONEY, 15));
		// Description
		description.add("Speak to Valandil and continue to Fenor in the cave.");
	}

	public HardcodedQuest10001NewBeginnings(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Valandil", "Welcome to Irion traveler! It is good to see new faces, the amount of people coming to these lands has been dwindling for the past few decades.", 
						"The first town on your path should be Enen, it is on a fairly straightforward path from here. Pass through the cave and there should be guides willing to help you. Good luck!")
				{
					@Override
					public Location getLocation() {
						return new Location(InfernalRealms.MAIN_WORLD, 1286, 73, -645);
					}
				},
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.FENORS_TASK);
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public boolean isStory() {
		return true;
	}

}
