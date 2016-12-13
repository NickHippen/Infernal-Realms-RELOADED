package net.infernalrealms.leaderboard;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.general.YAMLFile;

public class DungeonLeaderboard<T extends Record<?>> extends Leaderboard<T> {

	private static final byte MAX_TRACKED = 18;

	private DungeonType dungeonType;
	private Record.RecordType type;

	private DungeonLeaderboard(List<T> records, DungeonType dungeonType, Record.RecordType type) {
		super(records);
		this.setDungeonType(dungeonType);
		this.setType(type);
	}

	@Override
	public int getMaxRecordsTracked() {
		return MAX_TRACKED;
	}

	public void save() {
		YAMLFile.DUNGEON_LEADERBOARDS.getConfig().set(getDungeonType().toString() + "." + getType().getPath(), getRecords());
		YAMLFile.DUNGEON_LEADERBOARDS.save();
	}

	public DungeonType getDungeonType() {
		return dungeonType;
	}

	public void setDungeonType(DungeonType dungeonType) {
		this.dungeonType = dungeonType;
	}

	public Record.RecordType getType() {
		return type;
	}

	public void setType(Record.RecordType type) {
		this.type = type;
	}

	public static DungeonLeaderboard<Record<TimeScore>> loadDungeonLeaderboard(DungeonType dungeonType, Record.RecordType type) {
		List<Record<TimeScore>> records = (List<Record<TimeScore>>) YAMLFile.DUNGEON_LEADERBOARDS.getConfig()
				.getList(dungeonType.toString() + "." + type.getPath(), new ArrayList<>());
		return new DungeonLeaderboard<>(records, dungeonType, type);
	}

}
