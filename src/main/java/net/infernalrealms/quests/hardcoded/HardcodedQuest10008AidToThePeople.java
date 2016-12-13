package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10008AidToThePeople extends HardcodedQuest {

	public static final String QUEST_NAME = "Aid to the People";
	public static final int QUEST_ID = 10008;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 100));
		rewards.add(new Reward(Reward.Type.MONEY, 50));

		// Description
		description.add("Speak to Olfrik and slay 8 Young Dire Wolves.");
	}

	public HardcodedQuest10008AidToThePeople(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	@Override
	public void nextPart() {
		if (getPart() == 0) {
			String playerClass = PlayerData.getData(getPlayer()).getPlayerClass();
			getPlayer().sendMessage("");
			getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "INFO: " + ChatColor.RESET + "" + ChatColor.GRAY
					+ "Ability points provide combat bonuses for each class. You receive " + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "5" +
					ChatColor.RESET + "" + ChatColor.GRAY + " per level and each has a different function. For "
					+ playerClass + " we recommend a balance between "
					+ (playerClass.equalsIgnoreCase("Warrior") ? "strength"
							: (playerClass.equalsIgnoreCase("Archer") ? "dexterity" : "intelligence"))
					+ " and "
					+ (playerClass.equalsIgnoreCase("Warrior") ? "stamina"
							: (playerClass.equalsIgnoreCase("Archer") ? "agility" : "spirit"))
					+ ", Type /stats or use the inventory button to spend them at any time.");
			getPlayer().sendMessage("");
		}
		super.nextPart();
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Olfrik", "Hey, traveler. Before you continue on the path, could you slay 8 Young Dire Wolves in the forest next to us?", 
						"They have been presenting a danger to us common folk and need to be dealt with.", 
						"You should probably learn a few combat skills before you fight these beasts! Let me help you out."),
			},
			new Objective[] 
			{ // Part 2
 				new ObjectiveKill(1, 8),
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		new ObjectiveTalk("Olfrik",
				"(Shouting) If you continue down the path, you should find Braemir, he will have a task for you as well! Good luck!")
						.sendMessages(getPlayer());
		getQuest(getPlayer(), HardcodedQuest.QuestName.BRAEMIRS_TASK.getQuestID());
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
