package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.gui.ChooseClassGUI;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10003PathToEnen extends HardcodedQuest {

	public static final String QUEST_NAME = "Path To Enen";
	public static final int QUEST_ID = 10003;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 10));
		rewards.add(new Reward(Reward.Type.MONEY, 20));

		// Description
		description.add("The wizard Taurnil would like to talk to you.");
		description.add("He is located in the reading hall on the second");
		description.add("floor further in the building.");
	}

	public HardcodedQuest10003PathToEnen(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Taurnil", "Hmph. What? Who are you? What are you doing in my reading hall?",
						"Oh, Angar sent you. Typical. He is always sending people up here for help. Well, are you going to tell me? What are you good for anyways?")
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		Player player = getPlayer(); // Possible fix to occassional null pointer?
		getQuest(player, HardcodedQuest.QuestName.CHOOSE_YOUR_PATH);
		new BukkitRunnable() {

			@Override
			public void run() {
				ChooseClassGUI.open(player);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
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
