package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import net.infernalrealms.dungeons.Dungeon;
import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.quests.DungeonQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.util.GeneralUtil;

public class DungeonQuestTest extends DungeonQuest {

	public static final String QUEST_NAME = "Test";
	private static final List<Reward> rewards = new ArrayList<>();

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 300));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 2, 0)));
		rewards.add(new Reward(Reward.Type.CUSTOM, "Chances for dungeon-unique drops"));
	}

	public DungeonQuestTest() {
		super(getSpecificObjectives(), rewards);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
			{ // Part 1
				new ObjectiveKill(1, 1)
			},
			{ // Part 2
				new ObjectiveKill(1, 1)
			}
		};
		return objectives;
		// @formatter:on
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public DungeonType getDungeonType() {
		return DungeonType.TEST;
	}

	@Override
	public void nextPart() {
		super.nextPart();
	}

	@Override
	public void displayEntranceMessages() {
		if (getDungeon() == null || getDungeon().getParty() == null) {
			return;
		}
		getDungeon().getParty()
				.broadcastToDungeonParty(ChatColor.GRAY + "" + ChatColor.ITALIC + "You have arrived at the ancient tree of Yggdrasil.");
	}

	@Override
	public Location getBossLocation() {
		return new Location(Dungeon.INSTANCES_WORLD, 860.5, 23.5, 179.5, -35F, 0F);
	}

}