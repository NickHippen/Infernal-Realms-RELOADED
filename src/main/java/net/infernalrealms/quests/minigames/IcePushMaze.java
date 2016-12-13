package net.infernalrealms.quests.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.GeneralUtil.FlowAction;

public class IcePushMaze {

	public static class PushListener implements Listener {

		@EventHandler
		public void onBlockRightClick(PlayerInteractEvent event) {
			if (Boolean.TRUE) { // TODO Temp
				return;
			}

			if (event.getClickedBlock() == null || !event.getClickedBlock().getType().equals(Material.REDSTONE_BLOCK)) {
				return;
			}
			if (event.getBlockFace() == BlockFace.UP || event.getBlockFace() == BlockFace.DOWN) {
				return;
			}
			// Check that block is on ice
			Block below = event.getClickedBlock().getRelative(0, -1, 0);
			if (below == null || !below.getType().equals(Material.ICE)) {
				return;
			}
			Vector direction = new Vector(event.getBlockFace().getModX(), 0, event.getBlockFace().getModZ());
			Location finalLoc = event.getClickedBlock().getLocation();
			GeneralUtil.delayedLoop(-1, 0L, 10L, new FlowAction() {
				Location newLoc = finalLoc;
				Location oldLoc;
				Block below2;

				// TODO Add metadata to determine if it is moving (should stop duping)
				@Override
				public boolean run() {
					newLoc = finalLoc;
					oldLoc = new Location(newLoc.getWorld(), newLoc.getX(), newLoc.getY(), newLoc.getZ());
					newLoc = newLoc.subtract(direction);
					// Check if new location is on ice
					below2 = newLoc.getBlock().getRelative(0, -1, 0);
					if (below2 == null || !below2.getType().equals(Material.ICE)
							|| newLoc.getBlock() != null && newLoc.getBlock().getType() != Material.AIR) {
						if (below2 != null && below2.getType().equals(Material.GOLD_BLOCK)) {
							Bukkit.broadcastMessage("WIN!");
							oldLoc.getBlock().setType(Material.AIR);
							newLoc.getBlock().setType(Material.REDSTONE_BLOCK);
							return false;
						}
						newLoc.add(direction);
						oldLoc.getBlock().setType(Material.AIR);
						newLoc.getBlock().setType(Material.REDSTONE_BLOCK);
						return false;
					}
					oldLoc.getBlock().setType(Material.AIR);
					newLoc.getBlock().setType(Material.REDSTONE_BLOCK);
					return true;
				}
			});
			//			while (true) {
			//				newLoc = newLoc.subtract(direction);
			//				// Check if new location is on ice
			//				below = newLoc.getBlock().getRelative(0, -1, 0);
			//				if (below == null || !below.getType().equals(Material.ICE)
			//						|| newLoc.getBlock() != null && newLoc.getBlock().getType() != Material.AIR) {
			//					if (below != null && below.getType().equals(Material.GOLD_BLOCK)) {
			//						Bukkit.broadcastMessage("WIN!");
			//						break;
			//					}
			//					newLoc.add(direction);
			//					break;
			//				}
			//			}

			event.getClickedBlock().setType(Material.AIR);
			finalLoc.getBlock().setType(Material.REDSTONE_BLOCK);
		}

	}

}
