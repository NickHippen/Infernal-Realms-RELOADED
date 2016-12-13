package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HardcodedQuest10005ReadyForAdventure extends HardcodedQuest {

	public static final String QUEST_NAME = "Ready for Adventure";
	public static final int QUEST_ID = 10005;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.POTION, "Tiny Vitality Vial", 4));

		// Description
		description.add("Retrieve potions from Oropher.");
	}

	public HardcodedQuest10005ReadyForAdventure(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Oropher", "Hi there! Did you say Taurnil sent you?",  
						"As long as he is okay with this, I suppose I could spare some small potions. They are his materials after all, haha!", 
						"If you go back to him, he should show you the way outside.")
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.TO_FORTRESS_ULRIA);
		getPlayer().sendMessage("");
		getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "INFO: " + ChatColor.RESET + 
				"" + ChatColor.DARK_PURPLE + "Potions " + ChatColor.GRAY + "can be placed on the " + ChatColor.DARK_PURPLE
				+ "last 5 slots of your hotbar." + ChatColor.GRAY + " Use them when you are running low on health by pressing the number of their respective slot."
						+ ChatColor.UNDERLINE + "(they are instantly cast!)");
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
