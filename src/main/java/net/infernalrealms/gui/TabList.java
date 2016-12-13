package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TabList implements Listener {

	private static HashMap<String, TabListType> currentTabList = new HashMap<>();

	public static void sendTab(Player player, ArrayList<String> lines) {
		//		PacketContainer pc = InfernalRealms.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		//		String text = "";
		//		for (String line : lines) {
		//			text += line + "\n";
		//		}
		//		// Filler
		//		text += "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		//		pc.getChatComponents().write(0, WrappedChatComponent.fromText(text))
		//		// 62 width by 24 height
		//				.write(1, WrappedChatComponent.fromText("--------------------------------------------------------------"));
		//		try {
		//			InfernalRealms.getProtocolManager().sendServerPacket(player, pc);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	public static void sendBlankTab(Player player) {
		sendTab(player, new ArrayList<String>(0));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		currentTabList.put(player.getName(), TabListType.BLANK);

		//		new BukkitRunnable() {
		//
		//			@Override
		//			public void run() {
		//				Packet[] packets = new Packet[Bukkit.getOnlinePlayers().length];
		//				for (int i = 0; i < Bukkit.getOnlinePlayers().length; i++) {
		//					//					if (!Bukkit.getOnlinePlayers()[i].hasMetadata("NPC")) {
		//					packets[i] = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER,
		//							new EntityPlayer[] { ((CraftPlayer) Bukkit.getOnlinePlayers()[i]).getHandle() }); // Create a packet that removes them from the player list
		//					//					}
		//				}
		//				PacketUtil.sendToOnline(packets); // Send them that packet
		//			}
		//		}.runTaskLater(InfernalRealms.getPlugin(), 80L);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		currentTabList.remove(player.getName());
		QuestTabList.removeAllQuestsFromHelper(player);
	}

	public static TabListType getTabListType(Player player) {
		if (!currentTabList.containsKey(player.getName())) {
			return TabListType.BLANK;
		}
		return currentTabList.get(player.getName());
	}

	public static void setTabListType(Player player, TabListType tabListType) {
		currentTabList.put(player.getName(), tabListType);
	}

}
