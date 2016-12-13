package net.infernalrealms.mount;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftHorse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.player.PlayerData;
import net.minecraft.server.v1_9_R1.AttributeInstance;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.GenericAttributes;

public class HungerDecay extends BukkitRunnable {

	private Player player;

	private CraftHorse horse;

	public HungerDecay(Player player, CraftHorse horse) {
		this.setCraftHorse(horse);
		this.setPlayer(player);
	}

	public void run() {
		if (getCraftHorse().isDead() || getPlayer().getVehicle() == null) {
			this.cancel();
			return;
		}
		PlayerData playerData = PlayerData.getData(getPlayer());
		AttributeInstance speedAttribute = ((EntityInsentient) (getCraftHorse()).getHandle())
				.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		if (playerData.getMountHunger() <= 0) {
			speedAttribute.setValue(0.01);
			getPlayer().sendMessage(ChatColor.RED + "Your horse is starving! It is hardly able to move.");
		} else {
			if (speedAttribute.getValue() == 0.01) {
				speedAttribute.setValue(MountManager.convertLevelToSpeed(playerData.getMountSpeed()));
			}
		}
		// Decay hunger
		playerData.modifyMountHunger(-0.5);
		MountManager.updateSaddle(getPlayer(), getCraftHorse());
	}

	public CraftHorse getCraftHorse() {
		return horse;
	}

	public void setCraftHorse(CraftHorse horse) {
		this.horse = horse;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
