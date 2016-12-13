package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveOpenBank;
import net.infernalrealms.quests.objectives.ObjectiveSummonMount;
import net.infernalrealms.quests.objectives.ObjectiveTalk;

public class HardcodedQuest10014Meet_the_Town extends HardcodedQuest {

	public static final String QUEST_NAME = "Meet the Town";
	public static final int QUEST_ID = 10014;
	public static final int REQUIRED_LEVEL = -1;

	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 120));
		rewards.add(new Reward(Reward.Type.MONEY, 100));

		// Description
		description.add("I should go meet some of the people in town.");
	}

	public HardcodedQuest10014Meet_the_Town(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveTalk("Kassen", "Bella sent you? Well, you’re a new arrival. I suppose you need to set up a bank then. It is vital in order to keep your things safe.", 
											"Whenever you have something of value, you can store it at the bank and it will remain there until moved.", 
											"How about you head over there now? It is a fairly straightforward process."),
			}, 
			{ // Part 2
				new ObjectiveOpenBank(),
			},
			{ // Part 3
				new ObjectiveTalk("Neldor", "Hey! PLAYER_NAME, right? I heard you just got here. You know what that means; you need transportation!", 
											"As you can tell, I am kind of the horse guy around here, and those things are perfect for traveling fast.", 
											"I can help you out with one. But I might ask you a favor later on! Here’s how it works.",
											ChatColor.RESET + "" + ChatColor.GRAY + "Type /mount to summon your mount. It will allow for fast travel and a status symbol. You can customize it with /mount manage or use the inventory button. It can level up in different categories and provide many other benefits to your travels.")
			},
			{ // Part 4
				new ObjectiveSummonMount(),
			},
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public void nextPart() {
		super.nextPart();
		if (getPart() == 2) {
			new ObjectiveTalk("Banker", "Great! Oh, by the way, I heard Neldor wants to talk to you about something...")
					.sendMessages(getPlayer());
		}
	}

	@Override
	public void giveRewards() {
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
