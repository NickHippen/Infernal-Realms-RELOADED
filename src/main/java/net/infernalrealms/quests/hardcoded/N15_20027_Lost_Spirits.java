package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.GeneralUtil;

public class N15_20027_Lost_Spirits extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Lost Spirits";
	public static final int QUEST_ID = 20027;
	public static final int REQUIRED_LEVEL = 15;

	public N15_20027_Lost_Spirits(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Gill", "Ever since recent sightings of Cern’ Kek No’no, Spirit Knights have been showing up here.", 
												   "I’m not sure what has caused them to appear here, but they are interfering with the fastest route between the Nym and Orym region.", 
												   "Could you help eliminate some of these Spirit Knights blocking the way so travelers can pass through here easier?"),
				},
				{ // Part 2
					new ObjectiveKill(36, 45) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 876, 81, 534);
						}
					},
				},
				{ // Part 3
					new ObjectiveTalk("Gill", "Thanks for that, hopefully that should make it at least a little safer to travel.", 
											  "Oh! But don’t go too far, I have another idea on how to solve this problem..."),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 1800));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 2, 0)));

		// Quest Log Description
		description.add("Ever since recent sightings of Cern’ Kek No’no,");
		description.add("Spirit Knights have been showing up here. I’m not");
		description.add("sure what has caused them to appear here, but they");
		description.add("are interfering with the fastest route between the");
		description.add("Nym and Orym region. Could you help eliminate some");
		description.add("of these Spirit Knights blocking the way so travelers");
		description.add("can pass through here easier?");
	}

	@Override
	public void giveRewards() {
		super.giveRewards();
		getQuest(getPlayer(), HardcodedQuest.QuestName.GUARDIANS_OF_CERN_KEK_NONO.getQuestID());
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
