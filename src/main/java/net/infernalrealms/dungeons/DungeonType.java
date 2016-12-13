//package net.infernalrealms.dungeons;
//
//import net.infernalrealms.quests.DungeonQuest;
//import net.infernalrealms.quests.hardcoded.DungeonQuestDebug;
//import net.infernalrealms.quests.hardcoded.DungeonQuestYggdrasil;
//
//public enum DungeonType {
//
//	// Z changes the instances
//
//	//@formatter:off
//	YGGDRASIL("Yggdrasil", DungeonQuestYggdrasil.class, new int[] {1000, 44, 0}, 900),
//	LOST_HALLS("Lost Halls", null, new int[] {0, 50, 0}, 2400),
//	DEBUG("Debug",  DungeonQuestDebug.class, new int[] {1000, 44, 0}, 900),
//	;
//	//@formatter:on
//
//	public static final int MAX_INSTANCES = 3;
//
//	private String displayName;
//	private Class<? extends DungeonQuest> questClass;
//	private int[] spawnPointValues;
//	private int maxDuration;
//	private boolean[] instancesUsed;
//
//	private DungeonType(String displayName, Class<? extends DungeonQuest> questClass, int[] spawnPointValues,
//			int maxDuration /* In seconds */) {
//		this.displayName = displayName;
//		this.questClass = questClass;
//		this.spawnPointValues = spawnPointValues;
//		this.maxDuration = maxDuration;
//		this.instancesUsed = new boolean[MAX_INSTANCES];
//	}
//
//	public String getDisplayName() {
//		return this.displayName;
//	}
//
//	public Class<? extends DungeonQuest> getQuestClass() {
//		return this.questClass;
//	}
//
//	public boolean isInstanceEmpty(int index) {
//		try {
//			return !this.instancesUsed[index];
//		} catch (ArrayIndexOutOfBoundsException e) {
//			System.out.println("Error: Attempted to access a dungeon instance that does not exist");
//			return false;
//		}
//	}
//
//	/**
//	 * Returns -1 if no instances are open.
//	 */
//	public int getNextOpenInstance() {
//		for (int i = 0; i < instancesUsed.length; i++) {
//			if (!instancesUsed[i]) {
//				return i;
//			}
//		}
//		return -1;
//	}
//
//	public void modifyInstance(int index, boolean fill) {
//		try {
//			this.instancesUsed[index] = fill;
//		} catch (ArrayIndexOutOfBoundsException e) {
//			System.out.println("Error: Attempted to access a dungeon instance that does not exist");
//		}
//	}
//
//	public int[] getSpawnPointValues() {
//		return this.spawnPointValues;
//	}
//
//	public static DungeonType fromDisplayName(String name) {
//		for (DungeonType dt : values()) {
//			if (dt.getDisplayName().equalsIgnoreCase(name)) {
//				return dt;
//			}
//		}
//		return null;
//	}
//
//	public int getMaxDuration() {
//		return this.maxDuration;
//	}
//
//}
