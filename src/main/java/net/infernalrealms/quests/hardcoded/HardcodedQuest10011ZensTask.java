package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliverMulti;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HardcodedQuest10011ZensTask extends HardcodedQuest {

	public static final String QUEST_NAME = "Zens Task";
	public static final int QUEST_ID = 10011;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 100));
		rewards.add(new Reward(Reward.Type.MONEY, 100));

		// Description
		description.add("Collect the staff, robe and book.");
		description.add("They are dropped by the strong monsters");
		description.add("in the courtyard, upstairs, and in the");
		description.add("library.");
	}

	public HardcodedQuest10011ZensTask(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
					new ObjectiveDeliverMulti("Zen of Irion", new String[] { "BookOfKnowledge", "ZensArmor", "ZensWeapon" },
							"Thank you. This is a great help, you are the only person to have accepted my request.",
							"I can now topple the barrier that the demon Mor Dok has put in place. Although I can take down this barrier, I cannot kill him myself. That will be a task for you.",
							"Once you defeat him the path to Enen should be clear. Are you ready?") {
						@Override
						public String getDescription() {
							return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Bring the Zen his 3 items.";
						}
					}
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.MOR_DOK.getQuestID());
		getPlayer().sendMessage("");
		getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "! - " + ChatColor.RESET + "" +
				ChatColor.GRAY + "The Zen has begun teleporting you to Mor Dok’s location. Prepare for battle!");
		getPlayer().sendMessage("");
		new BukkitRunnable() {

			@Override
			public void run() {
				getPlayer().teleport(new Location(InfernalRealms.MAIN_WORLD, 881, 131, -422, -180F, 4.2F));
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 250L);

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
