package net.infernalrealms.homesteads;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HomesteadUtils {

	public static final int CENTER_X = -3;
	public static final int CENTER_Z = -2;

	private HomesteadUtils() {}

	public static boolean isInHomestead(Player player) {
		return player.getWorld().getName().startsWith("homesteads/");
	}

	public static boolean isInOwnHomestead(Player player) {
		return player.getWorld().getName().equals("homesteads/Homestead_" + player.getUniqueId().toString());
	}

	public static boolean isInVisitingHomestead(Player player) {
		return isInHomestead(player) && !isInOwnHomestead(player);
	}

	public static boolean isWithinBuildRange(Player player, Location location) {
		if (!isInOwnHomestead(player)) {
			return false;
		}
		HomesteadInstance homestead = HomesteadInstance.HOMESTEAD_INSTANCES.get(player.getUniqueId());
		int buildDistance = homestead.getTier().getBuildDistanceFromCenter();
		int blockY = location.getBlockY();
		if (blockY < 2 || blockY > 169) {
			return false;
		}
		int blockX = location.getBlockX();
		if (Math.abs(blockX - CENTER_X) > buildDistance) {
			return false;
		}
		int blockZ = player.getLocation().getBlockZ();
		if (Math.abs(blockZ - CENTER_Z) > buildDistance) {
			return false;
		}
		return true;
	}

}
