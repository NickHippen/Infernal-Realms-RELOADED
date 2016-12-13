package net.infernalrealms.gui;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.infernalrealms.general.InfernalRealms;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.PacketPlayOutSetSlot;

public class ItemMessageHandler {

	public static void displayMessage(Player player, String itemTitle) {
		EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
		org.bukkit.inventory.ItemStack bukkitItemStack = player.getInventory().getItem(0);
		if (bukkitItemStack == null) {
			return;
		}
		org.bukkit.inventory.ItemStack copy = new org.bukkit.inventory.ItemStack(bukkitItemStack.getType(), bukkitItemStack.getAmount(),
				bukkitItemStack.getData().getData());
		ItemMeta itemMeta = copy.getItemMeta();
		itemMeta.setDisplayName(itemTitle);
		copy.setItemMeta(itemMeta);
		ItemStack itemStack = CraftItemStack.asNMSCopy(copy);
		PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(0, 36, itemStack);
		nmsPlayer.playerConnection.sendPacket(packet);
		applyFreshDelayedInventoryUpdate(player);
	}

	private static void applyFreshDelayedInventoryUpdate(Player player) {
		if (player.hasMetadata("ItemMessageHandler")) {
			((BukkitTask) player.getMetadata("ItemMessageHandler").get(0).value()).cancel();
			player.removeMetadata("ItemMessageHandler", InfernalRealms.getPlugin());
		}
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				player.updateInventory();
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 40L);
		player.setMetadata("ItemMessageHandler", new FixedMetadataValue(InfernalRealms.getPlugin(), task));
	}

}
