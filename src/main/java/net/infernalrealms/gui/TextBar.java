package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class TextBar {

	private static HashMap<String, ArrayList<TextBar>> queue = new HashMap<>();

	private Player player;
	private int fadeIn;
	private int stay;
	private int fadeOut;
	private String title;
	private String subtitle;

	/**
	 * @param player The player to display to
	 * @param fadeIn Duration in ticks the message will take to fade in
	 * @param stay Duration in ticks the message will be displayed on the player's screen
	 * @param fadeOut Duration in ticks the message will take to fade out
	 * @param title The title (large text) of the TextBar
	 * @param subtitle The subtitle (smaller text below title) of the TextBar
	 */
	public TextBar(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
		this.player = player;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
		this.subtitle = subtitle;
	}

	public void sendTitle() {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
		connection.sendPacket(packetPlayOutTimes);

		if (subtitle != null) {
			IChatBaseComponent titleSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
			PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket(packetPlayOutSubTitle);
		}

		if (title != null) {
			IChatBaseComponent titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
			PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
			connection.sendPacket(packetPlayOutTitle);
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				queueNext(player);
			}

		}.runTaskLater(InfernalRealms.getPlugin(), fadeIn + stay + fadeOut);
	}

	/**
	 * Puts the TextBar into queue to be displayed to the player after all other TextBars have been displayed.
	 */
	public void queue() {
		addQueue(player, this);
	}

	public static void addQueue(Player player, TextBar textbar) {
		if (player == null || !player.isOnline()) {
			return;
		}
		if (!getQueue().containsKey(player.getName()))
			getQueue().put(player.getName(), new ArrayList<TextBar>());
		ArrayList<TextBar> currentQueue = getQueue().get(player.getName());
		currentQueue.add(textbar);
		getQueue().put(player.getName(), currentQueue);
		if (currentQueue.size() == 1) {
			try {
				textbar.sendTitle();
			} catch (Exception e) {}
		}
	}

	public static void queueNext(Player player) {
		if (getQueue().get(player.getName()) == null || getQueue().get(player.getName()).isEmpty())
			return;
		getQueue().get(player.getName()).remove(0);
		if (getQueue().get(player.getName()).isEmpty())
			return;
		getQueue().get(player.getName()).get(0).sendTitle();
	}

	public static HashMap<String, ArrayList<TextBar>> getQueue() {
		return queue;
	}

}
