package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10006ToFortressUlria extends HardcodedQuest {

	public static final String QUEST_NAME = "To Fortress Ulria";
	public static final int QUEST_ID = 10006;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 10));

		// Description
		description.add("Speak to Taurnil.");
	}

	public HardcodedQuest10006ToFortressUlria(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Taurnil", "Ah, PLAYER_NAME! While you were in the other room I found you this item!", 
						"It hasn't been touched in a long while, but I feel you could make better use of it.", 
						"Before I give it to you, however, demonstrate that skill for me so I can gauge your worth!"),
			},
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.SHOW_YOUR_WORTH);
		getPlayer().sendMessage("");
		getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "INFO: " + ChatColor.RESET + "" + ChatColor.GRAY + "Type " + ChatColor.DARK_PURPLE + "/skills " + ChatColor.GRAY
				+ "or use the inventory button to open the skill menu. Here you can click on the icon of the skill you wish to learn. You receive one skill point per level, spend them wisely!");
		getPlayer().sendMessage("");
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
