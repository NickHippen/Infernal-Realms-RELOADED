package net.infernalrealms.homesteads;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class HomesteadEventHandler implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPersonalHomesteadBlockPlace(BlockPlaceEvent event) {
		if (!HomesteadUtils.isInOwnHomestead(event.getPlayer())) {
			return;
		}
		if (!HomesteadUtils.isWithinBuildRange(event.getPlayer(), event.getBlock().getLocation())) {
			event.setBuild(false);
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onVisitingHomesteadBlockPlace(BlockPlaceEvent event) {
		if (!HomesteadUtils.isInVisitingHomestead(event.getPlayer())) {
			return;
		}
		event.setBuild(false);
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPersonalHomesteadBlockBreak(BlockBreakEvent event) {
		if (!HomesteadUtils.isInOwnHomestead(event.getPlayer())) {
			return;
		}
		if (!HomesteadUtils.isWithinBuildRange(event.getPlayer(), event.getBlock().getLocation())) {
			event.setCancelled(true);
			return;
		}
		event.setCancelled(false);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onVisitingHomesteadBlockBreak(BlockBreakEvent event) {
		if (!HomesteadUtils.isInVisitingHomestead(event.getPlayer())) {
			return;
		}
		event.setCancelled(true);
	}

}
