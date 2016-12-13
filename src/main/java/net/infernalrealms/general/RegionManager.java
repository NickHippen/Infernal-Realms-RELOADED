package net.infernalrealms.general;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionManager {

	private static final int DEFAULT_SPAWN = 3;

	// @formatter:off
		public static final Location[] RESPAWN_LOCATIONS = new Location[] {
				/* Tutorial Start */ InfernalRealms.TUTORIAL_START,
				/* Tutorial Forest */ new Location(InfernalRealms.MAIN_WORLD, 1170.5, 65.5, -635.5, 67.5F, 0F),
				/* Tutorial Castle */ new Location(InfernalRealms.MAIN_WORLD, 880, 70.5, -439, -170F, -2F),
				/* Start Town */ new Location(InfernalRealms.MAIN_WORLD, 1215.5, 81.5, 1107.5, 60F, 0F),
				/* First field near corrupt biome*/ new Location(InfernalRealms.MAIN_WORLD, 813, 78.5, 966.5, -109.6F, 0F),
				/* Corrupt biome */ new Location(InfernalRealms.MAIN_WORLD, 829, 73.5, 688, -22.1F, 0F),
				/* Bandit Camps */ new Location(InfernalRealms.MAIN_WORLD, 644, 92.5, 1076.5, 154F, 11F), 
				/* Biome after cave 1 */ new Location(InfernalRealms.MAIN_WORLD, 797.5, 71.5, 616, 121.6F, 0F),
				/* Alyuin Forest 1 */ new Location(InfernalRealms.MAIN_WORLD, 134.5, 69.5, 461.5, -90F, 0F),
				/* Alyuin Forest 2 */ new Location(InfernalRealms.MAIN_WORLD, -211.5, 70.5, 795.5, -55F, 0F),
				/* Alyuin Forest 3 */ new Location(InfernalRealms.MAIN_WORLD, 317.5, 71.5, 215.5, 0F, 0F),
				/* Illitran Corruption 1 */ new Location(InfernalRealms.MAIN_WORLD, 830.5, 73.5, 683.5, 0F, 0F),
				/* Devdan Cave 1 */ new Location(InfernalRealms.MAIN_WORLD, 1002.5, 70.5, 606.5, -90F, 0F),
				/* Orym Savannah 1 */ new Location(InfernalRealms.MAIN_WORLD, 799.5, 71.5, 617.5, 115F, 0F),
				/* Orym Savannah 2 */ new Location(InfernalRealms.MAIN_WORLD, 640.5, 71.5, 498.5, 45F, 0F),
				/* Dungeon of Gorwin 1 */ new Location(InfernalRealms.SECONDARY_WORLD, 3560.5, 10.5, 3807.5, -180F, 0F),
				/* Dungeon of Gorwin 2 */ new Location(InfernalRealms.SECONDARY_WORLD, 3803.5, 54.5, 3520.5, 90F, 0F),
				/* Dungeon of Gorwin 3 */ new Location(InfernalRealms.SECONDARY_WORLD, 3615.5, 67.5, 3323.5, -45F, 0F),
				/* Dungeon of Gorwin 4 */ new Location(InfernalRealms.SECONDARY_WORLD, 3244.5, 99.5, 3328.5, 0F, 0F),
				/* Gweyr 1 */ new Location(InfernalRealms.MAIN_WORLD, -56.5, 57.5, -91.5, 138.5F, 0F),
				/* Selgauth Peaks 1 */ new Location(InfernalRealms.MAIN_WORLD, 151.5, 101.5, 0.5, 45F, 6F),
				};
	// @formatter:on

	public static final ApplicableRegionSet[] RESPAWN_REGIONS = new ApplicableRegionSet[RESPAWN_LOCATIONS.length];

	public static ProtectedRegion getHighestPriorityRegion(ApplicableRegionSet regions) {
		if (regions.size() == 0) {
			return null;
		}
		ProtectedRegion region = null;
		for (ProtectedRegion r : regions) {
			if (r == null) {
				continue;
			}
			if (region == null || region.getPriority() < r.getPriority()) {
				region = r;
			}
		}
		return region;
	}

	public static ProtectedRegion getHighestParentOfRegion(ProtectedRegion region) {
		for (ProtectedRegion pRegion = region; pRegion != null; region = pRegion, pRegion = pRegion.getParent())
			;
		return region;
	}

	public static Location getRespawnLocation(ProtectedRegion region) {
		if (region == null) {
			return RESPAWN_LOCATIONS[DEFAULT_SPAWN]; // Default location (Start Town)
		}
		region = getHighestParentOfRegion(region);
		for (int i = 0; i < RESPAWN_REGIONS.length; i++) {
			ApplicableRegionSet regionSet = RESPAWN_REGIONS[i];
			if (regionSet.getRegions().contains(region)) {
				return RESPAWN_LOCATIONS[i];
			}
		}
		return RESPAWN_LOCATIONS[DEFAULT_SPAWN]; // Default location (Start Town)
	}

	public static Location getRespawnLocation(Player player) {
		return getRespawnLocation(player.getLocation());
	}

	public static Location getRespawnLocation(Location location) {
		return getRespawnLocation(getHighestPriorityRegion(
				InfernalRealms.getWorldGuardPlugin().getRegionManager(location.getWorld()).getApplicableRegions(location)));
	}

	public static void prepareRespawnLocations() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Preparing respawn locations...");
		for (int i = 0; i < RegionManager.RESPAWN_LOCATIONS.length; i++) {
			Location l = RegionManager.RESPAWN_LOCATIONS[i];
			RESPAWN_REGIONS[i] = InfernalRealms.getWorldGuardPlugin().getRegionManager(l.getWorld()).getApplicableRegions(l);
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "" + l + " { " + RESPAWN_REGIONS[i].getRegions() + " }");
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Respawn locations complete.");
	}

}
