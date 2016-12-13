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

public class HardcodedQuest10012MorDok extends HardcodedQuest {

	public static final String QUEST_NAME = "Mor'Dok";
	public static final int QUEST_ID = 10012;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 900));
		rewards.add(new Reward(Reward.Type.MONEY, 100));

		// Description
		description.add("Defeat Mor Dok.");
	}

	public HardcodedQuest10012MorDok(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveKill(6, 1),
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		getPlayer().teleport(new Location(InfernalRealms.MAIN_WORLD, 1110, 78.5, 1214, 90F, 0F));
		getPlayer().sendMessage("");
		getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "! - " + ChatColor.RESET + "" + ChatColor.GRAY + "" + 
		"You awaken in a room, a place you don’t recognize. The last thing you can remember is fighting Mor’Dok and speaking to the Zen. Are you in Enen?");
		getQuest(getPlayer(), HardcodedQuest.QuestName.UNKNOWN_PLACES.getQuestID());
		getPlayer().sendMessage("");
		super.giveRewards();
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
