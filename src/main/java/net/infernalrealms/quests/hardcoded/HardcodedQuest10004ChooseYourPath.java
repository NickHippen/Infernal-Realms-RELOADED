package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveChooseClass;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

import org.bukkit.entity.Player;

public class HardcodedQuest10004ChooseYourPath extends HardcodedQuest {

	public static final String QUEST_NAME = "Choose your Path";
	public static final int QUEST_ID = 10004;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 10));

		// Description
		description.add("Choose your class with /choose.");
	}

	public HardcodedQuest10004ChooseYourPath(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveChooseClass(),
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		PlayerData playerData = PlayerData.getData(getPlayer());
		getQuest(getPlayer(), HardcodedQuest.QuestName.READY_FOR_ADVENTURE);
		new ObjectiveTalk("Taurnil",
				" Ah, you are a " + playerData.getPlayerClass().toLowerCase() + ". Don’t see many of those around here anymore...",
				"I suppose I could help you on your way to Enen. Before we speak further, go talk to Oropher the alchemist in the room next to us for some potions.")
						.sendMessages(getPlayer());
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
