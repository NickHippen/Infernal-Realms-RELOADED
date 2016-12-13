package net.infernalrealms.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.Packet;

public class PacketUtil {

	private PacketUtil() {
	}

	public static void sendToOnline(Packet... packets) {
		org.apache.commons.lang.Validate.notNull(packets, "packets cannot be null");
		for (Player player : Bukkit.getOnlinePlayers()) {
			if ((player != null) && (player.isOnline())) {
				for (Packet packet : packets)
					sendPacket(player, packet);
			}
		}
	}

	public static void sendPacket(Player player, Packet packet) {
		if (packet == null)
			return;
		((EntityPlayer) GeneralUtil.getHandle(player)).playerConnection.sendPacket(packet);
	}

}
