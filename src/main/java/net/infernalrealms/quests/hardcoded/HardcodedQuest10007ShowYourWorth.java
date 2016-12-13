package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveLearnSkill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class HardcodedQuest10007ShowYourWorth extends HardcodedQuest {

	public static final String QUEST_NAME = "Show Your Worth";
	public static final int QUEST_ID = 10007;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 80));
		rewards.add(new Reward(Reward.Type.MONEY, 25));

		// Description
		description.add("Prove your worth by learning a skill.");
	}

	public HardcodedQuest10007ShowYourWorth(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveLearnSkill(),
			},
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		PlayerData playerData = PlayerData.getData(getPlayer());
		switch (playerData.getPlayerSuperClass().toLowerCase()) {
		case "warrior":
			new Reward(Reward.Type.SET_ITEM, "Dented Sword", 1).giveTo(getPlayer());
			break;
		case "archer":
			new Reward(Reward.Type.SET_ITEM, "Squeaky Bow", 1).giveTo(getPlayer());
			break;
		case ("magician"):
			new Reward(Reward.Type.SET_ITEM, "Dimly Lit Wand", 1).giveTo(getPlayer());
		}
		super.giveRewards();
		new ObjectiveTalk("Taurnil",
				"Magnificent! I’ll teleport you outside and you can get started on your journey. Speak with Olfrik before you reach fortress Ulria.")
				.sendMessages(getPlayer());
		getPlayer().teleport(new Location(InfernalRealms.MAIN_WORLD, 1182, 70.5, -634, 90.4F, 7.5F), TeleportCause.PLUGIN);
		getQuest(getPlayer(), HardcodedQuest.QuestName.AID_TO_THE_PEOPLE.getQuestID());
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
