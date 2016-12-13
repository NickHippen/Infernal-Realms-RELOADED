package net.infernalrealms.dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.infernalrealms.chat.InteractiveChat;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.mobs.FileMob;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.party.Party;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.DungeonQuest;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.util.InfernalStrings;

public class Dungeon {

	public static final World INSTANCES_WORLD = Bukkit.getWorld("dungeon_instances");
	public static final List<Dungeon> CURRENT_DUNGEONS = new ArrayList<>();

	private DungeonQuest quest;
	private Party party;
	private boolean solo;
	private int index;
	private DungeonType dungeonType;
	private int duration = 0; // Duration in seconds that the dungeon has been in progress
	private BukkitTask dungeonTick;
	private List<InfernalMob> spawnedMobs = new ArrayList<>();
	private List<UUID> deadPlayers = new ArrayList<>();
	private boolean ending = false;

	private Dungeon(DungeonType dungeonType, Party party, int index) {
		try {
			this.quest = dungeonType.getQuestClass().newInstance();
			this.quest.setIndex(index);
			this.index = index;
			this.dungeonType = dungeonType;
			this.quest.setDungeon(this);
		} catch (Exception e) {
			e.printStackTrace();
			this.quest = null;
			party.broadcastToParty(ChatColor.RED
					+ "A severe error occurred while creating the dungeon. Please report this to an administrator: " + toString());
		}
		this.party = party;
//		this.party.setDungeon(this);
		this.quest.displayEntranceMessages();
		if (party.getAllOnlineMembers().size() == 1) {
			this.setSolo(true);
		}
		for (Player player : party.getAllOnlineMembers()) {
			PlayerData playerData = PlayerData.getData(player);
			playerData.setLocation();
//			playerData.setDungeon(this);
			player.setMetadata("SafeClose", new FixedMetadataValue(InfernalRealms.getPlugin(), true));
		}
		dungeonTick = new BukkitRunnable() {
			@Override
			public void run() {
				duration += 1;
				if (duration >= dungeonType.getMaxDuration()) {
					party.broadcastToDungeonParty(ChatColor.GRAY + "You have " + ChatColor.DARK_RED + "ran out of time" + ChatColor.GRAY
							+ " to complete the dungeon!");
					endWithDelay();
					cancel();
				}
				if (duration % 30 == 0) {
					displayProgress();
				}
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 20, 20);

		party.broadcastToDungeonParty(" ");
		party.broadcastToDungeonParty(
				ChatColor.GRAY + "You have " + InfernalStrings.formatSeconds(dungeonType.getMaxDuration()) + "to complete this dungeon!");
		party.broadcastToDungeonParty(" ");
		party.broadcastToDungeonParty(ChatColor.GREEN + "Type /leavedungeon to leave the dungeon!");
		displayProgress();

		new BukkitRunnable() {
			@Override
			public void run() {
				teleportAllToStart();
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
		spawnMobs();
	}

	public DungeonQuest getQuest() {
		return this.quest;
	}

	public Party getParty() {
		return this.party;
	}

	public void teleportAllToStart() {
		for (Player player : party.getAllOnlineMembers()) {
			player.teleport(quest.getSpawnLocation());
		}
	}

	public void displayProgress() {
		Objective[] objectives = this.getQuest().getCurrentObjectives();
		getParty().broadcastToDungeonParty(" ");
		getParty().broadcastToDungeonParty(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "TIME:  " + (duration / 60) + ChatColor.GRAY
				+ " minutes " + ChatColor.DARK_PURPLE + ChatColor.BOLD + (duration % 60) + ChatColor.GRAY + " seconds");
		getParty().broadcastToDungeonParty(
				ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Objective" + (objectives.length == 1 ? "" : "s") + ":");
		for (Objective objective : objectives) {
			getParty().broadcastToDungeonParty(ChatColor.GRAY + objective.getDescription());
		}
		getParty().broadcastToDungeonParty(" ");
	}

	public void endWithDelay() {
		InteractiveChat ic = new InteractiveChat(ChatColor.GRAY + "The dungeon is being closed. Click " + ChatColor.DARK_RED + "here"
				+ ChatColor.GRAY + " to exit the dungeon or you will automatically exit in 30 seconds.");
		ic.applyClickCommand("/leavedungeon");
		ic.applyHoverMessage("Click here to leave the dungeon now.");
		party.broadcastToParty(ic);
		clearMobs();
		new BukkitRunnable() {
			@Override
			public void run() {
				endAll();
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 600L);
	}

	public void endAll() {
		endAll(null);
	}

	public void endAll(String additionalMessage) {
		this.ending = true;
		for (Player player : party.getAllOnlineMembers()) {
			if (additionalMessage != null) {
				player.sendMessage(additionalMessage);
			}
			exit(player);
		}
	}

	public void exit(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (!playerData.hasDungeon()) {
			return;
		}
		playerData.setDungeon(null);
		player.teleport(playerData.getLocation());
		player.sendMessage(ChatColor.RED + "You have left the dungeon.");
		if (getPlayerCount() > 0) {
			return; // Players still remain in the dungeon. Nothing left to do.
		}
		System.out.println("Ending dungeon: " + toString());
		if (dungeonTick != null) {
			dungeonTick.cancel();
		}
		party.setDungeon(null);
		dungeonType.modifyInstance(index, false);
		clearMobs();
		CURRENT_DUNGEONS.remove(this);
	}

	public int getPlayerCount() {
		int count = 0;
		for (Player player : party.getAllOnlineMembers()) {
			PlayerData playerData = PlayerData.getData(player);
			if (playerData.hasDungeon()) {
				count++;
			}
		}
		return count;
	}

	public void spawnMobs() {
		List<String> spawns = YAMLFile.DUNGEON_SPAWNS.getConfig().getStringList(this.getDungeonType().getDisplayName());
		if (spawns == null) {
			return;
		}
		for (String spawn : spawns) {
			String[] locMob = spawn.split("#");
			if (locMob.length != 2 || !FileMob.isFileMob(locMob[1])) {
				continue;
			}
			String loc = locMob[0];
			String[] coords = loc.split(",");
			if (coords.length != 3) {
				continue;
			}
			int x;
			int y;
			int z;
			try {
				x = Integer.parseInt(coords[0]);
				y = Integer.parseInt(coords[1]);
				z = Integer.parseInt(coords[2]) + 1000 * this.index;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				continue;
			}
			Location location = new Location(INSTANCES_WORLD, x, y, z);
			int mobID;
			try {
				mobID = Integer.parseInt(locMob[1]);
			} catch (Exception e) {
				System.out.println("Error @ spawn: " + spawn);
				return;
			}
			LivingEntity mob = FileMob.spawn(mobID, location);
			if (mob != null) {
				spawnedMobs.add((InfernalMob) ((CraftEntity) mob).getHandle());
			}
		}
	}

	public void clearMobs() {
		System.out.println(spawnedMobs.size());
		while (spawnedMobs.size() > 0) {
			InfernalMob im = spawnedMobs.get(0);
			if (im != null) {
				im.getBukkitLivingEntity().setRemoveWhenFarAway(true);
				im.getSelf().killer = null;
				im.getSelf().die();
			}
			System.out.println("Removed a mob");
			spawnedMobs.remove(0);
		}
	}

	public static void clearMobsAllInstances() {
		for (Dungeon dungeon : CURRENT_DUNGEONS) {
			dungeon.clearMobs();
		}
	}

	public DungeonType getDungeonType() {
		return this.dungeonType;
	}

	public void removeQuest() {
		this.quest = null;
	}

	public Location getSpawnLocation() {
		return getQuest().getSpawnLocation();
	}

	public static Dungeon createDungeon(DungeonType dungeonType, Party party) {
		int nextOpen = dungeonType.getNextOpenInstance();
		if (nextOpen != -1) { // Make sure there is an instance available.
			System.out.println("Creating new dungeon instances at index " + nextOpen + " for dungeon " + dungeonType);
			dungeonType.modifyInstance(nextOpen, true);
			return new Dungeon(dungeonType, party, nextOpen);
		}
		return null;
	}

	public static boolean checkAllReady(Inventory inventory) {
		if (inventory.getSize() != 9) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			ItemStack item = inventory.getItem(i);
			if (item == null || !item.hasItemMeta()) {
				return false;
			}
			if (item.getType() != Material.INK_SACK) {
				break;
			}
			if (item.getDurability() == 8) {
				return false;
			}
		}
		return true;
	}

	public void prepareForBossRespawn(Player player) {
		if (!getParty().isMember(player)) {
			return;
		}
		deadPlayers.add(player.getUniqueId());
		boolean membersRemaining = false;
		for (Player member : getParty().getDungeonMembers()) {
			if (!deadPlayers.contains(member.getUniqueId())) {
				membersRemaining = true;
				break;
			}
		}
		if (!membersRemaining) {
			endAll(ChatColor.RED + "Everyone has fallen to the boss, so the dungeon has be completed unsuccessfully.");
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				if (ending || !player.isOnline()) {
					return;
				}
				player.sendMessage(ChatColor.GREEN + "You have returned to the boss room.");
				player.teleport(getQuest().getBossLocation());
				deadPlayers.remove(player.getUniqueId());
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 600L);
	}

	public boolean isSolo() {
		return solo;
	}

	public void setSolo(boolean solo) {
		this.solo = solo;
	}

	public int getDuration() {
		return this.duration;
	}

	@Override
	public String toString() {
		return "DUNGEON: " + getDungeonType() + "#" + index;
	}

}
