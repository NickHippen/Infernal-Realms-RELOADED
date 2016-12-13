package net.infernalrealms.homesteads;

import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;

public class HomesteadCleaner extends BukkitRunnable {

	public HomesteadCleaner() {
		runTaskTimer(InfernalRealms.getPlugin(), 1200L, 1200L);
	}

	@Override
	public void run() {
		//		for (World world : Bukkit.getWorlds()) {
		//			if (!world.getName().startsWith("homesteads/")) {
		//				continue;
		//			}
		//			if (world.getPlayers().isEmpty()) {
		//				InfernalRealms.getPlugin().getLogger().log(Level.INFO, "Unloading homestead world: " + world.getName());
		//				Bukkit.getServer().unloadWorld(world, true);
		//			}
		//		}
		for (HomesteadInstance homestead : HomesteadInstance.HOMESTEAD_INSTANCES.values()) {
			if (homestead.getWorld() == null || !homestead.getWorld().getPlayers().isEmpty()) {
				continue;
			}
			homestead.unload();
		}
	}

}
