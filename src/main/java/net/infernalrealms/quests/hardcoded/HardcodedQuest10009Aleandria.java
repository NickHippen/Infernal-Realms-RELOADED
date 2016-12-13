package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class HardcodedQuest10009Aleandria extends HardcodedQuest {

	public static final String QUEST_NAME = "Aleandria";
	public static final int QUEST_ID = 10009;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 200));
		rewards.add(new Reward(Reward.Type.MONEY, 20));

		// Description
		description.add("Speak to Aleandria at Ulria's gates.");
	}

	public HardcodedQuest10009Aleandria(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Aleandria", "You are going to have some trouble if you are looking into getting inside here. Ulria has been overrun and is currently dangerous for common folk.",
						"Oh, what, you say you are a PLAYER_CLASS? Hmm.. Well as long as you have proven yourself… I will let you pass.", 
						"Before you continue down the dangerous corridors, you must talk to the Zen. He is in the main room protected by a force of magic.", 
						"Just continue forward and you should see him in the center of the large prayer temple. Be careful, the Zens are ones of great power."),
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getPlayer().teleport(new Location(InfernalRealms.MAIN_WORLD, 923.5, 71.5, -484, 46.2F, -0.6F), TeleportCause.PLUGIN);
		getQuest(getPlayer(), HardcodedQuest.QuestName.THE_ZEN.getQuestID());
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
