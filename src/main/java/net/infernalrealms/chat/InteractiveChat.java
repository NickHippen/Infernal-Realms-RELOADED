package net.infernalrealms.chat;

import java.util.List;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_9_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R1.ChatClickable;
import net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction;
import net.minecraft.server.v1_9_R1.ChatHoverable;
import net.minecraft.server.v1_9_R1.ChatHoverable.EnumHoverAction;
import net.minecraft.server.v1_9_R1.ChatMessage;
import net.minecraft.server.v1_9_R1.ChatModifier;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

public class InteractiveChat {

	private String text;

	private IChatBaseComponent chatBase;

	public InteractiveChat(String text) {
		this.chatBase = new ChatMessage(text);
		this.chatBase.setChatModifier(new ChatModifier());
	}

	public void applyClickLink(String link) {
		chatBase.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.OPEN_URL, link));
	}

	public void applyClickCommand(String command) {
		chatBase.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.RUN_COMMAND, command));
	}

	public void applyHoverMessage(String message) {
		this.chatBase.getChatModifier().setChatHoverable(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(message)));
	}

	public void applyHoverItem(ItemStack item) {
		chatBase.getChatModifier().setChatHoverable(new ChatHoverable(EnumHoverAction.SHOW_ITEM,
				new ChatMessage(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()).toString())));
	}

	public void sendToPlayer(Player player) {
		EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
		for (IChatBaseComponent component : CraftChatMessage.fromString(CraftChatMessage.fromComponent(chatBase))) {
			nmsPlayer.sendMessage(component.setChatModifier(chatBase.getChatModifier()));
		}
	}

	public void broadcastToPlayers(Player[] players) {
		for (Player player : players) {
			sendToPlayer(player);
		}
	}

	public void broadcastToPlayers(List<Player> players) {
		broadcastToPlayers(players.toArray(new Player[players.size()]));
	}

	public String getText() {
		return text;
	}

}
