package net.infernalrealms.quests;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.infernalrealms.dungeons.Dungeon;
import net.infernalrealms.dungeons2.DungeonInstance;
import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.leaderboard.DungeonLeaderboard;
import net.infernalrealms.leaderboard.Leaderboard;
import net.infernalrealms.leaderboard.PartyRecord;
import net.infernalrealms.leaderboard.Record;
import net.infernalrealms.leaderboard.SoloRecord;
import net.infernalrealms.leaderboard.TimeScore;
import net.infernalrealms.mobs.CustomMobData;
import net.infernalrealms.quests.objectives.Objective;

public abstract class DungeonQuest extends Quest {

	private int index;
	private DungeonInstance dungeon;
	private CustomMobData processedMobData;
	private boolean bossMode = false;

	public DungeonQuest(Objective[][] objectives, List<Reward> rewards) {
		super(null, 0, -1, objectives, rewards, null);
	}

	@Override
	public void save() {}

	@Override
	public List<String> getDescription() {
		return null;
	}

	@Override
	public void giveRewards() {
		for (Player player : dungeon.getParty().getDungeonMembers()) {
			for (Reward reward : getRewards()) {
				reward.giveTo(player);
			}
		}
	}

	@Override
	public void tryNext() {
		boolean display = false;
		for (Objective objective : getCurrentObjectives()) {
			if (objective.hasBeenShown() || !objective.isComplete()) {
				continue;
			}
			// New objective has been completed
			display = true;
			objective.setShown(true);
			break;
		}

		if (display) {
			for (Player player : getDungeon().getParty().getDungeonMembers()) {
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1F, 5F);
				for (Objective objective : getCurrentObjectives()) {
					if (objective.isComplete()) {
						player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[" + ChatColor.DARK_GREEN + "✔" + ChatColor.BOLD
								+ "]" + ChatColor.GREEN + ChatColor.BOLD + " [" + ChatColor.GREEN + objective.getDescription()
								+ ChatColor.BOLD + "]" + ChatColor.GREEN + ChatColor.ITALIC + " (" + getName() + ")");
					} else {
						player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[" + ChatColor.DARK_RED + "X" + ChatColor.BOLD + "]"
								+ ChatColor.RED + ChatColor.BOLD + " [" + ChatColor.RED + objective.getDescription() + ChatColor.BOLD + "]"
								+ ChatColor.RED + ChatColor.ITALIC + " (" + getName() + ")");
					}
				}
			}
		}
		super.tryNext();
		getDungeon().displayProgress();
	}

	@Override
	public void setComplete() {
		this.complete = true;
		this.giveRewards();
		dungeon.endWithDelay();
		dungeon.getParty().broadcastToDungeonParty(ChatColor.GREEN + "" + ChatColor.BOLD + "Victory!");
		dungeon.getParty().broadcastToDungeonParty(ChatColor.GRAY + dungeon.getDungeonType().getDisplayName() + " complete.");

		// Record Handling
		Record.RecordType type;
		Record<TimeScore> newRecord;
		if (dungeon.isSolo()) {
			newRecord = new SoloRecord<>(dungeon.getParty().getOwner(), new TimeScore(getDungeon().getDuration()));
			type = Record.RecordType.SOLO;
		} else {
			newRecord = new PartyRecord<>(dungeon.getParty(), new TimeScore(getDungeon().getDuration()));
			type = Record.RecordType.PARTY;
		}
		Leaderboard<Record<TimeScore>> leaderboard = DungeonLeaderboard.loadDungeonLeaderboard(getDungeonType(), type);
		Record.insertRecordSorted(leaderboard.getRecords(), newRecord);
		leaderboard.trim();

		leaderboard.save();
	}

	@Override
	public boolean isStory() {
		return false;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public CustomMobData getProcessedMobData() {
		return this.processedMobData;
	}

	public void setProcessedMobData(CustomMobData processedMobData) {
		this.processedMobData = processedMobData;
	}

	public boolean isBossMode() {
		return bossMode;
	}

	public void setBossMode(boolean bossMode) {
		this.bossMode = bossMode;
	}

	public abstract DungeonType getDungeonType();

	public DungeonInstance getDungeon() {
		return this.dungeon;
	}

	public void setDungeon(DungeonInstance dungeon) {
		this.dungeon = dungeon;
	}

	public void displayEntranceMessages() {}

	public abstract Location getBossLocation();

}
