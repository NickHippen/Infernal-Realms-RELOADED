package net.infernalrealms.player;

import net.infernalrealms.general.InfernalRealms;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleTimer extends BukkitRunnable {

	private PlayerData playerData;
	private Location prevLocation;
	private boolean playIdle = false;

	public ParticleTimer(PlayerData playerData) {
		this.playerData = playerData;
		this.prevLocation = playerData.getPlayer().getLocation();
		runTaskTimer(InfernalRealms.getPlugin(), 10L, 10L);
	}

	@Override
	public void run() {
		if (!playerData.getPlayer().isOnline()) {
			cancel();
			return;
		}
		if (prevLocation.getBlockX() == playerData.getPlayer().getLocation().getBlockX()
				&& prevLocation.getBlockY() == playerData.getPlayer().getLocation().getBlockY()
				&& prevLocation.getBlockZ() == playerData.getPlayer().getLocation().getBlockZ()) {
			// Idle
			if (!playIdle) {
				playIdle = !playIdle;
				return;
			}
			playerData.getIdleParticles().playIdleEffect(playerData.getPlayer());
			playIdle = !playIdle;
		} else {
			// Moving
			playerData.getFollowParticles().playFollowEffect(playerData.getPlayer());
			this.prevLocation = playerData.getPlayer().getLocation();
		}
	}
}
