package net.infernalrealms.util;

import java.awt.Color;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.DataWatcher;
import net.minecraft.server.v1_9_R1.DataWatcherObject;
import net.minecraft.server.v1_9_R1.DataWatcherSerializer;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.PacketDataSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutBlockBreakAnimation;

public class EffectsUtil {

	public static void sendParticle(ParticleEffect particleEffect, Player player, Location location, float offsetX, float offsetY,
			float offsetZ, float speed, int count) {
		try {
			particleEffect.sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendColoredParticle(ParticleEffect particleEffect, Player player, Location location, Color color, int count) {
		try {
			particleEffect.sendColor(player, location, color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void sendBlockDustParticle(Material material, Player player, Location location, float offsetX, float offsetY,
			float offsetZ, float speed, int count) {
		try {
			ParticleEffect.BLOCK_DUST.sendBlockDust(player, location, material.getId(), offsetX, offsetY, offsetZ, speed, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendParticleToLocation(ParticleEffect particleEffect, Location location, float offsetX, float offsetY, float offsetZ,
			float speed, int count) { // TODO Apply distance restrictions?
		for (Player player : location.getWorld().getPlayers()) {
			if (!player.hasMetadata("NPC")) {
				sendParticle(particleEffect, player, location, offsetX, offsetY, offsetZ, speed, count);
			}
		}
	}

	/**
	 * Valid Colors: RED_DUST, REDSTONE, NOTE, SPELL_MOB, MOB_SPELL
	 * @param particleEffect
	 * @param location
	 * @param color
	 * @param count
	 */
	public static void sendColoredParticleToLocation(ParticleEffect particleEffect, Location location, Color color, int count) { // TODO Apply distance restrictions?
		for (Player player : location.getWorld().getPlayers()) {
			if (!player.hasMetadata("NPC")) {
				sendColoredParticle(particleEffect, player, location, color, count);
			}
		}
	}

	public static void sendBlockDustParticleToLocation(Material material, Location location, float offsetX, float offsetY, float offsetZ,
			float speed, int count) { // TODO Apply distance restrictions?
		for (Player player : location.getWorld().getPlayers()) {
			if (!player.hasMetadata("NPC")) {
				sendBlockDustParticle(material, player, location, offsetX, offsetY, offsetZ, speed, count);
			}
		}
	}

	/**
	 * Sends the block cracking effect to a player
	 * @param block
	 * @param data an int from 1 to 9 that increases the cracking size
	 */
	public static void sendBreakPacket(Player player, Block block, int data) {
		if (data < 1) {
			data = 1;
		} else if (data > 9) {
			data = 9;
		}
		BlockPosition bp = new BlockPosition(block.getX(), block.getY(), block.getZ());
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, bp, data);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	/**
	 * Sends the block cracking effect to an area
	 * @param block
	 * @param data an int from 1 to 9 that increases the cracking size
	 */
	public static void sendBreakPacketToLocation(Block block, int data) {
		if (data < 1) {
			data = 1;
		} else if (data > 9) {
			data = 9;
		}
		BlockPosition bp = new BlockPosition(block.getX(), block.getY(), block.getZ());
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, bp, data);
		for (Player player : block.getWorld().getPlayers()) {
			player.sendMessage("" + data);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static void playItemSpray(Material m, Location location, int num, float force) {
		if (m == null)
			return;

		// Spawn Items
		Random rand = new Random();
		Location loc = location.clone().add(0, 1, 0);
		final Item[] items = new Item[num];
		for (int i = 0; i < num; i++) {
			items[i] = loc.getWorld().dropItem(loc, new ItemStack(m));
			items[i].setVelocity(
					new Vector((rand.nextDouble() - .5) * force, (rand.nextDouble() - .5) * force, (rand.nextDouble() - .5) * force));
			items[i].setPickupDelay(20);
		}

		// Delete Items
		Bukkit.getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				for (int i = 0; i < items.length; i++) {
					items[i].remove();
				}
			}
		}, 10L);
	}

	public static void mountItemToEntity(Material m, Entity mount, int duration) {
		// Spawn Item
		Item item = mount.getWorld().dropItem(mount.getLocation(), new ItemStack(m));
		item.setPickupDelay(duration * 2);
		mount.setPassenger(item);

		// Delete Item
		Bukkit.getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				item.remove();
			}
		}, duration);
	}

	public static void addPotionGraphicalEffect(LivingEntity entity, int color, long duration) {
		//		final EntityLiving el = ((CraftLivingEntity) entity).getHandle();
		//		final DataWatcher dw = el.getDataWatcher();
		//		dw.watch(7, Integer.valueOf(color)); // Index of color is 7
		//
		//		Bukkit.getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
		//			public void run() {
		//				dw.watch(7, 0);
		//			}
		//		}, duration);
	}

}
