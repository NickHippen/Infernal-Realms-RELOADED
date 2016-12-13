package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N17_20036_Origin_of_the_Growth extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Origin of the Growth";
	public static final int QUEST_ID = 20036;
	public static final int REQUIRED_LEVEL = 17;

	public N17_20036_Origin_of_the_Growth(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Drogo", "Turrios is the origin of all of the growth in this cave, but we haven’t been able to find him recently.", 
											   "Could you locate him and make sure he’s safe, something has been off recently..."),
				},
				{ // Part 2
					new ObjectiveTalk("Turrios", "Hmmm… Yes, I’m fine. Drogo was right about there being something off, though.", 
												 "Talk to me again if you are interested in taking care of this corruption...") {
						@Override
						public String getDescription() {
							return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Find " + this.getNpcName();
						}
					},
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 500));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 0)));

		// Quest Log Description
		description.add("Turrios is the origin of all of the growth in this");
		description.add("cave, but we haven’t been able to find him recently.");
		description.add("Could you locate him and make sure he’s safe,");
		description.add("something has been off recently...");
	}

	@Override
	public void giveRewards() {
		Quest.getQuest(getPlayer(), HardcodedQuest.QuestName.CORRUPTION_OF_THE_GROWTH);
		super.giveRewards();
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public boolean isStory() {
		return false;
	}

}
