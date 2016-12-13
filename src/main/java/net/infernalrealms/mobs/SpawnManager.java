package net.infernalrealms.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.mobs.types.InfernalMob;

public class SpawnManager {

	public static final Map<String, LivingEntity> spawnPointMobs = new HashMap<>();

	public static boolean containsSpawn(String spawn) {
		return YAMLFile.SPAWNS.getConfig().contains(spawn);
	}

	public static void setSpawn(String spawn, World world, Location location, String mobs) {
		if (!containsSpawn(spawn)) {
			YAMLFile.SPAWNS.getConfig().set(spawn + ".World", world.getName());
			YAMLFile.SPAWNS.getConfig().set(spawn + ".Location", Integer.toString(location.getBlockX()) + ","
					+ Integer.toString(location.getBlockY()) + "," + Integer.toString(location.getBlockZ()));
			YAMLFile.SPAWNS.getConfig().set(spawn + ".Mobs", mobs);
			YAMLFile.SPAWNS.getConfig().set(spawn + ".DespawnRange", 20);
			YAMLFile.SPAWNS.getConfig().set(spawn + ".MaxSpawned", 1);
			YAMLFile.SPAWNS.getConfig().set(spawn + ".RespawnRate", 10);
			YAMLFile.SPAWNS.save();
		}
	}

	public static void setDungeonSpawn(String dungeonName, Location location, String mob) {
		ArrayList<String> spawns = YAMLFile.DUNGEON_SPAWNS.getConfig().contains(dungeonName)
				? (ArrayList<String>) YAMLFile.DUNGEON_SPAWNS.getConfig().getStringList(dungeonName) : new ArrayList<String>();
		spawns.add(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "#" + mob);
		YAMLFile.DUNGEON_SPAWNS.getConfig().set(dungeonName, spawns);
		YAMLFile.DUNGEON_SPAWNS.save();
	}

	public static boolean containsDungeonSpawn(String spawnName) {
		return YAMLFile.DUNGEON_SPAWNS.getConfig().contains(spawnName);
	}

	public static void markNearestDungeonSpawn(String dungeonName) {
		ArrayList<String> spawns = YAMLFile.DUNGEON_SPAWNS.getConfig().contains(dungeonName)
				? (ArrayList<String>) YAMLFile.DUNGEON_SPAWNS.getConfig().getStringList(dungeonName) : new ArrayList<String>();
		// TODO
	}

	public static Map<String, LivingEntity> getSpawnPointMobs() {
		return spawnPointMobs;
	}

	//	public static removeAllMobsFromSpawn(String spawn) {
	//		for (LivingEntity mob : getSpawnPointMobs().keySet()) {
	//			if ()
	//		}
	//	}

	public static String getSpawnPoint(InfernalMob mob) {
		return mob.getData().getSpawn();
	}

	public static boolean isSpawnPointMob(LivingEntity e) {
		for (String spawn : getSpawnPointMobs().keySet()) {
			if (getSpawnPointMobs().get(spawn).equals(e)) {
				return true;
			}
		}
		return false;
	}

	public static void spawnMob(String spawn, int quantity) {
		if (!spawn.contains("DUNGEON")) {
			if (containsSpawn(spawn)) {
				boolean currentlySpawned = getSpawnPointMobs().containsKey(spawn);
				if (currentlySpawned) {
					return;
				}
				String[] locationSplit = YAMLFile.SPAWNS.getConfig().getString(spawn + ".Location").split(",");
				int xLocation = Integer.parseInt(locationSplit[0]);
				int yLocation = Integer.parseInt(locationSplit[1]);
				int zLocation = Integer.parseInt(locationSplit[2]);
				World world = Bukkit.getWorld(YAMLFile.SPAWNS.getConfig().getString(spawn + ".World"));
				Location location = new Location(world, xLocation, yLocation, zLocation);
				int mobID;
				try {
					mobID = Integer.parseInt(YAMLFile.SPAWNS.getConfig().getString(spawn + ".Mobs"));
				} catch (Exception e) {
					System.out.println("Error @ spawn: " + spawn);
					e.printStackTrace();
					return;
				}
				FileMob.spawn(mobID, location, spawn);
			}
		} else {
			String[] spawnSplit = spawn.split(":");
			if (containsSpawn(spawnSplit[1])) {
				int i;
				try {
					i = Integer.parseInt(spawnSplit[0].replace("DUNGEON", ""));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					return;
				}
				String[] locationSplit = YAMLFile.SPAWNS.getConfig().getString(spawnSplit[1] + ".Location").split(",");
				int xLocation = Integer.parseInt(locationSplit[0]);
				int yLocation = Integer.parseInt(locationSplit[1]);
				int zLocation = Integer.parseInt(locationSplit[2]);
				World world = Bukkit.getWorld(YAMLFile.SPAWNS.getConfig().getString(spawnSplit[1] + ".World"));
				Location location = new Location(world, xLocation, yLocation, zLocation + (i * 1000));
				int mobID;
				try {
					mobID = Integer.parseInt(YAMLFile.SPAWNS.getConfig().getString(spawnSplit[1] + ".Mobs"));
				} catch (Exception e) {
					System.out.println("Error @ spawn: " + spawn);
					e.printStackTrace();
					return;
				}
				FileMob.spawn(mobID, location, spawn);
			}
		}
	}

	public static void refreshSpawn(String spawn) {
		if (containsSpawn(spawn)) {
			if (!isDungeonSpawn(spawn)) {
				boolean currentlySpawned = getSpawnPointMobs().containsKey(spawn);
				if (currentlySpawned) {
					return;
				}
				String[] locationSplit = YAMLFile.SPAWNS.getConfig().getString(spawn + ".Location").split(",");
				int xLocation = Integer.parseInt(locationSplit[0]);
				int yLocation = Integer.parseInt(locationSplit[1]);
				int zLocation = Integer.parseInt(locationSplit[2]);
				World world = Bukkit.getWorld(YAMLFile.SPAWNS.getConfig().getString(spawn + ".World"));
				Location location = new Location(world, xLocation, yLocation, zLocation);
				//				String mobName = YAMLFile.SPAWNS.getConfig().getString(spawn + ".Mobs");
				int mobID;
				try {
					mobID = Integer.parseInt(YAMLFile.SPAWNS.getConfig().getString(spawn + ".Mobs"));
				} catch (Exception e) {
					System.out.println("Error @ spawn: " + spawn);
					e.printStackTrace();
					return;
				}
				FileMob.spawn(mobID, location, spawn);
			} else {
				//				for (int i = 0; i < DungeonType.MAX_INSTANCES; i++) {
				//					String[] locationSplit = YAMLFile.SPAWNS.getConfig().getString(spawn + ".Location").split(",");
				//					int xLocation = Integer.parseInt(locationSplit[0]);
				//					int yLocation = Integer.parseInt(locationSplit[1]);
				//					int zLocation = Integer.parseInt(locationSplit[2]);
				//					World world = Bukkit.getWorld(YAMLFile.SPAWNS.getConfig().getString(spawn + ".World"));
				//					Location location = new Location(world, xLocation, yLocation, zLocation + (i * 1000));
				//					//					String mobName = YAMLFile.SPAWNS.getConfig().getString(spawn + ".Mobs");
				//					int mobID;
				//					try {
				//						mobID = Integer.parseInt(YAMLFile.SPAWNS.getConfig().getString(spawn + ".Mobs"));
				//					} catch (Exception e) {
				//						System.out.println("Error @ spawn: " + spawn);
				//						e.printStackTrace();
				//						return;
				//					}
				//					FileMob.spawn(mobID, location, "DUNGEON" + i + ":" + spawn);
				//				}
			}
		}
	}

	public static boolean isDungeonSpawn(String spawn) {
		return YAMLFile.SPAWNS.getConfig().contains(spawn + ".Dungeon");
	}

	public static Location getSpawnLocation(String spawn) {
		if (containsSpawn(spawn)) {
			String[] locationSplit = YAMLFile.SPAWNS.getConfig().getString(spawn + ".Location").split(",");
			int xLocation = Integer.parseInt(locationSplit[0]);
			int yLocation = Integer.parseInt(locationSplit[1]);
			int zLocation = Integer.parseInt(locationSplit[2]);
			World world = Bukkit.getWorld(YAMLFile.SPAWNS.getConfig().getString(spawn + ".World"));
			return new Location(world, xLocation, yLocation, zLocation);
		}
		return null;
	}

	public static void refreshSpawns() {
		Set<String> set = YAMLFile.SPAWNS.getConfig().getKeys(false);
		for (String spawn : set) {
			refreshSpawn(spawn);
		}
	}

	public static void refreshSpawnsDelayed() {
		InfernalRealms.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				SpawnManager.refreshSpawns();
				System.out.println("Refreshed spawns!");
			}
		}, 200L);
	}

	public static boolean isOutOfRange(InfernalMob mob) {
		if (getSpawnPointMobs().containsValue(mob.getBukkitLivingEntity())) {
			String spawn = mob.getData().getSpawn();
			Location spawnLocation = getSpawnLocation(spawn);
			Location mobLocation = mob.getBukkitLivingEntity().getLocation();
			if (containsSpawn(spawn)) {
				int despawnRange = YAMLFile.SPAWNS.getConfig().getInt(spawn + ".DespawnRange");
				return Math.abs(spawnLocation.getBlockX() - mobLocation.getBlockX()) > despawnRange
						|| Math.abs(spawnLocation.getBlockZ() - mobLocation.getBlockZ()) > despawnRange;
			}
		}
		return false;
	}

	public static void resetMob(InfernalMob mob) {
		mob.getBukkitLivingEntity().setHealth(mob.getBukkitLivingEntity().getMaxHealth());
		if (getSpawnPointMobs().containsValue(mob.getBukkitLivingEntity())) {
			mob.getBukkitLivingEntity().teleport(getSpawnLocation(mob.getData().getSpawn()));
			mob.updateNameTagAtLocation();
		}
	}

	public static void respawnLater(final String spawn, long duration) {
		InfernalRealms.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				SpawnManager.spawnMob(spawn, 1);
			}
		}, duration);
	}
}
