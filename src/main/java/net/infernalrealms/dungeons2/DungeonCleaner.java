package net.infernalrealms.dungeons2;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;

public class DungeonCleaner extends BukkitRunnable {

	public DungeonCleaner() {
		runTaskTimer(InfernalRealms.getPlugin(), 600L, 1200L);
	}

	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			if (!world.getName().startsWith("dungeon_instance/")) {
				continue;
			}
			if (world.getPlayers().isEmpty()) {
				InfernalRealms.getPlugin().getLogger().log(Level.INFO, "Unloading dungeon world: " + world.getName());
				Bukkit.getServer().unloadWorld(world, true);
			}
		}
	}

}
