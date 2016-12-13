package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HardcodedQuest100085BraemirsTask extends HardcodedQuest {

	public static final String QUEST_NAME = "Braemirs Task";
	public static final int QUEST_ID = 100085;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 100));
		rewards.add(new Reward(Reward.Type.GEM, "YELLOW#1-1#COMMON", 1));

		// Description
		description.add("Talk to Braemir.");
	}

	public HardcodedQuest100085BraemirsTask(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Braemir", "Ah. Olfrik said I would have a task for you. As much as I enjoy some friendly help, I’m not really in any need at the moment.", 
						"I could actually use you to unload some random gems that aren’t worth much around here apart from weapon embellishments. How about you take this gem... hmmm where did I put it?",
						"Ah, here it is! This one should work perfect for your class!"),
			},
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "! - " + ChatColor.RESET + "" + ChatColor.GRAY
				+ "Braemir handed you a gem that you can insert into armor and weapons. Try dragging and dropping it into your weapon for a small stat boost!");
		new ObjectiveTalk("Braemir", "You should continue down the path now to fortress Ulria. Aleandria will be there at the gate!")
				.sendMessages(getPlayer());
		getQuest(getPlayer(), HardcodedQuest.QuestName.ALEANDRIA.getQuestID());
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
