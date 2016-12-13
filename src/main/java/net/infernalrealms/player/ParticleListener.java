package net.infernalrealms.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ParticleListener implements Listener {

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player) || event.isCancelled()) {
			return;
		}
		Player player = (Player) event.getDamager();
		PlayerData playerData = PlayerData.getData(player);
		playerData.getAttackParticles().playAttackEffect(event.getEntity());
	}

	@EventHandler
	public void onEntityTakeDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player) || event.isCancelled()) {
			return;
		}
		Player player = (Player) event.getEntity();
		PlayerData playerData = PlayerData.getData(player);
		playerData.getDamagedParticles().playDamagedEffect(player);
	}

}
