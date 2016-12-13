package net.infernalrealms.dungeons2;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.infernalrealms.quests.DungeonQuest;
import net.infernalrealms.quests.hardcoded.DungeonQuestTest;

public enum DungeonType {

	// @formatter:off
	TEST("Test", "Dungeon_Test", new Location(null, 1, 1, 1, 0, 0), 1800, () -> new DungeonQuestTest())
	;
	// @formatter:on

	private String displayName;
	private String worldName;
	private Location spawnLocation;
	private int timeLimit;
	private DungeonQuestLink questLink;

	private DungeonType(String displayName, String worldName, Location spawnLocation, int timeLimit, DungeonQuestLink questLink) {
		this.displayName = displayName;
		this.worldName = worldName;
		this.spawnLocation = spawnLocation;
		this.timeLimit = timeLimit;
		this.questLink = questLink;
	}

	public File getWorldFolder() {
		return new File(Bukkit.getWorldContainer(), "dungeons/" + getWorldName());
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getWorldName() {
		return worldName;
	}

	protected Location getSpawnLocation() {
		return spawnLocation;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public DungeonQuest createDungeonQuest() {
		return questLink.createQuestObject();
	}
	
	public static DungeonType fromDisplayName(String name) {
		for (DungeonType dt : values()) {
			if (dt.getDisplayName().equalsIgnoreCase(name)) {
				return dt;
			}
		}
		return null;
	}

}
