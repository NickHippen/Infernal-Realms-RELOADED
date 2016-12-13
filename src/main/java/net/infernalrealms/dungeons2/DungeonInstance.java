package net.infernalrealms.dungeons2;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.infernalrealms.chat.InteractiveChat;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.party.Party;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.DungeonQuest;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.util.EmptyGenerator;

public class DungeonInstance {

	private World world;
	private DungeonQuest dungeonQuest;
	private DungeonType dungeonType;
	private UUID uuid;
	private Party party;
	private int duration = 0;
	private BukkitTask timer;

	public DungeonInstance(DungeonType dungeonType, Party party) {
		this.dungeonType = dungeonType;
		this.uuid = UUID.randomUUID();
		this.party = party;
		getParty().setDungeon(this);
		getParty().broadcastToDungeonParty(ChatColor.BLUE + "Please wait, loading new dungeon instance...");
		try {
			new BukkitRunnable() {
				@Override
				public void run() {
					File dungeonWorldFolder = getDungeonType().getWorldFolder();
					// Create new world
					try {
						FileUtils.copyDirectory(dungeonWorldFolder, new File(Bukkit.getWorldContainer(), "dungeon_instance/" + getUUID()));
						new File(dungeonWorldFolder, "uid.dat").delete();
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
					try {
						setWorld(WorldCreator.name("dungeon_instance/" + getUUID()).generator(new EmptyGenerator()).createWorld());
					} catch (IllegalStateException e) {
						System.out.println(getWorld());
					}
					teleportSynchronously();
					startTimer();
				}
			}.runTaskAsynchronously(InfernalRealms.getPlugin());
		} catch (Exception e) {
			System.out.println("An error occurred while creating the homestead.");
		}
	}
	
	private void startTimer() {
		this.timer = new BukkitRunnable() {
			@Override
			public void run() {
				duration += 1;
				
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 20L, 20L);
	}

	private void teleportSynchronously() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (getWorld() == null) {
					getParty().broadcastToDungeonParty(ChatColor.RED
							+ "Error joining dungeon because the world was not loaded. If this error persists, contact an admin.");
					return;
				}
				getParty().getAllOnlineMembers().forEach(player -> player.teleport(getSpawnLocation()));
				getParty().broadcastToDungeonParty(ChatColor.GREEN + "You have entered the dungeon.");
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 0L);
	}

	public void endWithDelay() {
		InteractiveChat ic = new InteractiveChat(ChatColor.GRAY + "The dungeon is being closed. Click " + ChatColor.DARK_RED + "here"
				+ ChatColor.GRAY + " to exit the dungeon or you will automatically exit in 30 seconds.");
		ic.applyClickCommand("/leavedungeon");
		ic.applyHoverMessage("Click here to leave the dungeon now.");
		getParty().broadcastToDungeonParty(ic);
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
		for (Player player : party.getDungeonMembers()) {
			if (additionalMessage != null) {
				player.sendMessage(additionalMessage);
			}
			exit(player);
		}
		InfernalRealms.getPlugin().getLogger().info("Ending dungeon: " + getUUID());
		if (timer != null) {
			timer.cancel();
		}
		party.setDungeon(null);
	}

	public void exit(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (!playerData.hasDungeon()) {
			return;
		}
		playerData.setDungeon(null);
		player.teleport(playerData.getLocation());
		player.sendMessage(ChatColor.RED + "You have left the dungeon.");
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

	public Location getSpawnLocation() {
		Location loc = getDungeonType().getSpawnLocation();
		return new Location(getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	public boolean isSolo() {
		return getParty().getDungeonMembers().size() == 1;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public DungeonQuest getQuest() {
		return dungeonQuest;
	}

	public void setQuest(DungeonQuest dungeonQuest) {
		this.dungeonQuest = dungeonQuest;
	}

	public DungeonType getDungeonType() {
		return dungeonType;
	}

	public void setDungeonType(DungeonType dungeonType) {
		this.dungeonType = dungeonType;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
